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

import {loadOBJ} from "../load/obj-loader.js";
import {Animation} from "../animation/animation.js";
import {directionalLight, hemisphereLight} from "../light.js";

/**
 * Loads the animation with a rotating tomato.
 *
 * @return Promise<Animation>
 */
export function loadRotatingTomato() {
    return loadOBJ('models/tomato/materials.mtl', 'models/tomato/geometry.obj')
        .then((tomato) => {
            return new Animation(
                (stage) => onInstall(stage, tomato),
                (time) => onFrame(time, tomato)
            )
        })
}

function onFrame(time, tomato) {
    tomato.rotation.z = time * 0.001;
}

function onInstall(stage, tomato) {
    const {camera, scene} = stage;
    camera.position.set(0, -10, 12);
    camera.lookAt(0, 8, 0);
    scene.add(
        hemisphereLight(),
        directionalLight(10, 5, 10),
        tomato
    );
}
