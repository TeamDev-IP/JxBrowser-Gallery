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
