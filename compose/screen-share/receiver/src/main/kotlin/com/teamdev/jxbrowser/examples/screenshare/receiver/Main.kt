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

package com.teamdev.jxbrowser.examples.screenshare.receiver

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.window.singleWindowApplication
import com.teamdev.jxbrowser.browser.Browser
import com.teamdev.jxbrowser.browser.event.ConsoleMessageReceived
import com.teamdev.jxbrowser.compose.BrowserView
import com.teamdev.jxbrowser.dsl.Engine
import com.teamdev.jxbrowser.dsl.browser.navigation
import com.teamdev.jxbrowser.dsl.subscribe
import com.teamdev.jxbrowser.engine.Engine
import com.teamdev.jxbrowser.engine.RenderingMode.OFF_SCREEN
import com.teamdev.jxbrowser.license.internal.LicenseProvider
import kotlin.io.path.createTempFile

/**
 * An application that displays the content of the shared screen.
 */
fun main() = singleWindowApplication(title = "Screen Viewer") {
    val engine = remember { createEngine() }
    val browser = remember { engine.newBrowser() }

    browser.subscribe<ConsoleMessageReceived> { event ->
        println(event.consoleMessage().message())
    }

    BrowserView(browser)
    LaunchedEffect(Unit) {
        browser.loadWebrtcReceiver()
    }
}

private fun createEngine(): Engine = Engine(OFF_SCREEN) {
    options {
        license = LicenseProvider.license
    }
}

/**
 * Loads [WEBRTC_RECEIVER] file from resource and passes its content
 * to this [Browser] instance.
 */
private fun Browser.loadWebrtcReceiver() {
    val content = this.javaClass.getResource(WEBRTC_RECEIVER)!!.readBytes()
    val file = createTempFile().toFile().also { it.writeBytes(content) }
    navigation.loadUrl(file.absolutePath)
}

/**
 * Path to a file in resources with JavaScript code, which uses WebRTC
 * to receive a video stream of the shared screen.
 */
private const val WEBRTC_RECEIVER = "/receiving-peer.html"
