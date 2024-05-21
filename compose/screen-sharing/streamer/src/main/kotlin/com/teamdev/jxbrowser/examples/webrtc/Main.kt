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

package com.teamdev.jxbrowser.examples.webrtc

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.teamdev.jxbrowser.browser.Browser
import com.teamdev.jxbrowser.browser.callback.StartCaptureSessionCallback
import com.teamdev.jxbrowser.browser.callback.StartCaptureSessionCallback.Action
import com.teamdev.jxbrowser.browser.callback.StartCaptureSessionCallback.Params
import com.teamdev.jxbrowser.browser.event.CaptureSessionStarted
import com.teamdev.jxbrowser.capture.AudioCaptureMode
import com.teamdev.jxbrowser.capture.CaptureSession
import com.teamdev.jxbrowser.dsl.Engine
import com.teamdev.jxbrowser.dsl.browser.mainFrame
import com.teamdev.jxbrowser.dsl.browser.navigation
import com.teamdev.jxbrowser.dsl.register
import com.teamdev.jxbrowser.dsl.subscribe
import com.teamdev.jxbrowser.engine.Engine
import com.teamdev.jxbrowser.engine.RenderingMode.OFF_SCREEN
import com.teamdev.jxbrowser.license.internal.LicenseProvider

/**
 * An application that shares the primary screen.
 */
fun main() {
    val engine = createEngine()
    val browser = engine.newBrowser().apply { loadLocalhost() }
    var captureSession by mutableStateOf<CaptureSession?>(null)

    // Select a source when the browser is about to start a capturing session.
    browser.register(StartCaptureSessionCallback { params: Params, tell: Action ->
        // Let's share the entire screen.
        val screen = params.sources().screens()[0]
        tell.selectSource(screen, AudioCaptureMode.CAPTURE)
    })

    // When the capture session starts, save it to stop later.
    browser.subscribe<CaptureSessionStarted> { event: CaptureSessionStarted ->
        captureSession = event.capture()
    }

    singleWindowApplication(
        state = WindowState(width = 400.dp, height = 300.dp),
        title = "Streamer"
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (captureSession == null) {
                Button(onClick = {
                    browser.mainFrame?.executeJavaScript<Any>("startScreenSharing()")
                }) {
                    Text("Start sharing")
                }
            } else {
                Button(onClick = {
                    captureSession!!.stop()
                    captureSession = null
                }) {
                    Text("Stop sharing")
                }
            }
        }
    }
}

private fun createEngine(): Engine = Engine(OFF_SCREEN) {
    options {
        license = LicenseProvider.license
    }
}

private fun Browser.loadLocalhost() {
    val port = System.getProperty("server.port")
    navigation.loadUrlAndWait("http://localhost:$port/streamer")
}
