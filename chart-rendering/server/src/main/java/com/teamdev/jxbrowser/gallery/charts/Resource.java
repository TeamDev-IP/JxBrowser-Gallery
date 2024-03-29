package com.teamdev.jxbrowser.gallery.charts;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A utility type for reading resources from the classpath.
 */
final class Resource {

    private final URL url;

    /**
     * Creates a new instance of this class.
     *
     * @param resourceName the name of the resource to read
     */
    Resource(String resourceName) {
        this.url = Resource.class.getClassLoader()
                                 .getResource(resourceName);
    }

    /**
     * Returns the URL of the resource file.
     *
     * @return the resource URL
     */
    URL url() {
        return url;
    }

    /**
     * Reads the content of the resource file as a string.
     *
     * @return the resource content
     */
    String contentAsString() {
        try {
            var uri = url.toURI();
            var path = Path.of(uri);
            var result = Files.readString(path);
            return result;
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException("Unable to preload a resource.", e);
        }
    }
}
