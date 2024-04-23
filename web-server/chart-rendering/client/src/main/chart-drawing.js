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
import './parsing';

/**
 * The currently-drawn instance of the "Per capita energy use" chart.
 *
 * @type {Chart}
 */
let perCapitaEnergyUseChart;

/**
 * The currently-drawn instance of the "Energy consumption by source" chart.
 *
 * @type {Chart}
 */
let energyConsumptionBySourceChart;

/**
 * Default parameters for the "Per capita energy use" chart.
 *
 * @type {{
 *     entity: string, // the country or region to visualize the data for
 *     type: string, // 'line' or 'bar'
 *     showLabels: boolean, // whether to show data labels
 *     showTrendline: boolean, // whether to show a trendline
 *     xMin: number, // the minimum value for the x-axis
 *     xMax: number, // the maximum value for the x-axis
 * }}
 */
const perCapitaEnergyUseChartDefaults = {
    entity: 'World',
    type: 'line',
    showLabels: false,
    showTrendline: false,
    xMin: 1970,
    xMax: 2022
};

/**
 * Default parameters for the "Energy consumption by source" chart.
 *
 * @type {{
 *     entity: string, // the country or region to visualize the data for
 *     xMin: number, // the minimum value for the x-axis
 *     xMax: number, // the maximum value for the x-axis
 * }}
 */
const energyConsumptionBySourceChartDefaults = {
    entity: 'World',
    xMin: 1970,
    xMax: 2022,
};

/**
 * Draws a chart that visualizes the per capita energy use in a given country or region.
 *
 * @param canvas the ID of the canvas element to draw the chart on
 * @param data the data to visualize, in the form of {@link Array}
 * @param params the parameters for the chart. See {@link perCapitaEnergyUseChartDefaults}
 */
export function drawPerCapitaEnergyUseChart(
    canvas,
    data,
    params = perCapitaEnergyUseChartDefaults
) {
    if (perCapitaEnergyUseChart) {
        perCapitaEnergyUseChart.destroy();
    }
    const chartData = data;
    const colors = colorScheme(params.type);

    perCapitaEnergyUseChart = new Chart(
        document.getElementById(canvas),
        {
            plugins: [ChartDataLabels, chartTrendline],
            type: params.type,
            data: {
                datasets: datasets(),
            },
            options: options(),
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

    function datasets() {
        return [
            {
                label: `Per capita energy use, kilowatt hours, ${params.entity}`,
                data: chartData
                    .filter(row => row[0] === params.entity)
                    .map(row => {
                        return {x: parseInt(row[2]), y: parseFloat(row[3])};
                    }),
                borderColor: colors.chart,
                backgroundColor: colors.chart,
                trendlineLinear: trendline()
            },
        ];
    }

    function options() {
        return {
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
                    ticks: {
                        stepSize: 10,
                        callback: function (value) {
                            return value + ' kWh';
                        },
                    },
                },
            },
            devicePixelRatio: 3,
            plugins: {
                datalabels: {
                    align: 'top',
                    anchor: 'end',
                    display: params.showLabels ? 'auto' : false,
                    formatter: function (value) {
                        return Math.round(value.y) + ' kWh';
                    }
                },
                legend: {
                    onClick: false
                },
                tooltip: {
                    callbacks: {
                        label: function (context) {
                            return `${context.parsed.y} kWh`;
                        },
                        title: function (context) {
                            return context[0].label.replace(/\s/g, '');
                        }
                    }
                }
            }
        };
    }

    function trendline() {
        return params.showTrendline
            ? {
                colorMin: colors.trendline,
                colorMax: colors.trendline,
                lineStyle: "dotted|solid",
                width: 2
            }
            : null;
    }
}

/**
 * Draws a chart that visualizes the energy consumption by source in a given country or region.
 *
 * @param canvas the ID of the canvas element to draw the chart on
 * @param data the data to visualize, in the form of {@link Array}
 * @param params the parameters for the chart. See {@link energyConsumptionBySourceChartDefaults}
 */
export function drawEnergyConsumptionBySourceChart(
    canvas,
    data,
    params = energyConsumptionBySourceChartDefaults
) {
    if (energyConsumptionBySourceChart) {
        energyConsumptionBySourceChart.destroy();
    }
    const chartData = data
        .filter(row => row[0] === params.entity)
        .sort((a, b) => parseInt(a[2]) - parseInt(b[2]));
    const labels = chartData.map(row => row[2]);
    const colors = colorScheme();

    energyConsumptionBySourceChart = new Chart(
        document.getElementById(canvas),
        {
            type: 'line',
            data: {
                labels: labels,
                datasets: datasets(),
            },
            options: options(),
        },
    );

    function colorScheme() {
        return [
            '#153d5a', '#C43C4DFF', '#bc1058', '#bf8b2e', '#E18055FF',
            '#5CBE5EFF', '#0879ae', '#8a1538', '#005885'
        ];
    }

    function datasets() {
        return [
            {
                label: 'Oil, TWh',
                data: chartData.map(row => parseFloat(row[11])),
                borderColor: colors[8],
                backgroundColor: colors[8],
                fill: true,
                pointStyle: false
            },
            {
                label: 'Coal, TWh',
                data: chartData.map(row => parseFloat(row[10])),
                borderColor: colors[7],
                backgroundColor: colors[7],
                fill: true,
                pointStyle: false
            },
            {
                label: 'Gas, TWh',
                data: chartData.map(row => parseFloat(row[9])),
                borderColor: colors[6],
                backgroundColor: colors[6],
                fill: true,
                pointStyle: false
            },
            {
                label: 'Nuclear, TWh',
                data: chartData.map(row => parseFloat(row[8])),
                borderColor: colors[5],
                backgroundColor: colors[5],
                fill: true,
                pointStyle: false
            },
            {
                label: 'Hydropower, TWh',
                data: chartData.map(row => parseFloat(row[7])),
                borderColor: colors[4],
                backgroundColor: colors[4],
                fill: true,
                pointStyle: false
            },
            {
                label: 'Wind, TWh',
                data: chartData.map(row => parseFloat(row[6])),
                borderColor: colors[3],
                backgroundColor: colors[3],
                fill: true,
                pointStyle: false
            },
            {
                label: 'Solar, TWh',
                data: chartData.map(row => parseFloat(row[5])),
                borderColor: colors[2],
                backgroundColor: colors[2],
                fill: true,
                pointStyle: false
            },
            {
                label: 'Biofuels, TWh',
                data: chartData.map(row => parseFloat(row[4])),
                borderColor: colors[1],
                backgroundColor: colors[1],
                fill: true,
                pointStyle: false
            },
            {
                label: 'Other renewables (including geothermal and biomass), TWh',
                data: chartData.map(row => parseFloat(row[3])),
                borderColor: colors[0],
                backgroundColor: colors[0],
                fill: true,
                pointStyle: false
            }
        ];
    }

    function options() {
        return {
            animation: false,
            legend: {
                labels: {
                    fontFamily: 'Lato',
                }
            },
            scales: {
                x: {
                    min: params.xMin,
                    max: params.xMax,
                    type: 'linear',
                    ticks: {
                        callback: function (value) {
                            return value + '';
                        },
                    },
                },
                y: {
                    stacked: true,
                    ticks: {
                        callback: function (value) {
                            return value + ' TWh';
                        },
                    },
                }
            },
            devicePixelRatio: 3,
            plugins: {
                legend: {
                    onClick: false
                },
                tooltip: {
                    mode: 'index',
                    callbacks: {
                        label: function (context) {
                            return context.dataset.label + ': ' + `${context.parsed.y}`;
                        },
                        title: function (context) {
                            return context[0].label.replace(/\s/g, '');
                        }
                    }
                }
            }
        };
    }
}

window.drawPerCapitaEnergyUseChart = drawPerCapitaEnergyUseChart;
window.drawEnergyConsumptionBySourceChart = drawEnergyConsumptionBySourceChart;
