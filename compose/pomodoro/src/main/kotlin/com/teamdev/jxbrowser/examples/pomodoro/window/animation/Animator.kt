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

package com.teamdev.jxbrowser.examples.pomodoro.window.animation

import com.teamdev.jxbrowser.browser.Browser
import com.teamdev.jxbrowser.dsl.Engine
import com.teamdev.jxbrowser.dsl.browser.mainFrame
import com.teamdev.jxbrowser.dsl.browser.navigation
import com.teamdev.jxbrowser.dsl.browser.settings
import com.teamdev.jxbrowser.dsl.browser.transparentBackgroundEnabled
import com.teamdev.jxbrowser.engine.Engine
import com.teamdev.jxbrowser.engine.RenderingMode
import com.teamdev.jxbrowser.examples.pomodoro.window.animation.network.InterceptJarRequestCallback
import com.teamdev.jxbrowser.frame.Frame
import com.teamdev.jxbrowser.license.JxBrowserLicense
import com.teamdev.jxbrowser.license.internal.LicenseProvider
import com.teamdev.jxbrowser.net.Scheme
import java.lang.Thread.sleep

/**
 * Controls WebGL animations.
 *
 * The animator allow to start, stop and change the currently
 * used [animated model][AnimatedModel].
 */
class Animator : AutoCloseable {

    private companion object {

        /**
         * Location of a web page that contains WebGL animations.
         */
        const val WEBGL_PAGE = "/webgl/index.html"

        /**
         * Blocking interval in milliseconds, which is used to block
         * the current thread until the [WEBGL_PAGE] and its animations
         * complete loading.
         */
        const val POLLING_INTERVAL = 150L
    }

    private val engine by lazy {
        createEngine()
    }

    internal val browser by lazy {
        engine.createBrowser().also {
            it.loadWebglAnimations()
        }
    }

    /**
     * Starts the animation.
     */
    fun start() = executeJavascript("animator.start();")

    /**
     * Stops the animation.
     */
    fun stop() = executeJavascript("animator.stop();")

    /**
     * Updates the currently used animated model.
     */
    fun use(model: AnimatedModel) {
        val name = model.name.lowercase()
        executeJavascript("animator.use(animations.$name);")
    }

    /**
     * Loads [WEBGL_PAGE] into the browser.
     *
     * The current thread is blocked until all the animations complete loading.
     * Loading of animations takes more time than the loading of the page.
     * It is because as the page completes loading, its JS code only starts
     * loading of animation resources like geometry, materials and textures.
     */
    private fun Browser.loadWebglAnimations() {
        val index = this::class.java.getResource(WEBGL_PAGE)
            ?: error(
                "Pomodoro WebGL animations were not found in the app's resources. " +
                        "The expected location: `examples/pomodoro/src/main/resources$WEBGL_PAGE`."
            )
        navigation.loadUrl(index.toExternalForm())
        while (!animationsLoaded()) {
            sleep(POLLING_INTERVAL)
        }
    }

    /**
     * Checks if WebGL animations completed loading.
     *
     * [WEBGL_PAGE] is configured to export `animator` and `animation` global
     * variables as soon as all animations are ready, so this method checks
     * presence of these variables.
     */
    private fun Browser.animationsLoaded(): Boolean {
        if (mainFrame == null) {
            // The page itself hasn't loaded yet.
            return false
        }

        val frame = mainFrame!!
        return frame.hasGlobalVariable("animator")
                && frame.hasGlobalVariable("animations")
    }

    private fun Frame.hasGlobalVariable(name: String) =
        executeJavaScript<Boolean>("$name != null") == true

    private fun executeJavascript(code: String) {
        browser.mainFrame?.executeJavaScript<Unit>(code)
    }

    override fun close() {
        engine.close()
    }
}

/**
 * Creates a new engine with the pre-configured [InterceptJarRequestCallback].
 */
private fun createEngine() = Engine(RenderingMode.OFF_SCREEN) {
    options {
        license = JxBrowserLicense(LicenseProvider.key)
        schemes {
            add(Scheme.JAR, InterceptJarRequestCallback())
        }
    }
}

/**
 * Creates a new browser with a transparent background.
 */
private fun Engine.createBrowser(): Browser = newBrowser().apply {
    settings.transparentBackgroundEnabled = true
}
