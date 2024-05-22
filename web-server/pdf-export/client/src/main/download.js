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
 * Opens the file download dialog with a link to the passed file.
 *
 * @param dialog an object enclosing the download dialog element
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
