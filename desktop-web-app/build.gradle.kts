import org.gradle.api.JavaVersion.VERSION_17
import java.net.ConnectException
import java.net.Socket

repositories {
    mavenCentral()
}

plugins {
    java
    id("com.google.protobuf") version "0.9.1"
    id("application")
    jxbrowser
}
group = "org.teamdev.jxbrowser"
version = "1.0"

val applicationName = "MyJxBrowserWebApp" // Set your own name here.
val mainJar = "$applicationName-$version.jar"

val wedAppLocationDir = "web-app/shadcn"

val host = "localhost"
val port = 5173
lateinit var devServerThread: Thread

jxbrowser {
    version = "7.41.2"
}

java {
    sourceCompatibility = VERSION_17
    targetCompatibility = VERSION_17
}

val protobufVersion = "3.21.12"
val generatedProtoRootDir =
    project.layout.buildDirectory.dir("generated/source/proto")

sourceSets {
    main {
        proto {
            srcDir("proto") // Set your custom .proto directory
        }
        java.srcDir(generatedProtoRootDir)
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$protobufVersion" // specify protoc version
    }
}

dependencies {
    implementation(jxbrowser.currentPlatform)
    implementation(jxbrowser.swing)
    implementation("com.google.protobuf:protobuf-java:$protobufVersion")
}

tasks.register("startDevServer") {
    fun isSocketConnected(): Boolean {
        try {
            println("Connecting to $host:$port")
            val socket = Socket(host, port)
            return socket.isConnected
        } catch (e: ConnectException) {
            return false
        }
    }
    doLast {
        if (isSocketConnected()) {
            println("The dev server is already running.")
            return@doLast
        }

        devServerThread = Thread {
            exec {
                workingDir = file(wedAppLocationDir)
                commandLine("npm", "run", "dev", "--", "--port=$port")
            }
        }
        devServerThread.start()
        var connected = false
        var attempts = 5 // 5 seconds
        while (!connected && attempts > 0) {
            println("Waiting from the dev server response...")
            Thread.sleep(1000)
            connected = true
            attempts--
        }
        if (!connected) {
            error("The dev server hasn't started.")
        }
    }
}

tasks.register("stopDevServer") {
    doLast {
        println("Stopping the dev server...")
        if (::devServerThread.isInitialized && devServerThread.isAlive) {
            devServerThread.interrupt()
        }
        println("The dev server stopped.")
    }
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named("startDevServer")).finalizedBy(tasks.named("stopDevServer"))
}

application {
    applicationDefaultJvmArgs = listOf("-Dapp.dev.mode=true")
    mainClass.set("com.teamdev.jxbrowser.App")
}

tasks.register<Exec>("generateJsProto") {
    workingDir = file(wedAppLocationDir)
    commandLine("npx", "buf", "generate")
}

tasks.named("generateProto") {
    dependsOn(tasks.named("generateJsProto"))
}

tasks.register<Exec>("installNpmPackages") {
    workingDir = file(wedAppLocationDir)
    commandLine("npm", "install")
}

tasks.register<Exec>("buildWeb") {
    dependsOn(tasks.named("installNpmPackages"), tasks.named("generateJsProto"))
    workingDir = file(wedAppLocationDir)
    commandLine("npm", "run", "build")
    doLast {
        copy {
            from("$wedAppLocationDir/dist")
            into("src/main/resources/web")
        }
    }
}

tasks.jar {
    archiveFileName.set(mainJar)
    dependsOn(tasks.named("buildWeb"))
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }

    from({
        configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
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
