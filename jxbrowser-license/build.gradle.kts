/*
 *  Copyright (c) 2000-2023 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

plugins {
    alias(libs.plugins.kotlinJvm)
}

repositories {
    mavenCentral()
}

kotlin {
    explicitApi()
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(libs.versions.java.get())
        vendor = JvmVendorSpec.matching(libs.versions.javaVendor.get())
    }
}
