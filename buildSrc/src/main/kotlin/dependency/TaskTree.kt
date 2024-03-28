/*
 *  Copyright (c) 2000-2024 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

package dependency

/**
 * Prints a task dependency tree to the console.
 *
 * The simplest usage is the following:
 *
 * ```
 * ./gradlew compileKotlin taskTree --with-inputs --with-outputs
 * ```
 *
 * More complicated usage examples can be found [here](https://bit.ly/3I4MeYp).
 */
@Suppress("ConstPropertyName")
object TaskTree {

    /**
     * When changing the version, please, update the value in
     * `buildSrc/build.gradle.kts` too. So that they are synced.
     *
     * @see <a href="https://github.com/dorongold/gradle-task-tree/releases">Releases</a>
     */
    const val version = "2.1.1"
    const val id = "com.dorongold.task-tree"
}
