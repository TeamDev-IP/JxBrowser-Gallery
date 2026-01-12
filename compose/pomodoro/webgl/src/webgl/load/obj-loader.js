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

import {MTLLoader, OBJLoader} from "three/addons";
import {Object3D} from "three";

/**
 * Loads a 3D model in `.obj` format from the passed files.
 *
 * This format usually implies up to three files:
 *
 * 1) `.obj` file with geometry.
 * 2) `.mtl` file with materials.
 * 3) `.jpg` (or other image format) with textures.
 *
 * The first two should be passed to the loader explicitly. Textures are
 * always loaded implicitly. `.mtl` file contains their relative location.
 * Open it in a text editor to check, if needed.
 *
 * @param {string} materials path to `.mtl` file with materials.
 * @param {string} geometry path to `.obj` file with geometry.
 * @return {Promise<Object3D>}
 */
export function loadOBJ(materials, geometry) {
    return loadMaterials(materials)
        .then(materials => {
            materials.preload();
            return loadGeometry(geometry, materials);
        });
}

function loadMaterials(path) {
    const loader = new MTLLoader();
    return new Promise((resolve) => loader.load(path, resolve));
}

function loadGeometry(path, materials) {
    const loader = new OBJLoader();
    loader.setMaterials(materials);
    return new Promise((resolve) => loader.load(path, resolve));
}
