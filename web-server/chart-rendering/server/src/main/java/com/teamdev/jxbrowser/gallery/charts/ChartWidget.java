/*
 *  Copyright 2026, TeamDev
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
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
