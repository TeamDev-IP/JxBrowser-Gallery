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

import {englishLanguage, LanguageOption} from "@/converter/language.ts";

/**
 * A key for the launch-at-startup preference in the local storage.
 */
const launchAtStartupKey = "launch-at-startup";
/**
 * A key for the check-for-updates preference in the local storage.
 */
const checkForUpdatesKey = "check-for-updates";
/**
 * A key for the language in the local storage.
 */
const languageKey = "language";

/**
 * Returns true if the launch-at-startup preference is enabled.
 */
function launchAtStartupFromStorage() {
    return localStorage.getItem(launchAtStartupKey) === "true" || false;
}

/**
 * Saves "true" to the local storage if the launch-at-startup preference is enabled.
 */
function saveLaunchAtStartupInStorage(isEnabled: boolean) {
    localStorage.setItem(launchAtStartupKey, isEnabled ? "true" : "false");
}

/**
 * Returns true if the check-for-updates preference is enabled.
 */
function checkForUpdatesFromStorage() {
    return localStorage.getItem(checkForUpdatesKey) === "true" || false;
}

/**
 * Saves "true" to the local storage if the check-for-updates preference is enabled.
 */
function saveCheckForUpdatesInStorage(isEnabled: boolean) {
    localStorage.setItem(checkForUpdatesKey, isEnabled ? "true" : "false");
}

/**
 * Reads the language from the local storage.
 */
function languageFromStorage() {
    return localStorage.getItem(languageKey) as LanguageOption || englishLanguage;
}

/**
 * Saves a new language to the local storage.
 */
function saveLanguageInStorage(language: LanguageOption) {
    localStorage.setItem(languageKey, language);
}

export {
    launchAtStartupFromStorage,
    saveLaunchAtStartupInStorage,
    checkForUpdatesFromStorage,
    saveCheckForUpdatesInStorage,
    languageFromStorage,
    saveLanguageInStorage
};


