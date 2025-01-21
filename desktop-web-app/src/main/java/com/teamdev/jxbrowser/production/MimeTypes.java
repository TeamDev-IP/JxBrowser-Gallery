package com.teamdev.jxbrowser.production;

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