# JxBrowser-Gallery

A gallery of apps built with JxBrowser, each solving a real-world use-case.

## Prerequisites for building

JDK 17 is required to build the apps. Gradle JVM toolchain is configured to use `Zulu JDK`. 
Both Java version and vendor are declared in `gradle/libs.versions.toml`.

## JxBrowser license

To be able to run an application, JxBrowser license should be provided.
It is possible to begin with a [trial](https://teamdev.com/jxbrowser/#evaluate).

Once the key is ready, it should be put to `jxbrowser-license/src/main/resources/jxbrowser-license`.
The file is expected to contain a single line with a key.
