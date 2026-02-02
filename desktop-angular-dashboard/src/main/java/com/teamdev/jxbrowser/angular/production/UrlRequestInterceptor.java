/*
 * Copyright 2026, TeamDev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.teamdev.jxbrowser.angular.production;

import com.teamdev.jxbrowser.angular.AppDetails;
import com.teamdev.jxbrowser.net.HttpHeader;
import com.teamdev.jxbrowser.net.HttpStatus;
import com.teamdev.jxbrowser.net.UrlRequestJob;
import com.teamdev.jxbrowser.net.callback.InterceptUrlRequestCallback;

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
        var expectedUrlPrefix = AppDetails.appScheme() + "://" + AppDetails.appHost();

        if (url.startsWith(expectedUrlPrefix)) {
            var uri = URI.create(url);
            var path = uri.getPath();
            var fileName = path.equals("/") ? INDEX_HTML : path;
            return loadResource(params, fileName);
        }

        return Response.proceed();
    }

    private Response loadResource(Params params, String fileName) {
        var resourcePath = CONTENT_ROOT + fileName;

        try (var stream = getClass().getResourceAsStream(resourcePath)) {
            if (stream == null) {
                var job = createUrlRequestJob(params, HttpStatus.NOT_FOUND, fileName);
                job.complete();
                return Response.intercept(job);
            }

            var job = createUrlRequestJob(params, HttpStatus.OK, fileName);
            job.write(stream.readAllBytes());
            job.complete();
            return Response.intercept(job);
        } catch (IOException e) {
            var job = createUrlRequestJob(params, HttpStatus.INTERNAL_SERVER_ERROR, fileName);
            job.complete();
            return Response.intercept(job);
        }
    }

    private UrlRequestJob createUrlRequestJob(Params params, HttpStatus status, String fileName) {
        var builder = UrlRequestJob.Options.newBuilder(status);
        builder.addHttpHeader(HttpHeader.of(CONTENT_TYPE, MimeTypes.mimeType(fileName).value()));
        return params.newUrlRequestJob(builder.build());
    }
}
