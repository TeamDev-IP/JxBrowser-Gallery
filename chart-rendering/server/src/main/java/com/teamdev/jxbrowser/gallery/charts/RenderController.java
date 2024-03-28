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

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.view.swing.graphics.BitmapImage;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
import static java.util.Objects.requireNonNull;

@Controller("/render")
public class RenderController {

    private final Browser browser;

    RenderController() {
        // Initialize Chromium.
        var engine = Engine.newInstance(HARDWARE_ACCELERATED);
        // Create a Browser instance.
        browser = engine.newBrowser();
    }

    @Get("/fossil-fuel-consumption")
    @Produces(MediaType.IMAGE_PNG)
    public byte[] index() throws URISyntaxException, IOException {
        var dataFilePath = requireNonNull(RenderController.class.getClassLoader()
                                                                .getResource("fossil-fuel-consumption.csv"));
        var data = new String(Files.readAllBytes(Path.of(dataFilePath.toURI())));

        var jsPath = requireNonNull(RenderController.class.getClassLoader()
                                                          .getResource("app/charts.js"));
        var js = new String(Files.readAllBytes(Path.of(jsPath.toURI())));

        var pageUrl = RenderController.class.getClassLoader()
                                            .getResource("app/canvas.html");

        // Load the web page and wait until it is loaded completely.
        browser.navigation()
               .loadUrlAndWait(requireNonNull(pageUrl).toString());

        var response = new AtomicReference<byte[]>();
        browser.mainFrame()
               .ifPresent(frame -> {
                   var javaScript =
                           "const data = `%s`;".formatted(data)
                                   + js
                                   + "window.drawFossilFuelsConsumptionChart('chart', data);";
                   frame.executeJavaScript(javaScript);
                              var bitmap = browser.bitmap();
                              var image = BitmapImage.toToolkit(bitmap);
                              var stream = new ByteArrayOutputStream();
                              try {
                                  ImageIO.write(image, "png", stream);
                                  var bytes = stream.toByteArray();
                                  response.set(bytes);
                              } catch (IOException e) {
                                  throw new RuntimeException(e);
                              }
                          }
               );

        // Shutdown Chromium and release allocated resources.
        return response.get();
    }
}
