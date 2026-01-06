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

import com.teamdev.jxbrowser.examples.AppDetails;
import com.teamdev.jxbrowser.net.HttpHeader;
import com.teamdev.jxbrowser.net.HttpStatus;
import com.teamdev.jxbrowser.net.UrlRequestJob;
import com.teamdev.jxbrowser.net.callback.InterceptUrlRequestCallback;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

/**
 * A request interceptor for loading web resources.
 *
 * <p>It intercepts requests with the {@code jxbrowser://} scheme and loads HTML/CSS/JS files
 * of the built web resources.
 */
public final class UrlRequestInterceptor implements InterceptUrlRequestCallback {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String INDEX_HTML = "/index.html";
    private static final String CONTENT_ROOT = "/web";

    @Override
    public Response on(Params params) {
        var url = params.urlRequest().url();
        if (url.contains(AppDetails.appUrl())) {
            var uri = URI.create(url);
            String fileName;
            if (uri.getPath().equals("/")) {
                fileName = INDEX_HTML;
            } else {
                fileName = uri.getPath();
            }
            var job = urlRequestJob(params, fileName);
            readFile(fileName, job);
            return InterceptUrlRequestCallback.Response.intercept(job);
        }
        return InterceptUrlRequestCallback.Response.proceed();
    }

    private static UrlRequestJob urlRequestJob(InterceptUrlRequestCallback.Params params, String file) {
        var builder = UrlRequestJob.Options.newBuilder(HttpStatus.OK);
        builder.addHttpHeader(contentType(file));
        return params.newUrlRequestJob(builder.build());
    }

    private static void readFile(String fileName, UrlRequestJob job) {
        try (var stream = UrlRequestInterceptor.class.getResourceAsStream(CONTENT_ROOT + fileName)) {
            if (stream == null) {
                throw new FileNotFoundException(CONTENT_ROOT + fileName);
            }
            job.write(stream.readAllBytes());
            job.complete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static HttpHeader contentType(String file) {
        return HttpHeader.of(CONTENT_TYPE, MimeTypes.mimeType(file).value());
    }
}
