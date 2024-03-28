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
