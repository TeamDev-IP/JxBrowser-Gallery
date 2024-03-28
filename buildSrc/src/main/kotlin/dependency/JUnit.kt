/*
 *  Copyright (c) 2000-2023 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

package dependency

/**
 * Testing framework for Java/Kotlin.
 *
 * @see <a href="https://github.com/junit-team/junit5/releases">Releases</a>
 */
@Suppress("ConstPropertyName")
object JUnit {
    private const val version = "5.10.1"
    const val lib = "org.junit.jupiter:junit-jupiter:$version"

    /**
     * Allows launching JUnit framework on the JVM.
     *
     * Starting from Gradle 9, an explicit dependency on this artifact
     * is required.
     *
     * Please note, it has its own group, which is different from the one
     * in [lib]. And the version is not declared intentionally. It will be
     * resolved automatically.
     *
     * @see <a href="https://bit.ly/47yHl51">Upgrading from 8.1 and earlier</a>,
     *      <a href="https://bit.ly/3T6Pw42">JUnit 5 overview</a>.
     */
    const val launcher = "org.junit.platform:junit-platform-launcher"
}
