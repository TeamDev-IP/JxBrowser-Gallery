/*
 *  Copyright (c) 2000-2023 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

package dependency

/**
 * A Java library that provides a single interface to register Global Hotkeys.
 *
 * The supported platforms:
 *
 * 1. Windows.
 * 2. X11-based systems (only tested on some Linux distros and PCBSD).
 * 3. macOS.
 *
 * @see <a href="https://github.com/tulskiy/jkeymaster">GitHub</a>
 */
@Suppress("ConstPropertyName")
object JKeyMaster {
    private const val version = "1.3"
    const val lib = "com.github.tulskiy:jkeymaster:$version"
}
