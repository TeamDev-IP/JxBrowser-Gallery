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

import {httpGet} from "./http";
import {csvToArray} from "./parsing";
import {newLeftPanel} from "./left-panel";
import {newGrid} from "./grid";
import {newDownloadDialog, openDownloadDialog} from "./download";

const SERVER_URL = 'http://localhost:8080';

/**
 * Initializes the UI displaying the dataset information and the data grid.
 */
function initializeWebpage() {
    const datasetInfo = httpGet(`${SERVER_URL}/dataset/dietary-composition-by-country/info`);
    const parsedInfo = JSON.parse(datasetInfo);
    const csv = httpGet(`${SERVER_URL}/dataset/dietary-composition-by-country/data`);
    const data = csvToArray(csv.trim());

    const blob = new Blob([csv], {type: 'text/plain'});
    const dataUrl = window.URL.createObjectURL(blob);

    const downloadDialog = newDownloadDialog();
    document.getElementById('download-dialog-container').appendChild(downloadDialog.element);

    const infoPanel = newLeftPanel(parsedInfo, dataUrl, () => printToPdf(downloadDialog));
    document.getElementById('info-container').append(infoPanel);

    const grid = newGrid(data, 20, true);
    grid.render(document.getElementById('grid'));
}

/**
 * Prints the currently displayed table to PDF.
 */
async function printToPdf(downloadDialog) {
    const printButton = document.getElementById('print-button');
    printButton.disabled = true;
    const buttonText = printButton.innerText;
    printButton.innerText = 'Generating PDF...';

    const pdf = await generateAndFetchPdf();
    const url = window.URL.createObjectURL(pdf);
    const filename = 'webpage.pdf';
    openDownloadDialog(downloadDialog, url, filename);

    printButton.disabled = false;
    printButton.innerText = buttonText;
}

/**
 * Sends a request to the server to print the currently displayed table to PDF.
 */
async function generateAndFetchPdf() {
    const filterInputs = document.querySelectorAll('.filter-input');
    const queryString = Array.from(filterInputs)
        .filter(f => f.value)
        .map(filter => `${filter.columnName.toLowerCase()}=${filter.value}`)
        .join('&');
    const data = await fetch(
        `${SERVER_URL}/print/dietary-composition-by-country?${queryString}`
    ).then(r => r.blob());
    return data;
}

window.initializeWebpage = initializeWebpage;
