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
import com.teamdev.jxbrowser.ui.Bitmap;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import javax.imageio.ImageIO;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
import static java.awt.image.Raster.createInterleavedRaster;
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
                                          .getResource("canvas.html");

        // Preload the chart drawing JavaScript.
        chartDrawingJs = resourceContentAsString("chart-drawing.js");

        // Preload data sets.
        fossilFuelsConsumptionData = resourceContentAsString("fossil-fuels-consumption.csv");
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
        var image = toBufferedImage(bitmap);
        ImageIO.write(image, "png", new File(fileName));
    }

    /**
     * Converts a {@link Bitmap} instance to a {@link BufferedImage}.
     *
     * <p>Alternatively, add the "jxbrowser.javafx"/"jxbrowser.swing"/"jxbrowser.swt"
     * dependency to the project and use the {@code BitmapImage.toToolkitImage()} method.
     *
     * @param bitmap the {@link Bitmap} instance to convert
     * @return a {@link BufferedImage} instance
     */
    private static BufferedImage toBufferedImage(Bitmap bitmap) {
        var size = bitmap.size();
        var width = size.width();
        var height = size.height();
        var raster = createInterleavedRaster(
                0, width, height, width * 4, 4, new int[]{2, 1, 0, 3}, null
        );
        var colorModel = new ComponentColorModel(
                ColorSpace.getInstance(1000), new int[]{8, 8, 8, 8}, true, true, 3, 0
        );
        var dataBuffer = (DataBufferByte) raster.getDataBuffer();
        var pixels = bitmap.pixels();
        System.arraycopy(pixels, 0, dataBuffer.getData(), 0, pixels.length);
        return new BufferedImage(colorModel, raster, true, null);
    }

    private static String resourceContentAsString(String resourceName) {
        try {
            var dataFilePath = RenderController.class.getClassLoader()
                                                     .getResource(resourceName);
            var uri = requireNonNull(dataFilePath).toURI();
            var path = Path.of(uri);
            var result = Files.readString(path);
            return result;
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException("Unable to preload a resource.", e);
        }
    }
}
