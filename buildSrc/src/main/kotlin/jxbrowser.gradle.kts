/*
 *  Copyright (c) 2000-2023 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

import dependency.JxBrowser

plugins {
    id("jvm-module")
    id("com.teamdev.jxbrowser")
}

jxbrowser {
    version = JxBrowser.version
    includePreviewBuilds()
}

dependencies {
    implementation(jxbrowser.currentPlatform)
}
