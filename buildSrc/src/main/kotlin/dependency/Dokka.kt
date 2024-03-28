/*
 *  Copyright (c) 2000-2024 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

package dependency

/**
 * API documentation engine for Kotlin.
 */
@Suppress("ConstPropertyName")
object Dokka {

    /**
     * When changing the version, please, update the value in
     * `buildSrc/build.gradle.kts` too. So that they are synced.
     *
     * @see <a href="https://github.com/Kotlin/dokka/releases">Releases</a>
     */
    const val version = "1.9.20"
    const val id = "org.jetbrains.dokka"
}
