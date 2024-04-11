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

import {drawFossilFuelsConsumptionChart} from "./chart-drawing";
import {openFileDownloadDialog} from "./download";
import {httpGet} from "./http";
import {newTab, populateTab} from "./page-content";

const SERVER_URL = 'http://localhost:8080';

/**
 * Initializes the tab containing the chart that visualizes the share of primary
 * energy consumption from fossil fuels in Portugal.
 */
export function initFossilFuelsConsumptionChart() {
    const info = httpGet(`${SERVER_URL}/dataset/fossil-fuels-consumption/info`);
    const datasetInfo = JSON.parse(info);
    const tabId = newTab();
    const data = httpGet(`${SERVER_URL}/dataset/fossil-fuels-consumption/data`);

    populateTab(tabId, datasetInfo, data, exportToPng);
    drawFossilFuelsConsumptionChart(datasetInfo.id, data);

    async function exportToPng() {
        const typeSelector = document.getElementById('chart-type-select');
        const type = typeSelector.selectedOptions[0].value;
        const labelsCheckbox = document.getElementById('data-labels-checkbox');
        const showLabels = labelsCheckbox.checked;
        const trendlineCheckbox = document.getElementById('trendline-checkbox');
        const showTrendline = trendlineCheckbox.checked;
        const data = await fetch(
            `${SERVER_URL}/export/fossil-fuels-consumption/png?type=${type}&labels=${showLabels}&trendline=${showTrendline}`
        ).then(r => r.blob());
        const url = window.URL.createObjectURL(data);
        const filename = 'fossil-fuels.png';
        openFileDownloadDialog(url, filename);
    }
}

window.initFossilFuelsConsumptionChart = initFossilFuelsConsumptionChart;
