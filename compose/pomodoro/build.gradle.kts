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

import gradle.compose.allowBackgroundExecution
import gradle.jxBrowserPackagingVersion
import gradle.web.BuildWeb
import gradle.web.buildWebProject
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.compose")
    `compose-app`
}

dependencies {
    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "com.teamdev.jxbrowser.examples.pomodoro.MainKt"
        nativeDistributions {
            packageName = "Pomodoro"
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageVersion = jxBrowserPackagingVersion()
            macOS {
                iconFile = iconForMacOS()
                infoPlist {
                    allowBackgroundExecution()
                }
            }
        }
    }
}

tasks {
    val webgl = projectDir.resolve("webgl")
    val buildWebgl = buildWebProject(webgl)

    val pomodoroResources = projectDir.resolve("src/main/resources/webgl")
    val copyWebglOutput = copyWebglOutput(buildWebgl, pomodoroResources)

    val clean by existing {
        doLast {
            delete(buildWebgl)
            delete(copyWebglOutput)
        }
    }
}

/**
 * Registers a tak to copy [buildWebgl] output to the given [destination].
 */
fun TaskContainerScope.copyWebglOutput(
    buildWebgl: TaskProvider<BuildWeb>,
    destination: File
): TaskProvider<Copy> {
    val copyWebglOutput by registering(Copy::class) {
        from(buildWebgl)
        into(destination)
    }
    val processResources by existing {
        dependsOn(copyWebglOutput)
    }
    return copyWebglOutput
}

/**
 * Return a file with `.icns` Pomodoro icon.
 *
 * This icon has been generated with
 * [Copilot | Designer](https://www.bing.com/images/create).
 */
fun iconForMacOS() = File("distribution-resources/1024x1024.icns")
