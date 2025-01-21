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

package com.teamdev.jxbrowser.production;

import com.teamdev.jxbrowser.net.Scheme;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ApplicationContents {
    public static final boolean IS_PRODUCTION = System.getProperty("app.dev.mode") == null;
    public static final String APPLICATION_LOCATION =
            new File(UrlRequestInterceptor.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath())
                    .getParent();
    public static final Scheme SCHEME = com.teamdev.jxbrowser.net.Scheme.of("jxbrowser");
    public static final String CONTENT_ROOT = "web/";
    public static final String APP_URL = IS_PRODUCTION ? "jxbrowser://my-app.com" : "http://localhost:5173";
    public static final Path APPLICATION_RESOURCES_DIR;
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
        APPLICATION_RESOURCES_DIR = userDataDir.resolve("JxBrowserShadcnApp");
        CHROMIUM_USER_DATA_DIR = APPLICATION_RESOURCES_DIR.resolve("UserData");
    }
}
