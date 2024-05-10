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

export function newFiltersFor(columns, applyFiltering) {
    const filters = columns.map((column) => input(column));
    const filterContainer = document.getElementById('filters');
    filters.forEach(filter => filterContainer.appendChild(filter));
    filters.forEach((filter) => {
        filter.addEventListener('input', () => {
            const values = filters.map(input => {
                return {index: input.index, value: input.value};
            });
            applyFiltering(values);
        });
    });
    return filters;
}

function input(filterableColumn) {
    const input = document.createElement("input");
    const lowercaseColumn = filterableColumn.name.toLowerCase();
    input.placeholder = `Filter by ${lowercaseColumn}`;
    input.style.width = `${filterableColumn.width}px`;
    input.index = filterableColumn.index;
    return input;
}
