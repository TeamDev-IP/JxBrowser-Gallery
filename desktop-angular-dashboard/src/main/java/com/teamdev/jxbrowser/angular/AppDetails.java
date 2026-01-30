/*
 *  Copyright 2025, TeamDev. All rights reserved.
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
