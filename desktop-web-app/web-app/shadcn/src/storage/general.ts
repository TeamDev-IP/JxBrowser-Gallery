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

export type LanguageOption = "English" | "German" | "French"

const englishLanguage: LanguageOption = "English"
const germanLanguage: LanguageOption = "German"
const frenchLanguage: LanguageOption = "French"

const launchAtStartupKey = "general.launchAtStartup";
const checkForUpdatesKey = "general.checkForUpdates";
const languageKey = "general.language";

function launchAtStartupFromStorage() {
    return localStorage.getItem(launchAtStartupKey) === "true" || false;
}

function saveLaunchAtStartupInStorage(isEnabled: boolean) {
    localStorage.setItem(launchAtStartupKey, isEnabled ? "true" : "false");
}

function checkForUpdatesFromStorage() {
    return localStorage.getItem(checkForUpdatesKey) === "true" || false;
}

function saveCheckForUpdatesInStorage(isEnabled: boolean) {
    localStorage.setItem(checkForUpdatesKey, isEnabled ? "true" : "false");
}

function languageFromStorage() {
    return localStorage.getItem(languageKey) as LanguageOption || "";
}

function saveLanguageInStorage(language: LanguageOption) {
    localStorage.setItem(languageKey, language);
}

export {
    englishLanguage,
    germanLanguage,
    frenchLanguage,
    launchAtStartupFromStorage,
    saveLaunchAtStartupInStorage,
    checkForUpdatesFromStorage,
    saveCheckForUpdatesInStorage,
    languageFromStorage,
    saveLanguageInStorage
}


