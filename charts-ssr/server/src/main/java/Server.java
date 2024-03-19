/*
 * Copyright (c) 2024 TeamDev. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.engine.Engine;

import static com.teamdev.jxbrowser.engine.RenderingMode.OFF_SCREEN;

public final class Server {

    private Server() {
    }

    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public static void main(String[] args) {
        // Initialize Chromium.
        var engine = Engine.newInstance(OFF_SCREEN);

        // Create a Browser instance.
        var browser = engine.newBrowser();

        // Load a web page and wait until it is loaded completely.
        browser.navigation().loadUrlAndWait("https://html5test.teamdev.com/");

        // Print HTML of the loaded web page.
        browser.mainFrame().ifPresent(frame -> System.out.println(frame.html()));

        // Shutdown Chromium and release allocated resources.
        engine.close();
    }
}
