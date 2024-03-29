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
