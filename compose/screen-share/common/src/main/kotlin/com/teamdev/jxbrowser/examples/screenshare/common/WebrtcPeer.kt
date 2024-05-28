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
