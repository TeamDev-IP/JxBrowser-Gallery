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

import org.gradle.api.file.Directory
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.TaskContainerScope
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.register
import java.io.File

/**
 * Installs web project dependencies using `npm install` command.
 */
abstract class InstallWebDependencies :
    NpmExec<InstallWebDependencies>(InstallWebDependencies::class) {

    /**
     * Configuration file, containing web dependencies to install.
     */
    @get:InputFile
    val packageJson: Provider<RegularFile>
        get() = webProjectDir.file("package.json")

    /**
     * Directory with installed dependencies.
     */
    @get:OutputDirectory
    val nodeModules: Provider<Directory>
        get() = webProjectDir.dir("node_modules")

    @TaskAction
    override fun exec() {
        commandLine("install")
        super.exec()
    }
}

/**
 * Registers [InstallWebDependencies] task in this [TaskContainerScope].
 */
fun TaskContainerScope.installWeDependencies(
    webProjectDir: File
): TaskProvider<InstallWebDependencies> =
    register<InstallWebDependencies>("installWeDependencies") {
        this.webProjectDir = webProjectDir
    }
