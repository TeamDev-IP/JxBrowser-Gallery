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
                                          .getResource("widgets/canvas.html");

        // Preload the chart drawing JavaScript.
        chartDrawingJs = new Resource("widgets/chart-drawing.js").readAsString();

        // Preload data sets.
        fossilFuelsConsumptionData = new Resource("fossil-fuels-consumption.csv").readAsString();
    }

    @Get("/fossil-fuels-consumption")
    public HttpResponse<?> fossilFuelsConsumption() throws IOException {
        browser.navigation()
               .loadUrlAndWait(canvasUrl.toString());

        runChartDrawingJs(fossilFuelsConsumptionData, "window.drawFossilFuelsConsumptionChart");

        saveBitmap("fossil-fuels-consumption.png");

        return HttpResponse.ok();
    }

    private void runChartDrawingJs(String data, String chartDrawingFunction) {
        var mainFrame = browser.mainFrame()
                               .orElseThrow();
        var javaScript = "%s; const data = `%s`; %s('chart', data);".formatted(
                chartDrawingJs, data, chartDrawingFunction
        );
        mainFrame.executeJavaScript(javaScript);
    }

    private void saveBitmap(String fileName) throws IOException {
        var bitmap = browser.bitmap();
        var image = new BitmapConverter().toBufferedImage(bitmap);
        ImageIO.write(image, "png", new File(fileName));
    }
}
