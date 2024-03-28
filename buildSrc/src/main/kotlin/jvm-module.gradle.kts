/*
 *  Copyright (c) 2000-2023 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

import dependency.JUnit
import dependency.Java
import dependency.JxBrowser
import dependency.Kotest
import gradle.task.logSummaryReport
import gradle.versionFromCli
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("detekt-code-analysis")
    id("com.dorongold.task-tree")
}

/**
 * The version can be passed to the build as a CLI argument.
 *
 * We need this functionality because the version of the deployed
 * artifacts comes from CI/CD.
 */
version = versionFromCli() ?: JxBrowser.version
group = "com.teamdev.jxbrowser"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(Kotest.assertions)
    with(JUnit) {
        testImplementation(lib)
        testRuntimeOnly(launcher)
    }
}

kotlin {
    jvmToolchain {
        languageVersion = Java.version
        vendor = Java.vendor
    }
}

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform {
            includeEngines("junit-jupiter")
        }
        testLogging {
            showStandardStreams = true
            showExceptions = true
            exceptionFormat = TestExceptionFormat.FULL
            showStackTraces = true
            showCauses = true
        }
        logSummaryReport()
    }
}
