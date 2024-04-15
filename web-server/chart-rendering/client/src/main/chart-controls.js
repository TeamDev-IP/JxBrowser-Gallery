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
 * Adds controls for modifying the "fossil fuels consumption" chart.
 *
 * @param canvasId the ID of the canvas element where the chart is rendered
 * @returns an object containing references to the created controls
 */
export function addFossilFuelsConsumptionChartControls(canvasId) {
    const parentDiv = document.getElementById(canvasId).parentElement;
    const controlsContainer = document.createElement('div');
    controlsContainer.className = 'controls-container';
    parentDiv.appendChild(controlsContainer);

    const typeSelectorContainer = document.createElement('div');
    typeSelectorContainer.className = 'controls-sub-container';

    const typeSelector = chartTypeSelector(['Line', 'Bar']);
    typeSelectorContainer.appendChild(typeSelector);

    const showLabelsContainer = document.createElement('div');
    showLabelsContainer.className = 'controls-sub-container';

    const showLabelsCheckbox = checkboxWithLabel('Show labels');
    showLabelsContainer.appendChild(showLabelsCheckbox.label);

    const showTrendlineContainer = document.createElement('div');
    showTrendlineContainer.className = 'controls-sub-container';

    const showTrendlineCheckbox = checkboxWithLabel('Show trendline');
    showTrendlineContainer.appendChild(showTrendlineCheckbox.label);

    controlsContainer.appendChild(typeSelectorContainer);
    controlsContainer.appendChild(showLabelsContainer);
    controlsContainer.appendChild(showTrendlineContainer);

    const slidersContainer = document.createElement('div');
    slidersContainer.className = 'controls-sub-container';

    const xAxisSlider = slider('x scale', 1996, 2022);
    xAxisSlider.label.className = 'controls-slider';
    slidersContainer.appendChild(xAxisSlider.label);

    const yAxisSlider = slider('y scale', 0, 100);
    yAxisSlider.label.className = 'controls-slider';
    slidersContainer.appendChild(yAxisSlider.label);

    controlsContainer.appendChild(slidersContainer);

    return {
        typeSelector: typeSelector,
        showLabelsCheckbox: showLabelsCheckbox.checkbox,
        showTrendlineCheckbox: showTrendlineCheckbox.checkbox,
        xAxisSlider: xAxisSlider.slider,
        yAxisSlider: yAxisSlider.slider
    };
}

function chartTypeSelector(types) {
    const select = document.createElement('md-outlined-select');

    types.forEach((option, index) => {
        const element = document.createElement('md-select-option');
        if (index === 0) {
            element.selected = true;
        }
        element.setAttribute('value', option.toLowerCase());
        const div = document.createElement('div');
        div.setAttribute('slot', 'headline');
        div.textContent = option;
        element.appendChild(div);
        select.appendChild(element);
    });

    return select;
}

function checkboxWithLabel(text) {
    const label = document.createElement('label');
    label.className = 'controls-label';
    label.textContent = text;

    const checkbox = document.createElement('md-checkbox');
    checkbox.className = 'controls-checkbox';
    checkbox.setAttribute('touch-target', 'wrapper');

    label.prepend(checkbox);

    return {label: label, checkbox: checkbox};
}

function slider(text, start, end, step = 1) {
    const label = document.createElement('label');
    label.className = 'controls-label';
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
