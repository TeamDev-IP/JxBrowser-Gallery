/*
 * Copyright 2026, TeamDev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.teamdev.jxbrowser.angular;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

/**
 * Application details such as URLs, paths, and mode detection.
 */
public enum AppDetails {
    INSTANCE;

    private static final int DEV_SERVER_PORT = 4200;
    private static final String APP_SCHEME = "jxbrowser";
    private static final String APP_HOST = "my-app.com";

    private final Path appDataDir;
    private final Path chromiumUserDataDir;

    AppDetails() {
        String userHome = System.getProperty("user.home");
        String osName = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);

        if (osName.contains("win")) {
            String appData = System.getenv("LOCALAPPDATA");
            appDataDir = Paths.get(appData != null ? appData : userHome, "JxBrowserAngularApp");
        } else if (osName.contains("mac")) {
            appDataDir = Paths.get(userHome, "Library", "Application Support", "JxBrowserAngularApp");
        } else {
            appDataDir = Paths.get(userHome, ".jxbrowser-angular-app");
        }

        chromiumUserDataDir = appDataDir.resolve("UserData");
    }

    /**
     * Returns the URL of the application frontend.
     */
    public static String appUrl() {
        return isDevMode()
                ? "http://localhost:" + DEV_SERVER_PORT
                : APP_SCHEME + "://" + APP_HOST;
    }

    /**
     * Returns the custom scheme used in production mode.
     */
    public static String appScheme() {
        return APP_SCHEME;
    }

    /**
     * Returns the app host used in production mode URL.
     */
    public static String appHost() {
        return APP_HOST;
    }

    /**
     * Indicates if the application is running in development mode.
     */
    public static boolean isDevMode() {
        return "true".equals(System.getProperty("app.dev.mode"));
    }

    /**
     * Returns the directory path where the Chromium user data is stored.
     */
    public Path chromiumUserDataDir() {
        return chromiumUserDataDir;
    }

    /**
     * Returns the application data directory for storing logs and other data.
     */
    public Path appDataDir() {
        return appDataDir;
    }
}
