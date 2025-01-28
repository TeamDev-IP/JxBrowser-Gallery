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

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Application details such as the application location directory, resources directory, and Chromium user data directory.
 */
public enum AppDetails {
    INSTANCE;

    /**
     * Returns the directory path where the application is located.
     */
    public static Path appLocationDir() {
        return new File(AppDetails.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath())
                .getParentFile()
                .toPath();
    }

    /**
     * Returns the URL of the application frontend.
     */
    public static String appUrl() {
        return isProductionMode() ? "jxbrowser://my-app.com" : "http://localhost:5173";
    }

    public static String getAppIconFileName() {
        return "preferences.png";
    }

    /**
     * Indicates if the application is running in the production mode.
     */
    public static boolean isProductionMode() {
        return System.getProperty("app.dev.mode") == null;
    }

    private final Path appResourcesDir;
    private final Path chromiumUserDataDir;

    AppDetails() {
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
        appResourcesDir = userDataDir.resolve("JxBrowserShadcnApp");
        chromiumUserDataDir = appResourcesDir.resolve("UserData");
    }

    /**
     * Returns the directory path where the application resources are located.
     */
    public Path appResourcesDir() {
        return appResourcesDir;
    }

    /**
     * Returns the directory path where the Chromium user data is stored.
     */
    public Path chromiumUserDataDir() {
        return chromiumUserDataDir;
    }
}
