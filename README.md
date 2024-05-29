# JxBrowser-Gallery

A gallery of apps built with JxBrowser, each solving a real-world use-case.

All applications are grouped according to a toolkit they use:

- [Compose Desktop](compose/README.md)
- [Web Server](web-server/README.md)

## Prerequisites for building

JDK 17+ is required to build the apps. The Java version is declared in `gradle/libs.versions.toml`.

The project is assembled using the [Gradle][gradle] build tool. The [Gradle Wrapper][gradle-wrapper]
is included in the project, so there is no need to install Gradle separately.

## JxBrowser license

To be able to run an application, JxBrowser license should be provided.
It is possible to begin with a [trial][jxbrowser-trial].

Once the key is ready, it should be put to `jxbrowser-license/src/main/resources/jxbrowser-license`.
The file is expected to contain a single line with a key.

[gradle]: https://gradle.org
[gradle-wrapper]: https://docs.gradle.org/current/userguide/gradle_wrapper.html
[jxbrowser-trial]: https://teamdev.com/jxbrowser/#evaluate
