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
