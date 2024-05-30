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
