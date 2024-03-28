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
// See issue: https://github.com/TeamDev-IP/JxBrowser-Kotlin/issues/136
object Icons {
    val pause = BitmapPainter(useResource("icons/pause.png", ::loadImageBitmap))
    val play = BitmapPainter(useResource("icons/play.png", ::loadImageBitmap))
    val stop = BitmapPainter(useResource("icons/stop.png", ::loadImageBitmap))
}
