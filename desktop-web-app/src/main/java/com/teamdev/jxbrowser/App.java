package com.teamdev.jxbrowser;

import com.teamdev.jxbrowser.browser.callback.InjectJsCallback;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.js.JsObject;
import com.teamdev.jxbrowser.production.UrlRequestInterceptor;
import com.teamdev.jxbrowser.task.TaskService;
import com.teamdev.jxbrowser.view.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
import static com.teamdev.jxbrowser.production.ApplicationContents.APP_URL;
import static com.teamdev.jxbrowser.production.ApplicationContents.IS_PRODUCTION;
import static com.teamdev.jxbrowser.production.ApplicationContents.SCHEME;
import static javax.swing.SwingUtilities.invokeLater;

public final class App {

    private static final String LICENSE_KEY = "";

    public static void main(String[] args) {
        var optionsBuilder = EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                .licenseKey(LICENSE_KEY);
        // In production mode we load resources through the custom scheme and
        // our URL request interceptor. It's required because the compiled React
        // components cannot be loaded from the file system.
        if (IS_PRODUCTION) {
            optionsBuilder.addScheme(SCHEME, new UrlRequestInterceptor());
        }
        var engine = Engine.newInstance(optionsBuilder.build());
        var browser = engine.newBrowser();

        invokeLater(() -> {
            var frame = new JFrame("Desktop Web Application");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    engine.close();
                }
            });

            var appIconResource = App.class.getClassLoader()
                                         .getResource("app-icon.png");
            var appIcon = Toolkit.getDefaultToolkit().getImage(appIconResource);

            frame.setIconImage(appIcon);
            Taskbar.getTaskbar().setIconImage(appIcon);

            frame.add(BrowserView.newInstance(browser), BorderLayout.CENTER);
            frame.setSize(1050, 750);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

        browser.set(InjectJsCallback.class, params -> {
            JsObject window = params.frame().executeJavaScript("window");
            if (window != null) {
                window.putProperty("taskService", new TaskService());
            }
            return InjectJsCallback.Response.proceed();
        });

        browser.navigation().loadUrl(APP_URL);
    }
}