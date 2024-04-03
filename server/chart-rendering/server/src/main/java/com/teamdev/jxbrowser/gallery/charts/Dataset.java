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

/**
 * An enumeration of datasets available to the application.
 */
@SuppressWarnings("NonSerializableFieldInSerializableClass" /* OK for this enum. */)
enum Dataset {
    FOSSIL_FUELS_CONSUMPTION(
            "fossil-fuels-consumption.desc.json",
            "fossil-fuels-consumption.csv"
    );

    /**
     * The resource containing the dataset.
     */
    private final Resource description;
    private final Resource data;

    /**
     * Initializes a new enum instance for the resource with the passed name.
     */
    Dataset(String descriptionResource, String dataResource) {
        this.description = new Resource(descriptionResource);
        this.data = new Resource(dataResource);
    }

    /**
     * Returns the description of the dataset as a string.
     */
    String descriptionAsString() {
        return description.contentAsString();
    }

    /**
     * Returns the content of the dataset as a string.
     */
    String contentAsString() {
        return data.contentAsString();
    }
}
