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

package gradle.compose

import org.jetbrains.compose.desktop.application.dsl.InfoPlistSettings

/**
 * This file contains the configuration of Information Property List.
 *
 * `Info.plist` is a metadata file for executables in macOS and iOS.
 *
 * It is configured in `nativeDistributions` section of the Compose extension:
 *
 * ```
 * nativeDistributions {
 *     macOS {
 *         infoPlist {
 *             // ...
 *         }
 *     }
 * }
 * ```
 *
 * @see <a href="https://apple.co/49B4hB6">Apple Developer Docs</a>
 */
@Suppress("unused")
private const val ABOUT = ""

/**
 * Configures this [InfoPlistSettings] to allow the app to run in
 * the background without appearing in the Dock.
 */
fun InfoPlistSettings.allowBackgroundExecution() {
    extraKeysRawXml = """
                        <key>LSUIElement</key>
                        <string>true</string>
                    """.trimIndent()
}
