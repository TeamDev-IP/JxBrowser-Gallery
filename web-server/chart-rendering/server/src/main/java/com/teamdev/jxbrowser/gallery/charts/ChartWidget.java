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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

import static j2html.TagCreator.body;
import static j2html.TagCreator.canvas;
import static j2html.TagCreator.script;
import static java.nio.file.Files.writeString;

/**
 * A widget that renders a chart using the passed dataset and parameters.
 *
 * <p>The widget is saved as an HTML file and can be accessed via its
 * {@linkplain #url() URL}.
 */
final class ChartWidget {

    /**
     * The data upon which the rendered chart is based.
     */
    private final Dataset dataset;

    /**
     * The JavaScript function that renders the chart.
     */
    private final String drawFunction;

    /**
     * The parameters passed to the rendering function.
     */
    private final String drawParams;

    private ChartWidget(Dataset dataset, String drawFunction, String drawParams) {
        this.dataset = dataset;
        this.drawFunction = drawFunction;
        this.drawParams = drawParams;
    }

    /**
     * Creates a new widget with the passed parameters and saves it as an HTML file.
     *
     * @param dataset the data upon which the rendered chart is based
     * @param drawFunction the JavaScript function that renders the chart
     * @param drawParams the parameters passed to the rendering function
     * @return the created widget
     * @throws IOException if an I/O error occurs while writing the widget to a file
     */
    static ChartWidget createAndWriteToFile(Dataset dataset,
                                            String drawFunction,
                                            String drawParams)
            throws IOException {
        var widget = new ChartWidget(dataset, drawFunction, drawParams);
        widget.writeToFile();
        return widget;
    }

    /**
     * Returns the URL to the HTML file representing this widget.
     */
    URL url() {
        try {
            return path().toUri()
                         .toURL();
        } catch (MalformedURLException e) {
            throw new IllegalStateException(
                    "Malformed widget URL: `%s`.".formatted(path()), e
            );
        }
    }

    /**
     * Generates the HTML content of this widget.
     */
    private String html() {
        var contentType = "text/javascript";
        var html = body(
                canvas().withId(dataset.id()),
                script().withType(contentType)
                        .withSrc("charts.js"),
                script(js()).withType(contentType)
        ).render();
        return html;
    }

    /**
     * Generates the chart-drawing JavaScript for this widget.
     */
    private String js() {
        return """
                const data = `%s`;
                const parsedData = window.csvToArray(data);
                %s('%s', parsedData, %s);
                """.formatted(dataset.data(), drawFunction, dataset.id(), drawParams);
    }

    /**
     * Returns the path to the HTML file representing this widget.
     */
    private Path path() {
        return Path.of("widgets", dataset.id() + ".html");
    }

    /**
     * Writes this widget to a file.
     */
    private void writeToFile() throws IOException {
        writeString(path(), html());
    }
}
