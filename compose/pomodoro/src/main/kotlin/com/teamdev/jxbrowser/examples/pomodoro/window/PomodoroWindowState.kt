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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.teamdev.jxbrowser.examples.pomodoro.window.animation.AnimatedModel
import com.teamdev.jxbrowser.examples.pomodoro.window.timer.TimerState
import com.teamdev.jxbrowser.examples.pomodoro.window.animation.Animator
import com.teamdev.jxbrowser.examples.pomodoro.window.animation.AnimatorTimerListener
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
