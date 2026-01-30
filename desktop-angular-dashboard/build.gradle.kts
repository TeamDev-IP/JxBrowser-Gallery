/*
 *  Copyright 2026, TeamDev
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

import org.gradle.api.JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

plugins {
    java
    application
    `desktop-web-app`
}

group = "com.teamdev.jxbrowser.gallery"
version = "1.0"

val applicationName = "JxBrowserAngularApp"
val mainJar = "$applicationName-$version.jar"

java {
    sourceCompatibility = VERSION_17
    targetCompatibility = VERSION_17
}

dependencies {
    implementation(jxbrowser.currentPlatform)
    implementation(jxbrowser.swing)
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.formdev:flatlaf:3.4")
}

// Configure resources to include Angular build output in production mode
sourceSets {
    main {
        resources {
            // Angular build output will be placed in web-app/dist/web
            // and mapped to /web in the JAR for UrlRequestInterceptor
            srcDir("web-app/dist")
        }
    }
}

val webAppDir = "${projectDir}/web-app"
val isWindows = System.getProperty("os.name").startsWith("Windows")
val npmCommand = if (isWindows) "npm.cmd" else "npm"

fun runCommandInWebDirectory(errorMessage: String, vararg command: String) {
    val process = ProcessBuilder(*command)
        .directory(File(webAppDir))
        .redirectErrorStream(true)
        .start()

    process.inputStream.bufferedReader().use { reader ->
        reader.lines().forEach { println(it) }
    }

    val exitCode = process.waitFor()
    if (exitCode != 0) {
        throw IllegalStateException("$errorMessage. Exit code: $exitCode")
    }
}

tasks.register("installNpmPackages") {
    doLast {
        runCommandInWebDirectory("Failed to install NPM packages", npmCommand, "install")
    }
}

tasks.register("buildWeb") {
    dependsOn(tasks.named("installNpmPackages"))
    doLast {
        runCommandInWebDirectory("Failed to build web resources", npmCommand, "run", "build")

        // After build, rename the output folder to 'web' for UrlRequestInterceptor
        val browserDir = file("$webAppDir/dist/angular-jxbrowser-dashboard/browser")
        val webDir = file("$webAppDir/dist/web")

        if (browserDir.exists() && !webDir.exists()) {
            browserDir.renameTo(webDir)
            // Clean up the intermediate directory
            file("$webAppDir/dist/angular-jxbrowser-dashboard").deleteRecursively()
        }
    }
}

tasks.register("startDevServer") {
    dependsOn(tasks.named("installNpmPackages"))
    doLast {
        runCommandInWebDirectory("Failed to start dev server", npmCommand, "run", "start")
    }
}

application {
    applicationDefaultJvmArgs = listOf("-Dapp.dev.mode=true")
    mainClass.set("com.teamdev.jxbrowser.angular.App")
}

tasks.withType<JavaExec> {
    // Pass all system properties from command line
    systemProperties(System.getProperties().mapKeys { it.key as String })
}

// Configure JAR task to create an executable fat JAR
tasks.jar {
    archiveFileName.set(mainJar)
    dependsOn(tasks.named("buildWeb"))

    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    // Include all runtime dependencies in the JAR (fat JAR)
    from({
        configurations.runtimeClasspath.get().map {
            if (it.isDirectory) it else zipTree(it)
        }
    })

    doLast {
        // Copy the JAR to build/dist for jpackage
        copy {
            from("build/libs/$mainJar")
            into("build/dist")
        }
    }
}

// Task to create macOS DMG installer
tasks.register<Exec>("packageDmg") {
    dependsOn(tasks.build)

    commandLine(
        "jpackage",
        "--input", "./build/dist",
        "--main-jar", mainJar,
        "--name", applicationName,
        "--app-version", version,
        "--type", "dmg",
        "--main-class", application.mainClass.get(),
        "--dest", "./build/installer"
    )
}

// Task to create Windows EXE installer
tasks.register<Exec>("packageExe") {
    dependsOn(tasks.build)

    commandLine(
        "jpackage",
        "--input", "./build/dist",
        "--main-jar", mainJar,
        "--name", applicationName,
        "--app-version", version,
        "--type", "exe",
        "--main-class", application.mainClass.get(),
        "--dest", "./build/installer",
        "--win-dir-chooser",
        "--win-menu",
        "--win-shortcut-prompt"
    )
}
