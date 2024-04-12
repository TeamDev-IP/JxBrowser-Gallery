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

import '@material/web/button/outlined-button.js';
import '@material/web/checkbox/checkbox.js';
import '@material/web/list/list.js';
import '@material/web/list/list-item.js';
import '@material/web/select/outlined-select.js';
import '@material/web/select/select-option.js';
import '@material/web/slider/slider.js';
import '@material/web/tabs/primary-tab.js';
import '@material/web/tabs/tabs.js';
import {styles as typescaleStyles} from '@material/web/typography/md-typescale-styles.js';
import {drawFossilFuelsConsumptionChart} from './chart-drawing.js';

document.adoptedStyleSheets.push(typescaleStyles.styleSheet);
initTabSwitchListener();

let tabCount = 0;

/**
 * Creates a new content tab and returns the corresponding DOM element ID.
 *
 * @return {string} the ID of the DOM element representing the new tab
 */
export function newTab() {
    tabCount++;

    const primaryTab = document.createElement('md-primary-tab');
    primaryTab.id = `tab-${tabCount}`;
    primaryTab.innerText = `Dataset ${tabCount}`;
    primaryTab.classList.add('md-typescale-title-medium');
    if (tabCount === 1) {
        primaryTab.active = true;
    }

    const contentDiv = document.createElement('div');
    contentDiv.id = `content-${tabCount}`;
    contentDiv.className = 'tab-content';
    if (tabCount > 1) {
        contentDiv.style.display = 'none';
    }

    document.getElementById('tabs').appendChild(primaryTab);
    document.getElementById('content').appendChild(contentDiv);

    return contentDiv.id;
}

/**
 * Populates the tab with the specified dataset information.
 *
 * @param tabId the ID of the tab to populate
 * @param datasetInfo the dataset information to display
 * @param data the data to visualize
 * @param exportPng the function to call when the user clicks the "Export to PNG" button
 */
export function populateTab(tabId, datasetInfo, data, exportPng) {
    const content = document.getElementById(tabId);

    const datasetInfoContainer = datasetInfoPanel(datasetInfo, exportPng);
    content.appendChild(datasetInfoContainer);

    const canvasContainer = document.createElement('div');
    canvasContainer.className = 'canvas-container';

    const canvas = document.createElement('canvas');
    canvas.id = datasetInfo.id;
    canvasContainer.appendChild(canvas);

    const controlsContainer = document.createElement('div');
    controlsContainer.className = 'controls-container';

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

    controlsContainer.appendChild(selectsContainer);
    controlsContainer.appendChild(slidersContainer);

    canvasContainer.appendChild(controlsContainer);

    select.addEventListener('change', () => {
        const type = select.selectedOptions[0].value;
        const showDataLabels = dataLabelsCheckbox.checked;
        const showTrendline = trendlineCheckbox.checked;
        const xMin = xSlider.valueStart;
        const xMax = xSlider.valueEnd;
        const yMin = ySlider.valueStart;
        const yMax = ySlider.valueEnd;

        const context = canvas.getContext('2d');
        context.clearRect(0, 0, canvas.width, canvas.height);

        console.log(canvas.id, data, type, showDataLabels, showTrendline, xMin, xMax, yMin, yMax);

        drawFossilFuelsConsumptionChart(canvas.id, data, type, showDataLabels, showTrendline, xMin, xMax, yMin, yMax);
    });

    dataLabelsCheckbox.addEventListener('change', () => {
        const type = select.selectedOptions[0].value;
        const showDataLabels = dataLabelsCheckbox.checked;
        const showTrendline = trendlineCheckbox.checked;
        const xMin = xSlider.valueStart;
        const xMax = xSlider.valueEnd;
        const yMin = ySlider.valueStart;
        const yMax = ySlider.valueEnd;

        const context = canvas.getContext('2d');
        context.clearRect(0, 0, canvas.width, canvas.height);

        drawFossilFuelsConsumptionChart(canvas.id, data, type, showDataLabels, showTrendline, xMin, xMax, yMin, yMax);
    });

    trendlineCheckbox.addEventListener('change', () => {
        const type = select.selectedOptions[0].value;
        const showDataLabels = dataLabelsCheckbox.checked;
        const showTrendline = trendlineCheckbox.checked;
        const xMin = xSlider.valueStart;
        const xMax = xSlider.valueEnd;
        const yMin = ySlider.valueStart;
        const yMax = ySlider.valueEnd;

        const context = canvas.getContext('2d');
        context.clearRect(0, 0, canvas.width, canvas.height);

        drawFossilFuelsConsumptionChart(canvas.id, data, type, showDataLabels, showTrendline, xMin, xMax, yMin, yMax);
    });

    xSlider.addEventListener('change', () => {
        const type = select.selectedOptions[0].value;
        const showDataLabels = dataLabelsCheckbox.checked;
        const showTrendline = trendlineCheckbox.checked;
        const xMin = xSlider.valueStart;
        const xMax = xSlider.valueEnd;
        const yMin = ySlider.valueStart;
        const yMax = ySlider.valueEnd;

        const context = canvas.getContext('2d');
        context.clearRect(0, 0, canvas.width, canvas.height);

        drawFossilFuelsConsumptionChart(canvas.id, data, type, showDataLabels, showTrendline, xMin, xMax, yMin, yMax);
    });

    ySlider.addEventListener('change', () => {
        const type = select.selectedOptions[0].value;
        const showDataLabels = dataLabelsCheckbox.checked;
        const showTrendline = trendlineCheckbox.checked;
        const xMin = xSlider.valueStart;
        const xMax = xSlider.valueEnd;
        const yMin = ySlider.valueStart;
        const yMax = ySlider.valueEnd;

        const context = canvas.getContext('2d');
        context.clearRect(0, 0, canvas.width, canvas.height);

        drawFossilFuelsConsumptionChart(canvas.id, data, type, showDataLabels, showTrendline, xMin, xMax, yMin, yMax);
    });

    content.appendChild(canvasContainer);
}

/**
 * Initializes a listener responsible for tab switching according to the user input.
 */
function initTabSwitchListener() {
    document.getElementById('tabs').addEventListener('change', (event) => {
        const tabIndex = event.target.activeTabIndex;
        const tabId = `content-${tabIndex + 1}`;
        switchToTab(tabId)
    });

}

/**
 * Switches the displayed content to the tab with the specified ID.
 *
 * @param tabId the ID of the tab to switch to
 */
function switchToTab(tabId) {
    const tabContent = document.getElementsByClassName("tab-content");
    for (let i = 0; i < tabContent.length; i++) {
        tabContent[i].style.display = "none";
    }
    document.getElementById(tabId).style.display = "flex";
}

/**
 * Creates a panel that displays the specified dataset information.
 *
 * @param datasetInfo the dataset information to display
 * @param exportPng the function to call when the user clicks the "Export to PNG" button
 * @return {HTMLDivElement} the panel with the dataset information
 */
function datasetInfoPanel(datasetInfo, exportPng) {
    const datasetInfoPanel = document.createElement('div');
    datasetInfoPanel.classList.add('dataset-info-panel');

    const list = document.createElement('md-list');

    const title = document.createElement('md-list-item');
    title.appendChild(headline(`${datasetInfo.title}`));
    list.appendChild(title);

    const description = document.createElement('md-list-item');
    description.appendChild(headline('Description'));
    description.appendChild(supportingText(`${datasetInfo.description}`));
    list.appendChild(description);

    const rowCount = document.createElement('md-list-item');
    rowCount.appendChild(headline('Row count'));
    rowCount.appendChild(supportingText(`${datasetInfo.rowCount}`));
    list.appendChild(rowCount);

    const columns = document.createElement('md-list-item');
    columns.appendChild(headline(`Columns`));
    datasetInfo.columns.forEach(column => {
        const text = `<li><i>${column.title}</i> - ${column.description}`;
        columns.appendChild(supportingText(text));
    });
    list.appendChild(columns);

    datasetInfoPanel.appendChild(list);

    const buttonContainer = document.createElement('div');
    buttonContainer.classList.add('export-button-container');

    const button = document.createElement('md-outlined-button');
    button.innerText = 'Export to PNG';
    button.onclick = exportPng;
    buttonContainer.appendChild(button);

    datasetInfoPanel.appendChild(buttonContainer);

    return datasetInfoPanel;
}

/**
 * Creates a headline to be included into a list item.
 */
function headline(title) {
    const titleDiv = document.createElement('div');
    titleDiv.setAttribute('slot', 'headline');
    titleDiv.classList.add('md-typescale-title-small');
    titleDiv.innerText = title;
    return titleDiv;
}

/**
 * Creates supporting text to be included into a list item.
 */
function supportingText(text) {
    const supportingTextDiv = document.createElement('div');
    supportingTextDiv.setAttribute('slot', 'supporting-text');
    supportingTextDiv.classList.add('md-typescale-body-small');
    supportingTextDiv.innerHTML = text;
    return supportingTextDiv;
}
