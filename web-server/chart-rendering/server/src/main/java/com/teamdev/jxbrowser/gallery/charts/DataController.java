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
     * Returns the info about the "Fossil fuels consumption" dataset.
     *
     * @return the dataset info in the JSON format
     */
    @Get("/fossil-fuels-consumption/info")
    @Produces(TEXT_JSON)
    String fossilFuelsConsumptionInfo() {
        return Dataset.FOSSIL_FUELS_CONSUMPTION.infoAsString();
    }

    /**
     * Returns the content of the "Fossil fuels consumption" dataset.
     *
     * @return the dataset content in the CSV format
     */
    @Get("/fossil-fuels-consumption/data")
    @Produces(TEXT_CSV)
    String fossilFuelsConsumptionData() {
        return Dataset.FOSSIL_FUELS_CONSUMPTION.dataAsString();
    }

    /**
     * Returns the info about the "Life expectancy" dataset.
     *
     * @return the dataset info in the JSON format
     */
    @Get("/life-expectancy/info")
    @Produces(TEXT_JSON)
    String lifeExpectancyInfo() {
        return Dataset.LIFE_EXPECTANCY.infoAsString();
    }

    /**
     * Returns the content of the "Life expectancy" dataset.
     *
     * @return the dataset content in the CSV format
     */
    @Get("/life-expectancy/data")
    @Produces(TEXT_CSV)
    String lifeExpectancyData() {
        return Dataset.LIFE_EXPECTANCY.dataAsString();
    }
}
