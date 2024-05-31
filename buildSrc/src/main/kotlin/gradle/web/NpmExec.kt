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

import gradle.SilentExec
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import kotlin.reflect.KClass

/**
 * Specifies NPM executable and working directory.
 */
abstract class NpmExec<T : NpmExec<T>>(
    taskType: KClass<T>,
    isMuted: Boolean = true
) : SilentExec<T>(taskType, isMuted) {

    private val npm = if (isWindows()) "npm.cmd" else "npm"

    /**
     * The web project to execute NPM commands for.
     */
    @get:Internal // Let inheritors declare input on their own.
    abstract val webProjectDir: DirectoryProperty

    @TaskAction
    override fun exec() {
        workingDir(webProjectDir)
        commandLine(npm, *commandLine.toTypedArray())
        super.exec()
    }

    private fun isWindows(): Boolean = Os.isFamily(Os.FAMILY_WINDOWS)
}
