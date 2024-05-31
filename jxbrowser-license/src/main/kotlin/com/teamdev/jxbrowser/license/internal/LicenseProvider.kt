/*
 *  Copyright (c) 2024 TeamDev
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
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
