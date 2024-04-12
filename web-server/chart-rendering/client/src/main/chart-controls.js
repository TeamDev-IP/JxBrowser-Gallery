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

export function addFossilFuelsConsumptionChartControls(canvasId) {
    const parentDiv = document.getElementById(`${canvasId}-controls`);

    const selectsContainer = document.createElement('div');
    selectsContainer.className = 'controls-sub-container';

    const select = document.createElement('md-outlined-select');
    select.id = 'chart-type-select';

    const optionOne = document.createElement('md-select-option');
    optionOne.selected = true;
    optionOne.setAttribute('value', 'line');
    const divOne = document.createElement('div');
    divOne.setAttribute('slot', 'headline');
    divOne.textContent = 'Line';
    optionOne.appendChild(divOne);
    select.appendChild(optionOne);

    const optionTwo = document.createElement('md-select-option');
    optionTwo.setAttribute('value', 'bar');
    const divTwo = document.createElement('div');
    divTwo.setAttribute('slot', 'headline');
    divTwo.textContent = 'Bar';
    optionTwo.appendChild(divTwo);
    select.appendChild(optionTwo);

    selectsContainer.appendChild(select);

    const dataLabels = document.createElement('label');
    dataLabels.className = 'controls-label';
    dataLabels.textContent = 'Show data labels';

    const dataLabelsCheckbox = document.createElement('md-checkbox');
    dataLabelsCheckbox.id = 'data-labels-checkbox';
    dataLabelsCheckbox.className = 'controls-checkbox';
    dataLabelsCheckbox.setAttribute('touch-target', 'wrapper');

    dataLabels.prepend(dataLabelsCheckbox);

    selectsContainer.appendChild(dataLabels);

    const trendline = document.createElement('label');
    trendline.className = 'controls-label';
    trendline.textContent = 'Show trendline';

    const trendlineCheckbox = document.createElement('md-checkbox');
    trendlineCheckbox.id = 'trendline-checkbox';
    trendlineCheckbox.className = 'controls-checkbox';
    trendlineCheckbox.setAttribute('touch-target', 'wrapper');

    trendline.prepend(trendlineCheckbox);

    selectsContainer.appendChild(trendline);

    parentDiv.appendChild(selectsContainer);

    const slidersContainer = document.createElement('div');
    slidersContainer.className = 'controls-sub-container';

    const xScaleLabel = document.createElement('label');
    xScaleLabel.className = 'controls-label';
    xScaleLabel.textContent = 'x scale';

    const xSlider = document.createElement('md-slider');
    xSlider.id = 'x-slider';
    xSlider.range = true;
    xSlider.labeled = true;
    xSlider.min = '1996';
    xSlider.valueStart = '1996';
    xSlider.max = '2022';
    xSlider.valueEnd = '2022';
    xSlider.step = '1';

    xScaleLabel.prepend(xSlider);

    slidersContainer.appendChild(xScaleLabel);

    const yScaleLabel = document.createElement('label');
    yScaleLabel.className = 'controls-label';
    yScaleLabel.textContent = 'y scale';

    const ySlider = document.createElement('md-slider');
    ySlider.id = 'y-slider';
    ySlider.range = true;
    ySlider.labeled = true;
    ySlider.valueStart = '0';
    ySlider.valueEnd = '100';
    ySlider.step = '1';

    yScaleLabel.prepend(ySlider);

    slidersContainer.appendChild(yScaleLabel);

    parentDiv.appendChild(slidersContainer);

    return {
        typeSelector: select,
        showLabelsCheckbox: dataLabelsCheckbox,
        showTrendlineCheckbox: trendlineCheckbox,
        xAxisSlider: xSlider,
        yAxisSlider: ySlider
    };
}
