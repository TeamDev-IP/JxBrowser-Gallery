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

let tabCount = 0;

export function newTab() {
    tabCount++;

    const button = document.createElement('button');
    button.id = `tab-${tabCount}`;
    button.innerText = `Dataset #${tabCount}`;
    button.onclick = () => switchToTab(`content-${tabCount}`);

    const contentDiv = document.createElement('div');
    contentDiv.id = `content-${tabCount}`;
    contentDiv.className = 'tab-content';
    if (tabCount > 1) {
        contentDiv.style.display = 'none';
    }
    contentDiv.style.width = '800px';

    document.getElementById('tabs').appendChild(button);
    document.getElementById('content').appendChild(contentDiv);

    return contentDiv.id;
}

export function populateTab(tabId, datasetInfo, exportPng) {
    const content = document.getElementById(tabId);

    const datasetInfoDiv = datasetInfoPanel(datasetInfo);
    content.appendChild(datasetInfoDiv);

    const canvas = document.createElement('canvas');
    canvas.id = datasetInfo.id;
    content.appendChild(canvas);

    const br = document.createElement('br');
    content.appendChild(br);

    const button = document.createElement('button');
    button.innerText = 'Export to PNG';
    button.onclick = exportPng;
    content.appendChild(button);
}

function switchToTab(tabId) {
    const tabContent = document.getElementsByClassName("tab-content");
    for (let i = 0; i < tabContent.length; i++) {
        tabContent[i].style.display = "none";
    }
    document.getElementById(tabId).style.display = "block";
}

function datasetInfoPanel(datasetInfo) {
    const datasetInfoPanel = document.createElement('div');
    datasetInfoPanel.style.width = '800px';
    datasetInfoPanel.style.border = '1px solid black';
    datasetInfoPanel.style.padding = '10px';
    datasetInfoPanel.style.marginBottom = '10px';

    const titleParagraph = document.createElement('p');
    titleParagraph.innerHTML = `<b>Dataset #${tabCount}:</b> ${datasetInfo.title}`;
    datasetInfoPanel.appendChild(titleParagraph);

    const descriptionParagraph = document.createElement('p');
    descriptionParagraph.innerText = `${datasetInfo.description}`;
    datasetInfoPanel.appendChild(descriptionParagraph);

    const rowCountParagraph = document.createElement('p');
    rowCountParagraph.innerHTML = `<b>Row count:</b> ${datasetInfo.rowCount}`;
    datasetInfoPanel.appendChild(rowCountParagraph);

    const columnsParagraph = document.createElement('p');
    columnsParagraph.innerText = `Columns:`;
    columnsParagraph.style.fontWeight = 'bold';
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
