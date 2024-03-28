/*
 *  Copyright (c) 2000-2023 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

plugins {
    id("jvm-module")
    id("com.teamdev.jxbrowser")
}

jxbrowser {
    version = "${libs.findVersion("jxbrowser").get()}"
    includePreviewBuilds()
}

dependencies {
    implementation(jxbrowser.currentPlatform)
}

private val Project.libs: VersionCatalog
    get() = versionCatalogs.named("libs")
