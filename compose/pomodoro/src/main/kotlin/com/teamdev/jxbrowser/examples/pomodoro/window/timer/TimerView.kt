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

package com.teamdev.jxbrowser.examples.pomodoro.window.timer

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Shows the remaining timer.
 */
@Composable
fun RemainingTime(value: String) {
    Text(
        text = value,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.ExtraLight,
        fontSize = 48.sp
    )
}

/**
 * Creates a set of buttons to control timer's behavior.
 */
@Composable
fun TimerControls(
    onStart: () -> Unit,
    onPause: () -> Unit,
    onStop: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = spacedBy(16.dp, CenterHorizontally)
    ) {
        Button(onClick = onStart) {
            Text("Start", fontWeight = FontWeight.Light)
        }
        OutlinedButton(onClick = onPause, colors = outlinedColors()) {
            Text("Pause", fontWeight = FontWeight.Light)
        }
        OutlinedButton(onClick = onStop, colors = outlinedColors()) {
            Text("Stop", fontWeight = FontWeight.Light)
        }
    }
}

/**
 * Overrides the default `primary` color for better readability
 * in the light theme.
 */
@Composable
private fun outlinedColors() = ButtonDefaults.outlinedButtonColors(
    contentColor = MaterialTheme.colors.onSurface
)
