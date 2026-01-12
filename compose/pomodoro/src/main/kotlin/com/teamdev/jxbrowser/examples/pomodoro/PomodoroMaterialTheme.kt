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

package com.teamdev.jxbrowser.examples.pomodoro

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Configures [MaterialTheme] for Pomodoro.
 */
@Composable
fun PomodoroMaterialTheme(content: @Composable () -> Unit) = MaterialTheme(
    colors = bluishColors(),
    shapes = roundedShapes(),
    content = content,
)

/**
 * Returns either bluish dark or bluish light colors based on
 * the current system theme.
 */
@Composable
@Suppress("MagicNumber") // Colors are defined using RGB components.
private fun bluishColors(): Colors {
    val bluish = Color(80, 140, 240)
    val whitish = Color(230, 230, 230)
    val eastBay = Color(80, 90, 110)
    return if (isSystemInDarkTheme()) {
        val bluishDark = Color(30, 40, 60)
        darkColors().copy(
            primary = bluish,
            onPrimary = whitish,
            background = bluishDark.copy(alpha = 0.75f),
            onBackground = eastBay,
            surface = bluishDark,
        )
    } else {
        val bluishLight = Color(140, 150, 180)
        lightColors().copy(
            primary = bluish,
            onPrimary = whitish,
            background = bluishLight.copy(alpha = 0.75f),
            onBackground = eastBay,
            surface = bluishLight,
        )
    }
}

private fun roundedShapes() = Shapes(
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(8.dp),
)
