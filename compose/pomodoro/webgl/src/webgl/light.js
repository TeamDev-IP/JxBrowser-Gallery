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

import {DirectionalLight, HemisphereLight} from "three";

/**
 * Creates a new light positioned directly above the scene, with a color fading
 * from the sky color to the ground one.
 *
 * @return {HemisphereLight}
 */
export function hemisphereLight() {
    const skyColor = 0xFBFAF5; // soft white.
    const groundColor = 0xB97A20; // brownish orange.
    const intensity = 3;
    return new HemisphereLight(skyColor, groundColor, intensity);
}

/**
 * Creates a new directional light directed to the given point.
 *
 * @param {number} x
 * @param {number} y
 * @param {number} z
 * @return {DirectionalLight}
 */
export function directionalLight(x, y, z) {
    const color = 0xFFFFFF; // warm white.
    const intensity = 2;
    const light = new DirectionalLight(color, intensity);
    light.position.set(x, y, z);
    return light;
}
