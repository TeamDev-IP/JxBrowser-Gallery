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

import com.google.gson.JsonParser;

/**
 * An enumeration of datasets available to the application.
 */
@SuppressWarnings("NonSerializableFieldInSerializableClass" /* OK for this enum. */)
enum Dataset {
    FOSSIL_FUELS_CONSUMPTION("fossil-fuels-consumption.info.json");

    /**
     * The resource containing the dataset info.
     */
    private final Resource info;

    /**
     * The resource containing the dataset data.
     */
    private final Resource data;

    /**
     * Initializes a new enum instance for the dataset denoted by the passed resource name.
     */
    Dataset(String resourceName) {
        this.info = new Resource(resourceName);
        this.data = new Resource(dataRef(info.contentAsString()));
    }

    /**
     * Returns the description of the dataset as a JSON string.
     */
    String infoAsString() {
        return info.contentAsString();
    }

    /**
     * Returns the content of the dataset as a string.
     */
    String dataAsString() {
        return data.contentAsString();
    }

    private static String dataRef(String json) {
        var jsonElement = JsonParser.parseString(json);
        var jsonObject = jsonElement.getAsJsonObject();
        var result = jsonObject.get("dataRef").getAsString();
        return result;
    }
}
