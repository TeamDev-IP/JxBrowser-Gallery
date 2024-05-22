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

