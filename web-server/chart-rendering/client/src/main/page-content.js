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
 * @param exportPng the function to call when the user clicks the "Export to PNG" button
 */
export function populateTab(tabId, datasetInfo, exportPng) {
    const content = document.getElementById(tabId);

    const leftPanelContainer = leftPanel(datasetInfo, exportPng);
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
 * Creates a panel that displays the specified dataset information as well as
 * the "Export to PNG" button.
 *
 * @param datasetInfo the dataset information to display
 * @param exportPng the function to call when the user clicks the "Export to PNG" button
 * @return the panel with the dataset information
 */
function leftPanel(datasetInfo, exportPng) {
    const panel = document.createElement('div');
    panel.classList.add('left-panel');

    const infoContainer = document.createElement('div');
    infoContainer.classList.add('info-container');

    const list = document.createElement('md-list');

    const description = document.createElement('md-list-item');
    const div = headline(`${datasetInfo.description}`);
    div.classList.add('dataset-description');
    description.appendChild(div);
    list.appendChild(description);

    const rowCountLabel = document.createElement('span');
    rowCountLabel.innerText = 'Row count: ';

    const rowCountValue = document.createElement('span');
    rowCountValue.className = 'row-count-value';
    rowCountValue.innerText = datasetInfo.rowCount;

    const rowCount = document.createElement('md-list-item');
    rowCount.appendChild(headline(rowCountLabel.outerHTML + rowCountValue.outerHTML));
    list.appendChild(rowCount);

    const columns = document.createElement('md-list-item');
    columns.appendChild(headline(`Columns`));
    list.appendChild(columns);

    const columnContainer = document.createElement('div');
    datasetInfo.columns.forEach(column => {
        const listItem = document.createElement('md-list-item');
        listItem.classList.add('column-title');
        const icon = document.createElement('md-icon');
        icon.setAttribute('slot', 'start');
        icon.classList.add('material-symbols-outlined');
        if (column.type === 'number') {
            icon.innerText = '123';
        } else if (column.type === 'geo') {
            icon.innerText = 'public';
        } else if (column.type === 'string') {
            icon.innerText = 'abc';
        }
        listItem.appendChild(supportingText(column.title));
        listItem.appendChild(icon);
        columnContainer.appendChild(listItem);
    });
    list.appendChild(columnContainer);

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

/**
 * Creates supporting text to be included into a list item.
 */
function supportingText(text) {
    const supportingTextDiv = document.createElement('div');
    supportingTextDiv.setAttribute('slot', 'supporting-text');
    supportingTextDiv.classList.add('md-typescale-body-small');
    supportingTextDiv.innerHTML = text;
    return supportingTextDiv;
}
