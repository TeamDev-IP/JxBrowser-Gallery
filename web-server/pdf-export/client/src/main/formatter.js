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
 * A formatter that deduplicates the specified columns in a `Grid` instance.
 *
 * If all values of the specified columns match the corresponding values
 * in the previous row, this formatter replaces them with empty strings.
 */
export class DeduplicatingFormatter {

    /**
     * Creates a new formatter which deduplicates the specified columns.
     *
     * @param columnsToDeduplicate the indexes of the columns to deduplicate
     */
    constructor(columnsToDeduplicate) {
        this.columns = columnsToDeduplicate;
        this.formatted = new Map();
        this.previousRow = null;
    }

    /**
     * Formats a cell located at the specified `columnIndex` in `row`.
     *
     * @param row the row containing the cell
     * @param columnIndex the index of the column containing the cell
     * @return the formatted cell value
     */
    format(row, columnIndex) {
        const formatted = this.formatted.get(row.id);
        if (formatted) {
            return formatted[columnIndex];
        }
        const rowData = row.cells.map(cell => cell.data);
        const shouldDeduplicate = this.previousRow && this.columns.every(c => {
            return this.previousRow.cells[c].data === row.cells[c].data;
        });
        if (shouldDeduplicate) {
            this.columns.forEach(index => {
                rowData[index] = '';
            });
        }
        this.formatted.set(row.id, rowData);
        this.previousRow = row;
        return rowData[columnIndex];
    }

    /**
     * Clears the internal state of the formatter, allowing to deduplicate rows
     * on a page-by-page basis.
     */
    clear() {
        this.formatted.clear();
        this.previousRow = null;
    }
}
