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
