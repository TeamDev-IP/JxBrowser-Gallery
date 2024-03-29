package com.teamdev.jxbrowser.gallery.charts;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

/**
 * A utility class for reading resources from the classpath.
 */
final class Resource {

    private final String resourceName;
    private final ClassLoader classLoader;

    /**
     * Creates a new instance of this class.
     *
     * @param resourceName the name of the resource to read
     */
    Resource(String resourceName) {
        this.resourceName = resourceName;
        this.classLoader = Resource.class.getClassLoader();
    }

    /**
     * Reads the content of the resource file as a string.
     *
     * @return the resource content
     */
    String readAsString() {
        try {
            var dataFilePath = classLoader.getResource(resourceName);
            var uri = requireNonNull(dataFilePath).toURI();
            var path = Path.of(uri);
            var result = Files.readString(path);
            return result;
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException("Unable to preload a resource.", e);
        }
    }
}
