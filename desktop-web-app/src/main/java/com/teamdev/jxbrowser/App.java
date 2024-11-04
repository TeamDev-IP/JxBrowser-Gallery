package com.teamdev.jxbrowser;

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
import com.teamdev.jxbrowser.js.JsObject;
import com.teamdev.jxbrowser.logging.Level;
import com.teamdev.jxbrowser.logging.Logger;
import com.teamdev.jxbrowser.production.UrlRequestInterceptor;
import com.teamdev.jxbrowser.task.TaskService;
import com.teamdev.jxbrowser.view.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutionException;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
import static com.teamdev.jxbrowser.production.ApplicationContents.APP_URL;
import static com.teamdev.jxbrowser.production.ApplicationContents.IS_PRODUCTION;
import static com.teamdev.jxbrowser.production.ApplicationContents.SCHEME;
import static javax.swing.SwingUtilities.invokeLater;

public final class App {

    private static final String LICENSE_KEY = "";
    private static final int RPC_PORT = 50051;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.setProperty("jxbrowser.logging.file", "jxbrowser.log");
        Logger.level(Level.DEBUG);
        var optionsBuilder = EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                .licenseKey(LICENSE_KEY);
        if (IS_PRODUCTION) {
            optionsBuilder.addScheme(SCHEME, new UrlRequestInterceptor());
        }
        var engine = Engine.newInstance(optionsBuilder.build());
        var browser = engine.newBrowser();

        invokeLater(() -> {
            var frame = new JFrame("JxBrowser WebApp");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    engine.close();
                }
            });

            var imageResource = App.class.getClassLoader().getResource("leaf.png");
            var image = Toolkit.getDefaultToolkit().getImage(imageResource);

            frame.setIconImage(image);
            Taskbar.getTaskbar().setIconImage(image);

            frame.add(BrowserView.newInstance(browser), BorderLayout.CENTER);
            frame.setSize(1280, 900);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

        browser.set(InjectJsCallback.class, params -> {
            JsObject window = params.frame().executeJavaScript("window");
            if (window != null) {
                window.putProperty("rpcPort", RPC_PORT);
            }
            return InjectJsCallback.Response.proceed();
        });

        if (!IS_PRODUCTION) {
            browser.devTools().show();
        }

        initRpc(browser);
    }

    private static void initRpc(Browser browser) throws InterruptedException, ExecutionException {
        var serverBuilder = Server.builder().http(RPC_PORT);
        var corsBuilder = CorsService.builder(APP_URL)
                .allowRequestMethods(HttpMethod.POST)
                .allowRequestHeaders(
                        HttpHeaderNames.CONTENT_TYPE,
                        HttpHeaderNames.of("x-grpc-web"),
                        HttpHeaderNames.of("x-user-agent"))
                .exposeHeaders(GrpcHeaderNames.GRPC_STATUS,
                        GrpcHeaderNames.GRPC_MESSAGE,
                        GrpcHeaderNames.ARMERIA_GRPC_THROWABLEPROTO_BIN);

        serverBuilder.service(GrpcService.builder()
                        .addService(new TaskService())
                        .build(),
                corsBuilder.newDecorator(),
                LoggingService.newDecorator());

        try (var server = serverBuilder.build()) {
            server.start();
            browser.navigation().loadUrl(APP_URL);
            server.blockUntilShutdown();
        }
    }
}