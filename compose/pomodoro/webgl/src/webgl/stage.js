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
