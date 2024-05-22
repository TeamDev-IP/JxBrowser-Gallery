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

package com.teamdev.jxbrowser.gallery.pdf

import com.teamdev.jxbrowser.browser.Browser
import com.teamdev.jxbrowser.dsl.browser.mainFrame
import com.teamdev.jxbrowser.dsl.browser.navigation

/**
 * A resource containing the HTML template for the table rendering.
 */
private const val TEMPLATE_RESOURCE = "/rendering/index.html"

/**
 * Renders the "Dietary composition by country" table using the passed [Browser] instance.
 *
 * The specified [filterValues] are used to filter the table data before rendering.
 *
 * Once the table fully renders, the JS code on the page will call the PDF printing
 * functionality that has been configured via [configurePrinting].
 */
fun renderTable(browser: Browser, filterValues: Iterable<String>) {
    val templateUrl = resourceUrl(TEMPLATE_RESOURCE)!!
    browser.navigation.loadUrlAndWait(templateUrl)

    val data = Dataset.DIETARY_COMPOSITION_BY_COUNTRY.data()
    val filters = filterValues.joinToString(prefix = "[", postfix = "]") { "'$it'" }
    val javaScript = """
        const csv = `$data`;
        const data = window.csvToArray(csv.trim());
        const filterValues = $filters;
        const filtered = data.filter(row => {
            return filterValues.every((v, index) => {
                return !v
                    || row[index]
                    && row[index].toLowerCase().includes(v.toLowerCase());
            });
        });
        const grid = window.newGrid(filtered, null, false);
        grid.render(document.getElementById('grid'));
    """
    browser.mainFrame!!.executeJavaScript<Unit>(javaScript)
}
