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

package gradle.web

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.util.PatternSet
import org.gradle.kotlin.dsl.TaskContainerScope
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import java.io.File

/**
 * Builds the given web project using `npm run build` command.
 *
 * Please make sure project's `package.json` has such a script declared.
 *
 * For example:
 *
 * ```
 * "scripts": {
 *     "build": "vite build"
 * }
 * ```
 */
abstract class BuildWeb : NpmExec<BuildWeb>(BuildWeb::class) {

    /**
     * Returns [webProjectDir] excluding the directories, which should not
     * participate in up-to-date checks.
     *
     * The excluded directories:
     *
     * - `node_modules`.
     * - [outputDir].
     */
    @get:InputFiles
    val webProjectFiles: FileTree
        get() {
            val outputDirName = outputDir.get().asFile.name
            val exclusions = PatternSet().apply {
                exclude(
                    "node_modules",
                    outputDirName
                )
            }
            return webProjectDir.get().asFileTree
                .matching(exclusions)
        }

    /**
     * Build output.
     */
    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @TaskAction
    override fun exec() {
        commandLine("run", "build", "--", "--outDir", outputDir.get())
        super.exec()
    }
}

/**
 * Registers [InstallWebDependencies] and [BuildWeb] tasks
 * in this [TaskContainerScope].
 */
fun TaskContainerScope.buildWebProject(
    webProjectDir: File,
    outputDir: File = webProjectDir.resolve("dist")
): TaskProvider<BuildWeb> {
    val installWebDependencies by registering(InstallWebDependencies::class) {
        this.webProjectDir = webProjectDir
    }
    val buildWeb by registering(BuildWeb::class) {
        this.webProjectDir = webProjectDir
        this.outputDir = outputDir
        dependsOn(installWebDependencies)
    }
    return buildWeb
}
