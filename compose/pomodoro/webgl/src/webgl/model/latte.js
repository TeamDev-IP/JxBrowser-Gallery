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

import {directionalLight, hemisphereLight} from "../light.js";
import {Animation} from "../animation/animation.js";
import {loadGLTF} from "../load/gltf-loader.js";

/**
 * Loads the animation with a rotating cup of latte.
 *
 * @return Promise<Animation>
 */
export function loadRotatingLatte() {
    return loadGLTF("models/latte.glb")
        .then((latte) => {
            return new Animation(
                (stage) => onInstall(stage, latte),
                (time) => onFrame(time, latte)
            );
        })
}

function onFrame(time, latte) {
    latte.rotation.y = time * 0.001;
}

function onInstall(stage, latte) {
    const {camera, scene} = stage;
    camera.position.set(10, 12, 0);
    camera.lookAt(0, 2, 0);
    scene.add(
        hemisphereLight(),
        directionalLight(10, 10, 5),
        latte
    );
}
