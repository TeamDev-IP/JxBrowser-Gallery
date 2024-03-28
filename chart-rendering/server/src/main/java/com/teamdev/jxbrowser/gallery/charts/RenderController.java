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
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
import static java.util.Objects.requireNonNull;

@Controller("/render")
public class RenderController {

    private final Browser browser;

    private final URL canvasUrl;
    private final String chartDrawingJs;
    private final String fossilFuelsConsumptionData;

    RenderController() {
        // Initialize Chromium.
        var engine = Engine.newInstance(HARDWARE_ACCELERATED);

        // Create a Browser instance.
        browser = engine.newBrowser();

        // Set the HTML canvas URL.
        canvasUrl = RenderController.class.getClassLoader()
                                          .getResource("app/canvas.html");

        // Preload the chart drawing JavaScript.
        chartDrawingJs = fileContentAsString("app/charts.js");

        // Preload data sets.
        fossilFuelsConsumptionData = fileContentAsString("fossil-fuels-consumption.csv");
    }

    @Get("/fossil-fuels-consumption")
    public HttpResponse<?> index() throws IOException {
        // Load the web page and wait until it is loaded completely.
        browser.navigation()
               .loadUrlAndWait(canvasUrl.toString());

        var mainFrame = browser.mainFrame();
        if (mainFrame.isEmpty()) {
            throw new IllegalStateException("The browser main frame is not available");
        }
        var frame = mainFrame.get();
        var javaScript =
                "const data = `%s`;".formatted(fossilFuelsConsumptionData)
                        + chartDrawingJs
                        + "window.drawFossilFuelsConsumptionChart('chart', data);";
        frame.executeJavaScript(javaScript);
        var bitmap = browser.bitmap();
        var image = BitmapImage.toToolkit(bitmap);
        ImageIO.write(image, "png", new File("fossil-fuels-consumption.png"));

        return HttpResponse.ok();
    }

    private static String fileContentAsString(String fileName) {
        try {
            var classLoader = RenderController.class.getClassLoader();
            var dataFilePath = classLoader.getResource(fileName);
            var uri = requireNonNull(dataFilePath).toURI();
            var path = Path.of(uri);
            var bytes = Files.readAllBytes(path);
            var result = new String(bytes);
            return result;
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException("Unable to preload data.", e);
        }
    }
}
