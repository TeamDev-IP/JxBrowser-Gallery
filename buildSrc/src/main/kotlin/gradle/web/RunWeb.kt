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
    // `@Option` requires exactly a boxed `Integer`.
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
const val DEFAULT_NODE_PORT = 3000
