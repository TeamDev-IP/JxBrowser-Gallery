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

package com.teamdev.jxbrowser.examples.screenshare.sender

import com.teamdev.jxbrowser.browser.Browser
import com.teamdev.jxbrowser.browser.callback.StartCaptureSessionCallback
import com.teamdev.jxbrowser.browser.callback.StartCaptureSessionCallback.Action
import com.teamdev.jxbrowser.browser.callback.StartCaptureSessionCallback.Params
import com.teamdev.jxbrowser.browser.event.CaptureSessionStarted
import com.teamdev.jxbrowser.capture.AudioCaptureMode
import com.teamdev.jxbrowser.capture.event.CaptureSessionStopped
import com.teamdev.jxbrowser.dsl.browser.mainFrame
import com.teamdev.jxbrowser.dsl.register
import com.teamdev.jxbrowser.dsl.subscribe
import com.teamdev.jxbrowser.examples.screenshare.common.SignalingServer
import com.teamdev.jxbrowser.examples.screenshare.common.asJsObject
import com.teamdev.jxbrowser.frame.Frame
import java.io.File
import java.lang.Thread.sleep
import kotlin.io.path.createTempFile

/**
 *
 */
internal class WebrtcSender(
    browser: Browser,
    onSharingStarted: () -> Unit,
    onSharingStopped: () -> Unit,
) {

    private companion object {

        /**
         * Path to a web page in resources that uses WebRTC to send a video
         * stream of the shared screen.
         */
        const val WEBRTC_SENDER_PAGE = "/sending-peer.html"

        /**
         * Blocking interval in milliseconds, which is used to block
         * the current thread until the [WEBRTC_SENDER_PAGE] complete loading.
         */
        const val POLLING_INTERVAL = 150L
    }

    private val frame = browser.mainFrame!!.also {
        it.loadWebrtcSender()
    }

    init {
        // Select a source when the browser is about to start a capturing session.
        browser.register(StartCaptureSessionCallback { params: Params, tell: Action ->
            val primaryScreen = params.sources().screens()[0]
            tell.selectSource(primaryScreen, AudioCaptureMode.CAPTURE)
        })
        // Integrate `onSessionStarted` and `onSessionStopped` callbacks.
        browser.subscribe<CaptureSessionStarted> { event: CaptureSessionStarted ->
            onSharingStarted()
            event.capture().subscribe<CaptureSessionStopped> {
                onSharingStopped()
            }
        }
    }

    /**
     * Connects to the given signaling [server].
     */
    fun connect(server: SignalingServer) =
        frame.executeJavaScript<Unit>("connect(${server.asJsObject()})")

    /**
     * Starts screen sharing session.
     */
    fun startScreenSharing() =
        frame.executeJavaScript<Unit>("startScreenSharing()")

    /**
     * Stops screen sharing session.
     */
    fun stopScreenSharing() =
        frame.executeJavaScript<Unit>("stopScreenSharing()")

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
    private fun Frame.loadWebrtcSender() {
        val file = withTempFile(WEBRTC_SENDER_PAGE)
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
    val file = createTempFile().toFile()
    file.writeBytes(content)
    return file
}
