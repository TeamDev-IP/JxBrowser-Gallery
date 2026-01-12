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

/**
 * Creates a panel that displays the dataset information.
 *
 * @param datasetInfo the dataset information to display
 * @param datasetDataUrl the URL pointing to the file containing the dataset data
 * @param printToPdf the function to call when the user clicks the "Print to PDF" button
 * @return the panel with the dataset information
 */
export function newInfoPanel(datasetInfo, datasetDataUrl, printToPdf) {
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

    const buttonContainer = addListItem(list, '');
    const exportButton = document.createElement('button');
    exportButton.innerText = 'Export to PDF';
    exportButton.id = 'export-button';
    exportButton.classList.add('btn', 'btn-outline-dark');
    exportButton.addEventListener('click', () => {
        printToPdf();
    });
    buttonContainer.appendChild(exportButton);

    panel.appendChild(list);

    return panel;
}

/**
 * Adds an item with the passed inner `html` to the list.
 */
function addListItem(list, html) {
    const item = document.createElement('li');
    item.classList.add(
        'list-group-item',
        'align-items-start',
        'small'
    );
    item.innerHTML = html;
    list.appendChild(item);
    return item;
}
