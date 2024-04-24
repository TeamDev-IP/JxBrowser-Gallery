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
import io.micronaut.context.annotation.Context;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.server.types.files.SystemFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

/**
 * A controller that exports charts to various image and non-image formats.
 *
 * <p>The process of rendering and exporting a chart is roughly the following:
 * <ol>
 *   <li>A {@link ChartWidget} instance with the passed chart parameters is created
 *       and saved as an HTML file.
 *   <li>A pre-created {@link Browser} instance loads the local URL denoting
 *       the saved HTML file.
 *   <li>The browser's bitmap, which now contains the rendered chart, is converted
 *       to {@code BufferedImage} and saved in the desired image format.
 *   <li>The saved file bytes are streamed back to the client as {@link SystemFile}.
 * </ol>
 *
 * @implNote This controller is of {@link Context} scope to avoid increased latency
 * upon receiving the first request.
 */
@Controller("/export")
@Context
final class ChartExportController {

    /**
     * A browser instance that is re-used for server-side rendering of the charts.
     */
    private final Browser browser;

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
    }

    /**
     * Exports the "Per Capita Energy Use" chart to a PNG image.
     *
     * @param params the parameters to pass to the chart drawing function
     * @return a {@link SystemFile} instance representing the exported PNG image
     * @throws IOException if an I/O error occurs during the operation
     */
    @Get("/per-capita-energy-use/png")
    SystemFile perCapitaEnergyUsePng(@QueryValue String params) throws IOException {
        var widget = ChartWidget.createAndWriteToFile(
                Dataset.PER_CAPITA_ENERGY_USE, "window.drawPerCapitaEnergyUseChart", params
        );
        var widgetUrl = widget.url();
        browser.navigation()
               .loadUrlAndWait(widgetUrl.toString());

        var image = saveBitmapPng("images/per-capita-energy-use.png");
        return new SystemFile(image);
    }

    /**
     * Exports the "Energy Consumption by Source" chart to a PNG image.
     *
     * @param params the parameters to pass to the chart drawing function
     * @return a {@link SystemFile} instance representing the exported PNG image
     * @throws IOException if an I/O error occurs during the operation
     */
    @Get("/energy-consumption-by-source/png")
    SystemFile energyConsumptionBySourcePng(@QueryValue String params) throws IOException {
        var widget = ChartWidget.createAndWriteToFile(
                Dataset.ENERGY_CONSUMPTION_BY_SOURCE,
                "window.drawEnergyConsumptionBySourceChart",
                params
        );
        var widgetUrl = widget.url();
        browser.navigation()
               .loadUrlAndWait(widgetUrl.toString());

        var image = saveBitmapPng("images/energy-consumption-by-source.png");
        return new SystemFile(image);
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
