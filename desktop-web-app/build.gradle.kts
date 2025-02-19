/*
 *  Copyright (c) 2025 TeamDev
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

import com.google.protobuf.gradle.id
import org.gradle.api.JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

plugins {
    java
    id("com.google.protobuf") version "0.9.1"
    id("application")
    `desktop-web-app`
}
group = "com.teamdev.jxbrowser.gallery"
version = "1.0"

val applicationName = "JxBrowserWebApp"
val mainJar = "$applicationName-$version.jar"

val wedAppLocationDir = "${projectDir}/web-app/"

val host = "localhost"
val port = 5173
lateinit var devServerThread: Thread

java {
    sourceCompatibility = VERSION_17
    targetCompatibility = VERSION_17
}

val protobufVersion = "3.21.12"
var grpcVersion = "1.67.1"
val generatedProtoRootDir =
    project.layout.buildDirectory.dir("generated/source/proto")

sourceSets {
    main {
        proto {
            srcDir("proto")
        }
        java.srcDir(generatedProtoRootDir)
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$protobufVersion"
        plugins {
            id("grpc") { artifact = "io.grpc:protoc-gen-grpc-java:${grpcVersion}" }
        }
        generateProtoTasks {
            all().forEach { task ->
                task.plugins {
                    id("grpc") {}
                }
            }
        }
    }
}

dependencies {
    implementation(jxbrowser.currentPlatform)
    implementation(jxbrowser.swing)

    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.linecorp.armeria:armeria-grpc:1.30.1")
    implementation("com.linecorp.armeria:armeria:1.30.1")
    implementation("com.linecorp.armeria:armeria-grpc-protocol:1.30.1")
    implementation("com.google.protobuf:protobuf-java:$protobufVersion")
}

val isWindows = System.getProperty("os.name").startsWith("Windows")
val npmCommand = if (isWindows) "npm.cmd" else "npm"
val npxCommand = if (isWindows) "npx.cmd" else "npx"

fun runCommandInWebDirectory(errorMessage: String, vararg command: String) {
    val process = ProcessBuilder(*command)
        .directory(File(wedAppLocationDir))
        .start()

    process.inputStream.bufferedReader().use { reader ->
        reader.lines().forEach { println(it) }
    }

    process.errorStream.bufferedReader().use { reader ->
        reader.lines().forEach { System.err.println(it) }
    }

    val exitCode = process.waitFor()
    if (exitCode != 0) {
        throw IllegalStateException("$errorMessage. Exit code: $exitCode")
    }
}

tasks.register("startDevServer") {
    dependsOn(tasks.named("generateJsProto"))
    doLast {
        runCommandInWebDirectory(
            "Failed to start dev server",
            npmCommand, "run", "dev", "--", "--port=$port", "--strictPort"
        )
    }
}

application {
    applicationDefaultJvmArgs = listOf("-Dapp.dev.mode=true")
    mainClass.set("com.teamdev.jxbrowser.examples.App")
}

tasks.register("installNpmPackages") {
    doLast {
        runCommandInWebDirectory("Failed to install NPM packages", npmCommand, "install")
    }
}

tasks.register("generateJsProto") {
    dependsOn(tasks.named("installNpmPackages"))
    doLast {
        runCommandInWebDirectory(
            "Failed to generate JavaScript proto",
            npxCommand, "buf", "generate"
        )
    }
}

tasks.named("generateProto") {
    dependsOn(tasks.named("generateJsProto"))
}

tasks.register("buildWeb") {
    dependsOn(tasks.named("installNpmPackages"), tasks.named("generateJsProto"))
    doLast {
        runCommandInWebDirectory(
            "Failed to build web resources",
            npmCommand, "run", "build"
        )
        val webResources = "src/main/resources/web"
        delete(webResources)
        copy {
            from("$wedAppLocationDir/dist")
            into(webResources)
        }
    }
}

tasks.jar {
    archiveFileName.set(mainJar)
    dependsOn(tasks.named("buildWeb"))
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from({
        configurations.runtimeClasspath.get().map {
            if (it.isDirectory) it else zipTree(it)
        }
    })

    doLast {
        copy {
            from("$wedAppLocationDir/dist/")
            into("build/dist/web")
        }
        copy {
            from("build/libs/$mainJar")
            into("build/dist")
        }
    }
}

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
        "--dest", "./build/installer",
        "--icon", "src/main/resources/app.icns",
    )
}

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
        "--win-shortcut-prompt",
        "--icon", "src/main/resources/app.ico",
    )
}
