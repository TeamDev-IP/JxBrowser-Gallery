/*
 *  Copyright (c) 2000-2023 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

import dependency.Java
import dependency.JxBrowser

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

kotlin {
    jvmToolchain {
        languageVersion = Java.version
        vendor = Java.vendor
    }
}
