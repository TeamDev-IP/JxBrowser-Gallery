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
import { Grid, html } from "gridjs";

const data = httpGet('http://localhost:8080/dataset/dietary-composition-by-country/data');
const array = csvToArray(data);
console.log(array);
const grid = new Grid({
    columns: [
        "Entity",
        "Code",
        "Year",
        "Type",
        {
            name: html('<div style="border-bottom: 1px solid #ccc">Value, per person, per day</div>'),
            formatter: (cell) => `${((parseFloat(cell) ? parseFloat(cell) : 0).toFixed(3))} kcal`,
            attributes: (cell) => {
                return {
                    'data-cell-content': cell,
                    'style': 'text-align: right'
                };
            }
        }
    ],
    data: array,
    search: true,
    pagination: {
        limit: 20,
        summary: true
    },
    style: {
        table: {
            border: '1px solid #ccc',
        },
        th: {
            color: '#000',
            'border-bottom': '1px solid #ccc',
            'text-align': 'left'
        },
        td: {
            'text-align': 'left',
            width: '100px'
        }
    },
    className: {
        paginationButton: 'btn btn-outline-secondary',
    }
});

grid.render(document.getElementById("content"));
