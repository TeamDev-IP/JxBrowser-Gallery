/*
 *  Copyright (c) 2000-2024 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

package gradle

import org.gradle.api.tasks.AbstractExecTask
import org.gradle.api.tasks.TaskAction
import java.io.ByteArrayOutputStream
import kotlin.reflect.KClass

/**
 * Decorator for the standard `Exec` task, which hides both error
 * and standard output streams as long as the execution is successful.
 *
 * If the task fails to execute the command, then the content of both
 * streams is printed and the exception is thrown.
 */
abstract class SilentExec<T : SilentExec<T>>(taskType: KClass<T>) :
    AbstractExecTask<T>(taskType.java) {

    private val allOutput = ByteArrayOutputStream()

    init {
        standardOutput = allOutput
        errorOutput = allOutput

        // To be able to print streams' content before throwing.
        isIgnoreExitValue = true
    }

    @TaskAction
    override fun exec() {
        super.exec()
        if (executionFailed()) {
            println("$allOutput")
            throw IllegalStateException("The failed command: `$commandLine`.")
        }
    }

    private fun executionFailed() = executionResult.get().exitValue != 0
}
