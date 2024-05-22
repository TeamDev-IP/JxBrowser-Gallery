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

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

/**
 * Runs the given web project using `npm start` command.
 *
 * Please make sure project's `package.json` has such a script declared.
 * Although, for "start" script there is a default value (`node server.js`),
 * it is recommended to have the used scripts explicitly declared.
 *
 * For example:
 *
 * ```
 * "scripts": {
 *     "start": "node server.js"
 * }
 * ```
 *
 * The task allows to configure the used [port]. It can be changed by a Gradle
 * script or passed from CLI.
 *
 * An example of the task invocation with the specified port:
 *
 * ```
 * ./gradlew :compose:screen-sharing:server:run --port=4000
 * ```
 */
abstract class RunWeb : NpmExec<RunWeb>(RunWeb::class, isMuted = false) {

    @Input
    @Option(description = "Port to be taken by the running app.")
    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    var port: Integer = Integer.valueOf(DEFAULT_NODE_PORT) as Integer

    @TaskAction
    override fun exec() {
        commandLine("start", "--", "--port", port)
        super.exec()
    }
}

/**
 * Node's default port.
 */
private const val DEFAULT_NODE_PORT = 3000
