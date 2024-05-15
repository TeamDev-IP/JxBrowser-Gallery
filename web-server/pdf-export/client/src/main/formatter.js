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
