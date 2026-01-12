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

package com.teamdev.jxbrowser.examples.pomodoro.window.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

/**
 * Creates a set of filter chips that allow users to change
 * the currently used timer.
 */
@Composable
fun TimerPicker(
    timers: List<TimerState>,
    currentTimer: TimerState,
    onSelect: (TimerState) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        timers.forEach { timer ->
            TimerChip(
                name = timer.displayName,
                selected = timer == currentTimer,
                onSelect = { onSelect(timer) }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TimerChip(name: String, selected: Boolean, onSelect: () -> Unit) {
    val chipColors = ChipDefaults.filterChipColors(
        backgroundColor = Color.Transparent,
        selectedBackgroundColor = MaterialTheme.colors.primary
    )
    FilterChip(
        selected = selected,
        onClick = onSelect,
        shape = MaterialTheme.shapes.medium,
        colors = chipColors,
    ) {
        Text(
            text = name,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Light
        )
    }
}
