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

package com.teamdev.jxbrowser.examples.pomodoro.window.animation.network

import com.teamdev.jxbrowser.net.HttpHeader
import com.teamdev.jxbrowser.net.HttpStatus
import com.teamdev.jxbrowser.net.UrlRequestJob
import com.teamdev.jxbrowser.net.callback.InterceptUrlRequestCallback
import com.teamdev.jxbrowser.net.callback.InterceptUrlRequestCallback.Params
import com.teamdev.jxbrowser.net.callback.InterceptUrlRequestCallback.Response
import com.teamdev.jxbrowser.net.callback.InterceptUrlRequestCallback.Response.intercept
import com.teamdev.jxbrowser.net.callback.InterceptUrlRequestCallback.Response.proceed
import java.net.URL
import java.net.URLConnection

/**
 * URL passed to [InterceptUrlRequestCallback].
 */
private typealias RequestedUrl = String

/**
 * Overrides response data for requests that ask for a file from Jar archives.
 *
 * This interceptor is needed when Chromium requests files from Jar archives.
 * Such requests take place when an initially loaded page itself is located
 * in the application resources (and thus, in a Jar archive).
 *
 * It is possible to load a page from resources as the following:
 *
 * ```
 * val index: URL = this::class.java.getResource("/index.html")!!
 * browser.navigation().loadUrl(index.toExternalForm())
 * ```
 *
 * This page may initiate loading of other resources: style sheets, images,
 * scripts, etc. And they all are located in a Jar just like `index.html`.
 * Their paths are always relative. Chromium can't load them on its own.
 *
 * This class intercepts such requests, loads the files on its own
 * and overrides response data.
 */
class InterceptJarRequestCallback : InterceptUrlRequestCallback {

    override fun on(params: Params): Response {
        val requestedUrl: RequestedUrl = params.urlRequest().url()
        if (requestedUrl.isNotJarEntry()) {
            return proceed()
        }
        val javaUrl = requestedUrl.toJavaUrl()
        val jarEntry = JarEntry(javaUrl)
        val job = overrideResponseData(jarEntry, params)
        return intercept(job)
    }

    private fun overrideResponseData(entry: JarEntry, params: Params): UrlRequestJob {
        val header = HttpHeader.of("Content-Type", entry.mimeType)
        val options = UrlRequestJob.Options
            .newBuilder(HttpStatus.OK)
            .addHttpHeader(header)
            .build()
        val job = params.newUrlRequestJob(options).apply {
            write(entry.data)
            complete()
        }
        return job
    }
}

/**
 * Says if this [RequestedUrl] doesn't point to a file inside a Jar archive.
 */
private fun RequestedUrl.isNotJarEntry() = startsWith("jar:").not()

/**
 * Converts this [RequestedUrl] from Chromium notation
 * to [Java's one][java.net.JarURLConnection].
 */
private fun RequestedUrl.toJavaUrl(): URL {
    val javaSpec = replace("jar://file", "jar:file:")
    return URL(javaSpec)
}

/**
 * A file inside a Jar archive.
 */
private class JarEntry(url: URL) {
    val mimeType = URLConnection.guessContentTypeFromName(url.file) ?: ""
    val data by lazy { url.readBytes() }
}
