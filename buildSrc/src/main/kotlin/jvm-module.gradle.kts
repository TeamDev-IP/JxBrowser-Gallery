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
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("detekt-code-analysis")
    id("com.dorongold.task-tree")
}

version = JxBrowser.version
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
