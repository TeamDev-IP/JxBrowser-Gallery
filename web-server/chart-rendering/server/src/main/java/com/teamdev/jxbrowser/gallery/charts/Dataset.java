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

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * An enumeration of datasets available to the application.
 */
@SuppressWarnings("NonSerializableFieldInSerializableClass" /* OK for this enum. */)
enum Dataset {
    ENERGY_CONSUMPTION_BY_SOURCE("energy-consumption-by-source.info.json"),
    FOSSIL_FUELS_CONSUMPTION("fossil-fuels-consumption.info.json"),
    PER_CAPITA_ENERGY_USE("per-capita-energy-use.info.json");

    /**
     * The parsed dataset info.
     */
    private final JsonObject info;

    /**
     * The resource containing the dataset data.
     */
    private final Resource data;

    /**
     * Initializes a new enum instance for the dataset denoted by the resource
     * with the passed name.
     */
    Dataset(String resourceName) {
        var infoResource = new Resource(resourceName);
        var infoContent = infoResource.contentAsString();
        this.info = JsonParser.parseString(infoContent)
                              .getAsJsonObject();
        var dataRef = info.get("dataRef")
                          .getAsString();
        this.data = new Resource(dataRef);
    }

    /**
     * Returns the ID of the dataset.
     */
    String id() {
        return info.get("id").getAsString();
    }

    /**
     * Returns the description of the dataset as a JSON string.
     */
    String info() {
        return info.toString();
    }

    /**
     * Returns the content of the dataset in a form of a string.
     */
    String data() {
        return data.contentAsString();
    }
}
