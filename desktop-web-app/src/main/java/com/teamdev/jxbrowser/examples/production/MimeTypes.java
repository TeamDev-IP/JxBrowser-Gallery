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

package com.teamdev.jxbrowser.examples.production;

import com.teamdev.jxbrowser.deps.com.google.common.collect.ImmutableMap;
import com.teamdev.jxbrowser.internal.Lazy;
import com.teamdev.jxbrowser.net.MimeType;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import static com.teamdev.jxbrowser.logging.Logger.warn;
import static java.lang.String.format;
import static java.util.Locale.ENGLISH;

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
        var fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return extToMime.get().getOrDefault(fileExtension.toLowerCase(ENGLISH), OCTET_STREAM);
    }

    private static Map<String, MimeType> createMap() {
        ImmutableMap.Builder<String, MimeType> builder = ImmutableMap.builder();
        var props = new Properties();
        var propsUrl = MimeTypes.class.getClassLoader().getResource("mime-types.properties");
        if (propsUrl != null) {
            try (var inputStream = propsUrl.openStream()) {
                props.load(inputStream);
                props.forEach((key, value) ->
                        builder.put(key.toString(), MimeType.of(value.toString())));
            } catch (IOException ignore) {
                warn(format("Couldn't read the list of MIME types from: %s", propsUrl));
            }
        }
        return builder.build();
    }
}
