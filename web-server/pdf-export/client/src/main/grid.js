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
import {newFiltersFor} from "./filters";
import {DeduplicatingFormatter} from "./formatter";

/**
 * Creates a new grid visualizing the data about the dietary composition by region.
 *
 * The created grid is extended with the filtering capabilities as well as
 * the deduplication formatting for "Region", "Code", and "Year" columns.
 *
 * @param data the data to visualize, in the form of a two-dimensional array
 * @param pageSize the number of rows to show on a single page or `null`
 *                 to disable the pagination
 * @param showFilters `true` to show the filters, `false` otherwise
 * @return the created {@link Grid} instance
 */
export function newGrid(data, pageSize, showFilters) {
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
        autoWidth: true
    };
    if (pageSize) {
        config.pagination = {
            summary: true,
            limit: pageSize,
            buttonsCount: 5
        };
    }
    const grid = new Grid(config);
    const filters = [];
    grid.config.store.subscribe(
        (state, prev) => renderStateListener(
            state,
            prev,
            () => formatter.clear(),
            () => {
                const regionCells = document.getElementsByClassName('region-cell');
                Array.from(regionCells).forEach(cell => {
                    if (cell.innerText !== '') {
                        const closestTr = cell.closest('.gridjs-tr');
                        closestTr.setAttribute("style", "border-top: 1px solid #e5e7eb;");
                    }
                });
                if (showFilters && filters.length === 0) {
                    filters.push(createFilters(grid, data));
                }
                if (window.javaPrinter) {
                    window.javaPrinter.print();
                }
            }
        )
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
            name: 'Code',
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
 * Creates the filters that allow filtering the data in the grid.
 */
function createFilters(grid, data) {
    const headers = document.querySelectorAll('.gridjs-th');
    const widths = Array.from(headers)
        .map(header => header.offsetWidth);
    const filterableColumns = [
        {name: "Region", index: 0, width: widths[0]},
        {name: "Code", index: 1, width: widths[1]},
        {name: "Year", index: 2, width: widths[2], styles: ['right-aligned']},
        {name: "Type", index: 3, width: widths[3]},
    ];
    const filters = newFiltersFor(filterableColumns, values => applyFilters(grid, data, values));
    return filters;
}

/**
 * Applies the filter values to the dataset and re-renders the visualization.
 */
function applyFilters(grid, data, filterValues) {
    const filteredData = data.filter(row => {
        return filterValues.every((v) => {
            const filterValue = v.value.toLowerCase();
            return !filterValue
                || row[v.index]
                && row[v.index].toLowerCase().includes(filterValue);
        });
    });
    grid.updateConfig({
        data: filteredData
    }).forceRender();
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
