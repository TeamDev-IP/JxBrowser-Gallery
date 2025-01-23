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

import {defaultFontSize, FontSizeOption} from "@/components/converter/font-size.ts";
import {systemTheme, ThemeOption} from "@/components/converter/theme.ts";

/**
 * A key for the font size in the local storage.
 */
const fontSizeKey = "font-size";
/**
 * A key for the theme in the local storage.
 */
const themeKey = "vite-ui-theme";

/**
 * Reads the theme from the local storage.
 */
function themeFromStorage() {
    return localStorage.getItem(themeKey) as ThemeOption || systemTheme;
}

/**
 * Saves a new theme to the local storage.
 */
function saveThemeInStorage(language: ThemeOption) {
    localStorage.setItem(themeKey, language);
}

/**
 * Reads the font size from the local storage.
 */
function fontSizeFromStorage() {
    return localStorage.getItem(fontSizeKey) as FontSizeOption || defaultFontSize;
}

/**
 * Saves a new font size to the local storage.
 */
function saveFontSizeInStorage(option: FontSizeOption) {
    localStorage.setItem(fontSizeKey, option);
}

export {
    fontSizeFromStorage,
    saveFontSizeInStorage,
    themeFromStorage,
    saveThemeInStorage
};
