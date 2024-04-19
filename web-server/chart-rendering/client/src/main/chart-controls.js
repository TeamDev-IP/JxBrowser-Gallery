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

import '@material/web/checkbox/checkbox.js';
import '@material/web/select/outlined-select.js';
import '@material/web/select/select-option.js';
import '@material/web/slider/slider.js';

/**
 * Adds controls for modifying the "Fossil fuels consumption" chart.
 *
 * @param canvasId the ID of the canvas element where the chart is rendered
 * @returns an object containing references to the created controls
 */
export function addFossilFuelsConsumptionChartControls(canvasId) {
    const controls = addControlsContainer(canvasId);

    const typeSelector = selector([
        {display: 'Line', value: 'line'},
        {display: 'Bar', value: 'bar'}
    ], 'line');
    const showLabelsCheckbox = checkbox('Show labels');
    const showTrendlineCheckbox = checkbox('Show trendline');
    const xAxisSlider = slider('x scale', 1996, 2022);
    const yAxisSlider = slider('y scale', 0, 100);

    controls.append(
        typeSelector,
        showLabelsCheckbox.label,
        showTrendlineCheckbox.label,
        xAxisSlider.label,
        yAxisSlider.label
    );

    return {
        typeSelector: typeSelector,
        showLabelsCheckbox: showLabelsCheckbox.checkbox,
        showTrendlineCheckbox: showTrendlineCheckbox.checkbox,
        xAxisSlider: xAxisSlider.slider,
        yAxisSlider: yAxisSlider.slider
    };
}

/**
 * Adds controls for modifying the "Per capita energy use" chart.
 *
 * @param canvasId the ID of the canvas element where the chart is rendered
 * @param data the data used to render the chart
 * @returns an object containing references to the created controls
 */
export function addPerCapitaEnergyUseChartControls(canvasId, data) {
    const controls = addControlsContainer(canvasId);

    const countries = [...new Map(
        data.map(row => ({display: row[0], value: row[0]}))
            .map(c => [c.value, c])
    ).values()];
    const countrySelector = selector(countries, 'World');
    const typeSelector = selector([{display: 'Line', value: 'line'}], 'line');
    const showLabelsCheckbox = checkbox('Show labels');
    const showTrendlineCheckbox = checkbox('Show trendline');
    const xAxisSlider = slider('x scale', 1960, 2022);
    const yAxisSlider = slider('y scale', 0, 150_000);

    controls.append(
        countrySelector,
        typeSelector,
        showLabelsCheckbox.label,
        showTrendlineCheckbox.label,
        xAxisSlider.label,
        yAxisSlider.label
    );

    return {
        countrySelector: countrySelector,
        typeSelector: typeSelector,
        showLabelsCheckbox: showLabelsCheckbox.checkbox,
        showTrendlineCheckbox: showTrendlineCheckbox.checkbox,
        xAxisSlider: xAxisSlider.slider,
        yAxisSlider: yAxisSlider.slider
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
