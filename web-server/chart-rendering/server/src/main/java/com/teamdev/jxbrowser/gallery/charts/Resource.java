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

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A utility type for reading resources from the classpath.
 */
final class Resource {

    /**
     * The URL of the resource file this instance handles.
     */
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
     * Reads the content of the resource file as a single string.
     *
     * @return the resource content
     */
    String contentAsString() {
        try {
            var uri = url.toURI();
            var path = Path.of(uri);
            var content = Files.readString(path);
            var trimmed = content.trim();
            return trimmed;
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException("Unable to preload a resource.", e);
        }
    }
}
