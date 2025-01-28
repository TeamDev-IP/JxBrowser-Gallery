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

import com.teamdev.jxbrowser.examples.AppDetails;
import com.teamdev.jxbrowser.net.HttpHeader;
import com.teamdev.jxbrowser.net.HttpStatus;
import com.teamdev.jxbrowser.net.UrlRequestJob;
import com.teamdev.jxbrowser.net.callback.InterceptUrlRequestCallback;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * A request interceptor for loading web resources.
 *
 * <p>It intercepts requests with the {@code jxbrowser://} scheme and loads HTML/CSS/JS files
 * of the built web resources.
 */
public final class UrlRequestInterceptor implements InterceptUrlRequestCallback {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String INDEX_HTML = "index.html";
    private static final String CONTENT_ROOT = "web/";
    private static final int BUFFER_SIZE = 4096;

    @Override
    public Response on(Params params) {
        var url = params.urlRequest().url();
        if (url.contains(AppDetails.appUrl())) {
            var uri = URI.create(url);
            String filePath;
            if (uri.getPath().equals("/")) {
                filePath = CONTENT_ROOT + INDEX_HTML;
            } else {
                filePath = CONTENT_ROOT + uri.getPath();
            }
            var pathOnDisk = AppDetails.appLocationDir().resolve(filePath);
            var job = urlRequestJob(params, filePath);
            try {
                readFile(pathOnDisk, job);
                job.complete();
                return InterceptUrlRequestCallback.Response.intercept(job);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return InterceptUrlRequestCallback.Response.proceed();
    }

    private static UrlRequestJob urlRequestJob(InterceptUrlRequestCallback.Params params, String file) {
        var builder = UrlRequestJob.Options.newBuilder(HttpStatus.OK);
        builder.addHttpHeader(contentType(file));
        return params.newUrlRequestJob(builder.build());
    }

    private static void readFile(Path filePath, UrlRequestJob job) throws IOException {
        try (FileInputStream stream = new FileInputStream(filePath.toFile())) {
            var buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = stream.read(buffer)) > 0) {
                if (bytesRead != buffer.length) {
                    buffer = Arrays.copyOf(buffer, bytesRead);
                }
                job.write(buffer);
            }
        }
    }

    private static HttpHeader contentType(String file) {
        return HttpHeader.of(CONTENT_TYPE, MimeTypes.mimeType(file).value());
    }
}
