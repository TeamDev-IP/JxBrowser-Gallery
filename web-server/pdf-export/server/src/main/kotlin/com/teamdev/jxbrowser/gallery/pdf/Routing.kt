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

import com.teamdev.jxbrowser.browser.callback.PrintCallback
import com.teamdev.jxbrowser.browser.callback.PrintHtmlCallback
import com.teamdev.jxbrowser.dsl.Engine
import com.teamdev.jxbrowser.dsl.browser.mainFrame
import com.teamdev.jxbrowser.dsl.browser.navigation
import com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED
import com.teamdev.jxbrowser.license.JxBrowserLicense
import com.teamdev.jxbrowser.license.internal.LicenseProvider
import com.teamdev.jxbrowser.print.Orientation.PORTRAIT
import com.teamdev.jxbrowser.print.PdfPrinter
import com.teamdev.jxbrowser.print.PrintJob
import com.teamdev.jxbrowser.print.event.PrintCompleted
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respondFile
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import java.nio.file.Paths
import java.util.concurrent.CountDownLatch


private const val WAIT_FOR_PRINT_SECONDS = 5L

fun Application.configureRouting() {
    val licenseKey = LicenseProvider.key
    val engine = Engine(HARDWARE_ACCELERATED) {
        options {
            license = JxBrowserLicense(licenseKey)
        }
    }
    val browser = engine.newBrowser()

    routing {
        get("/") {
            browser.set(PrintCallback::class.java, PrintCallback { _, tell -> tell.print() })
            val latch = CountDownLatch(1)
            browser.set(
                PrintHtmlCallback::class.java,
                PrintHtmlCallback { params: PrintHtmlCallback.Params,
                                    tell: PrintHtmlCallback.Action ->
                    val pdfPrinter =
                        params.printers().pdfPrinter()
                    val printJob: PrintJob<PdfPrinter.HtmlSettings> = pdfPrinter.printJob()
                    printJob.settings()
                        .pdfFilePath(Paths.get("google.pdf").toAbsolutePath())
                        .enablePrintingBackgrounds()
                        .orientation(PORTRAIT)
                        .apply()
                    printJob.on(PrintCompleted::class.java) { _ ->
                        latch.countDown()
                    }
                    tell.proceed(pdfPrinter)
                })
            browser.navigation.loadUrlAndWait("https://google.com")
            browser.mainFrame?.print()
            latch.await(WAIT_FOR_PRINT_SECONDS, java.util.concurrent.TimeUnit.SECONDS)
            call.respondFile(Paths.get("google.pdf").toFile())
        }
    }
}
