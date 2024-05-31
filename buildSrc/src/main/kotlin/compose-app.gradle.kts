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

import gradle.get
import gradle.libs
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("jvm-module")
    id("jxbrowser")
    id("org.jetbrains.compose")
}

repositories {
    google()
}

dependencies {
    implementation(jxbrowser.compose)
    implementation(compose.desktop.currentOs)
    implementation(project(":jxbrowser-license"))
}

compose.desktop {
    application {
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = project.name
            packageVersion = jxBrowserVersion()
        }
    }
}

/**
 * Returns the version of JxBrowser.
 *
 * This method removes `-eap` prefix from the returned version, if any.
 * For example, if the currently used version is `8.0.0-eap.7-test`,
 * then the method would return just `8.0.0`.
 *
 * This is needed because Dmg version descriptor should match
 * the following format: `MAJOR[.MINOR][.PATCH]`.
 */
private fun Project.jxBrowserVersion(): String {
    val pattern = "(.*?)-eap".toRegex()
    val version = libs.versions.jxbrowser.get()
    val matchResult = pattern.find(version)
    return matchResult?.groups?.get(1)?.value ?: version
}
