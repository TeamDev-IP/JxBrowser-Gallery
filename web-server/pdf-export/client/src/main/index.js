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

import {httpGet} from "./http";
import "gridjs/dist/theme/mermaid.css";
import {csvToArray} from "./parsing";
import {Grid, html} from "gridjs";
import {leftPanel} from "./left-panel";

const SERVER_URL = 'http://localhost:8080';

const info = httpGet(`${SERVER_URL}/dataset/dietary-composition-by-country/info`);
const datasetInfo = JSON.parse(info);
const csv = httpGet(`${SERVER_URL}/dataset/dietary-composition-by-country/data`);
const data = csvToArray(csv);

const blob = new Blob([csv], {type: 'text/plain'});
const dataUrl = window.URL.createObjectURL(blob);

const infoPanel = leftPanel(datasetInfo, dataUrl);
document.getElementById('info-container').prepend(infoPanel);

const grid = new Grid({
    columns: [
        {id: 'entity', name: 'Entity', width: '20%'},
        {id: 'code', name: 'Code', width: '20%'},
        {id: 'year', name: 'Year', width: '20%'},
        {id: 'type', name: 'Type', width: '20%'},
        {
            id: 'value',
            name: 'Value, per person, per day',
            width: '20%',
            formatter: (cell) => html(
                '<div style="text-align: right">' +
                `${((parseFloat(cell) ? parseFloat(cell) : 0).toFixed(3))} kcal` +
                '</div>'
            ),
        }
    ],
    data: data,
    pagination: {
        limit: 20,
        summary: true
    },
    style: {
        table: {
            border: '1px solid #ccc',
            width: '85%'
        },
        th: {
            color: '#000',
            'border-bottom': '1px solid #ccc',
            'text-align': 'left',
            'padding-left': '3px',
            'padding-right': '2px'
        },
        td: {
            'text-align': 'left',
            'padding-left': '3px',
            'padding-right': '2px'
        }
    },
    className: {
        paginationButton: 'btn btn-outline-dark',
    }
});

grid.render(document.getElementById('grid'));

const filterableColumns = ["Entity", "Code", "Year", "Type"];
const filters = filterableColumns.map((column) => {
    const input = document.createElement("input");
    input.classList.add('filter');
    input.placeholder = `Filter by ${column.toLowerCase()}`;
    return input;
});

const filterContainer = document.getElementById('filters');
filters.forEach(input => filterContainer.appendChild(input));

filters.forEach((input) => {
    input.addEventListener('input', () => {
        const filteredData = applyFilters();
        grid.updateConfig({
            data: filteredData
        }).forceRender();
    });
});

function applyFilters() {
    return data.filter(row => {
        return filters.every((input, index) => {
            const filterValue = input.value.toLowerCase();
            return !filterValue || row[index] && row[index].toLowerCase().includes(filterValue);
        });
    });
}
