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

import {webglRenderer} from "./render.js";
import {observeResizing} from "../dom/canvas.js";
import {perspectiveCamera} from "./camera.js";
import {Scene} from "three";

/**
 * Contains all WebGL components that are needed for rendering.
 *
 * It includes: renderer, camera and scene.
 *
 * The stage binds canvas size changes to the created camera and renderer.
 * They should always know the actual size of the destination canvas.
 * It allows them to render the scene with the expected size and proportions.
 */
export class Stage {

    /**
     * Creates a new instance.
     *
     * @param {HTMLCanvasElement} canvas
     */
    constructor(canvas) {
        this.#subscribeToSizeChanges(canvas);
        this.renderer = webglRenderer(canvas);
        this.camera = perspectiveCamera(canvas);
        this.scene = new Scene();
        this.canvas = canvas;
    }

    /**
     * Renders the stage.
     */
    render() {
        this.renderer.render(
            this.scene,
            this.camera
        );
    }

    /**
     * Resets the stage by removing all objects from the underlying `scene`.
     *
     * Please note, camera settings are not reset. Anyway, new objects
     * usually adjust camera settings on their own.
     */
    reset() {
        const scene = this.scene;
        while (scene.children.length > 0) {
            const child = scene.children[0];
            scene.remove(child);
        }
    }

    /**
     * Tracks resizing of the canvas to pass the updated values
     * to the renderer and camera.
     *
     * @param {HTMLCanvasElement} canvas
     */
    #subscribeToSizeChanges(canvas) {
        observeResizing(canvas, (width, height) => {
            this.renderer.setSize(width, height, false);
            this.camera.aspect = width / height;
            this.camera.updateProjectionMatrix();
            this.render();
        });
    }
}
