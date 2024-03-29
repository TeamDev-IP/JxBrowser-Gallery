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
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

@Controller("/export")
public class ChartExportController {

    private final Browser browser;
    private final URL canvasUrl;

    ChartExportController() {
        // Initialize Chromium.
        var engine = Engine.newInstance(HARDWARE_ACCELERATED);

        // Create a Browser instance.
        browser = engine.newBrowser();

        // Set the HTML canvas URL.
        canvasUrl = new Resource("chart-rendering/canvas.html").url();
    }

    @Get("/fossil-fuels-consumption/png")
    public HttpResponse<?> fossilFuelsConsumptionPng() throws IOException {
        browser.navigation()
               .loadUrlAndWait(canvasUrl.toString());

        var data = Dataset.FOSSIL_FUELS_CONSUMPTION.contentAsString();
        runChartDrawingJs(data, "window.drawFossilFuelsConsumptionChart");

        saveBitmapPng("exported/fossil-fuels-consumption.png");

        return HttpResponse.ok();
    }

    private void runChartDrawingJs(String data, String chartDrawingFunction) {
        var mainFrame = browser.mainFrame()
                               .orElseThrow();
        var javaScript = "const data = `%s`; %s('chart', data);".formatted(
                data, chartDrawingFunction
        );
        mainFrame.executeJavaScript(javaScript);
    }

    private void saveBitmapPng(String fileName) throws IOException {
        var bitmap = browser.bitmap();
        var image = new BitmapConverter().toBufferedImage(bitmap);
        var output = new File(fileName);
        output.getParentFile()
              .mkdirs();
        ImageIO.write(image, "png", output);
    }
}
