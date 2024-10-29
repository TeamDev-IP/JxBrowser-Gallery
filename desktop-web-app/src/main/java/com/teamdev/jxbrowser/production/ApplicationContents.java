package com.teamdev.jxbrowser.production;

import com.teamdev.jxbrowser.net.Scheme;

import java.io.File;

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
    public static final String APP_URL = IS_PRODUCTION ? "jxbrowser://my-app.com": "http://localhost:5173/";
    public static final String INDEX_HTML = "index.html";
}
