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
import {styles as typescaleStyles} from '@material/web/typography/md-typescale-styles.js';
// noinspection ES6UnusedImports Needed so the Material Web styles are picked up correctly.
import { MdList } from '@material/web/list/list.js';
// noinspection ES6UnusedImports Needed so the Material Web styles are picked up correctly.
import { MdListItem } from '@material/web/list/list-item.js';

document.adoptedStyleSheets.push(typescaleStyles.styleSheet);

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
 * @param exportPng the function to call when the user clicks the "Export to PNG" button
 */
export function populateTab(tabId, datasetInfo, exportPng) {
    const content = document.getElementById(tabId);

    const datasetInfoDiv = datasetInfoPanel(datasetInfo);
    content.appendChild(datasetInfoDiv);

    const canvasContainer = document.createElement('div');
    canvasContainer.className = 'canvas-container';

    const canvas = document.createElement('canvas');
    canvas.id = datasetInfo.id;
    canvasContainer.appendChild(canvas);

    const br = document.createElement('br');
    canvasContainer.appendChild(br);

    const button = document.createElement('md-outlined-button');
    button.innerText = 'Export to PNG';
    button.onclick = exportPng;
    canvasContainer.appendChild(button);

    content.appendChild(canvasContainer);
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
    document.getElementById(tabId).style.display = "flex";
}

/**
 * Creates a panel that displays the specified dataset information.
 *
 * @param datasetInfo the dataset information to display
 * @return {HTMLDivElement} the panel with the dataset information
 */
function datasetInfoPanel(datasetInfo) {
    const datasetInfoPanel = document.createElement('div');
    datasetInfoPanel.classList.add('dataset-info-panel');
    datasetInfoPanel.classList.add('md-typescale-title-medium');

    const list = document.createElement('md-list');

    const title = document.createElement('md-list-item');
    title.appendChild(createHeadlineDiv(`${datasetInfo.title}`));
    list.appendChild(title);

    const description = document.createElement('md-list-item');
    description.appendChild(createHeadlineDiv('Description'));
    description.appendChild(createSupportingTextDiv(`${datasetInfo.description}`));
    list.appendChild(description);

    const rowCount = document.createElement('md-list-item');
    rowCount.appendChild(createHeadlineDiv('Row count'));
    rowCount.appendChild(createSupportingTextDiv(`${datasetInfo.rowCount}`));
    list.appendChild(rowCount);

    const columns = document.createElement('md-list-item');
    columns.appendChild(createHeadlineDiv(`Columns:`));
    datasetInfo.columns.forEach(column => {
        const text = `<i>${column.title}</i> - ${column.description}`;
        columns.appendChild(createSupportingTextDiv(text));
    });
    list.appendChild(columns);

    datasetInfoPanel.appendChild(list);
    return datasetInfoPanel;
}

function createHeadlineDiv(title) {
    const titleDiv = document.createElement('div');
    titleDiv.setAttribute('slot', 'headline');
    titleDiv.innerText = title;
    return titleDiv;
}

function createSupportingTextDiv(text) {
    const supportingTextDiv = document.createElement('div');
    supportingTextDiv.setAttribute('slot', 'supporting-text');
    supportingTextDiv.innerHTML = text;
    return supportingTextDiv;
}
