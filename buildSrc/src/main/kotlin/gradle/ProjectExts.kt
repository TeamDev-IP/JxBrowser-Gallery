/*
 *  Copyright (c) 2024 TeamDev
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

package gradle

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.internal.catalog.ExternalModuleDependencyFactory.VersionNotationSupplier
import org.gradle.kotlin.dsl.the

/**
 * Returns generated type-safe version catalogs accessors for the conventional
 * `libs` catalog.
 */
val Project.libs
    get() = the<LibrariesForLibs>()

/**
 * Allows to access the version immediately, omitting the intermediate call
 * to `asProvider()` method.
 *
 * The method is useful when the declared version has subversion(s). For example,
 * if there are both `versions.java` and `versions.java.vendor`, then it is
 * impossible to call `get()` directly on `java`.
 */
fun VersionNotationSupplier.get(): String = asProvider().get()

/**
 * Returns the version of JxBrowser for packaging the Compose builds.
 *
 * This method removes `-eap...` suffix from the returned version, if any.
 * For example, if the currently used version is `8.0.0-eap.7`,
 * then the method would return just `8.0.0`.
 *
 * This is needed because `.dmg` version descriptor must be
 * in the following format: `MAJOR[.MINOR][.PATCH]`.
 */
fun Project.jxBrowserPackagingVersion(): String {
    val pattern = "(.*?)-eap".toRegex()
    val version = libs.versions.jxbrowser.get()
    val matchResult = pattern.find(version)
    return matchResult?.groups?.get(1)?.value ?: version
}
