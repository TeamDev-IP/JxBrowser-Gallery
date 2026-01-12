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
                        "The expected location: `pomodoro/src/main/resources$WEBGL_PAGE`."
            )
        navigation.loadUrl(index.toExternalForm())
        while (!animationsLoaded()) {
            sleep(POLLING_INTERVAL)
        }
    }

    /**
     * Checks if WebGL animations have completed loading.
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
    license = LicenseProvider.license
    schemes {
        add(Scheme.JAR, InterceptJarRequestCallback())
    }
}

/**
 * Creates a new browser with a transparent background.
 */
private fun Engine.createBrowser(): Browser = newBrowser().apply {
    settings.transparentBackgroundEnabled = true
}
