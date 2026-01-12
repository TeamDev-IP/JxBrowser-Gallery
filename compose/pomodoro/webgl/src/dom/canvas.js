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

/**
 * Finds a canvas with the given identifier in the document.
 *
 * @param {string} id
 * @return {HTMLCanvasElement}
 */
export function findCanvas(id) {
    return document.querySelector(`#${id}`);
}

/**
 * Subscribes to updates of the canvas size.
 *
 * @param {HTMLCanvasElement} canvas the observed canvas.
 * @param {function(width: number, height: number)} callback the function to be
 * called when the canvas size has changed.
 */
export function observeResizing(canvas, callback) {
    const observer = resizeObserver(callback);
    observer.observe(canvas);
}

/**
 * Creates a size observer for a single element.
 *
 * @param {function(width: number, height:number)} callback the function to be
 * called when the size has changed. Please note, this callback will be used
 * for all future subscribers.
 * @return ResizeObserver
 */
function resizeObserver(callback) {
    return new ResizeObserver((entries) => {
        const rect = entries[0].contentRect;
        const width = rect.width;
        const height = rect.height;
        callback(width, height);
    });
}
