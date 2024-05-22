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
import com.teamdev.jxbrowser.browser.callback.InjectJsCallback
import com.teamdev.jxbrowser.browser.callback.PrintCallback
import com.teamdev.jxbrowser.browser.callback.PrintHtmlCallback
import com.teamdev.jxbrowser.dsl.browser.mainFrame
import com.teamdev.jxbrowser.dsl.register
import com.teamdev.jxbrowser.js.JsAccessible
import com.teamdev.jxbrowser.js.JsObject
import com.teamdev.jxbrowser.print.event.PrintCompleted
import java.nio.file.Path

/**
 * Configures printing for the [Browser] instance.
 *
 * With the help of [InjectJsCallback], the JS code loaded by the [Browser] instance
 * is supplied with a `javaPrinter` object that can be used to initiate the printing
 * when the webpage is ready. Such an approach is necessary when the loaded page
 * does asynchronous operations that need to complete before printing. When the page
 * is rendered synchronously, it's possible to use `browser.loadUrlAndWait(...)` ->
 * `browser.mainFrame!!.print()` directly.
 *
 * The [PrintCallback] and [PrintHtmlCallback] callbacks configure the [Browser]
 * instance to immediately save the current page to PDF at [destination] upon
 * receiving a print request.
 *
 * The [onPrinted] callback runs once the operation completes.
 */
fun Browser.configurePrinting(destination: Path, onPrinted: () -> Unit) {
    register(injectJsCallback(this))
    register(PrintCallback)
    register(printHtmlCallback(destination) {
        onPrinted()
    })
}

/**
 * Creates the callback that injects the [Printer] object into the JS code loaded
 * by the [Browser] instance.
 */
private fun injectJsCallback(browser: Browser) = InjectJsCallback { params ->
    val window = params.frame().executeJavaScript<JsObject>("window")
    window!!.putProperty(
        "javaPrinter", Printer(browser)
    )
    InjectJsCallback.Response.proceed()
}

/**
 * The callback that instructs the [Browser] to immediately print the webpage
 * upon receiving a print request.
 */
private val PrintCallback = PrintCallback { _, tell -> tell.print() }

/**
 * Creates a [PrintHtmlCallback] that prints the webpage to a PDF file.
 *
 * The file is saved to the specified [destination].
 *
 * The [onCompleted] callback runs once the printing operation completes.
 */
private fun printHtmlCallback(destination: Path, onCompleted: () -> Unit): PrintHtmlCallback {
    return PrintHtmlCallback {
            params: PrintHtmlCallback.Params,
            tell: PrintHtmlCallback.Action,
        ->
        val pdfPrinter = params
            .printers()
            .pdfPrinter()
        val printJob = pdfPrinter.printJob()
        printJob
            .settings()
            .pdfFilePath(destination.toAbsolutePath())
            .enablePrintingBackgrounds()
            .apply()
        printJob.on(PrintCompleted::class.java) { _ ->
            onCompleted()
        }
        tell.proceed(pdfPrinter)
    }
}

/**
 * The class that enables the printing of the webpage from inside the JS code.
 */
class Printer(private val browser: Browser) {

    /**
     * Initiates the printing of the webpage.
     *
     * The [JsAccessible] annotation makes this method callable from the webpage
     * loaded by the [Browser] instance.
     */
    @JsAccessible
    fun print() {
        browser.mainFrame!!.print()
    }
}
