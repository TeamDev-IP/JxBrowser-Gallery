/*
 *  Copyright (c) 2000-2024 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

package com.teamdev.jxbrowser.license.internal

/**
 * Provides a license key for JxBrowser.
 *
 * Fetches a key from the app's resources and [exposes][key] it as
 * a plain string. It is a user responsibility to put a key into [LICENSE_FILE].
 *
 * Please note, this provider is for internal usage within
 * the repository modules. Some tests and example modules need a license.
 */
object LicenseProvider {

    /**
     * Path to a file in resources containing a license key for JxBrowser.
     *
     * This file is expected to contain a single line with a key.
     */
    private const val LICENSE_FILE = "/jxbrowser-license"

    /**
     * JxBrowser license key.
     */
    val key: String by lazy { readLicenseFromResources() }

    private fun readLicenseFromResources(): String {
        val file = javaClass.getResource(LICENSE_FILE)
                ?: error("JxBrowser license key was not found in the app's resources. " +
                        "The expected location: `jxbrowser-license/src/main/resources$LICENSE_FILE`.")
        val content = file.readText()
        return content
    }
}
