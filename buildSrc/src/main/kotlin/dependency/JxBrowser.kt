/*
 *  Copyright (c) 2000-2023 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

package dependency

/**
 * Library for integration of Chromium into Java/Kotlin apps.
 *
 * @see <a href="https://jxbrowser-support.teamdev.com/release-notes/">Releases</a>
 */
@Suppress("ConstPropertyName")
object JxBrowser {

    const val version = "8.0.0-eap.7-test"

    /**
     * JxBrowser Gradle plugin.
     *
     * When changing the version, please, update the value in
     * `buildSrc/build.gradle.kts` too. So that they are synced.
     */
    object GradlePlugin {
        const val version = "1.0.2"
        const val id = "com.teamdev.jxbrowser"
    }
}
