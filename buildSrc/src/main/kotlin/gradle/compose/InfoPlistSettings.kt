/*
 *  Copyright (c) 2000-2024 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

package gradle.compose

import org.jetbrains.compose.desktop.application.dsl.InfoPlistSettings

/**
 * This file contains the configuration of Information Property List.
 *
 * `Info.plist` is a metadata file for executables in macOS and IOS.
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
