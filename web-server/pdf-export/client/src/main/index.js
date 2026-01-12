/*
 *  Copyright (c) 2024 TeamDev
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

import {httpGet} from "./http";
import {csvToArray} from "./parsing";
import {newInfoPanel} from "./info-panel";
import {newGrid} from "./grid";
import {newDownloadDialog, openDownloadDialog, openWaitingDialog} from "./download";

const SERVER_URL = 'http://localhost:8080';

/**
 * Initializes the UI displaying the dataset information and the data grid.
 */
function initializeWebpage() {
    const datasetInfo = httpGet(`${SERVER_URL}/dataset/dietary-composition-by-region/info`);
    const parsedInfo = JSON.parse(datasetInfo);
    const csv = httpGet(`${SERVER_URL}/dataset/dietary-composition-by-region/data`);
    const data = csvToArray(csv.trim());

    const blob = new Blob([csv], {type: 'text/plain'});
    const dataUrl = window.URL.createObjectURL(blob);

    const downloadDialog = newDownloadDialog();
    document.getElementById('download-dialog-container').appendChild(downloadDialog.element);

    const infoPanel = newInfoPanel(parsedInfo, dataUrl, () => exportToPdf(downloadDialog));
    document.getElementById('info-container').append(infoPanel);

    const grid = newGrid(data, 20, true, '');
    grid.render(document.getElementById('grid'));
}

/**
 * Exports the currently displayed table to PDF.
 */
async function exportToPdf(downloadDialog) {
    const exportButton = document.getElementById('export-button');
    exportButton.disabled = true;
    const buttonText = exportButton.innerText;
    exportButton.innerText = 'Generating PDF...';
    openWaitingDialog(downloadDialog);

    const pdf = await generateAndFetchPdf();
    const url = window.URL.createObjectURL(pdf);
    const filename = 'dietary-composition-by-region.pdf';
    openDownloadDialog(downloadDialog, url, filename);

    exportButton.disabled = false;
    exportButton.innerText = buttonText;
}

/**
 * Sends a request to the server to print the currently displayed table to PDF.
 */
async function generateAndFetchPdf() {
    const search = Array.from(document.getElementsByClassName('gridjs-search-input'))[0];
    const queryString = `search=${search.value}`;
    const data = await fetch(
        `${SERVER_URL}/print/dietary-composition-by-region?${queryString}`
    ).then(r => r.blob());
    return data;
}

window.initializeWebpage = initializeWebpage;
