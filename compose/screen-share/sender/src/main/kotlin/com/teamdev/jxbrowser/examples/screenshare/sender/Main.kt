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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.teamdev.jxbrowser.dsl.Engine
import com.teamdev.jxbrowser.engine.Engine
import com.teamdev.jxbrowser.engine.RenderingMode.OFF_SCREEN
import com.teamdev.jxbrowser.examples.screenshare.common.SignalingServer
import com.teamdev.jxbrowser.license.internal.LicenseProvider

/**
 * A Compose application that shares the primary screen.
 */
fun main() = singleWindowApplication(
    state = WindowState(width = 400.dp, height = 300.dp),
    title = "Screen share"
) {
    val engine = remember { createEngine() }
    val browser = remember { engine.newBrowser() }
    val webrtc = remember { WebrtcSender(browser) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (webrtc.isSharing) {
            Button(webrtc::stopScreenSharing) {
                Text("Stop sharing")
            }
        } else {
            Button(webrtc::startScreenSharing) {
                Text("Start sharing")
            }
        }
    }

    // Supposing it connects immediately to the local server.
    LaunchedEffect(Unit) {
        webrtc.connect(SIGNALING_SERVER)
    }
}

private fun createEngine(): Engine = Engine(OFF_SCREEN) {
    options {
        license = LicenseProvider.license
    }
}

private val SIGNALING_SERVER = run {
    val port = System.getProperty("server.port").toInt()
    SignalingServer("localhost", port)
}
