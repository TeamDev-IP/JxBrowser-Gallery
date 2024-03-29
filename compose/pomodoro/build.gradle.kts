/*
 *  Copyright 2024, TeamDev. All rights reserved.
 *
 *  Redistribution and use in source and/or binary forms, with or without
 *  modification, must retain the above copyright notice and the following
 *  disclaimer.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 *  A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 *  OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 *  OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import gradle.compose.allowBackgroundExecution
import gradle.web.BuildWeb
import gradle.web.buildWebProject

plugins {
    `compose-app`
}

compose.desktop {
    application {
        mainClass = "com.teamdev.jxbrowser.examples.pomodoro.MainKt"
        nativeDistributions {
            packageName = "Pomodoro"
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
