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

package com.teamdev.jxbrowser.examples.pomodoro.window

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import com.teamdev.jxbrowser.compose.BrowserView
import com.teamdev.jxbrowser.examples.pomodoro.window.timer.RemainingTime
import com.teamdev.jxbrowser.examples.pomodoro.window.timer.TimerControls
import com.teamdev.jxbrowser.examples.pomodoro.window.timer.TimerPicker

/**
 * Creates a window with the timers and their associated WebGL animations.
 */
@Composable
fun PomodoroWindow(state: PomodoroWindowState) {
    PlatformWindow(
        title = state.title,
        isShown = state.isShown,
        onClose = state::hide
    ) {
        OutlinedContainer {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                val animator = state.animator
                OutlinedCard {
                    Column(
                        modifier = Modifier.padding(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val currentTimer = state.currentTimer
                        TimerPicker(
                            timers = state.timers,
                            currentTimer = currentTimer,
                            onSelect = {
                                currentTimer.stop()
                                state.currentTimer = it
                            }
                        )
                        RemainingTime(currentTimer.remainingTime)
                        Spacer(Modifier.height(12.dp))
                        TimerControls(
                            onStart = currentTimer::start,
                            onPause = currentTimer::pause,
                            onStop = currentTimer::stop,
                        )
                        LaunchedEffect(currentTimer) {
                            animator.use(currentTimer.animation)
                        }
                    }
                }
                OutlinedCard {
                    BrowserView(animator.browser)
                }
            }
        }
    }
}

/**
 * Creates a transparent (and thus, undecorated) platform window.
 *
 * Ideally, the window position should be aligned with the position
 * of the tray icon. But, as for now, there's no such API in Compose.
 * So, it is just located somewhere close to the location of all tray icons.
 *
 * See issue: [#289](https://github.com/JetBrains/compose-multiplatform/issues/289).
 */
@Composable
private fun PlatformWindow(
    title: String,
    isShown: Boolean,
    onClose: () -> Unit,
    content: @Composable FrameWindowScope.() -> Unit
) {
    val windowState = rememberWindowState(
        width = 350.dp,
        height = 500.dp,
        position = WindowPosition(1285.dp, 30.dp)
    )
    Window(
        title = title,
        visible = isShown,
        onCloseRequest = onClose,
        state = windowState,
        undecorated = true,
        transparent = true,
        resizable = false,
        content = content
    )
}

/**
 * Creates a top-level container with the outlined rounded edges.
 *
 * This container replaces the default decoration of [PlatformWindow],
 * allowing to configure a semi-transparent background and rounded edges.
 */
@Composable
private fun OutlinedContainer(content: @Composable BoxScope.() -> Unit) {
    val fillMaxSize = Modifier.fillMaxSize()
    Surface(
        modifier = fillMaxSize,
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colors.background,
        border = outlinedBorder()
    ) {
        Box(
            modifier = fillMaxSize.padding(10.dp),
            content = content
        )
    }
}

/**
 * Creates a card with the outlined rounded edges to be used
 * inside [OutlinedContainer].
 */
@Composable
private fun OutlinedCard(content: @Composable () -> Unit) {
    Card(
        elevation = 0.dp,
        border = outlinedBorder(),
        content = content
    )
}

/**
 * Creates a tiny border.
 *
 * `0.dp` indeed creates the smallest possible border.
 */
@Composable
private fun outlinedBorder() = BorderStroke(0.dp, MaterialTheme.colors.onBackground)
