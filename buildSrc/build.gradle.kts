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
        // Keep synced with `dependency.Java` object.
        languageVersion = JavaLanguageVersion.of(17)
        vendor = JvmVendorSpec.AZUL
    }
}

dependencies {
    implementation(":jxbrowser-license")
    implementation(GradlePlugins.kotlin)
    implementation(GradlePlugins.detekt)
    implementation(GradlePlugins.jxbrowser)
    implementation(GradlePlugins.compose)
    implementation(GradlePlugins.taskTree)
    implementation(GradlePlugins.dokka)
}

/**
 * Contains Gradle plugins dependencies that should be kept in sync with
 * their corresponding dependency objects.
 *
 * Dependencies are usually declared in `buildSrc` sources. But sometimes,
 * they are needed on the classpath of `buildSrc` itself. In this case,
 * dependency constants are duplicated.
 *
 * When bumping or downgrading a dependency, make sure to do the same in its
 * original dependency objects.
 *
 * Also, when adding a new dependency here, specify its source dependency
 * object in the comment.
 */
@Suppress("ConstPropertyName")
object GradlePlugins {

    private const val kotlinVersion = "1.9.20" // `Kotlin.version`
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"

    private const val detektVersion = "1.23.3" // `Detekt.GradlePlugin.version`
    const val detekt = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$detektVersion"

    private const val jxbrowserVersion = "1.0.2" // `JxBrowser.GradlePlugin.version`
    const val jxbrowser = "com.teamdev.jxbrowser:JxBrowser-Gradle-Plugin:$jxbrowserVersion"

    private const val composeVersion = "1.6.1" // `Compose.GradlePlugin.version`
    const val compose = "org.jetbrains.compose:compose-gradle-plugin:$composeVersion"

    private const val taskTreeVersion = "2.1.1" // `TaskTree.version`.
    const val taskTree = "com.dorongold.plugins:task-tree:$taskTreeVersion"

    private const val dokkaVersion = "1.9.20" // `Dokka.version`.
    const val dokka = "org.jetbrains.dokka:dokka-gradle-plugin:$dokkaVersion"
}
