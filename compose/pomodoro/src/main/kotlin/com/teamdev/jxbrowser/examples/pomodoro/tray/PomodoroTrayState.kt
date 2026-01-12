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
