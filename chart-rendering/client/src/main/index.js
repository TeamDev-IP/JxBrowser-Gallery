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

const data = `Portugal,PRT,1996,81.29369
Portugal,PRT,1997,83.36548
Portugal,PRT,1998,84.836
Portugal,PRT,1999,90.897446
Portugal,PRT,2000,86.92667
Portugal,PRT,2001,84.353134
Portugal,PRT,2002,90.251045
Portugal,PRT,2003,82.943
Portugal,PRT,2004,87.91043
Portugal,PRT,2005,91.884384
Portugal,PRT,2006,84.42744
Portugal,PRT,2007,83.85632
Portugal,PRT,2008,84.85321
Portugal,PRT,2009,81.150635
Portugal,PRT,2010,72.60679
Portugal,PRT,2011,75.488716
Portugal,PRT,2012,78.298454
Portugal,PRT,2013,70.894264
Portugal,PRT,2014,69.70747
Portugal,PRT,2015,75.54063
Portugal,PRT,2016,70.72357
Portugal,PRT,2017,78.5582
Portugal,PRT,2018,73.121956
Portugal,PRT,2019,73.62269
Portugal,PRT,2020,68.29146
Portugal,PRT,2021,66.67629
Portugal,PRT,2022,71.03251`;

function CSVToArray(strData, strDelimiter) {
    strDelimiter = (strDelimiter || ',');
    var objPattern = new RegExp(
        (
            '(\\' + strDelimiter + '|\\r?\\n|\\r|^)' +
            '(?:"([^"]*(?:""[^"]*)*)"|' +
            '([^"\\' + strDelimiter + '\\r\\n]*))'
        ),
        'gi',
    );
    var arrData = [[]];
    var arrMatches = null;
    while (arrMatches = objPattern.exec(strData)) {
        var strMatchedDelimiter = arrMatches[1];
        if (
            strMatchedDelimiter.length &&
            strMatchedDelimiter !== strDelimiter
        ) {
            arrData.push([]);
        }
        var strMatchedValue;
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

const parsedData = CSVToArray(data);
console.log(parsedData);

(async function () {
    new Chart(
        document.getElementById('charts'),
        {
            type: 'line',
            data: {
                labels: parsedData.map(row => row[2]),
                datasets: [
                    {
                        label: 'Portugal: share of primary energy consumption from fossil fuels',
                        data: parsedData.map(row => row[3]),
                    },
                ],
            },
            options: {
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
            },
        },
    );
})();
