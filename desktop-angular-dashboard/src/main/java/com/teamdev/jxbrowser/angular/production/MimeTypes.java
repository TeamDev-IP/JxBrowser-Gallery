/*
 *  Copyright 2025, TeamDev. All rights reserved.
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

package com.teamdev.jxbrowser.angular.production;

import com.teamdev.jxbrowser.net.MimeType;

import java.io.IOException;
import java.util.HashMap;
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
    private static final Map<String, MimeType> MIME_TYPES = createMap();

    /**
     * Prevents instantiation of this utility class.
     */
    private MimeTypes() {
    }

    /**
     * Returns the MIME type for the given file name.
     */
    public static MimeType mimeType(String fileName) {
        var extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return MIME_TYPES.getOrDefault(extension.toLowerCase(ENGLISH), OCTET_STREAM);
    }

    private static Map<String, MimeType> createMap() {
        Map<String, MimeType> map = new HashMap<>();
        var propsUrl = MimeTypes.class.getClassLoader().getResource("mime-types.properties");
        if (propsUrl != null) {
            var props = new Properties();
            try (var inputStream = propsUrl.openStream()) {
                props.load(inputStream);
                props.forEach((key, value) ->
                        map.put(key.toString(), MimeType.of(value.toString())));
            } catch (IOException ignore) {
                warn(format("Couldn't read MIME types from: %s", propsUrl));
            }
        }
        return map;
    }
}
