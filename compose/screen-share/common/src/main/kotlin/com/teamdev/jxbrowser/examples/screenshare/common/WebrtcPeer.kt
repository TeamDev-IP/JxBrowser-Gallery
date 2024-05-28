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
import com.teamdev.jxbrowser.frame.Frame
import java.io.File
import java.lang.Thread.sleep

abstract class WebrtcPeer(
    browser: Browser,
    webPage: String,
) {

    private companion object {

        /**
         * Blocking interval in milliseconds, which is used to block
         * the current thread until the [webPage] complete loading.
         */
        const val POLLING_INTERVAL = 150L
    }

    protected val frame = browser.mainFrame!!.also {
        it.loadWebrtcSender(webPage)
    }

    /**
     * Connects to the given signaling [server].
     */
    fun connect(server: SignalingServer) =
        frame.executeJavaScript<Unit>("connect(${server.asJsObject()})")

    /**
     * Loads [WEBRTC_SENDER_PAGE] into this [Frame].
     *
     * Please note, the file content is not passed to JxBrowser "as is"
     * (`frame.loadHtml(content)`) intentionally. Such a loading is performed
     * via `data:` scheme, which prohibits access to media devices because pages
     * loaded this way are not considered secure.
     *
     * See also: [Secure Contexts](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts)
     *           [Chromium #40135832](https://issues.chromium.org/issues/40135832?pli=1)
     */
    private fun Frame.loadWebrtcSender(webPage: String) {
        val file = withTempFile(webPage)
        loadUrl(file.absolutePath)
        while (!pageLoaded()) {
            sleep(POLLING_INTERVAL)
        }
    }

    /**
     * Checks if WebRTC sender page has completed loading.
     *
     * The page exports `connect()` method to a global scope. It is considered
     * loaded when `connect` variable is initialized.
     */
    private fun Frame.pageLoaded(): Boolean = hasGlobalVariable("connect")

    private fun Frame.hasGlobalVariable(name: String) =
        executeJavaScript<Boolean>("$name != null") == true
}

/**
 * Reads the content of the given [resourcesFile] and writes it
 * to a temporary file.
 */
private fun withTempFile(resourcesFile: String): File {
    val webPage = ::withTempFile.javaClass.getResource(resourcesFile)!!
    val content = webPage.readBytes()
    val file = kotlin.io.path.createTempFile().toFile()
    file.writeBytes(content)
    return file
}
