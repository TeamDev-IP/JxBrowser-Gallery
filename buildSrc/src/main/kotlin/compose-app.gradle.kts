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
 * Returns version of JxBrowser.
 *
 * This method removes `-eap` prefix from the returned version, if any.
 * For example, if this string is `8.0.0-eap.7-test`, then the method
 * would return just `8.0.0`.
 *
 * This is needed because `Dmg` version descriptor should match
 * the following format: `MAJOR[.MINOR][.PATCH]`.
 */
private fun Project.jxBrowserVersion(): String {
    val pattern = "(.*?)-eap".toRegex()
    val version = libs.findVersion("jxbrowser").get().toString()
    val matchResult = pattern.find(version)
    return matchResult?.groups?.get(1)?.value ?: version
}

private val Project.libs: VersionCatalog
    get() = versionCatalogs.named("libs")
