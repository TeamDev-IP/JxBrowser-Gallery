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
export function newLeftPanel(datasetInfo, datasetDataUrl) {
    const panel = document.createElement('div');

    const list = document.createElement('ul');
    list.classList.add('list-group');

    addListItem(list, `${datasetInfo.description}`);
    addListItem(list, `<h6>Row count</h6> ${datasetInfo.rowCount}`);
    addListItem(list, `<h6>Source</h6> ${datasetInfo.source}`);

    const dataLink = document.createElement('a');
    dataLink.href = datasetDataUrl;
    dataLink.download = 'data.csv';
    dataLink.innerText = 'data.csv';
    addListItem(list, `<h6>Download</h6> ${dataLink.outerHTML}`)

    panel.appendChild(list);

    return panel;
}

function addListItem(list, html) {
    const item = document.createElement('li');
    item.classList.add(
        'list-group-item',
        'align-items-start',
        'small'
    );
    item.innerHTML = html;
    list.appendChild(item);
}
