# JxBrowser-Gallery

A gallery of apps built with JxBrowser, each solving a real-world use-case.

Beside being a browser component per se, JxBrowser has many applications,
some of which may not be obvious. In this repository, we demonstrate
several of them, and hope they will serve a source of inspiration
for our clients.

To widen the audience, we also use a variety of UI toolkits and languages
available on JVM.

### Compose Multiplatform for desktop

The following apps are written in Kotlin, showcasing the JxBrowser's component
for the Compose UI toolkit.

* [Pomodoro](compose/pomodoro/README.md) 
— a system tray helper for Pomodoro users, featuring WebGL 3D models
in a desktop app.

* [Screen Sharing](compose/screen-share/README.md)
— an app to share a screen via WebRTC.

### Server-side rendering

The applications in this section are centered around the idea 
of using HTML, CSS and JS to visualize business data. 
It is simpler than doing the same with the JVM-native libraries.

JxBrowser here is a server-side tool to render and export the results
to a media format of choice, such as images, or PDFs.

* [Chart rendering](web-server/chart-rendering/README.md) —
automates building the charts using popular JS libraries, with an export to PNG.

* [PDF export](web-server/pdf-export/README.md) — 
renders the rich table data with CSS and JS, and exports the result to PDF.

## Prerequisites

JDK 17+ is required to build the apps.

The applications are assembled using [Gradle][gradle]. The [Gradle Wrapper][gradle-wrapper]
is included in the project, so there is no need to install Gradle separately.

## JxBrowser license

To be able to run the applications, JxBrowser license is required.
The simplest way to start is to go for the [trial license][jxbrowser-trial].

The JxBrowser license key should be put to `jxbrowser-license/src/main/resources/jxbrowser-license`.<br>
The file is expected to contain a single line with the key value. For example:
```
6YNS4HJ1IQUF6HWDG9SP98GSWUXWYVB49C4494X6LRP70ORIFHXQJXT29JVQYUCZO71...
```

## Dependencies

This repository uses a [Gradle version catalog][gradle-version-catalog] 
to declare the properties of module's dependencies, including the source/target
version of Java.<br>
The file is located at `gradle/libs.versions.toml`.

## Redistribution and licensing

The source code of this repository is licensed under MIT.

[gradle]: https://gradle.org
[gradle-wrapper]: https://docs.gradle.org/current/userguide/gradle_wrapper.html
[gradle-version-catalog]: https://docs.gradle.org/current/userguide/platforms.html#sub:conventional-dependencies-toml
[jxbrowser-trial]: https://teamdev.com/jxbrowser/#evaluate
