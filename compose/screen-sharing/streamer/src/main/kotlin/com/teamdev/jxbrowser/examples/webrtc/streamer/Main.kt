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

package com.teamdev.jxbrowser.examples.webrtc.streamer

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
import com.teamdev.jxbrowser.browser.event.ConsoleMessageReceived
import com.teamdev.jxbrowser.capture.AudioCaptureMode
import com.teamdev.jxbrowser.capture.CaptureSession
import com.teamdev.jxbrowser.dsl.Engine
import com.teamdev.jxbrowser.dsl.browser.mainFrame
import com.teamdev.jxbrowser.dsl.browser.navigation
import com.teamdev.jxbrowser.dsl.register
import com.teamdev.jxbrowser.dsl.subscribe
import com.teamdev.jxbrowser.engine.Engine
import com.teamdev.jxbrowser.engine.RenderingMode.OFF_SCREEN
import com.teamdev.jxbrowser.frame.Frame
import com.teamdev.jxbrowser.js.JsAccessible
import com.teamdev.jxbrowser.js.JsObject
import com.teamdev.jxbrowser.license.internal.LicenseProvider
import java.io.File
import java.lang.Thread.sleep

/**
 * An application that shares the primary screen.
 */
fun main() {
    val engine = createEngine()
    val browser = engine.newBrowser()

    // The capture session is stored as observable Compose state.
    var captureSession by mutableStateOf<CaptureSession?>(null)

    browser.subscribe<ConsoleMessageReceived> { event ->
        println(event.consoleMessage().message())
    }

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

    // Loads the page connected to WebRTC server and returns
    // browser's main frame.
    val mainFrame = browser.loadWebrtcStreamer()
    mainFrame.executeJavaScript<Unit>("connect({host: \"localhost\", port: 9000})")

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
                    mainFrame.executeJavaScript<Unit>("startScreenSharing()")
                }) {
                    Text("Start sharing")
                }
            } else {
                Button(onClick = {
                    mainFrame.executeJavaScript<Unit>("stopScreenSharing()")
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

private fun Browser.loadWebrtcStreamer(): Frame {
    val streamer = File("src/main/resources/streamer.html")
    navigation.loadUrl(streamer.absolutePath)
    while (!streamLoaded()) {
        sleep(150L) // extract.
    }
    return mainFrame!!
}

/**
 * Checks if WebRTC streamer has completed loading.
 *
 * The page is configured to export...
 */
private fun Browser.streamLoaded(): Boolean {
    if (mainFrame == null) {
        // The page itself hasn't loaded yet.
        return false
    }

    val frame = mainFrame!!
    return frame.hasGlobalVariable("connect")
}

private fun Frame.hasGlobalVariable(name: String) =
    executeJavaScript<Boolean>("$name != null") == true
