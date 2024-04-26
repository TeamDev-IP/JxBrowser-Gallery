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
import com.teamdev.jxbrowser.browser.callback.PrintCallback
import com.teamdev.jxbrowser.browser.callback.PrintHtmlCallback
import com.teamdev.jxbrowser.dsl.browser.mainFrame
import com.teamdev.jxbrowser.dsl.browser.navigation
import com.teamdev.jxbrowser.dsl.register
import com.teamdev.jxbrowser.print.event.PrintCompleted
import java.nio.file.Path
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

const val PRINT_TIMEOUT_SECONDS = 5L

fun Browser.printToPdfAndWait(url: String, dest: Path) {
    val latch = CountDownLatch(1)
    register(PrintCallback)
    register(printHtmlCallback(dest) {
        latch.countDown()
    })
    navigation.loadUrlAndWait(url)
    mainFrame!!.print()
    latch.await(PRINT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
}

private val PrintCallback = PrintCallback { _, tell -> tell.print() }

private fun printHtmlCallback(destination:Path, onCompleted: () -> Unit): PrintHtmlCallback {
    return PrintHtmlCallback { params: PrintHtmlCallback.Params,
                               tell: PrintHtmlCallback.Action ->
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
