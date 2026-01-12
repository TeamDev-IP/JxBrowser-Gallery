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

package com.teamdev.jxbrowser.examples.pomodoro.tray

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toAwtImage
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.toSize
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
import kotlin.math.roundToInt

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

/**
 * Displays a tray icon in the system taskbar.
 *
 * We intentionally use a custom composable for tray handling. It renders
 * the icon into a fixed-size [BufferedImage] with padding to ensure consistent
 * tray icon appearance across platforms.
 */
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
    val composition = rememberCompositionContext()
    val scope = rememberCoroutineScope()
    val popupMenu = remember { PopupMenu() }
    val iconImage = rememberIcon(icon)

    val tray = remember {
        TrayIcon(iconImage).apply {
            isImageAutoSize = true
            addActionListener { currentOnAction() }
        }
    }

    SideEffect {
        if (tray.image != iconImage) tray.image = iconImage
        if (tray.toolTip != tooltip) tray.toolTip = tooltip
    }

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

/**
 * Creates and remembers the AWT [Image] icon from the given [icon].
 *
 * It renders the icon into a [BufferedImage] with a fixed box size.
 * This ensures consistent tray icon appearance across platforms.
 */
@Composable
private fun rememberIcon(icon: Painter): Image {
    // Using `LocalDensity` here is not appropriate because the tray's density does not match it.
    // The tray's density corresponds to the density of the screen where it is displayed.
    val density = GlobalDensity
    val layoutDirection = GlobalLayoutDirection
    val style = remember { TrayStyle(density) }

    return remember(icon) {
        val awtIcon = icon.toAwtImage(density, layoutDirection, style.iconSize.toSize())
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

/**
 * Defines tray icon layout parameters based on screen density.
 */
private class TrayStyle(private val density: Density) {
    val boxSize = IntSize(22.scaled, 22.scaled)
    val iconSize = IntSize(16.scaled, 16.scaled)
    val iconPosition = Point(3.scaled, 3.scaled)

    /**
     * Scales pixel value to fit the screen density.
     */
    private val Int.scaled: Int
        get() = (this * density.density).roundToInt()
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

/**
 * Gets the screen density.
 *
 * This value is constant and suitable for use with components
 * rendered outside the Compose window (e.g., AWT tray icon).
 */
internal val GlobalDensity: Density
    get() = GraphicsEnvironment.getLocalGraphicsEnvironment()
        .defaultScreenDevice
        .defaultConfiguration
        .run {
            Density(defaultTransform.scaleX.toFloat(), fontScale = 1f)
        }

/**
 * Gets the global layout direction.
 *
 * This value is constant and suitable for use with components
 * rendered outside the Compose window.
 */
internal val GlobalLayoutDirection: LayoutDirection
    get() = if (ComponentOrientation.getOrientation(Locale.getDefault()).isLeftToRight) {
        LayoutDirection.Ltr
    } else {
        LayoutDirection.Rtl
    }
