/*
 *  Copyright 2024, TeamDev. All rights reserved.
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

package com.teamdev.jxbrowser.gallery.charts;

import com.teamdev.jxbrowser.engine.Engine;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

import java.util.concurrent.atomic.AtomicReference;

import static com.teamdev.jxbrowser.engine.RenderingMode.OFF_SCREEN;
import static java.util.Objects.requireNonNull;

@Controller("/render")
public class RenderController {

    @Get
    @Produces(MediaType.TEXT_HTML)
    public String index() {
        var pageUrl = RenderController.class.getClassLoader()
                                            .getResource("app/index.html");

        // Initialize Chromium.
        var engine = Engine.newInstance(OFF_SCREEN);

        // Create a Browser instance.
        var browser = engine.newBrowser();

        // Load the web page and wait until it is loaded completely.
        browser.navigation()
               .loadUrlAndWait(requireNonNull(pageUrl).toString());

        // Print HTML of the loaded web page.
        var response = new AtomicReference<String>();
        browser.mainFrame()
               .ifPresent(frame -> response.set(frame.html()));

        // Shutdown Chromium and release allocated resources.
        engine.close();
        return response.get();
    }
}
