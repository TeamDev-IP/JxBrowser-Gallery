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

/**
 * Controls the given animation, if any.
 *
 * It has the following responsibilities:
 *
 * 1. Change the currently used animation.
 * 2. Play and stop the animation.
 *
 * When an animation is started, its {@link Animation#onFrame} is executed
 * along with the browser frame updates (usually, it is 60Hz, which means sixty
 * times per second). Right after it, the stage is re-rendered to reflect
 * the changes.
 *
 * Please note, as for now, this class doesn't support a fair pausing.
 * Pausing implies continuing from where we "paused". To implement such,
 * we should count for the elapsed time between pausing and continuation.
 * As for now, it is just not implemented.
 */
export class Animator {

    #animation = null;
    #isRunning = false;

    /**
     * Creates a new instance.
     *
     * @param {Stage} stage
     */
    constructor(stage) {
        this.stage = stage;
    }

    /**
     * Changes the currently used animation.
     *
     * @param {Animation} animation
     */
    use(animation) {
        this.stop();
        this.#animation = animation;
        this.stage.reset();
        animation.onInstall(this.stage);
        this.stage.render();
    }

    /**
     * Starts the animation.
     */
    start() {
        if (this.#isRunning || this.#animation == null) {
            return;
        }

        this.#isRunning = true;

        const loop = (currentTime) => {
            this.#animation.onFrame(currentTime);
            this.stage.render();
            if (this.#isRunning) {
                // Requests to call `loop()` again on the next frame.
                requestAnimationFrame(loop);
            }
        };

        // Initiates the loop.
        loop(Date.now());
    }

    /**
     * Stops the animation.
     *
     * Please note, this method doesn't reset the scene to the initial state.
     * Changes made by {@link Animation#onFrame} are NOT discarded until
     * the animation is changed.
     */
    stop() {
        this.#isRunning = false;
    }
}
