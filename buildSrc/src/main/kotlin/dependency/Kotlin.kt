/*
 *  Copyright (c) 2000-2023 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

package dependency

/**
 * Kotlin language.
 *
 * @see <a href="https://github.com/JetBrains/kotlin/releases">Releases</a>
 */
@Suppress("ConstPropertyName")
object Kotlin {

    /**
     * The language version.
     *
     * When changing the version, please, update the value in
     * `buildSrc/build.gradle.kts` too. So that they are synced.
     */
    const val version = "1.9.20"

    /**
     * Provides introspection of the program structure at runtime.
     */
    const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"

    object Coroutines {
        private const val version = "1.7.3"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val swing = "org.jetbrains.kotlinx:kotlinx-coroutines-swing:$version"
    }
}
