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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
 * An application that shares the primary screen.
 */
fun main() = singleWindowApplication(
    state = WindowState(width = 400.dp, height = 300.dp),
    title = "Screen share"
) {
    val engine = remember { createEngine() }
    val browser = remember { engine.newBrowser() }

    var isSharing by remember { mutableStateOf(false) }
    val webrtc = remember {
        WebrtcSender(
            browser,
            onSharingStarted = { isSharing = true },
            onSharingStopped = { isSharing = false }
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isSharing) {
            Button(webrtc::stopScreenSharing) {
                Text("Stop sharing")
            }
        } else {
            Button(webrtc::startScreenSharing) {
                Text("Start sharing")
            }
        }
    }

    // Supposing it connects immediately.
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
