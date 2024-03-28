/*
 *  Copyright (c) 2000-2023 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

package gradle.task

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Enables Compose compiler reports that contain results about
 * the stability inference.
 *
 * These reports describe each composable function declared in the given
 * module, including the stability of their parameters, and whether they
 * are restartable or skippable.
 *
 * @see <a href="https://bit.ly/46Y5TTO">Compose compiler reports</a>
 */
fun KotlinCompile.enableComposeReports() {
    val buildDir = project.layout.buildDirectory.asFile.get()
    val reportsDir = buildDir.resolve("reports").absolutePath
    compilerOptions {
        freeCompilerArgs.addAll(
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$reportsDir/compose",
        )
    }
}
