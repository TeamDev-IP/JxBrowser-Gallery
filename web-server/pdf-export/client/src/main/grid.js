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

import {Grid, html} from "gridjs";
import {DeduplicatingFormatter} from "./formatter";

/**
 * The flag indicating whether the grid has been initialized.
 */
let initialized = false;

/**
 * The number of buttons to show in the pagination controls section.
 */
const paginationButtonCount = 7;

/**
 * The fixed position of the pagination controls on the page.
 *
 * This variable is used to store the initial position of the pagination controls
 * and fixate them in place while the table content changes.
 */
let paginationControlsPosition = null;

/**
 * Creates a new grid visualizing the data about the dietary composition by region.
 *
 * The created grid is extended with the filtering capabilities as well as
 * the deduplication formatting for "Region", "Code", and "Year" columns.
 *
 * @param data the data to visualize, in the form of a two-dimensional array
 * @param pageSize the number of rows to show on a single page or `null`
 *                 to disable the pagination
 * @param showControls `true` to show the table controls, `false` otherwise
 * @param keyword the keyword to search for in the table
 * @return the created {@link Grid} instance
 */
export function newGrid(data, pageSize, showControls, keyword) {
    const formatter = new DeduplicatingFormatter([0, 1, 2]);
    const config = {
        columns: columns(formatter),
        data: data,
        className: {
            table: 'small',
            pagination: 'small',
            paginationSummary: 'pagination-text',
            paginationButton: 'btn btn-outline-dark btn-sm pagination-button'
        },
        autoWidth: true,
        search: {
            keyword: keyword
        }
    };
    if (pageSize) {
        config.pagination = {
            summary: true,
            limit: pageSize,
            buttonsCount: paginationButtonCount
        };
    }
    const grid = new Grid(config);
    grid.config.store.subscribe(
        (state, prev) => {
            renderStateListener(
                state,
                prev,
                () => onPreRendered(formatter),
                () => onRendered(showControls)
            );
        }
    );
    return grid;
}

/**
 * Returns the column configurations of the grid.
 */
function columns(formatter) {
    return [
        {
            id: 'region',
            name: html(
                '<div class="region-column">' +
                'Region' +
                '</div>'
            ),
            formatter: (cell, row) => {
                const formattedCell = formatter.format(row, 0);
                return html(
                    `<div class="region-cell">${formattedCell}</div>`
                );
            }
        },
        {
            id: 'code',
            name: html(
                '<div class="code-column">' +
                'Code' +
                '</div>'
            ),
            formatter: (cell, row) => formatter.format(row, 1)
        },
        {
            id: 'year',
            name: html(
                '<div class="right-aligned">' +
                'Year' +
                '</div>'
            ),
            formatter: (cell, row) => html(
                '<div class="right-aligned">' +
                `${formatter.format(row, 2)}` +
                '</div>'
            )
        },
        {
            id: 'type',
            name: 'Type',
            formatter: (cell, row) => formatter.format(row, 3)
        },
        {
            id: 'value',
            name: html(
                '<div class="right-aligned">' +
                'Value' +
                '</div>'
            ),
            formatter: (cell, row) => {
                const formatted = formatter.format(row, 4);
                return html(
                    '<div class="right-aligned">' +
                    `${((parseFloat(formatted) ? parseFloat(formatted) : 0).toFixed(1))} kcal` +
                    '</div>'
                );
            }
        }
    ];
}

/**
 * Clears the deduplication formatter upon the pre-rendering stage.
 */
function onPreRendered(formatter) {
    formatter.clear();
}

/**
 * Performs the necessary adjustments to the grid upon the rendering stage.
 */
function onRendered(showControls) {
    redrawRowSectionDividers();
    if (showControls) {
        if (!initialized) {
            restyleSearchBar();
            createPaginationFormatter();
            window.addEventListener('resize', adjustSideSpaceSize);
            initialized = true;
        }
        adjustSideSpaceSize();
    } else {
        hideSearchBar();
    }
    if (window.javaPrinter) {
        window.javaPrinter.print();
    }
}

/**
 * Creates the dividers that separate sections of data belonging to the same region and year.
 */
function redrawRowSectionDividers() {
    const sectionStarts = Array.from(document.getElementsByClassName('section-start'));
    sectionStarts.forEach(tr => {
        tr.classList.remove('section-start');
    });

    const regionCells = Array.from(document.getElementsByClassName('region-cell'));
    regionCells.forEach(cell => {
        if (cell.innerText !== '') {
            const closestTr = cell.closest('.gridjs-tr');
            closestTr.classList.add('section-start');
        }
    });
}

/**
 * Hides the search bar.
 */
function hideSearchBar() {
    const search = searchBar();
    search.style.display = 'none';
}

/**
 * Restyles the native Grid.js search bar to make it more similar to the rest
 * of the table controls.
 */
function restyleSearchBar() {
    const search = searchBar();
    search.type = '';
    search.classList.add('small', 'text-muted');
    search.placeholder = 'Search by keyword...';
}

/**
 * Returns the search bar element.
 */
function searchBar() {
    return Array.from(document.getElementsByClassName('gridjs-search-input'))[0];
}

/**
 * Creates an observer that fixes the positions of the pagination controls.
 *
 * The default controls provided by Grid.js tend to jump up and down as well as
 * left to right upon the page switching. This observer constantly reformats
 * the controls to fix them in place for more convenient navigation.
 */
function createPaginationFormatter() {
    const observer = new MutationObserver((mutations) => {
        mutations.forEach((mutation) => {
            if (mutation.type === 'childList') {
                fixatePaginationControls();
            }
        });
    });
    observer.observe(document, {childList: true, subtree: true});
}

/**
 * Fixes the pagination controls vertical position and dimensions.
 */
function fixatePaginationControls() {
    const paginationDiv = document.getElementsByClassName('gridjs-pagination')[0];
    if (paginationDiv && !paginationControlsPosition) {
        paginationControlsPosition = paginationDiv.getBoundingClientRect();
    }
    paginationDiv.style.position = 'absolute';
    paginationDiv.style.top = `${paginationControlsPosition.top}px`;

    const paginationButtons = Array.from(document.getElementsByClassName('pagination-button'));
    paginationButtons.filter(button => button.innerText !== 'Previous')
        .filter(button => button.innerText !== 'Next')
        .forEach(button => button.classList.add('small-pagination-button'));

    paginationButtons.forEach(button => button.style.display = 'inline-block');

    const targetCount = paginationButtonCount + 2;
    const realCount = paginationButtons.length;
    if (realCount <= targetCount) {
        return;
    }
    buttonsToHide(paginationButtons, realCount, targetCount)
        .forEach(button => button.style.display = 'none');
}

/**
 * Determines the buttons to hide when re-formatting the pagination controls.
 *
 * The removal algorithm aims to keep the current page button in the center, if possible.
 */
function buttonsToHide(paginationButtons, realCount, targetCount) {
    const buttonText = paginationButtons.map(button => button.innerText);
    const firstBreak = buttonText.indexOf('...');
    const lastBreak = buttonText.lastIndexOf('...');
    const buttonsToHide = [];
    const buttonsToHideCount = realCount - targetCount;
    if (firstBreak === -1) {
        return [];
    }
    if (firstBreak === lastBreak) {
        const buttonsAfterBreak = paginationButtons.length - firstBreak - 1;
        if (buttonsAfterBreak > 2) {
            for (let i = firstBreak + 1; i < firstBreak + buttonsToHideCount + 1; i++) {
                buttonsToHide.push(paginationButtons[i]);
            }
        } else {
            for (let i = firstBreak - buttonsToHideCount; i < firstBreak; i++) {
                buttonsToHide.push(paginationButtons[i]);
            }
        }
    } else {
        const buttonsToRemoveOnEachSide = buttonsToHideCount / 2;
        for (let i = 0; i < buttonsToRemoveOnEachSide; i++) {
            buttonsToHide.push(paginationButtons[firstBreak + 1 + i]);
            buttonsToHide.push(paginationButtons[lastBreak - 1 - i]);
        }
    }
    return buttonsToHide;
}

/**
 * Adjusts the height of the empty spaces so that they cover the whole
 * (including scrollable) page height.
 */
function adjustSideSpaceSize() {
    const leftSpace = document.getElementById('left-empty-space');
    const rightSpace = document.getElementById('right-empty-space');
    const documentHeight = document.documentElement.scrollHeight;
    leftSpace.style.height = `${documentHeight}px`;
    rightSpace.style.height = `${documentHeight}px`;
}

/**
 * Listens to the grid state changes and triggers `onPreRendered` and `onRendered`
 * depending on the state.
 */
function renderStateListener(state, prevState, onPreRendered, onRendered) {
    if (prevState.status < state.status) {
        if (prevState.status === 1 && state.status === 2) {
            onPreRendered();
        }
        if (prevState.status === 2 && state.status === 3) {
            onRendered();
        }
    }
}

window.newGrid = newGrid;
