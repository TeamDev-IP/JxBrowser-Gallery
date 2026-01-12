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

package com.teamdev.jxbrowser.examples.pomodoro.window.animation.network

import com.teamdev.jxbrowser.dsl.net.HttpHeader
import com.teamdev.jxbrowser.dsl.net.UrlRequestJobOptions
import com.teamdev.jxbrowser.net.HttpStatus
import com.teamdev.jxbrowser.net.UrlRequestJob
import com.teamdev.jxbrowser.net.callback.InterceptUrlRequestCallback
import com.teamdev.jxbrowser.net.callback.InterceptUrlRequestCallback.Params
import com.teamdev.jxbrowser.net.callback.InterceptUrlRequestCallback.Response
import com.teamdev.jxbrowser.net.callback.InterceptUrlRequestCallback.Response.intercept
import com.teamdev.jxbrowser.net.callback.InterceptUrlRequestCallback.Response.proceed
import java.net.URI
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
        val contentType = HttpHeader("Content-Type", entry.mimeType)
        val options = UrlRequestJobOptions(HttpStatus.OK, listOf(contentType))
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
    return URI(javaSpec).toURL()
}

/**
 * A file inside a Jar archive.
 */
private class JarEntry(url: URL) {
    val mimeType = URLConnection.guessContentTypeFromName(url.file) ?: ""
    val data by lazy { url.readBytes() }
}
