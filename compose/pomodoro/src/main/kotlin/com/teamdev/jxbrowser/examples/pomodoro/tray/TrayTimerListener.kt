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

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.window.Notification
import androidx.compose.ui.window.TrayState
import com.teamdev.jxbrowser.examples.pomodoro.window.timer.TimerListener

/**
 * A listener used by tray to update the icon and send notifications
 * in response to timer state updates.
 *
 * @param [finishNotification] a notification to send when a timer finishes.
 * @param [composeTray] a Compose tray used for sending notifications.
 * @param [currentIcon] a holder of the current tray icon.
 */
class TrayTimerListener(
    private val finishNotification: Notification,
    private val composeTray: TrayState,
    private val currentIcon: MutableState<BitmapPainter>
) : TimerListener {

    override fun onStart() {
        currentIcon.value = Icons.play
    }

    override fun onPause() {
        currentIcon.value = Icons.pause
    }

    override fun onStop() {
        currentIcon.value = Icons.stop
    }

    override fun onFinish() {
        currentIcon.value = Icons.stop
        composeTray.sendNotification(finishNotification)
    }
}
