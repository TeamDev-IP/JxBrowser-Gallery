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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.teamdev.jxbrowser.examples.pomodoro.window.animation.AnimatedModel
import com.teamdev.jxbrowser.examples.pomodoro.window.animation.Animator
import com.teamdev.jxbrowser.examples.pomodoro.window.animation.AnimatorTimerListener
import com.teamdev.jxbrowser.examples.pomodoro.window.timer.TimerState
import kotlinx.coroutines.CoroutineScope
import kotlin.time.Duration.Companion.minutes

/**
 * State of [PomodoroWindow].
 *
 * @param [scope] A coroutine scope for running timer jobs.
 */
class PomodoroWindowState(scope: CoroutineScope) : AutoCloseable {

    /**
     * All timers used in Pomodoro technic.
     */
    val timers = listOf(
        TimerState("Pomodoro", "Time to break!", AnimatedModel.TOMATO, 25.minutes, scope),
        TimerState("Short Break", "Time to focus!", AnimatedModel.ESPRESSO, 5.minutes, scope),
        TimerState("Long Break", "Time to focus!", AnimatedModel.LATTE, 15.minutes, scope)
    )

    /**
     * Window's title.
     */
    val title = "Pomodoro"

    /**
     * The currently active timer.
     */
    var currentTimer by mutableStateOf(timers.first())

    /**
     * Controls WebGL animations.
     */
    val animator = Animator()

    /**
     * Whether the window is shown.
     */
    var isShown by mutableStateOf(true)

    init {
        // Binds the animator to events of each timer (starting, pausing
        // and stopping), so that the animator could react on them.
        val animatorListener = AnimatorTimerListener(animator)
        timers.forEach { timer ->
            timer.registerListener(animatorListener)
        }
    }

    /**
     * Sets the window visibility to `false`.
     */
    fun hide() {
        isShown = false
    }

    override fun close() {
        animator.close()
    }
}
