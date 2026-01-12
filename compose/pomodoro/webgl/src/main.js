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

import {findCanvas} from "./dom/canvas.js";
import {Stage} from "./webgl/stage.js";
import {loadRotatingTomato} from "./webgl/model/tomato.js";
import {Animator} from "./webgl/animation/animator.js";
import {loadRotatingEspresso} from "./webgl/model/espresso.js";
import {loadRotatingLatte} from "./webgl/model/latte.js";

const canvas = findCanvas("webgl");
const stage = new Stage(canvas);
const animator = new Animator(stage);
const animations = {}

Promise.all([
    loadRotatingTomato()
        .then(rotatingTomato => {
            animations.tomato = rotatingTomato
        }),
    loadRotatingEspresso()
        .then(rotatingEspresso => {
            animations.espresso = rotatingEspresso;
        }),
    loadRotatingLatte()
        .then(rotatingLatte => {
            animations.latte = rotatingLatte;
        })
]).then(() => {
    // Exports `animator` and `animations` to the global scope.
    window.animator = animator;
    window.animations = animations;
})
