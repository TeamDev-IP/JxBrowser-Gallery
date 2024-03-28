/*
 *  Copyright (c) 2000-2023 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
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
    implementation(":jxbrowser-license")
    implementation(compose.desktop.currentOs)
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
