/*
 *  Copyright (c) 2025 TeamDev
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
 * A key for the email notifications in the local storage.
 */
const emailNotificationsKey = "email-notifications";
/**
 * A key for the desktop notifications in the local storage.
 */
const desktopNotificationsKey = "desktop-notifications";

/**
 * Returns true if the email notifications are enabled.
 */
function emailNotificationsFromStorage() {
    return localStorage.getItem(emailNotificationsKey) === "true" || false;
}

/**
 * Saves "true" to the local storage if the email notifications are enabled.
 */
function saveEmailNotificationsInStorage(isEnabled: boolean) {
    localStorage.setItem(emailNotificationsKey, isEnabled ? "true" : "false");
}

/**
 * Returns true if the desktop notifications are enabled.
 */
function desktopNotificationsKeyFromStorage() {
    return localStorage.getItem(desktopNotificationsKey) === "true" || false;
}

/**
 * Saves "true" to the local storage if the desktop notifications are enabled.
 */
function saveDesktopNotificationsKeyInStorage(isEnabled: boolean) {
    localStorage.setItem(desktopNotificationsKey, isEnabled ? "true" : "false");
}

export {
    emailNotificationsFromStorage,
    saveEmailNotificationsInStorage,
    desktopNotificationsKeyFromStorage,
    saveDesktopNotificationsKeyInStorage
};
