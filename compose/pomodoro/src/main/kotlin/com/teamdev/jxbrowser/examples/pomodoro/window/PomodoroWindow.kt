/*
 *  Copyright 2026, TeamDev
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
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.teamdev.jxbrowser.examples.pomodoro.window.timer.RemainingTime
import com.teamdev.jxbrowser.examples.pomodoro.window.timer.TimerControls
import com.teamdev.jxbrowser.examples.pomodoro.window.timer.TimerPicker
import com.teamdev.jxbrowser.view.compose.BrowserView

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
        shape = MaterialTheme.shapes.large.copy(all = CornerSize(10.dp)),
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
        shape = RoundedCornerShape(5.dp),
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
