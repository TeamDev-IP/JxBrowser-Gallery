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
import io.ktor.http.content.OutgoingContent
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respondFile
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.util.AttributeKey
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.file.Paths
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * The timeout for the PDF printing operation in seconds.
 *
 * This value is set to a relatively high number because printing of the PDF files
 * can take a long time depending on the content size.
 */
const val PRINT_TIMEOUT_SECONDS = 120L

/**
 * The [Browser] instance used across the application.
 */
private val browser = newBrowser()

/**
 * Configures the [Application] routes.
 */
fun Application.configureRouting() {
    routing {

        /**
         * Returns the info about the "Dietary composition by region" dataset.
         */
        get("/dataset/dietary-composition-by-region/info") {
            val info = Dataset.DIETARY_COMPOSITION_BY_REGION.info()
            val json = Json.encodeToString(info)
            call.respondText(json)
        }

        /**
         * Returns the content of the "Dietary composition by region" dataset.
         */
        get("/dataset/dietary-composition-by-region/data") {
            val data = Dataset.DIETARY_COMPOSITION_BY_REGION.data()
            call.respondText(data)
        }

        /**
         * Prints the table to PDF using [browser] and returns the PDF file.
         *
         * The file is also saved locally to the `exported` directory.
         */
        get("/print/dietary-composition-by-region") {
            val pdfPath = Paths.get("exported/webpage.pdf")
            val countDownLatch = CountDownLatch(1)
            browser.configurePrinting(pdfPath) {
                countDownLatch.countDown()
            }

            val queryParams = call.request.queryParameters
            val filterValues = listOf(
                queryParams["region"] ?: "",
                queryParams["code"] ?: "",
                queryParams["year"] ?: "",
                queryParams["type"] ?: ""
            )
            renderTable(browser, filterValues)

            countDownLatch.await(PRINT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            call.respondFile(pdfPath.toFile(), configure = OutgoingContent::configure)
        }
    }
}

/**
 * Increases the maximum possible length of the response content.
 */
private fun OutgoingContent.configure() {
    setProperty(AttributeKey("Content-Length"), "77000000")
}
