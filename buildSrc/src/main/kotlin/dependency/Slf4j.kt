/*
 *  Copyright (c) 2000-2023 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

package dependency

/**
 * Simple Logging Facade for Java.
 *
 * We are not going to use it for logging, but to see logging emitted
 * by [JKeyMaster] library.
 *
 * @see <a href="https://www.slf4j.org/news.html">Releases</a>
 */
@Suppress("ConstPropertyName")
object Slf4j {
    private const val version = "2.0.5"
    const val simpleBackend = "org.slf4j:slf4j-simple:$version"
}
