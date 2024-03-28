/*
 *  Copyright (c) 2000-2023 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

kotlin {
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(libs.versions.java.get())
        vendor = JvmVendorSpec.matching(libs.versions.javaVendor.get())
    }
}

dependencies {
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.detekt.gradlePlugin)
    implementation(libs.jxbrowser.gradlePlugin)
    implementation(libs.compose.gradlePlugin)
    implementation(libs.taskTree.gradlePlugin)
}
