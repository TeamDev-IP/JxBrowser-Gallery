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

package com.teamdev.jxbrowser.examples.screenshare.common

import com.teamdev.jxbrowser.browser.Browser
import com.teamdev.jxbrowser.dsl.browser.mainFrame
import com.teamdev.jxbrowser.dsl.browser.navigation
import java.io.File
import kotlin.io.path.createTempFile

/**
 * An abstract implementation of a WebRTC peer based on PeerJs and JxBrowser.
 *
 * WebRTC API is accessible only from browsers. As so, this class relies
 * on both JavaScript code (which actually invokes the API) and the browser
 * instance to run this code from JVM.
 *
 * @param [browser] JxBrowser instance allowing using Chromium from JVM.
 * @param [webPage] Path to a file in the app resources, containing JS code
 *  that actually invokes WebRTC API.
 */
abstract class WebrtcPeer(
    browser: Browser,
    webPage: String,
) {

    private val frame = browser.mainFrame!!

    init {
        browser.loadWebPage(webPage)
    }

    /**
     * Connects this [WebrtcPeer] to the given signaling [server].
     */
    fun connect(server: SignalingServer) {
        val connectionString = server.asJsObject()
        executeJavaScript("connect($connectionString)")
    }

    /**
     * Executes the given JavaScript code in the context
     * of the [loaded web page][loadWebPage].
     */
    protected fun executeJavaScript(javaScript: String) =
        frame.executeJavaScript<Unit>(javaScript)

    /**
     * Loads the given [webPage] into this [Browser] from the app resources.
     *
     * Please note, the file content is not passed to JxBrowser "as is"
     * (`frame.loadHtml(content)`) intentionally. Such a loading is performed
     * via `data:` scheme, which prohibits access to media devices because pages
     * loaded this way are not considered secure.
     *
     * The file content is put to a temporary file to work around
     * this restriction. Pages loaded via `file:` scheme are considered secure.
     *
     * See also: [Secure Contexts](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts)
     *           [Chromium #40135832](https://issues.chromium.org/issues/40135832?pli=1)
     *
     * @param [webPage] Path to a web page in the app resources.
     */
    private fun Browser.loadWebPage(webPage: String) {
        val file = tempFile(webPage)
        navigation.loadUrlAndWait(file.absolutePath)
    }

    /**
     * Reads the given [webPage] file from the app resources, and writes
     * its content to a temporary HTML file.
     */
    private fun tempFile(webPage: String): File {
        val url = this::tempFile.javaClass.getResource(webPage)!!
        val content = url.readBytes()
        val file = createTempFile(suffix = ".html").toFile()
        return file.also { it.writeBytes(content) }
    }
}
