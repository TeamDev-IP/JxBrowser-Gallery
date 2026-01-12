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

package com.teamdev.jxbrowser.examples.screenshare.sender

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.teamdev.jxbrowser.browser.Browser
import com.teamdev.jxbrowser.browser.callback.StartCaptureSessionCallback
import com.teamdev.jxbrowser.browser.callback.StartCaptureSessionCallback.Action
import com.teamdev.jxbrowser.browser.callback.StartCaptureSessionCallback.Params
import com.teamdev.jxbrowser.browser.event.CaptureSessionStarted
import com.teamdev.jxbrowser.capture.AudioCaptureMode
import com.teamdev.jxbrowser.capture.event.CaptureSessionStopped
import com.teamdev.jxbrowser.dsl.register
import com.teamdev.jxbrowser.dsl.subscribe
import com.teamdev.jxbrowser.examples.screenshare.common.WebrtcPeer

/**
 * A WebRTC sender peer.
 *
 * The sender allows starting and stopping a screen sharing session.
 *
 * @param [browser] The browser instance used for running JavaScript code,
 *  which actually invokes WebRTC API.
 */
internal class WebrtcSender(browser: Browser) : WebrtcPeer(browser, "/sending-peer.html") {

    /**
     * Says whether this sender has an active screen sharing session.
     */
    var isSharing by mutableStateOf(false)
        private set

    init {
        // Select a source when the browser is about to start a capturing session.
        browser.register(StartCaptureSessionCallback { params: Params, tell: Action ->
            val primaryScreen = params.sources().screens()[0]
            tell.selectSource(primaryScreen, AudioCaptureMode.CAPTURE)
        })
        // Update `isSharing` state variable as a session starts and stops.
        browser.subscribe<CaptureSessionStarted> { event: CaptureSessionStarted ->
            isSharing = true
            event.capture().subscribe<CaptureSessionStopped> {
                isSharing = false
            }
        }
    }

    /**
     * Starts a screen sharing session.
     */
    fun startScreenSharing() = executeJavaScript("startScreenSharing()")

    /**
     * Stops the screen sharing session.
     */
    fun stopScreenSharing() = executeJavaScript("stopScreenSharing()")
}
