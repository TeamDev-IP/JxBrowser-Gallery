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

import {directionalLight, hemisphereLight} from "../light.js";
import {Animation} from "../animation/animation.js";
import {loadGLTF} from "../load/gltf-loader.js";

/**
 * Loads the animation with a rotating cup of espresso.
 *
 * @return Promise<Animation>
 */
export function loadRotatingEspresso() {
    return loadGLTF("models/espresso.glb")
        .then((espresso) => {
            return new Animation(
                (stage) => onInstall(stage, espresso),
                (time) => onFrame(time, espresso)
            );
        })
}

function onFrame(time, espresso) {
    espresso.rotation.y = time * 0.001;
}

function onInstall(stage, espresso) {
    const {camera, scene} = stage;
    camera.position.set(10, 11, 0);
    camera.lookAt(0, 2, 0);
    scene.add(
        hemisphereLight(),
        directionalLight(10, 10, 5),
        espresso
    );
}
