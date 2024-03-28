/*
 *  Copyright (c) 2000-2023 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

package dependency

/**
 * A static code analyzer for Kotlin.
 *
 * @see <a href="https://github.com/detekt/detekt/releases">Releases</a>
 */
@Suppress("ConstPropertyName")
object Detekt {

    /**
     * Detekt version.
     *
     * When changing the version, please, update the value in
     * `buildSrc/build.gradle.kts` too. So that they are synced.
     */
    private const val version = "1.23.3"

    object GradlePlugin {
        const val version = Detekt.version
        const val id = "io.gitlab.arturbosch.detekt"
    }
}
