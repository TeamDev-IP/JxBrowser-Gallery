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
