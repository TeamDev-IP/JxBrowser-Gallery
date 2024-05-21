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
         * Returns the info about the "Dietary composition by country" dataset.
         */
        get("/dataset/dietary-composition-by-country/info") {
            val info = Dataset.DIETARY_COMPOSITION_BY_COUNTRY.info()
            val json = Json.encodeToString(info)
            call.respondText(json)
        }

        /**
         * Returns the content of the "Dietary composition by country" dataset.
         */
        get("/dataset/dietary-composition-by-country/data") {
            val data = Dataset.DIETARY_COMPOSITION_BY_COUNTRY.data()
            call.respondText(data)
        }

        /**
         * Prints the table to PDF using [browser] and returns the PDF file.
         *
         * The file is also saved locally to the `exported` directory.
         */
        get("/print/dietary-composition-by-country") {
            val pdfPath = Paths.get("exported/webpage.pdf")
            val countDownLatch = CountDownLatch(1)
            browser.configurePrinting(pdfPath) {
                countDownLatch.countDown()
            }

            val queryParams = call.request.queryParameters
            val filterValues = listOf(
                queryParams["entity"] ?: "",
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
