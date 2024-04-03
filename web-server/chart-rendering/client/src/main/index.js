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

import * as a from './chart-rendering.js';

const SERVER_URL = 'http://localhost:8080';
let chartCount = 0;

export async function exportFossilFuelsConsumptionChart() {
    const data = await fetch(`${SERVER_URL}/export/fossil-fuels-consumption/png`)
        .then(r => r.blob());
    const url = window.URL.createObjectURL(data);
    const filename = 'fossil-fuels.png';
    openFileDownloadPopup(url, filename);
}

function openFileDownloadPopup(url, filename) {
    const win = window.open(
        "",
        "",
        `scrollbars=no,resizable=no,status=no,location=no,toolbar=no,
            menubar=no,width=600,height=300,left=100,top=100`
    );
    win.document.body.innerHTML =
        `Your file: <a href="${url}" download="${filename}">${filename}</a>
                <br><br>
                Also, available on the server filesystem as
                '<i>{project root}/server/chart-rendering/server/exported/${filename}</i>'.`
}

async function populateFossilFuelsConsumptionDesc() {
    chartCount++;

    const info = await fetch(`${SERVER_URL}/dataset/fossil-fuels-consumption/info`)
        .then(response => response.text());

    const dataset = JSON.parse(info);

    const datasetDiv = document.createElement('div');
    datasetDiv.style.width = '800px';
    datasetDiv.style.border = '1px solid black';
    datasetDiv.style.padding = '10px';
    datasetDiv.style.marginBottom = '10px';

    const titleParagraph = document.createElement('p');
    titleParagraph.innerText = `Dataset #${chartCount}: ${dataset.title}`;
    titleParagraph.style.fontWeight = 'bold';
    datasetDiv.appendChild(titleParagraph);

    const descriptionParagraph = document.createElement('p');
    descriptionParagraph.innerText = `${dataset.description}`;
    datasetDiv.appendChild(descriptionParagraph);

    const rowCountParagraph = document.createElement('p');
    rowCountParagraph.innerHTML = `<b>Number of records:</b> ${dataset.rowCount}`;
    datasetDiv.appendChild(rowCountParagraph);

    const columnsParagraph = document.createElement('p');
    columnsParagraph.innerText = `Columns:`;
    columnsParagraph.style.fontWeight = 'bold';
    datasetDiv.appendChild(columnsParagraph);

    const columnsList = document.createElement('ul');
    dataset.columns.forEach(column => {
        const columnListItem = document.createElement('li');
        columnListItem.innerHTML = `<i>${column.title}</i> - ${column.description}`;
        columnsList.appendChild(columnListItem);
    });
    datasetDiv.appendChild(columnsList);

    document.getElementById(`dataset-${chartCount}`).prepend(datasetDiv);
}

function httpGet(url) {
    const xmlHttp = new XMLHttpRequest();
    xmlHttp.open('GET', url, false);
    xmlHttp.send(null);
    return xmlHttp.responseText;
}

function switchTab(tabId) {
    const tabContent = document.getElementsByClassName("tab-content");
    let i;
    for (i = 0; i < tabContent.length; i++) {
        tabContent[i].style.display = "none";
    }
    document.getElementById(tabId).style.display = "block";
}

populateFossilFuelsConsumptionDesc();
const data = httpGet(`${SERVER_URL}/dataset/fossil-fuels-consumption/data`);
window.drawFossilFuelsConsumptionChart('fossil-fuels-consumption', data);

window.exportFossilFuelsConsumptionChart = exportFossilFuelsConsumptionChart;
window.switchTab = switchTab;
