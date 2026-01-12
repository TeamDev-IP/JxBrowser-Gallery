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

import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource

/**
 * Icons used in the tray.
 *
 * The tray changes the used icon depending on the state of the currently
 * used timer.
 *
 * These icons are [free](https://bit.ly/3Tjmsqg) for personal and commercial
 * use with attribution.
 *
 * Created by [Hilmy Abiyyu A.](https://www.flaticon.com/authors/hilmy-abiyyu-a).
 */
// TODO:2024-03-18:yevhenii.nadtochii: They need to be re-sized.
// See issue: https://github.com/TeamDev-IP/JxBrowser-Gallery/issues/7
object Icons {
    val pause = BitmapPainter(useResource("icons/pause.png", ::loadImageBitmap))
    val play = BitmapPainter(useResource("icons/play.png", ::loadImageBitmap))
    val stop = BitmapPainter(useResource("icons/stop.png", ::loadImageBitmap))
}
