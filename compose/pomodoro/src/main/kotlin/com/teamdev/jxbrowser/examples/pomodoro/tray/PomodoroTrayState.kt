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

package com.teamdev.jxbrowser.examples.pomodoro.tray

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.Notification
import androidx.compose.ui.window.Notification.Type
import androidx.compose.ui.window.TrayState
import com.teamdev.jxbrowser.examples.pomodoro.window.PomodoroWindowState
import com.teamdev.jxbrowser.examples.pomodoro.window.timer.TimerState

/**
 * State for [PomodoroTray].
 */
class PomodoroTrayState(private val window: PomodoroWindowState) {

    /**
     * The current tray icon.
     */
    val icon = mutableStateOf(Icons.stop)

    /**
     * The remaining time of the currently active timer.
     */
    val remainingTime
        get() = window.currentTimer.remainingTime

    /**
     * Toggle name for the window visibility switcher.
     */
    val toggleName
        get() = if (window.isShown) "Hide" else "Show"

    /**
     * A built-in state for Compose trays.
     *
     * Pomodoro doesn't create the tray from scratch, but rather wraps
     * the built-in Compose implementation, and this is its state.
     *
     * Currently, it only allows sending notifications.
     */
    val composeTray = TrayState()

    init {
        window.timers.forEach { timer ->
            val notification = timerFinished(window.title, timer)
            val listener = TrayTimerListener(notification, composeTray, icon)
            timer.registerListener(listener)
        }
    }

    /**
     * Switches the window visibility.
     */
    fun toggleWindowVisibility() {
        window.isShown = window.isShown.not()
    }
}

@Suppress("SameParameterValue") // Effectively, `window.title` is a constant.
private fun timerFinished(title: String, timer: TimerState) = Notification(
    title = title,
    message = """
        ${timer.displayName} has finished.
        ${timer.finishPrompt}
    """.trimIndent(),
    type = Type.Info
)
