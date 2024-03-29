package com.teamdev.jxbrowser.gallery.charts;

@SuppressWarnings("NonSerializableFieldInSerializableClass" /* OK for this enum. */)
enum Dataset {
    FOSSIL_FUELS_CONSUMPTION("fossil-fuels-consumption.csv");

    private final Resource resource;

    Dataset(String resourceName) {
        this.resource = new Resource(resourceName);
    }

    String contentAsString() {
        return resource.contentAsString();
    }
}
