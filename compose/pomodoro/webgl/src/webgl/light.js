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
