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

import com.linecorp.armeria.common.HttpHeaderNames;
import com.linecorp.armeria.common.HttpMethod;
import com.linecorp.armeria.common.grpc.protocol.GrpcHeaderNames;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.cors.CorsService;
import com.linecorp.armeria.server.grpc.GrpcService;
import com.linecorp.armeria.server.logging.LoggingService;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.browser.callback.InjectJsCallback;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.examples.prefs.PrefsService;
import com.teamdev.jxbrowser.examples.production.UrlRequestInterceptor;
import com.teamdev.jxbrowser.js.JsObject;
import com.teamdev.jxbrowser.license.internal.LicenseProvider;
import com.teamdev.jxbrowser.logging.Level;
import com.teamdev.jxbrowser.logging.Logger;
import com.teamdev.jxbrowser.net.Scheme;
import com.teamdev.jxbrowser.view.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
import static com.teamdev.jxbrowser.examples.AppDetails.appUrl;
import static com.teamdev.jxbrowser.examples.AppDetails.getAppIconFileName;
import static com.teamdev.jxbrowser.examples.AppDetails.isProductionMode;
import static com.teamdev.jxbrowser.net.Scheme.of;
import static java.awt.Taskbar.Feature.ICON_IMAGE;
import static javax.swing.SwingUtilities.invokeLater;

/**
 * Configures JxBrowser, gRPC and displays the application window.
 */
public final class AppInitializer {

    private static final int RPC_PORT = 50051;
    private static final Scheme SCHEME = of("jxbrowser");

    public void initialize() throws InterruptedException {
        setupLogging();
        var engine = createEngine();
        var browser = engine.newBrowser();
        setupUI(engine, browser);
        setupBrowserCallbacks(browser);
        initializeRpc(browser);
    }

    private static void setupLogging() {
        String logFile = AppDetails.INSTANCE.appResourcesDir()
                .resolve("jxbrowser.log")
                .toString();
        System.setProperty("jxbrowser.logging.file", logFile);
        Logger.level(Level.DEBUG);
    }

    private static Engine createEngine() {
        var optionsBuilder = EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                .userDataDir(AppDetails.INSTANCE.chromiumUserDataDir())
                .licenseKey(LicenseProvider.INSTANCE.getKey());
        if (isProductionMode()) {
            optionsBuilder.addScheme(SCHEME, new UrlRequestInterceptor());
        }

        return Engine.newInstance(optionsBuilder.build());
    }

    private static void setupUI(Engine engine, Browser browser) {
        invokeLater(() -> {
            var frame = new JFrame("Preferences");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    engine.close();
                }
            });

            setAppIcon(frame);
            frame.add(BrowserView.newInstance(browser), BorderLayout.CENTER);
            frame.setSize(640, 600);
            frame.setMinimumSize(new Dimension(640, 560));
            frame.setMaximumSize(new Dimension(1280, 900));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static void setAppIcon(JFrame frame) {
        var appIconRes = AppInitializer.class.getClassLoader().getResource(getAppIconFileName());
        if (appIconRes != null) {
            var icon = Toolkit.getDefaultToolkit().getImage(appIconRes);
            frame.setIconImage(icon);
            if (Taskbar.isTaskbarSupported() && Taskbar.getTaskbar().isSupported(ICON_IMAGE)) {
                Taskbar.getTaskbar().setIconImage(icon);
            }
        }
    }

    private static void setupBrowserCallbacks(Browser browser) {
        browser.set(InjectJsCallback.class, params -> {
            JsObject window = params.frame().executeJavaScript("window");
            if (window != null) {
                window.putProperty("rpcPort", RPC_PORT);
            }
            return InjectJsCallback.Response.proceed();
        });

        if (!isProductionMode()) {
            browser.devTools().show();
        }
    }

    private static void initializeRpc(Browser browser) throws InterruptedException {
        var serverBuilder = Server.builder().http(RPC_PORT);
        var corsBuilder = CorsService.builder(appUrl())
                .allowRequestMethods(HttpMethod.POST)
                .allowRequestHeaders(
                        HttpHeaderNames.CONTENT_TYPE,
                        HttpHeaderNames.of("x-grpc-web"),
                        HttpHeaderNames.of("x-user-agent"))
                .exposeHeaders(GrpcHeaderNames.GRPC_STATUS,
                        GrpcHeaderNames.GRPC_MESSAGE,
                        GrpcHeaderNames.ARMERIA_GRPC_THROWABLEPROTO_BIN);

        serverBuilder.service(GrpcService.builder()
                        .addService(new PrefsService())
                        .build(),
                corsBuilder.newDecorator(),
                LoggingService.newDecorator());

        try (var server = serverBuilder.build()) {
            server.start();
            browser.navigation().loadUrl(appUrl());
            server.blockUntilShutdown();
        }
    }
}
