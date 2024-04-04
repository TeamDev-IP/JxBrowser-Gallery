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

import "./chart-drawing";
import {httpGet} from "./http";
import {newTab, populateTab} from "./page-content";
import {openFileDownloadPopup} from "./popup";

const SERVER_URL = 'http://localhost:8080';

window.initFossilFuelsConsumptionChart = () => {
    const info = httpGet(`${SERVER_URL}/dataset/fossil-fuels-consumption/info`);
    const datasetInfo = JSON.parse(info);
    const tabId = newTab();
    populateTab(tabId, datasetInfo, exportToPng);

    const data = httpGet(`${SERVER_URL}/dataset/fossil-fuels-consumption/data`);
    drawFossilFuelsConsumptionChart(datasetInfo.id, data);

    async function exportToPng() {
        const data = await fetch(`${SERVER_URL}/export/fossil-fuels-consumption/png`)
            .then(r => r.blob());
        const url = window.URL.createObjectURL(data);
        const filename = 'fossil-fuels.png';
        openFileDownloadPopup(url, filename);
    }
}
