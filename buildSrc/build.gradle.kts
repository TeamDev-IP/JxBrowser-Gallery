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

import org.gradle.api.internal.catalog.ExternalModuleDependencyFactory.VersionNotationSupplier

plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

kotlin {
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(libs.versions.java.get())
        vendor = JvmVendorSpec.matching(libs.versions.java.vendor.get())
    }
}

dependencies {
    implementation(generatedVersionCatalog())
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.detekt.gradlePlugin)
    implementation(libs.jxbrowser.gradlePlugin)
    implementation(libs.compose.gradlePlugin)
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
