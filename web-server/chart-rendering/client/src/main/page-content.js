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

import '@material/web/button/outlined-button.js';
import '@material/web/icon/icon.js';
import '@material/web/list/list.js';
import '@material/web/list/list-item.js';
import '@material/web/tabs/primary-tab.js';
import '@material/web/tabs/tabs.js';
import {styles as typescaleStyles} from '@material/web/typography/md-typescale-styles.js';

document.adoptedStyleSheets.push(typescaleStyles.styleSheet);
initTabSwitchListener();

let tabCount = 0;

/**
 * Creates a new content tab and returns the corresponding DOM element ID.
 *
 * @param displayName the display name of the new tab
 * @param active whether the new tab should be active by default
 * @return {string} the ID of the DOM element representing the new tab
 */
export function newTab(displayName, active) {
    tabCount++;

    const primaryTab = document.createElement('md-primary-tab');
    primaryTab.id = `tab-${tabCount}`;
    primaryTab.innerText = `${displayName}`;
    primaryTab.classList.add('md-typescale-title-medium');
    if (active) {
        primaryTab.active = true;
    }

    const contentDiv = document.createElement('div');
    contentDiv.id = `content-${tabCount}`;
    contentDiv.className = 'tab-content';
    if (!active) {
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
 * @param datasetDataUrl the URL pointing to the file containing the dataset data
 * @param exportPng the function to call when the user clicks the "Export to PNG" button
 */
export function populateTab(tabId, datasetInfo, datasetDataUrl, exportPng) {
    const content = document.getElementById(tabId);

    const leftPanelContainer = leftPanel(datasetInfo, datasetDataUrl, exportPng);
    content.appendChild(leftPanelContainer);

    const chartContainer = document.createElement('div');
    chartContainer.className = 'canvas-container';

    const canvas = document.createElement('canvas');
    canvas.id = datasetInfo.id;
    canvas.className = 'chart-canvas';
    chartContainer.appendChild(canvas);

    content.appendChild(chartContainer);
}

/**
 * Initializes a listener responsible for tab switching according to the user input.
 */
function initTabSwitchListener() {
    document.getElementById('tabs').addEventListener('change', (event) => {
        const tabIndex = event.target.activeTabIndex;
        const tabId = `content-${tabIndex + 1}`;
        switchToTab(tabId)
    });
}

/**
 * Switches the displayed content to the tab with the specified ID.
 *
 * @param tabId the ID of the tab to switch to
 */
function switchToTab(tabId) {
    const tabContent = document.getElementsByClassName("tab-content");
    for (let i = 0; i < tabContent.length; i++) {
        tabContent[i].style.display = "none";
    }
    document.getElementById(tabId).style.display = "flex";
}

/**
 * Creates a panel that displays the dataset information as well as the export button.
 *
 * @param datasetInfo the dataset information to display
 * @param datasetDataUrl the URL pointing to the file containing the dataset data
 * @param exportPng the function to call when the user clicks the "Export to PNG" button
 * @return the panel with the dataset information
 */
function leftPanel(datasetInfo, datasetDataUrl, exportPng) {
    const panel = document.createElement('div');
    panel.classList.add('left-panel');

    const infoContainer = document.createElement('div');
    infoContainer.classList.add('info-container');

    const list = document.createElement('md-list');

    const description = document.createElement('md-list-item');
    const div = headline(`${datasetInfo.description}`);
    div.classList.add('description');
    description.appendChild(div);
    list.appendChild(description);

    const rowCountLabel = document.createElement('span');
    rowCountLabel.innerText = 'Row count: ';

    const rowCountValue = document.createElement('span');
    rowCountValue.className = 'row-count';
    rowCountValue.innerText = datasetInfo.rowCount;

    const rowCount = document.createElement('md-list-item');
    rowCount.appendChild(headline(rowCountLabel.outerHTML + rowCountValue.outerHTML));
    list.appendChild(rowCount);

    const sourceLabel = document.createElement('span');
    sourceLabel.innerText = 'Source: ';

    const sourceValue = document.createElement('span');
    sourceValue.className = 'source';
    sourceValue.innerHTML = datasetInfo.source;

    const source = document.createElement('md-list-item');
    source.appendChild(headline(sourceLabel.outerHTML + sourceValue.outerHTML));
    list.appendChild(source);

    const dataLinkLabel = document.createElement('span');
    dataLinkLabel.innerText = 'Download: ';

    const dataLinkValue = document.createElement('a');
    dataLinkValue.href = datasetDataUrl;
    dataLinkValue.download = 'data.csv';
    dataLinkValue.innerText = 'data.csv';

    const dataLink = document.createElement('md-list-item');
    dataLink.classList.add('data-link');
    dataLink.appendChild(headline(dataLinkLabel.outerHTML + dataLinkValue.outerHTML));
    list.appendChild(dataLink);

    infoContainer.appendChild(list);
    panel.appendChild(infoContainer);

    const buttonContainer = document.createElement('div');
    buttonContainer.classList.add('export-button-container');

    const button = document.createElement('md-outlined-button');
    button.innerText = 'Export to PNG';
    button.onclick = exportPng;
    buttonContainer.appendChild(button);

    panel.appendChild(buttonContainer);

    return panel;
}

/**
 * Creates a headline to be included into a list item.
 */
function headline(title) {
    const titleDiv = document.createElement('div');
    titleDiv.setAttribute('slot', 'headline');
    titleDiv.classList.add('md-typescale-title-small');
    titleDiv.innerHTML = title;
    return titleDiv;
}
