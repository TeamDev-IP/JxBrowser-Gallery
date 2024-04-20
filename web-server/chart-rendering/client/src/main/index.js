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

import '@material/web/dialog/dialog.js';
import '@material/web/tabs/primary-tab.js';
import '@material/web/tabs/tabs.js';

import {
    csvToArray,
    drawEnergyConsumptionBySourceChart,
    drawPerCapitaEnergyUseChart
} from "./chart-drawing";
import {openFileDownloadDialog} from "./download";
import {httpGet} from "./http";
import {newTab, populateTab} from "./page-content";
import {
    addEnergyConsumptionBySourceChartControls,
    addPerCapitaEnergyUseChartControls
} from "./chart-controls";

const SERVER_URL = 'http://localhost:8080';

/**
 * Initializes the tab containing the chart that visualizes the per capita energy use
 * in the selected country.
 */
export function initPerCapitaEnergyUseChart() {
    const info = httpGet(`${SERVER_URL}/dataset/per-capita-energy-use/info`);
    const datasetInfo = JSON.parse(info);
    const data = httpGet(`${SERVER_URL}/dataset/per-capita-energy-use/data`);

    const tabId = newTab("Per capita energy use", true);
    populateTab(tabId, datasetInfo, exportPng);

    drawPerCapitaEnergyUseChart(datasetInfo.id, data);

    const controls = addPerCapitaEnergyUseChartControls(datasetInfo.id, csvToArray(data));
    Object.values(controls)
          .forEach(control => control.addEventListener('change', redrawChart));

    async function exportPng() {
        const params = chartParams(controls);
        const encodedParams = encodeURIComponent(JSON.stringify(params));
        const data = await fetch(
            `${SERVER_URL}/export/per-capita-energy-use/png?params=${encodedParams}`
        ).then(r => r.blob());
        const url = window.URL.createObjectURL(data);
        const filename = 'per-capita-energy-use.png';
        openFileDownloadDialog(url, filename);
    }

    function redrawChart() {
        const params = chartParams(controls);
        drawPerCapitaEnergyUseChart(datasetInfo.id, data, params);
    }

    function chartParams(controls) {
        return {
            entity: controls.entitySelector.selectedOptions[0].value,
            type: controls.typeSelector.selectedOptions[0].value,
            showLabels: controls.showLabelsCheckbox.checked,
            showTrendline: controls.showTrendlineCheckbox.checked,
            xMin: controls.xAxisSlider.valueStart,
            xMax: controls.xAxisSlider.valueEnd
        };
    }
}

export function initEnergyConsumptionBySourceChart() {
    const info = httpGet(`${SERVER_URL}/dataset/energy-consumption-by-source/info`);
    const datasetInfo = JSON.parse(info);
    const data = httpGet(`${SERVER_URL}/dataset/energy-consumption-by-source/data`);

    const tabId = newTab("Energy consumption by source", false);
    populateTab(tabId, datasetInfo, exportPng);

    drawEnergyConsumptionBySourceChart(datasetInfo.id, data);

    const controls = addEnergyConsumptionBySourceChartControls(datasetInfo.id, csvToArray(data));
    Object.values(controls)
          .forEach(control => control.addEventListener('change', redrawChart));

    async function exportPng() {
        const params = chartParams(controls);
        const encodedParams = encodeURIComponent(JSON.stringify(params));
        const data = await fetch(
            `${SERVER_URL}/export/energy-consumption-by-source/png?params=${encodedParams}`
        ).then(r => r.blob());
        const url = window.URL.createObjectURL(data);
        const filename = 'energy-consumption-by-source.png';
        openFileDownloadDialog(url, filename);
    }

    function redrawChart() {
        const params = chartParams(controls);
        drawEnergyConsumptionBySourceChart(datasetInfo.id, data, params);
    }

    function chartParams(controls) {
        return {
            entity: controls.entitySelector.selectedOptions[0].value,
            xMin: controls.xAxisSlider.valueStart,
            xMax: controls.xAxisSlider.valueEnd
        };
    }
}

window.initEnergyConsumptionBySourceChart = initEnergyConsumptionBySourceChart;
window.initPerCapitaEnergyUseChart = initPerCapitaEnergyUseChart;
