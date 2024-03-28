/*
 *  Copyright (c) 2000-2023 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

/**
 * Please note this module can't use dependencies from `buildSrc`.
 *
 * It is an included build. `buildSrc` itself depends on it. Thus, Kotlin
 * and Java versions are specified manually here, and should be synced
 * with ones from the dependency objects.
 *
 * This syncing inconvenience can be addressed by [#75](https://bit.ly/3GNZi3G).
 */
private val about = ""

plugins {
    kotlin("jvm") version "1.9.20"
}

repositories {
    mavenCentral()
}

kotlin {
    explicitApi()
    jvmToolchain {
        // Keep synced with `dependency.Java` object.
        languageVersion = JavaLanguageVersion.of(17)
        vendor = JvmVendorSpec.AZUL
    }
}
