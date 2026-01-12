/*
 *  Copyright 2026, TeamDev
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

package gradle.web

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileTree
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.util.PatternSet
import org.gradle.kotlin.dsl.TaskContainerScope
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.register
import java.io.File

/**
 * Executes the custom build via `npm run *build-custom*` command.
 *
 * Please make sure project's `package.json` has such a script declared.
 *
 * For example:
 *
 * ```
 * "scripts": {
 *     "build-custom": "vite build --config vite.custom.config.js"
 * }
 * ```
 */
abstract class BuildCustom : NpmExec<BuildCustom>(BuildCustom::class) {

    /**
     * The build command to execute.
     */
    @get:Input
    abstract val command: Property<String>

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
                    "dist",
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
        commandLine("run", command.get(), "--", "--outDir", outputDir.get())
        super.exec()
    }
}

/**
 * Registers [InstallWebDependencies] and [BuildCustom] tasks
 * in this [TaskContainerScope].
 */
fun TaskContainerScope.buildCustom(
    taskName: String,
    command: String,
    webProjectDir: File,
    outputDir: File = webProjectDir.resolve("dist")
): TaskProvider<BuildCustom> {
    val installWebDependencies by getting(InstallWebDependencies::class) {
        this.webProjectDir = webProjectDir
    }
    val buildCustom = register(taskName, BuildCustom::class) {
        this.webProjectDir = webProjectDir
        this.outputDir = outputDir
        this.command = command
        dependsOn(installWebDependencies)
    }
    return buildCustom
}
