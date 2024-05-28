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

internal class WebrtcSender(browser: Browser) : WebrtcPeer(browser, "/sending-peer.html") {

    var isSharing by mutableStateOf(false)
        private set

    init {
        // Select a source when the browser is about to start a capturing session.
        browser.register(StartCaptureSessionCallback { params: Params, tell: Action ->
            val primaryScreen = params.sources().screens()[0]
            tell.selectSource(primaryScreen, AudioCaptureMode.CAPTURE)
        })
        // Integrate `onSessionStarted` and `onSessionStopped` callbacks.
        browser.subscribe<CaptureSessionStarted> { event: CaptureSessionStarted ->
            isSharing = true
            event.capture().subscribe<CaptureSessionStopped> {
                isSharing = false
            }
        }
    }

    /**
     * Starts screen sharing session.
     */
    fun startScreenSharing() = executeJavaScript("startScreenSharing()")

    /**
     * Stops screen sharing session.
     */
    fun stopScreenSharing() = executeJavaScript("stopScreenSharing()")
}
