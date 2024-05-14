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

let previousRow;
const modifiedRows = new Map();

/**
 * Creates a new grid visualizing the data about the dietary composition of countries.
 *
 * @param data the data to visualize, in the form of a two-dimensional array
 * @return the created {@link Grid} instance
 */
export function newGrid(data) {
    const grid = new Grid({
        columns: columns(),
        data: data,
        pagination: {
            limit: 20,
            summary: true
        },
        className: {
            table: 'small',
            pagination: 'small',
            paginationButton: 'btn btn-outline-dark'
        },
        width: '60%',
        autoWidth: true
    });
    const filters = [];
    grid.config.store.subscribe(
        (state, prev) => renderStateListener(state, prev, () => {
            modifiedRows.clear();
            previousRow = null;
            if (filters.length === 0) {
                filters.push(createFilters(grid, data));
            }
        })
    );
    return grid;
}

/**
 * Returns the columns configuration of the grid.
 */
function columns() {
    return [
        {
            id: 'entity',
            name: html(
                '<div class="entity-column">' +
                'Entity' +
                '</div>'
            ),
            formatter: (cell, row) => {
                if (modifiedRows.get(row.id)) {
                    return modifiedRows.get(row.id)[0];
                }
                if (previousRow
                    && previousRow.cells[0].data === row.cells[0].data
                    && previousRow.cells[1].data === row.cells[1].data
                    && previousRow.cells[2].data === row.cells[2].data) {
                    previousRow = row;
                    modifiedRows.set(row.id, ['', '', '', row.cells[3].data, row.cells[4].data]);
                    return '';
                }
                modifiedRows.set(row.id, [row.cells[0].data, row.cells[1].data, row.cells[2].data, row.cells[3].data, row.cells[4].data]);
                previousRow = row;
                return cell;
            }
        },
        {
            id: 'code',
            name: 'Code',
            formatter: (cell, row) => {
                if (modifiedRows.get(row.id)) {
                    return modifiedRows.get(row.id)[1];
                }
                const pageStart = modifiedRows.size % PAGE_SIZE === 0;
                if (!pageStart
                    && previousRow
                    && previousRow.cells[0].data === row.cells[0].data
                    && previousRow.cells[1].data === row.cells[1].data
                    && previousRow.cells[2].data === row.cells[2].data) {
                    previousRow = row;
                    modifiedRows.set(row.id, ['', '', '', row.cells[3].data, row.cells[4].data]);
                    return '';
                }
                modifiedRows.set(row.id, [row.cells[0].data, row.cells[1].data, row.cells[2].data, row.cells[3].data, row.cells[4].data]);
                previousRow = row;
                return cell;
            }
        },
        {
            id: 'year',
            name: html(
                '<div class="right-aligned">' +
                'Year' +
                '</div>'
            ),
            formatter: (cell, row) => {
                if (modifiedRows.get(row.id)) {
                    return html(
                        '<div class="right-aligned">' +
                        `${modifiedRows.get(row.id)[2]}` +
                        '</div>'
                    );
                }
                const pageStart = modifiedRows.size % PAGE_SIZE === 0;
                if (!pageStart
                    && previousRow
                    && previousRow.cells[0].data === row.cells[0].data
                    && previousRow.cells[1].data === row.cells[1].data
                    && previousRow.cells[2].data === row.cells[2].data) {
                    previousRow = row;
                    modifiedRows.set(row.id, ['', '', '', row.cells[3].data, row.cells[4].data]);
                    return '';
                }
                modifiedRows.set(row.id, [row.cells[0].data, row.cells[1].data, row.cells[2].data, row.cells[3].data, row.cells[4].data]);
                previousRow = row;
                return html(
                    '<div class="right-aligned">' +
                    `${cell}` +
                    '</div>'
                );
            }
        },
        {
            id: 'type',
            name: 'Type',
            formatter: cell => {
                return cell;
            }
        },
        {
            id: 'value',
            name: html(
                '<div class="right-aligned">' +
                'Value' +
                '</div>'
            ),
            formatter: (cell) => html(
                '<div class="right-aligned">' +
                `${((parseFloat(cell) ? parseFloat(cell) : 0).toFixed(1))} kcal` +
                '</div>'
            )
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
        {name: "Year", index: 2, width: widths[2]},
        {name: "Type", index: 3, width: widths[3]}
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
 * Listens to the grid state changes and triggers `onRendered` when the grid is fully rendered.
 */
function renderStateListener(state, prevState, onRendered) {
    if (prevState.status < state.status) {
        if (prevState.status === 1 && state.status === 2) {
            onRendered();
        }
    }
}
