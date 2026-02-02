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

import com.formdev.flatlaf.FlatDarkLaf;
import com.teamdev.jxbrowser.angular.backend.DashboardBackend;
import com.teamdev.jxbrowser.angular.production.UrlRequestInterceptor;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.browser.callback.InjectJsCallback;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.js.JsObject;
import com.teamdev.jxbrowser.net.Scheme;
import com.teamdev.jxbrowser.license.internal.LicenseProvider;
import com.teamdev.jxbrowser.logging.Level;
import com.teamdev.jxbrowser.logging.Logger;
import com.teamdev.jxbrowser.view.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
import static javax.swing.SwingUtilities.invokeLater;

/**
 * Configures JxBrowser, sets up the JS-Java Bridge, and displays the application window.
 */
public final class AppInitializer {

    private static final String APP_TITLE = "Angular Dashboard - JxBrowser";
    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 800;

    public void initialize() {
        setupLogging();
        setupLookAndFeel();
        var engine = createEngine();
        var browser = engine.newBrowser();
        setupJavaScriptBridge(browser);
        setupUI(engine, browser);
        browser.navigation().loadUrl(AppDetails.appUrl());
    }

    private void setupLogging() {
        String logFile = AppDetails.INSTANCE.appDataDir()
                .resolve("jxbrowser.log")
                .toString();
        System.setProperty("jxbrowser.logging.file", logFile);
        Logger.level(Level.DEBUG);
    }

    private void setupLookAndFeel() {
        try {
            System.setProperty("apple.awt.application.appearance", "NSAppearanceNameDarkAqua");
            FlatDarkLaf.setup();
        } catch (Exception e) {
            System.err.println("Failed to setup FlatLaf dark theme: " + e.getMessage());
        }
    }

    private Engine createEngine() {
        var optionsBuilder = EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                .userDataDir(AppDetails.INSTANCE.chromiumUserDataDir())
                .licenseKey(LicenseProvider.INSTANCE.getKey());

        if (!AppDetails.isDevMode()) {
            optionsBuilder.addScheme(Scheme.of(AppDetails.appScheme()), new UrlRequestInterceptor());
        }

        return Engine.newInstance(optionsBuilder.build());
    }

    private void setupJavaScriptBridge(Browser browser) {
        var backend = new DashboardBackend();
        browser.set(InjectJsCallback.class, params -> {
            JsObject window = params.frame().executeJavaScript("window");
            if (window != null) {
                window.putProperty("backend", backend);
            }
            return InjectJsCallback.Response.proceed();
        });

        if (AppDetails.isDevMode()) {
            browser.devTools().show();
        }
    }

    private void setupUI(Engine engine, Browser browser) {
        invokeLater(() -> {
            var frame = new JFrame(APP_TITLE);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    engine.close();
                }
            });

            frame.add(BrowserView.newInstance(browser), BorderLayout.CENTER);
            frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            frame.setMinimumSize(new Dimension(800, 600));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
