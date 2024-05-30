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

/**
 * Creates input elements for filtering the data in the grid.
 *
 * @param columns the filterable columns
 * @param applyFilters the function that applies the filter values
 * @return the created filter elements
 */
export function newFiltersFor(columns, applyFilters) {
    const filters = columns.map((column) => input(column));
    const filterContainer = document.getElementById('filters');
    filters.forEach(filter => filterContainer.appendChild(filter));
    filters.forEach((filter) => {
        filter.addEventListener('input', () => {
            const values = filters.map(input => {
                return {index: input.index, value: input.value};
            });
            applyFilters(values);
        });
    });
    return filters;
}

/**
 * Creates an input element for filtering the data by the passed column.
 */
function input(filterableColumn) {
    const input = document.createElement("input");
    input.classList.add('filter-input', 'small', 'text-muted');
    if (filterableColumn.styles) {
        input.classList.add(...filterableColumn.styles);
    }
    input.placeholder = `Search`;
    input.style.width = `${filterableColumn.width}px`;
    input.columnName = filterableColumn.name;
    input.index = filterableColumn.index;
    return input;
}
