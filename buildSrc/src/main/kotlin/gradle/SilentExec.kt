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

package gradle

import org.gradle.api.tasks.AbstractExecTask
import org.gradle.api.tasks.TaskAction
import java.io.ByteArrayOutputStream
import kotlin.reflect.KClass

/**
 * Decorator for the standard `Exec` task, which can hide both error
 * and standard output streams.
 *
 * If [isMuted] is `true`, then both error and standard streams are hidden
 * as long as the execution is successful. If the task fails to execute
 * the given command, then the content of both streams is printed,
 * and the exception is thrown.
 */
abstract class SilentExec<T : SilentExec<T>>(
    taskType: KClass<T>,
    private val isMuted: Boolean = false
) : AbstractExecTask<T>(taskType.java) {

    private val allOutput = ByteArrayOutputStream()

    init {
        if (isMuted) {
            standardOutput = allOutput
            errorOutput = allOutput

            // To be able to print streams' content before throwing.
            isIgnoreExitValue = true
        }
    }

    @TaskAction
    override fun exec() {
        super.exec()
        if (isMuted && executionFailed()) {
            println("$allOutput")
            throw IllegalStateException("The failed command: `$commandLine`.")
        }
    }

    private fun executionFailed() = executionResult.get().exitValue != 0
}
