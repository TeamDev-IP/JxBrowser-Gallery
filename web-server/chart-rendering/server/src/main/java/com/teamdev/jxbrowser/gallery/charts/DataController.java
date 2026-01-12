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

package com.teamdev.jxbrowser.gallery.charts;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

import static io.micronaut.http.MediaType.TEXT_CSV;
import static io.micronaut.http.MediaType.TEXT_JSON;

/**
 * A controller that provides access to the datasets used within the application.
 */
@Controller("/dataset")
final class DataController {

    /**
     * Returns the info about the "Per capita energy use" dataset.
     *
     * @return the dataset info in the JSON format
     */
    @Get("/per-capita-energy-use/info")
    @Produces(TEXT_JSON)
    String perCapitaEnergyUseInfo() {
        return Dataset.PER_CAPITA_ENERGY_USE.info();
    }

    /**
     * Returns the content of the "Per capita energy use" dataset.
     *
     * @return the dataset content in the CSV format
     */
    @Get("/per-capita-energy-use/data")
    @Produces(TEXT_CSV)
    String perCapitaEnergyUseData() {
        return Dataset.PER_CAPITA_ENERGY_USE.data();
    }

    /**
     * Returns the info about the "Energy consumption by source" dataset.
     *
     * @return the dataset info in the JSON format
     */
    @Get("/energy-consumption-by-source/info")
    @Produces(TEXT_JSON)
    String energyConsumptionBySourceInfo() {
        return Dataset.ENERGY_CONSUMPTION_BY_SOURCE.info();
    }

    /**
     * Returns the content of the "Energy consumption by source" dataset.
     *
     * @return the dataset content in the CSV format
     */
    @Get("/energy-consumption-by-source/data")
    @Produces(TEXT_CSV)
    String energyConsumptionBySourceData() {
        return Dataset.ENERGY_CONSUMPTION_BY_SOURCE.data();
    }
}
