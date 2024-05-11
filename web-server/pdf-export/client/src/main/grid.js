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
            limit: 10,
            summary: true
        },
        className: {
            paginationButton: 'btn btn-outline-dark',
        }
    });
    const filters = [];
    grid.config.store.subscribe(
        (state, prev) => renderStateListener(state, prev, () => {
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
            name: 'Entity',
            width: '15%',
            'max-width': '15%'
        },
        {
            id: 'code',
            name: 'Code',
            width: '10%',
            'max-width': '10%',
            formatter: (cell) => html(
                '<div class="centered-cell">' +
                `${cell}` +
                '</div>'
            )
        },
        {
            id: 'year',
            name: 'Year',
            width: '10%',
            'max-width': '10%',
            formatter: (cell) => html(
                '<div class="centered-cell">' +
                `${cell}` +
                '</div>'
            )
        },
        {
            id: 'type',
            name: 'Type',
            width: '15%',
            'max-width': '15%'
        },
        {
            id: 'value',
            name: 'Value',
            width: '10%',
            'max-width': '10%',
            formatter: (cell) => html(
                '<div class="right-aligned-cell">' +
                `${((parseFloat(cell) ? parseFloat(cell) : 0).toFixed(2))} kcal` +
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
        if (prevState.status === 2 && state.status === 3) {
            onRendered();
        }
    }
}
