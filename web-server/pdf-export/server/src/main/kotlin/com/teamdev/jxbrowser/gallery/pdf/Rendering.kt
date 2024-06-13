/*
 *  Copyright (c) 2024 TeamDev
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

package com.teamdev.jxbrowser.gallery.pdf

import com.teamdev.jxbrowser.browser.Browser
import com.teamdev.jxbrowser.dsl.browser.mainFrame
import com.teamdev.jxbrowser.dsl.browser.navigation

/**
 * A resource containing the HTML template for the table rendering.
 */
private const val TEMPLATE_RESOURCE = "/rendering/index.html"

/**
 * Renders the "Dietary composition by region" table using the passed [Browser] instance.
 *
 * The specified [searchValue] is used to filter the table data before rendering.
 *
 * Once the table fully renders, the JS code on the page will call the PDF printing
 * functionality that has been configured via [configurePrinting].
 */
fun renderTable(browser: Browser, searchValue: String) {
    val templateUrl = resourceUrl(TEMPLATE_RESOURCE)!!
    browser.navigation.loadUrlAndWait(templateUrl)

    val data = Dataset.DIETARY_COMPOSITION_BY_REGION.data()
    val javaScript = """
        const csv = `$data`;
        const data = window.csvToArray(csv.trim());
        const grid = window.newGrid(data, null, false, '$searchValue');
        grid.render(document.getElementById('grid'));
    """
    browser.mainFrame!!.executeJavaScript<Unit>(javaScript)
}
