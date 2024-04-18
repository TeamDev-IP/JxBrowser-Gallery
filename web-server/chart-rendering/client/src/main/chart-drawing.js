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

/**
 * The currently-drawn instance of the "fossil fuels consumption" chart.
 *
 * @type {Chart}
 */
let fossilFuelsConsumptionChart;

/**
 * The currently-drawn instance of the "life expectancy" chart.
 */
let lifeExpectancyChart;

/**
 * Default parameters for the "fossil fuels consumption" chart.
 *
 * @type {{
 *     type: string, // 'line' or 'bar'
 *     showLabels: boolean, // whether to show data labels
 *     showTrendline: boolean, // whether to show a trendline
 *     xMin: number, // the minimum value for the x-axis
 *     xMax: number, // the maximum value for the x-axis
 *     yMin: number, // the minimum value for the y-axis
 *     yMax: number, // the maximum value for the y-axis
 * }}
 */
const fossilFuelConsumptionChartDefaults = {
    type: 'line',
    showLabels: false,
    showTrendline: false,
    xMin: 1996,
    xMax: 2022,
    yMin: 0,
    yMax: 100
};

/**
 * Default parameters for the "life expectancy" chart.
 *
 * @type {{
 *     type: string, // 'line' or 'bar'
 *     showLabels: boolean, // whether to show data labels
 *     showTrendline: boolean, // whether to show a trendline
 *     xMin: number, // the minimum value for the x-axis
 *     xMax: number, // the maximum value for the x-axis
 *     yMin: number, // the minimum value for the y-axis
 *     yMax: number, // the maximum value for the y-axis
 * }}
 */
const lifeExpectancyChartDefaults = {
    type: 'line',
    showLabels: false,
    showTrendline: false,
    xMin: 1996,
    xMax: 2022,
    yMin: 0,
    yMax: 100
};

/**
 * Draws a chart that visualizes the share of primary energy consumption from
 * fossil fuels in Portugal.
 *
 * @param canvas the ID of the canvas element to draw the chart on
 * @param csvData the CSV data to be visualized
 * @param params the parameters for the chart. See {@link fossilFuelConsumptionChartDefaults}
 */
export function drawFossilFuelsConsumptionChart(canvas,
                                                csvData,
                                                params = fossilFuelConsumptionChartDefaults) {
    if (fossilFuelsConsumptionChart) {
        fossilFuelsConsumptionChart.destroy();
    }
    const colors = colorScheme(params.type);
    const parsedData = csvToArray(csvData);
    const trendline = params.showTrendline
        ? {
            colorMin: colors.trendline,
            colorMax: colors.trendline,
            lineStyle: "dotted|solid",
            width: 2
        }
        : null;
    fossilFuelsConsumptionChart = new Chart(
        document.getElementById(canvas),
        {
            plugins: [ChartDataLabels, chartTrendline],
            type: params.type,
            data: {
                datasets: [
                    {
                        label: 'Share of primary energy consumption from fossil fuels, Portugal',
                        data: parsedData.map(row => {
                            return {x: parseInt(row[0]), y: parseFloat(row[1])};
                        }),
                        borderColor: colors.chart,
                        backgroundColor: colors.chart,
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
                    x: {
                        type: 'linear',
                        min: params.xMin,
                        max: params.xMax,
                        ticks: {
                            callback: function (value) {
                                return value + '';
                            },
                        },
                    },
                    y: {
                        min: params.yMin,
                        max: params.yMax,
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
                        display: params.showLabels,
                        formatter: function (value) {
                            return Math.round(value.y) + '%';
                        }
                    }
                }
            },
        },
    );

    function colorScheme(type) {
        if (type === 'line') {
            return {
                chart: '#c15065',
                trendline: '#0879ae80'
            };
        }
        if (type === 'bar') {
            return {
                chart: '#0879ae80',
                trendline: '#c15065'
            };
        }
        throw new Error(`Unknown chart type: '${type}'.`);
    }
}

/**
 * Draws a chart that visualizes the life expectancy at birth in Portugal.
 *
 * @param canvas the ID of the canvas element to draw the chart on
 * @param csvData the CSV data to be visualized
 * @param params the parameters for the chart. See {@link lifeExpectancyChartDefaults}
 */
export function drawLifeExpectancyChart(canvas, csvData, params = lifeExpectancyChartDefaults) {
    if (lifeExpectancyChart) {
        lifeExpectancyChart.destroy();
    }
    const parsedData = csvToArray(csvData);
    const colors = colorScheme(params.type);
    const trendline = params.showTrendline
        ? {
            colorMin: colors.trendline,
            colorMax: colors.trendline,
            lineStyle: "dotted|solid",
            width: 2
        }
        : null;
    lifeExpectancyChart = new Chart(
        document.getElementById(canvas),
        {
            plugins: [ChartDataLabels, chartTrendline],
            type: params.type,
            data: {
                datasets: [
                    {
                        label: 'Life expectancy at birth, Portugal',
                        data: parsedData
                            .filter(row => row[0] === 'Portugal')
                            .map(row => {
                                return {x: parseInt(row[2]), y: parseFloat(row[3])};
                            }),
                        borderColor: colors.chart,
                        backgroundColor: colors.chart,
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
                    x: {
                        type: 'linear',
                        min: params.xMin,
                        max: params.xMax,
                        ticks: {
                            callback: function (value) {
                                return value + '';
                            },
                        },
                    },
                    y: {
                        min: params.yMin,
                        max: params.yMax,
                        ticks: {
                            stepSize: 10,
                            callback: function (value) {
                                return value + ' years';
                            },
                        },
                    },
                },
                devicePixelRatio: 3,
                plugins: {
                    datalabels: {
                        align: 'top',
                        anchor: 'end',
                        display: params.showLabels,
                        formatter: function (value) {
                            return Math.round(value.y);
                        }
                    }
                }
            },
        },
    );

    function colorScheme(type) {
        if (type === 'line') {
            return {
                chart: '#c1bf50',
                trendline: '#0879ae80'
            };
        }
        if (type === 'bar') {
            return {
                chart: '#0879ae80',
                trendline: '#c1bf50'
            };
        }
        throw new Error(`Unknown chart type: '${type}'.`);
    }
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
window.drawLifeExpectancyChart = drawLifeExpectancyChart;
