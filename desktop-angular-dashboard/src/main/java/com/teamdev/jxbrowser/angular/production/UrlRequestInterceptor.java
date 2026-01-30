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
