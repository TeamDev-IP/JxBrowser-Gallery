/*
 *  Copyright 2026, TeamDev
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

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * An enumeration of datasets available to the application.
 */
@SuppressWarnings("NonSerializableFieldInSerializableClass" /* OK for this enum. */)
enum Dataset {

    PER_CAPITA_ENERGY_USE("per-capita-energy-use.info.json"),
    ENERGY_CONSUMPTION_BY_SOURCE("energy-consumption-by-source.info.json");

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
     * Returns the info about the dataset as a JSON string.
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
