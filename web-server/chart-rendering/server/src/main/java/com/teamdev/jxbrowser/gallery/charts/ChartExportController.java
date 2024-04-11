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
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.license.internal.LicenseProvider;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.server.types.files.SystemFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

/**
 * A controller that exports charts to various image and non-image formats.
 *
 * <p>The process of rendering and exporting a chart is roughly the following:
 * <ol>
 *   <li>A pre-created {@link Browser} instance navigates to the local URL denoting
 *       the HTML canvas where the chart will be drawn.
 *   <li>The chart data is loaded and passed to the chart-drawing script via
 *       {@code frame.executeJavaScript(...)}.
 *   <li>A JavaScript function is called to draw the chart on the canvas.
 *   <li>In case of an export to an image, the browser's bitmap, which now contains
 *       the rendered chart, is converted to {@code BufferedImage} and saved
 *       in the desired image format.
 *   <li>The saved file bytes are streamed back to the client as {@link SystemFile}.
 * </ol>
 */
@Controller("/export")
final class ChartExportController {

    /**
     * A browser instance that is re-used for server-side rendering of the charts.
     */
    private final Browser browser;

    /**
     * The URL of the local HTML representing the canvas where the charts are drawn.
     */
    private final URL canvasUrl;

    /**
     * Creates a new controller instance.
     *
     * <p>Initializes a {@link Browser} instance that will be used for server-side
     * rendering of the charts.
     *
     * <p>Also, obtains the URL of the local HTML representing the canvas where
     * the charts will be drawn.
     */
    @SuppressWarnings("resource" /* The engine has the same lifetime as the application. */)
    ChartExportController() {
        var options = EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                .licenseKey(LicenseProvider.INSTANCE.getKey())
                .build();
        var engine = Engine.newInstance(options);
        browser = engine.newBrowser();
        canvasUrl = new Resource("rendering/canvas.html").url();
    }

    /**
     * Exports the "Fossil Fuels Consumption" chart to a PNG image.
     *
     * @return a {@link SystemFile} instance representing the exported PNG image
     * @throws IOException if an I/O error occurs during the operation
     */
    @SuppressWarnings("MethodWithTooManyParameters" /* A lot of parameters for the printed chart. */)
    @Get("/fossil-fuels-consumption/png")
    SystemFile fossilFuelsConsumptionPng(@QueryValue String type,
                                         @QueryValue boolean labels,
                                         @QueryValue boolean trendline,
                                         @QueryValue int xmin,
                                         @QueryValue int xmax,
                                         @QueryValue int ymin,
                                         @QueryValue int ymax)
            throws IOException {
        browser.navigation()
               .loadUrlAndWait(canvasUrl.toString());

        var data = Dataset.FOSSIL_FUELS_CONSUMPTION.dataAsString();
        runChartDrawingJs(
                data,
                "window.drawFossilFuelsConsumptionChart",
                type,
                labels,
                trendline,
                xmin,
                xmax,
                ymin,
                ymax
        );

        var image = saveBitmapPng("exported/fossil-fuels-consumption.png");

        return new SystemFile(image);
    }

    private void runChartDrawingJs(String data,
                                   String chartDrawingFunction,
                                   String chartType,
                                   boolean showDataLabels,
                                   boolean showTrendline,
                                   int xScaleMin,
                                   int xScaleMax,
                                   int yScaleMin,
                                   int yScaleMax) {
        var mainFrame = browser.mainFrame()
                .orElseThrow();
        var javaScript = "const data = `%s`; %s('chart', data, '%s', %b, %b, %d, %d, %d, %d);"
                .formatted(
                        data, chartDrawingFunction, chartType, showDataLabels, showTrendline, xScaleMin, xScaleMax, yScaleMin, yScaleMax
                );
        mainFrame.executeJavaScript(javaScript);
    }

    private File saveBitmapPng(String fileName) throws IOException {
        var bitmap = browser.bitmap();
        var image = new BitmapConverter().toBufferedImage(bitmap);
        var output = new File(fileName);
        output.getParentFile()
              .mkdirs();
        ImageIO.write(image, "png", output);
        return output;
    }
}
