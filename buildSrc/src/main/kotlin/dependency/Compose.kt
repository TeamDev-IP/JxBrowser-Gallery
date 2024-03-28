/*
 *  Copyright (c) 2000-2023 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

package dependency

/**
 * A declarative framework for sharing UIs across multiple platforms.
 *
 * @see <a href="https://github.com/JetBrains/compose-multiplatform/releases">Releases</a>
 */
@Suppress("ConstPropertyName")
object Compose {

    /**
     * Compose Gradle plugin.
     *
     * When changing the version, please, update the value in
     * `buildSrc/build.gradle.kts` too. So that they are synced.
     */
    object GradlePlugin {
        const val version = "1.6.1"
        const val id = "org.jetbrains.compose"
    }
}
