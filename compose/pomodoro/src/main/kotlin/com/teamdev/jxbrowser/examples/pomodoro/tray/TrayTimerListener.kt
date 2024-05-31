/*
 *  Copyright (c) 2024 TeamDev
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
