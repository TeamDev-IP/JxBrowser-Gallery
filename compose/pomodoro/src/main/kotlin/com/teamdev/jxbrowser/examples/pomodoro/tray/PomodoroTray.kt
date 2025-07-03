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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toAwtImage
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.MenuScope
import androidx.compose.ui.window.Notification
import androidx.compose.ui.window.TrayState
import androidx.compose.ui.window.rememberTrayState
import androidx.compose.ui.window.setContent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.awt.ComponentOrientation
import java.awt.GraphicsEnvironment
import java.awt.Image
import java.awt.Point
import java.awt.PopupMenu
import java.awt.SystemTray
import java.awt.TrayIcon
import java.awt.image.BufferedImage
import java.util.*
import kotlin.math.round

/**
 * Adds the application icon to the platform taskbar.
 *
 * With this icon, a user can show/hide the Pomodoro window, see the remaining
 * time in the tooltip and quit the application.
 */
@Composable
fun ApplicationScope.PomodoroTray(state: PomodoroTrayState) =
    Tray(
        icon = state.icon.value,
        state = state.composeTray,
        tooltip = state.remainingTime,
        onAction = state::toggleWindowVisibility,
        menu = {
            Item(state.toggleName, onClick = state::toggleWindowVisibility)
            Item("Quit", onClick = ::exitApplication)
        }
    )

@Composable
private fun Tray(
    icon: Painter,
    state: TrayState = rememberTrayState(),
    tooltip: String? = null,
    onAction: () -> Unit = {},
    menu: @Composable MenuScope.() -> Unit = {},
) {
    val currentOnAction by rememberUpdatedState(onAction)
    val currentMenu by rememberUpdatedState(menu)

    val iconImage = rememberIcon(icon)
    val tray = remember {
        TrayIcon(iconImage).apply {
            isImageAutoSize = true
            addActionListener { currentOnAction() }
        }
    }

    val popupMenu = remember { PopupMenu() }

    SideEffect {
        if (tray.image != iconImage) tray.image = iconImage
        if (tray.toolTip != tooltip) tray.toolTip = tooltip
    }

    val composition = rememberCompositionContext()
    val scope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        tray.popupMenu = popupMenu
        val menuComposition = popupMenu.setContent(composition) {
            currentMenu()
        }

        SystemTray.getSystemTray().add(tray)

        state.notificationFlow
            .onEach(tray::displayMessage)
            .launchIn(scope)

        onDispose {
            menuComposition.dispose()
            SystemTray.getSystemTray().remove(tray)
        }
    }
}

@Composable
private fun rememberIcon(icon: Painter): Image {
    val density = GlobalDensity
    val layoutDirection = GlobalLayoutDirection
    val style = remember { TrayStyle(density) }

    return remember(icon) {
        val awtIcon = icon.toAwtImage(density, layoutDirection, style.iconSize.toCompose())
        val compositedIcon = BufferedImage(
            style.boxSize.width,
            style.boxSize.height,
            BufferedImage.TYPE_INT_ARGB
        )
        val graphics = compositedIcon.createGraphics()
        graphics.drawImage(
            awtIcon,
            style.iconPosition.x,
            style.iconPosition.y,
            null
        )
        graphics.dispose()
        compositedIcon
    }
}

private class TrayStyle(density: Density) {
    val boxSize = AwtSize(22.dpToPx(density), 22.dpToPx(density))
    val iconSize = AwtSize(16.dpToPx(density), 16.dpToPx(density))
    val iconPosition = Point(3.dpToPx(density), 3.dpToPx(density))
}

private fun Int.dpToPx(density: Density): Int =
    round(this * density.density).toInt()

private data class AwtSize(val width: Int, val height: Int) {
    fun toCompose(): Size = Size(width.toFloat(), height.toFloat())
}

private fun TrayIcon.displayMessage(notification: Notification) {
    val messageType = when (notification.type) {
        Notification.Type.None -> TrayIcon.MessageType.NONE
        Notification.Type.Info -> TrayIcon.MessageType.INFO
        Notification.Type.Warning -> TrayIcon.MessageType.WARNING
        Notification.Type.Error -> TrayIcon.MessageType.ERROR
    }
    displayMessage(notification.title, notification.message, messageType)
}

internal val GlobalDensity: Density
    get() = GraphicsEnvironment.getLocalGraphicsEnvironment()
        .defaultScreenDevice
        .defaultConfiguration
        .run {
            Density(defaultTransform.scaleX.toFloat(), fontScale = 1f)
        }

internal val GlobalLayoutDirection: LayoutDirection
    get() = if (ComponentOrientation.getOrientation(Locale.getDefault()).isLeftToRight) {
        LayoutDirection.Ltr
    } else {
        LayoutDirection.Rtl
    }
