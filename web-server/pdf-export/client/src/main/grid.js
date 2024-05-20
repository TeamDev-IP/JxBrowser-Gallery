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

import {Grid, html} from "gridjs";
import {newFiltersFor} from "./filters";
import {DeduplicatingFormatter} from "./formatter";

/**
 * Creates a new grid visualizing the data about the dietary composition of countries.
 *
 * The created grid is extended with the filtering capabilities as well as
 * the deduplication formatting for "Entity", "Code", and "Year" columns.
 *
 * @param data the data to visualize, in the form of a two-dimensional array
 * @param pageSize the number of rows to show on a single page or `null` to show all rows
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
            limit: pageSize
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
 * Returns the columns configuration of the grid.
 */
function columns(formatter) {
    return [
        {
            id: 'entity',
            name: html(
                '<div class="entity-column">' +
                'Entity' +
                '</div>'
            ),
            formatter: (cell, row) => formatter.format(row, 0)
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
        {name: "Entity", index: 0, width: widths[0]},
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
