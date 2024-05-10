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

/**
 * Creates a panel that displays the dataset information.
 *
 * @param datasetInfo the dataset information to display
 * @param datasetDataUrl the URL pointing to the file containing the dataset data
 * @return the panel with the dataset information
 */
export function leftPanel(datasetInfo, datasetDataUrl) {
    const panel = document.createElement('div');
    panel.id = 'left-panel';

    const infoContainer = document.createElement('div');

    const list = document.createElement('ul');
    list.classList.add('list-group');

    const description = document.createElement('li');
    description.classList.add('list-group-item');
    description.classList.add('d-flex');
    description.classList.add('justify-content-between');
    description.classList.add('align-items-start');
    description.innerHTML = `${datasetInfo.description}`;
    list.appendChild(description);

    const rowCountLabel = document.createElement('span');
    rowCountLabel.innerText = 'Row count: ';

    const rowCountValue = document.createElement('span');
    rowCountValue.innerText = datasetInfo.rowCount;

    const rowCount = document.createElement('li');
    rowCount.classList.add('list-group-item');
    rowCount.classList.add('d-flex');
    rowCount.classList.add('justify-content-between');
    rowCount.classList.add('align-items-start');
    rowCount.appendChild(subheading(rowCountLabel.outerHTML + rowCountValue.outerHTML));
    list.appendChild(rowCount);

    const sourceLabel = document.createElement('span');
    sourceLabel.innerText = 'Source: ';

    const sourceValue = document.createElement('span');
    sourceValue.innerHTML = datasetInfo.source;

    const source = document.createElement('li');
    source.classList.add('list-group-item');
    source.classList.add('d-flex');
    source.classList.add('justify-content-between');
    source.classList.add('align-items-start');
    source.appendChild(subheading(sourceLabel.outerHTML + sourceValue.outerHTML));
    list.appendChild(source);

    const dataLinkLabel = document.createElement('span');
    dataLinkLabel.innerText = 'Download: ';

    const dataLinkValue = document.createElement('a');
    dataLinkValue.href = datasetDataUrl;
    dataLinkValue.download = 'data.csv';
    dataLinkValue.innerText = 'data.csv';

    const dataLink = document.createElement('li');
    dataLink.classList.add('list-group-item');
    dataLink.classList.add('d-flex');
    dataLink.classList.add('justify-content-between');
    dataLink.classList.add('align-items-start');
    dataLink.appendChild(subheading(dataLinkLabel.outerHTML + dataLinkValue.outerHTML));
    list.appendChild(dataLink);

    infoContainer.appendChild(list);
    panel.appendChild(infoContainer);

    return panel;
}

function subheading(title) {
    const titleDiv = document.createElement('div');
    titleDiv.innerHTML = title;
    return titleDiv;
}
