/*
 *  Copyright (c) 2000-2024 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

package gradle.web

import org.gradle.api.file.Directory
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

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
