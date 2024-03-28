/*
 *  Copyright (c) 2000-2024 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
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
abstract class NpmExec<T : NpmExec<T>>(taskType: KClass<T>) :
    SilentExec<T>(taskType) {

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
