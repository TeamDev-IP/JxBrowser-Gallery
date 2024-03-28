/*
 *  Copyright (c) 2000-2023 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

package dependency

/**
 * Testing framework for Kotlin.
 *
 * @see <a href="https://github.com/kotest/kotest/releases">Releases</a>
 */
@Suppress("ConstPropertyName")
object Kotest {
    private const val version = "5.7.2"
    const val assertions = "io.kotest:kotest-assertions-core:$version"
}
