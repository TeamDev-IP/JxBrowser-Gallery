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

import org.gradle.api.internal.catalog.ExternalModuleDependencyFactory.VersionNotationSupplier

plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

kotlin {

}

dependencies {
    implementation(generatedVersionCatalog())
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.kotlin.serialization.gradlePlugin)
    implementation(libs.detekt.gradlePlugin)
    implementation(libs.jxbrowser.gradlePlugin)
    implementation(libs.compose.gradlePlugin)
    implementation(libs.compose.compiler.gradlePlugin)
    implementation(libs.ktor.gradlePlugin)
    implementation(libs.micronaut.gradlePlugin)
    implementation(libs.taskTree.gradlePlugin)
}

/**
 * Provides generated type-safe version catalogs accessors
 * as dependency notation to be used on the classpath of `buildSrc`.
 *
 * By default, the generated accessors can't be used in script plugins. But it
 * is possible to put them there manually. Without this "hack", we would have
 * to use the default suggest approach based on raw strings.
 *
 * @see <a href="https://github.com/gradle/gradle/issues/15383">Gradle #15383</a>
 */
fun generatedVersionCatalog(): Any {
    val generatedClass = libs.javaClass
    val sources = generatedClass.superclass.protectionDomain.codeSource
    return files(sources.location)
}

/**
 * Allows to access the version immediately, omitting the intermediate call
 * to `asProvider()` method.
 *
 * The method is useful when the declared version has subversion(s). For example,
 * if there are both `versions.java` and `versions.java.vendor`, then it is
 * impossible to call `get()` directly on `java`.
 */
fun VersionNotationSupplier.get(): String = asProvider().get()
