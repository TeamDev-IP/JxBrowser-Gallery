/*
 *  Copyright (c) 2000-2023 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

import gradle.kotlin.dsl.accessors._bab2ea2dab2c8fd57c2317797b04ea1a.compose
import gradle.kotlin.dsl.accessors._bab2ea2dab2c8fd57c2317797b04ea1a.desktop
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("jvm-module")
    id("jxbrowser")
    id("org.jetbrains.compose")
}

repositories {
    google()
    jetBrainsDev()
}

dependencies {
    implementation(":jxbrowser-license")
    implementation(jxbrowser.compose)
    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        // TODO:2024-03-28:yevhenii.nadtochii: Can't use JxBrowser EAP version here.
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = project.name
            packageVersion = "8.0.0"
        }
    }
}

/**
 * Registers JetBrains repository that contains `-dev` artifacts.
 *
 * Such artifacts are not stable and thus published to a separate repository.
 * In general, their usage is discouraged.
 *
 * Sometimes, we need them to try a newer version of Compose until
 * it is officially released. For example, to check out a new feature,
 * or verify a delivered bug fix.
 */
fun RepositoryHandler.jetBrainsDev() = maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
