import com.google.protobuf.gradle.id
import org.gradle.api.JavaVersion.VERSION_17
import java.net.ConnectException
import java.net.Socket

repositories {
    mavenCentral()
}

plugins {
    java
    jxbrowser
    id("com.google.protobuf") version "0.9.1"
    id("application")
}
group = "com.teamdev.jxbrowser.gallery"
version = "1.0"

val applicationName = "JxBrowserWebApp"
val mainJar = "$applicationName-$version.jar"

val wedAppLocationDir = "web-app/shadcn"

val host = "localhost"
val port = 5173
lateinit var devServerThread: Thread

jxbrowser {
    version = "8.0.0"
}

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
    implementation("com.linecorp.armeria:armeria-grpc:1.30.1")
    implementation("com.linecorp.armeria:armeria:1.30.1")
    implementation("com.linecorp.armeria:armeria-grpc-protocol:1.30.1")
    implementation("com.google.protobuf:protobuf-java:$protobufVersion")
}

val isWindows = System.getProperty("os.name").startsWith("Windows")
val npmCommand = if (isWindows) "npm.cmd" else "npm"
val npxCommand = if (isWindows) "npx.cmd" else "npx"

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
                commandLine(npmCommand, "run", "dev", "--", "--port=$port", "--strictPort")
            }
        }
        devServerThread.start()
        var connected = false
        var attempts = 5
        while (!connected && attempts > 0) {
            println("Waiting from the dev server response...")
            Thread.sleep(1000)
            connected = devServerThread.isAlive && isSocketConnected()
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

tasks.register<Exec>("installNpmPackages") {
    workingDir = file(wedAppLocationDir)
    commandLine(npmCommand, "install")
}

tasks.register<Exec>("generateJsProto") {
    workingDir = file(wedAppLocationDir)
    commandLine(npxCommand, "buf", "generate")
    dependsOn(tasks.named("installNpmPackages"))
}

tasks.named("generateProto") {
    dependsOn(tasks.named("generateJsProto"))
}

tasks.register<Exec>("buildWeb") {
    dependsOn(tasks.named("installNpmPackages"), tasks.named("generateJsProto"))
    workingDir = file(wedAppLocationDir)
    commandLine(npmCommand, "run", "build")
    doLast {
        delete("src/main/resources/web")
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
        "--win-shortcut-prompt"
    )
}
