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
import '@material/web/tabs/primary-tab.js';
import '@material/web/tabs/tabs.js';

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
    primaryTab.innerText = `Dataset #${tabCount}`;
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
 * @param exportPng the function to call when the user clicks the "Export to PNG" button
 */
export function populateTab(tabId, datasetInfo, exportPng) {
    const content = document.getElementById(tabId);

    const datasetInfoDiv = datasetInfoPanel(datasetInfo);
    content.appendChild(datasetInfoDiv);

    const canvas = document.createElement('canvas');
    canvas.id = datasetInfo.id;
    content.appendChild(canvas);

    const br = document.createElement('br');
    content.appendChild(br);

    const button = document.createElement('md-outlined-button');
    button.innerText = 'Export to PNG';
    button.onclick = exportPng;
    content.appendChild(button);
}

/**
 * Switches the displayed content to the tab with the specified ID.
 *
 * @param tabId the ID of the tab to switch to
 */
export function switchToTab(tabId) {
    const tabContent = document.getElementsByClassName("tab-content");
    for (let i = 0; i < tabContent.length; i++) {
        tabContent[i].style.display = "none";
    }
    document.getElementById(tabId).style.display = "block";
}

/**
 * Creates a panel that displays the specified dataset information.
 *
 * @param datasetInfo the dataset information to display
 * @return {HTMLDivElement} the panel with the dataset information
 */
function datasetInfoPanel(datasetInfo) {
    const datasetInfoPanel = document.createElement('div');
    datasetInfoPanel.className = 'dataset-info-panel';

    const titleParagraph = document.createElement('p');
    titleParagraph.innerHTML = `<b>Dataset #${tabCount}:</b> ${datasetInfo.title}`;
    datasetInfoPanel.appendChild(titleParagraph);

    const descriptionParagraph = document.createElement('p');
    descriptionParagraph.innerText = `${datasetInfo.description}`;
    datasetInfoPanel.appendChild(descriptionParagraph);

    const rowCountParagraph = document.createElement('p');
    rowCountParagraph.innerHTML = `Row count: ${datasetInfo.rowCount}`;
    datasetInfoPanel.appendChild(rowCountParagraph);

    const columnsParagraph = document.createElement('p');
    columnsParagraph.innerHTML = `Columns:`;
    datasetInfoPanel.appendChild(columnsParagraph);

    const columnList = document.createElement('ul');
    datasetInfo.columns.forEach(column => {
        const columnListItem = document.createElement('li');
        columnListItem.innerHTML = `<i>${column.title}</i> - ${column.description}`;
        columnList.appendChild(columnListItem);
    });
    datasetInfoPanel.appendChild(columnList);

    return datasetInfoPanel;
}
