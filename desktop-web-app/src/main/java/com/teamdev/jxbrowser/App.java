package com.teamdev.jxbrowser;

import com.teamdev.jxbrowser.browser.Browser;
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
import java.net.URL;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
import static com.teamdev.jxbrowser.production.ApplicationContents.APP_URL;
import static com.teamdev.jxbrowser.production.ApplicationContents.IS_PRODUCTION;
import static com.teamdev.jxbrowser.production.ApplicationContents.SCHEME;

public final class App {

    private static final String LICENSE_KEY = "";

    public static void main(String[] args) {
        EngineOptions.Builder builder = EngineOptions.newBuilder(HARDWARE_ACCELERATED).licenseKey(LICENSE_KEY);
        if (IS_PRODUCTION) {
            builder.addScheme(SCHEME, new UrlRequestInterceptor());
        }
        Engine engine = Engine.newInstance(builder.build());

        Browser browser = engine.newBrowser();

        SwingUtilities.invokeLater(() -> {
            BrowserView view = BrowserView.newInstance(browser);
            JFrame frame = new JFrame("JxBrowser Web Application");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    engine.close();
                }
            });

            Taskbar taskbar = Taskbar.getTaskbar();
            Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
            URL imageResource = App.class.getClassLoader().getResource("leaf.png");
            Image image = defaultToolkit.getImage(imageResource);
            taskbar.setIconImage(image);
            frame.setIconImage(image);

            frame.add(view, BorderLayout.CENTER);
            frame.setSize(1280, 900);
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