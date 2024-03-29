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
