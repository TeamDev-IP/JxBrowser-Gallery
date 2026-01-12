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

import '@material/web/dialog/dialog.js';
import '@material/web/tabs/primary-tab.js';
import '@material/web/tabs/tabs.js';

import {
    addEnergyConsumptionBySourceChartControls,
    addPerCapitaEnergyUseChartControls
} from "./chart-controls";
import {drawEnergyConsumptionBySourceChart, drawPerCapitaEnergyUseChart} from "./chart-drawing";
import {openFileDownloadDialog} from "./download";
import {httpGet} from "./http";
import {newTab, populateTab} from "./page-content";
import {csvToArray} from "./parsing";

const SERVER_URL = 'http://localhost:8080';

/**
 * Initializes the tab containing the chart that visualizes the per capita energy use
 * in the selected country or region.
 */
export function initPerCapitaEnergyUseChart() {
    const info = httpGet(`${SERVER_URL}/dataset/per-capita-energy-use/info`);
    const datasetInfo = JSON.parse(info);
    const csvData = httpGet(`${SERVER_URL}/dataset/per-capita-energy-use/data`);
    const data = csvToArray(csvData);

    const blob = new Blob([csvData], {type: 'text/plain'});
    const dataUrl = window.URL.createObjectURL(blob);

    const tabId = newTab("Per capita energy use", true);
    populateTab(tabId, datasetInfo, dataUrl, exportPng);

    drawPerCapitaEnergyUseChart(datasetInfo.id, data);

    const controls = addPerCapitaEnergyUseChartControls(datasetInfo.id, data);
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

/**
 * Initializes the tab containing the chart that visualizes the energy consumption
 * by source in the selected country or region.
 */
export function initEnergyConsumptionBySourceChart() {
    const info = httpGet(`${SERVER_URL}/dataset/energy-consumption-by-source/info`);
    const datasetInfo = JSON.parse(info);
    const csvData = httpGet(`${SERVER_URL}/dataset/energy-consumption-by-source/data`);
    const data = csvToArray(csvData);

    const blob = new Blob([csvData], {type: 'text/plain'});
    const dataUrl = window.URL.createObjectURL(blob);

    const tabId = newTab("Energy consumption by source", false);
    populateTab(tabId, datasetInfo, dataUrl, exportPng);

    drawEnergyConsumptionBySourceChart(datasetInfo.id, data);

    const controls = addEnergyConsumptionBySourceChartControls(datasetInfo.id, data);
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
