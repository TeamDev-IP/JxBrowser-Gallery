/*
 *  Copyright (c) 2000-2024 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering

/**
 * Registers `buildArtifacts` task that assembles JAR files needed
 * for publishing to Maven.
 *
 * In addition to the compilation output, it also creates JARs for sources,
 * Dokka and Javadoc.
 *
 * Please note, this script plugin doesn't use `maven-publish` and
 * their publications intentionally. JxBrowser is published with TeamCity.
 */
@Suppress("PrivatePropertyName", "unused")
private val ABOUT = ""

plugins {
    id("jvm-module")
    id("org.jetbrains.dokka")
}

// Dokka tasks log a lot, but we can't silence them.
// See issue: https://github.com/Kotlin/dokka/issues/1894
tasks {
    val jar by existing(Jar::class) {
        withJxBrowserPrefix()
        manifest {
            attributes["Automatic-Module-Name"] = "jxbrowser.${project.name}"
            attributes["Implementation-Version"] = project.version
        }
    }
    val sourcesJar by registering(Jar::class) {
        withJxBrowserPrefix()
        from(sourceSets.main.get().allSource)
        archiveClassifier.set("sources")
    }
    val dokkaHtmlJar by registering(Jar::class) {
        withJxBrowserPrefix()
        dependsOn(tasks.dokkaHtml)
        from(tasks.dokkaHtml.flatMap { it.outputDirectory })
        archiveClassifier.set("dokka")
    }
    val dokkaJavadocJar by registering(Jar::class) {
        withJxBrowserPrefix()
        dependsOn(tasks.dokkaJavadoc)
        from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
        archiveClassifier.set("javadoc")
    }
    val buildArtifacts by registering {
        dependsOn(jar, sourcesJar, dokkaHtmlJar, dokkaJavadocJar)
    }
}

fun Jar.withJxBrowserPrefix() {
    archiveBaseName = "jxbrowser-${archiveBaseName.get()}"
}
