/*
 *  Copyright 2024, TeamDev. All rights reserved.
 *  
 *  Redistribution and use in source and/or binary forms, with or without
 *  disclaimer.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 *  A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 *  OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 *  OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import gradle.libs

plugins {
    id("ktor-server")
    kotlin("plugin.serialization")
}

application {
    mainClass.set("com.teamdev.jxbrowser.gallery.pdf.MainKt")
}

dependencies {
    implementation(libs.ktor.cors)
    implementation(libs.kotlin.serialization)
}

val dependentTasks = listOf("processResources")

dependentTasks.forEach { taskName ->
    tasks.named(taskName) {
        // Ensure the client-side code is built first so that the table-drawing
        // JS bundle is available.
        dependsOn(":web-server:pdf-export:client:build")
    }
}
