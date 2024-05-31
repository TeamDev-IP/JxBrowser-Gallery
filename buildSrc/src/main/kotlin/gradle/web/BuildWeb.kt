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
