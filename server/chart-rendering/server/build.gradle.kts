/*
 *  Copyright 2024, TeamDev. All rights reserved.
 *
 *  Redistribution and use in source and/or binary forms, with or without
 *  modification, must retain the above copyright notice and the following
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

plugins {
    java
    id("io.micronaut.application") version "4.3.4"
    id("com.teamdev.jxbrowser") version "1.0.2"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

micronaut {
    version = "4.3.4"
    runtime("netty")
}

repositories {
    mavenCentral()
}

jxbrowser {
    version = "8.0.0-eap.1"
    includePreviewBuilds()
}

dependencies {
    // Use the JxBrowser cross-platform dependency.
    implementation(jxbrowser.crossPlatform)

    // Add dependencies needed at Micronaut runtime.
    runtimeOnly("io.micronaut.serde:micronaut-serde-jackson")
    runtimeOnly("org.yaml:snakeyaml")
}

application {
    mainClass.set("com.teamdev.jxbrowser.gallery.charts.Application")
}

tasks.withType<JavaCompile> {
    options.release.set(17)
}

tasks.withType<JavaExec> {
    // Assign all Java system properties from the command line to the JavaExec task.
    systemProperties(System.getProperties().mapKeys { it.key as String })
}

tasks.named("build") {
    // Ensure the client-side code is built first so that the chart-drawing
    // JS bundle is available.
    dependsOn(":server:chart-rendering:client:build")
}
