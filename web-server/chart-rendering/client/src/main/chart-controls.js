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

import '@material/web/checkbox/checkbox.js';
import '@material/web/select/outlined-select.js';
import '@material/web/select/select-option.js';
import '@material/web/slider/slider.js';

/**
 * Adds controls for modifying the "Per capita energy use" chart.
 *
 * @param canvasId the ID of the canvas element where the chart is rendered
 * @param data the data used to render the chart
 * @returns an object containing references to the created controls
 */
export function addPerCapitaEnergyUseChartControls(canvasId, data) {
    const controls = addControlsContainer(canvasId);

    const entities = [...new Map(
        data.map(row => ({display: row[0], value: row[0]}))
            .map(c => [c.value, c])
    ).values()];
    const entitySelector = selector(entities, 'World');
    const typeSelector = selector([
        {display: 'Line', value: 'line'},
        {display: 'Bar', value: 'bar'}
    ], 'line');
    const showLabelsCheckbox = checkbox('Show labels');
    const showTrendlineCheckbox = checkbox('Show trendline');
    const xAxisSlider = slider('Time span', 1970, 2022);

    controls.append(
        entitySelector,
        typeSelector,
        showLabelsCheckbox.label,
        showTrendlineCheckbox.label,
        xAxisSlider.label
    );

    return {
        entitySelector: entitySelector,
        typeSelector: typeSelector,
        showLabelsCheckbox: showLabelsCheckbox.checkbox,
        showTrendlineCheckbox: showTrendlineCheckbox.checkbox,
        xAxisSlider: xAxisSlider.slider
    };
}

/**
 * Adds controls for modifying the "Energy consumption by source" chart.
 *
 * @param canvasId the ID of the canvas element where the chart is rendered
 * @param data the data used to render the chart
 * @returns an object containing references to the created controls
 */
export function addEnergyConsumptionBySourceChartControls(canvasId, data) {
    const controls = addControlsContainer(canvasId);

    const entities = [...new Map(
        data.map(row => ({display: row[0], value: row[0]}))
            .map(c => [c.value, c])
    ).values()];
    const entitySelector = selector(entities, 'World');
    const xAxisSlider = slider('Time span', 1970, 2022);

    controls.append(
        entitySelector,
        xAxisSlider.label
    );

    return {
        entitySelector: entitySelector,
        xAxisSlider: xAxisSlider.slider
    };
}

/**
 * Adds a container that holds the chart controls.
 */
function addControlsContainer(canvasId) {
    const parentDiv = document.getElementById(canvasId).parentElement;
    const controlsContainer = document.createElement('div');
    controlsContainer.className = 'controls-container';
    parentDiv.appendChild(controlsContainer);
    return controlsContainer;
}

/**
 * Creates a selector for the chart type.
 */
function selector(options, selected) {
    const select = document.createElement('md-outlined-select');
    select.className = 'controls-select';

    options.forEach((option) => {
        const element = document.createElement('md-select-option');
        if (option.value === selected) {
            element.selected = true;
        }
        element.setAttribute('value', option.value);
        const div = document.createElement('div');
        div.setAttribute('slot', 'headline');
        div.textContent = option.display;
        element.appendChild(div);
        select.appendChild(element);
    });

    return select;
}

/**
 * Creates a checkbox control element with a label.
 */
function checkbox(text) {
    const label = document.createElement('label');
    label.className = 'controls-label';
    label.textContent = text;

    const checkbox = document.createElement('md-checkbox');
    checkbox.className = 'controls-checkbox';
    checkbox.setAttribute('touch-target', 'wrapper');

    label.prepend(checkbox);

    return {label: label, checkbox: checkbox};
}

/**
 * Creates a slider control element with a label.
 */
function slider(text, start, end, step = 1) {
    const label = document.createElement('label');
    label.classList.add('controls-label', 'controls-slider');
    label.textContent = text;

    const slider = document.createElement('md-slider');
    slider.range = true;
    slider.labeled = true;
    slider.min = start;
    slider.valueStart = start;
    slider.max = end;
    slider.valueEnd = end;
    slider.step = step;

    label.prepend(slider);

    return {label: label, slider: slider};
}
