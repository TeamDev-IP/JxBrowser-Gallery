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

import gradle.web.BuildCustom
import gradle.web.BuildWeb
import gradle.web.buildCustom
import gradle.web.buildWebProject

tasks {
    val buildWebProject = buildWebProject(projectDir)
    val buildServerJs = buildCustom("buildServerJs", "build-server-js", projectDir)

    val serverResources = projectDir.resolve("../server/widgets")
    val copyServerJs = copyServerJs(buildWebProject, buildServerJs, serverResources)

    val build by registering {
        dependsOn(buildWebProject)
        dependsOn(buildServerJs)
        dependsOn(copyServerJs)
    }
}

/**
 * Registers a task to copy [buildServerJs] output to the given [destination].
 */
fun TaskContainerScope.copyServerJs(
    buildWebProject: TaskProvider<BuildWeb>,
    buildServerJs: TaskProvider<BuildCustom>,
    destination: File
): TaskProvider<Copy> {
    val copyServerJs by registering(Copy::class) {
        from(buildServerJs)
        into(destination)
        include("charts.js")
        dependsOn(buildWebProject)
    }
    return copyServerJs
}
