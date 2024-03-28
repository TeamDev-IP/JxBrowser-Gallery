/*
 *  Copyright (c) 2000-2023 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("detekt-code-analysis")
    id("com.dorongold.task-tree")
}

version = "${libs.findVersion("jxbrowser").get()}"
group = "com.teamdev.jxbrowser"

repositories {
    mavenCentral()
}

kotlin {
    val javaVersion = "${libs.findVersion("java").get()}"
    val javaVendor = "${libs.findVersion("javaVendor").get()}"
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(javaVersion)
        vendor = JvmVendorSpec.matching(javaVendor)
    }
}

private val Project.libs: VersionCatalog
    get() = versionCatalogs.named("libs")
