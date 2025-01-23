/*
 *  Copyright (c) 2025 TeamDev
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

package com.teamdev.jxbrowser.examples;

import com.teamdev.jxbrowser.examples.production.ProductionMode;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A utility containing information about application location and its resources.
 */
public final class AppContents {
    public static final String APP_LOCATION =
            new File(AppContents.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath())
                    .getParent();
    public static final String APP_URL = ProductionMode.isEnabled() ? "jxbrowser://my-app.com" : "http://localhost:5173";
    public static final Path APP_RESOURCES_DIR;
    public static final Path CHROMIUM_USER_DATA_DIR;

    static {
        String userHome = System.getProperty("user.home");
        String osName = System.getProperty("os.name").toLowerCase();
        Path userDataDir;

        if (osName.contains("win")) {
            String appData = System.getenv("APPDATA");
            userDataDir = appData != null ?
                    Paths.get(appData) : Paths.get(userHome, "AppData", "Local");
        } else {
            userDataDir = Paths.get(userHome, "Library", "Application Support");
        }
        APP_RESOURCES_DIR = userDataDir.resolve("JxBrowserShadcnApp");
        CHROMIUM_USER_DATA_DIR = APP_RESOURCES_DIR.resolve("UserData");
    }

    private AppContents() {
        // Prevents instance creation.
    }
}
