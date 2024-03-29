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

package com.teamdev.jxbrowser.examples.pomodoro.window.timer

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.teamdev.jxbrowser.examples.pomodoro.window.animation.AnimatedModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * A countdown clock that starts from the specified time duration and
 * stops when reaching zero.
 *
 * Accepts [TimerListener]s to notify them about the timer events.
 *
 * @param [displayName] The timer name for UI.
 * @param [finishPrompt] A message suggesting what to do next.
 * @param [animation] The associated animation.
 * @param [duration] The countdown duration.
 * @param [scope] A coroutine scope in which the time is counted.
 */
class TimerState(
    val displayName: String,
    val finishPrompt: String,
    val animation: AnimatedModel,
    private val duration: Duration,
    private val scope: CoroutineScope,
) {

    private companion object {
        val second = 1.seconds
    }

    private var _remaining by mutableStateOf(duration)
    private val listeners = mutableListOf<TimerListener>()
    private var timerJob: Job? = null

    /**
     * The remaining time.
     *
     * Use this property in Composables to receive the updated remaining time.
     * The time is formatted to the following string: `MM:SS`.
     */
    val remainingTime by derivedStateOf { _remaining.format() }

    /**
     * Starts the timer.
     */
    fun start() {
        timerJob?.cancel()
        timerJob = scope.launch {
            while (_remaining.isPositive()) {
                delay(second)
                _remaining = _remaining.minus(second)
            }
            listeners.forEach { it.onFinish() }
            _remaining = duration
        }
        listeners.forEach { it.onStart() }
    }

    /**
     * Pauses the timer.
     */
    fun pause() {
        timerJob?.cancel()
        listeners.forEach { it.onPause() }
    }

    /**
     * Stops the timer, resetting the [remainingTime]
     * to the [initial value][duration].
     */
    fun stop() {
        _remaining = duration
        timerJob?.cancel()
        listeners.forEach { it.onStop() }
    }

    /**
     * Registers [TimerListener] to notify it about the timer events.
     */
    fun registerListener(listener: TimerListener) {
        listeners.add(listener)
    }
}

/**
 * Returns string representation of this [Duration] in `MM:SS` format.
 */
private fun Duration.format() = toComponents { _, minutes, seconds, _ ->
    "%02d:%02d".format(minutes, seconds)
}
