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
import {openFileDownloadDialog} from "./download";

const SERVER_URL = 'http://localhost:8080';

function initializeWebpage() {
    const datasetInfo = httpGet(`${SERVER_URL}/dataset/dietary-composition-by-country/info`);
    const parsedInfo = JSON.parse(datasetInfo);
    const csv = httpGet(`${SERVER_URL}/dataset/dietary-composition-by-country/data`);
    const data = csvToArray(csv.trim());

    const blob = new Blob([csv], {type: 'text/plain'});
    const dataUrl = window.URL.createObjectURL(blob);

    const infoPanel = newLeftPanel(parsedInfo, dataUrl, printToPdf);
    document.getElementById('info-container').append(infoPanel);

    const grid = newGrid(data, 20, true);
    grid.render(document.getElementById('grid'));
}

/**
 * Prints the currently displayed table to PDF.
 */
async function printToPdf() {
    const printButton = document.getElementById('print-button');
    printButton.disabled = true;
    const buttonText = printButton.innerText;
    printButton.innerText = 'Generating PDF...';

    const filterInputs = document.querySelectorAll('.filter-input');
    const queryString = Array.from(filterInputs)
        .filter(f => f.value)
        .map(filter => `${filter.columnName.toLowerCase()}=${filter.value}`)
        .join('&');
    const data = await fetch(
        `${SERVER_URL}/print/dietary-composition-by-country?${queryString}`
    ).then(r => r.blob());
    const url = window.URL.createObjectURL(data);
    const filename = 'webpage.pdf';
    openFileDownloadDialog(url, filename);

    printButton.disabled = false;
    printButton.innerText = buttonText;
}

window.initializeWebpage = initializeWebpage;
