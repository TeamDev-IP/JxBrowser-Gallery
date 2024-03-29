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
