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

import {Modal} from 'bootstrap';

/**
 * Creates a new modal element enclosing the download dialog.
 */
export function newDownloadDialog() {
    const modal = document.createElement('div');
    modal.id = 'download-dialog';
    modal.className = 'modal';
    modal.tabIndex = -1;

    const modalDialog = document.createElement('div');
    modalDialog.className = 'modal-dialog';
    modal.appendChild(modalDialog);

    const modalContent = document.createElement('div');
    modalContent.className = 'modal-content';
    modalDialog.appendChild(modalContent);

    const modalHeader = document.createElement('div');
    modalHeader.className = 'modal-header';
    modalContent.appendChild(modalHeader);

    const modalTitle = document.createElement('h5');
    modalTitle.className = 'modal-title';
    modalTitle.innerText = 'Your file is ready';
    modalHeader.appendChild(modalTitle);

    const closeButton = document.createElement('button');
    closeButton.type = 'button';
    closeButton.className = 'btn-close';
    closeButton.dataset.bsDismiss = 'modal';
    closeButton.ariaLabel = 'Close';
    modalHeader.appendChild(closeButton);

    const modalBody = document.createElement('div');
    modalBody.className = 'modal-body';
    modalContent.appendChild(modalBody);

    const dialogBody = document.createElement('p');
    dialogBody.id = 'download-dialog-body';
    modalBody.appendChild(dialogBody);

    return {element: modal, body: dialogBody};
}

/**
 * Opens the PDF generation waiting dialog.
 *
 * @param dialog an object enclosing the dialog element
 */
export function openWaitingDialog(dialog) {
    dialog.body.innerHTML = 'Generating the PDF file...';
    const modal = Modal.getOrCreateInstance(dialog.element);
    modal.show();
}

/**
 * Opens the file download dialog with a link to the passed file.
 *
 * @param dialog an object enclosing the dialog element
 * @param url the URL of the file to download
 * @param filename the name under which the file should be saved
 */
export function openDownloadDialog(dialog, url, filename) {
    dialog.body.innerHTML =
        `Download link: <a href="${url}" download="${filename}">${filename}</a>
                <br><br>
                Also, available on the server filesystem as
                '<i>*project root*/web-server/pdf-export/server/exported/${filename}</i>'.`
    const modal = Modal.getOrCreateInstance(dialog.element);
    modal.show();
}
