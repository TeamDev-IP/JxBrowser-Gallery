/*
 *  Copyright (c) 2025 TeamDev
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

package com.teamdev.jxbrowser.examples.production;

import com.teamdev.jxbrowser.deps.com.google.common.collect.ImmutableMap;
import com.teamdev.jxbrowser.internal.Lazy;
import com.teamdev.jxbrowser.net.MimeType;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import static com.teamdev.jxbrowser.logging.Logger.warn;
import static java.lang.String.format;

/**
 * A utility for working with MIME types.
 */
public final class MimeTypes {

    private static final MimeType OCTET_STREAM = MimeType.of("application/octet-stream");
    private static final Lazy<Map<String, MimeType>> extToMime = new Lazy<>(MimeTypes::createMap);

    /**
     * Prevents instantiation of this utility class.
     */
    private MimeTypes() {
    }

    /**
     * Returns a mime type based on the extension of {@code fileName}.
     */
    public static MimeType mimeType(String fileName) {
        var extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return extToMime.get().getOrDefault(extension.toLowerCase(Locale.ENGLISH), OCTET_STREAM);
    }

    private static Map<String, MimeType> createMap() {
        ImmutableMap.Builder<String, MimeType> mapBuilder = ImmutableMap.builder();
        var properties = new Properties();
        var propertiesUrl = MimeTypes.class.getClassLoader().getResource("ext-to-mimetype.properties");
        if (propertiesUrl != null) {
            try (var inputStream = propertiesUrl.openStream()) {
                properties.load(inputStream);
                properties.forEach((key, value) -> mapBuilder.put(key.toString(),
                        MimeType.of(value.toString())));
            } catch (IOException ignore) {
                warn(format("Couldn't read the list of mime-types from: %s", propertiesUrl));
            }
        }
        return mapBuilder.build();
    }
}