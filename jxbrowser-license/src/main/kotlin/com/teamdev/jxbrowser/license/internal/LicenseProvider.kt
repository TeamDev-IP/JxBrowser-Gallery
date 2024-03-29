/*
 *  Copyright 2024, TeamDev. All rights reserved.
 *
 *  Redistribution and use in source and/or binary forms, with or without
 *  modification, must retain the above copyright notice and the following
 *  disclaimer.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 *  A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 *  OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 *  OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.teamdev.jxbrowser.license.internal

import com.teamdev.jxbrowser.license.JxBrowserLicense
import com.teamdev.jxbrowser.engine.EngineOptions
import com.teamdev.jxbrowser.dsl.engine.OptionsDsl

/**
 * Provides a license key for JxBrowser.
 *
 * Fetches a key from the app's resources and [exposes][key] it as
 * a plain string. It is a user responsibility to put a key into [LICENSE_FILE].
 *
 * Please note, this provider is for internal usage within
 * the repository modules.
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
     *
     * Pass the property value to [EngineOptions.Builder.licenseKey] (Java API).
     */
    val key: String by lazy { readLicenseFromResources() }

    /**
     * JxBrowser license.
     *
     * Pass the property value to [OptionsDsl.license] (Kotlin API).
     */
    val license: JxBrowserLicense by lazy { JxBrowserLicense(key) }

    @Suppress("MaxLineLength") // Contains a file path.
    private fun readLicenseFromResources(): String {
        val file = javaClass.getResource(LICENSE_FILE)
                ?: error("JxBrowser license key was not found in the app's resources. " +
                        "The expected location: `jxbrowser-license/src/main/resources$LICENSE_FILE`.")
        val content = file.readText()
        return content
    }
}
