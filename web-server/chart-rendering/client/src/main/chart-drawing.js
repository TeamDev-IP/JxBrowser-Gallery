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

import Chart from 'chart.js/auto';
import ChartDataLabels from 'chartjs-plugin-datalabels';
import chartTrendline from 'chartjs-plugin-trendline';

let currentlyDrawnChart;

/**
 * Draws a chart that visualizes the share of primary energy consumption from
 * fossil fuels in Portugal.
 *
 * @param canvas the ID of the canvas element to draw the chart on
 * @param csvData the CSV data to be visualized
 * @param type the type of the chart to draw
 * @param displayLabels whether to display the data labels on the chart
 * @param displayTrendline whether to display the trendline on the chart
 */
export function drawFossilFuelsConsumptionChart(canvas,
                                                csvData,
                                                type = 'line',
                                                displayLabels = false,
    if (currentlyDrawnChart) {
        currentlyDrawnChart.destroy();
    }
    const trendlineColor = type === 'line' ? '#0879ae80' : '#C15065';
    const parsedData = csvToArray(csvData);
    const trendline = displayTrendline
        ? {
            colorMin: trendlineColor,
            colorMax: trendlineColor,
            lineStyle: "dotted|solid",
            width: 2
        }
        : null;
    currentlyDrawnChart = new Chart(
        document.getElementById(canvas),
        {
            plugins: [ChartDataLabels, chartTrendline],
            type: type,
            data: {
                labels: parsedData.map(row => row[0]),
                datasets: [
                    {
                        label: 'Share of primary energy consumption from fossil fuels, Portugal',
                        data: parsedData.map(row => row[1]),
                        borderColor: '#C15065',
                        backgroundColor: '#0879ae80',
                        trendlineLinear: trendline
                    },
                ],
            },
            options: {
                animation: false,
                legend: {
                    labels: {
                        fontFamily: 'Lato'
                    }
                },
                scales: {
                    y: {
                        min: 0,
                        max: 100,
                        ticks: {
                            stepSize: 20,
                            callback: function (value) {
                                return value + '%';
                            },
                        },
                    },
                },
                devicePixelRatio: 3,
                plugins: {
                    datalabels: {
                        align: 'top',
                        anchor: 'end',
                        display: displayLabels,
                        formatter: function (value) {
                            return Math.round(value) + '%';
                        }
                    }
                }
            },
        },
    );
}

/**
 * Parses the CSV data from a string into a JS array.
 */
function csvToArray(strData, strDelimiter) {
    strDelimiter = (strDelimiter || ',');
    const objPattern = new RegExp(
        (
            '(\\' + strDelimiter + '|\\r?\\n|\\r|^)' +
            '(?:"([^"]*(?:""[^"]*)*)"|' +
            '([^"\\' + strDelimiter + '\\r\\n]*))'
        ),
        'gi',
    );
    const arrData = [[]];
    let arrMatches = null;
    while (arrMatches = objPattern.exec(strData)) {
        const strMatchedDelimiter = arrMatches[1];
        if (
            strMatchedDelimiter.length &&
            strMatchedDelimiter !== strDelimiter
        ) {
            arrData.push([]);
        }
        let strMatchedValue;
        if (arrMatches[2]) {
            strMatchedValue = arrMatches[2].replace(
                new RegExp('""', 'g'),
                '"',
            );
        } else {
            strMatchedValue = arrMatches[3];
        }
        arrData[arrData.length - 1].push(strMatchedValue);
    }
    return (arrData);
}

window.drawFossilFuelsConsumptionChart = drawFossilFuelsConsumptionChart;
