/*
 *  Copyright (c) 2000-2023 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

import io.gitlab.arturbosch.detekt.Detekt

/**
 * This script-plugin sets up Kotlin code analyzing with Detekt.
 *
 * After applying, Detekt is configured to use `buildSrc/resources/detekt-config.yml`
 * config file.
 */
@Suppress("PrivatePropertyName", "unused")
private val ABOUT = ""

plugins {
    id("io.gitlab.arturbosch.detekt")
}

detekt {
    buildUponDefaultConfig = true
    config.from(files("$rootDir/buildSrc/src/main/resources/detekt-config.yml"))
}

tasks {
    withType<Detekt>().configureEach {
        reports {
            html.required.set(true) // Only HTML report is generated.
            xml.required.set(false)
            txt.required.set(false)
            sarif.required.set(false)
            md.required.set(false)
        }
    }
}
