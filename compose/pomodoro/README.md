# Pomodoro tracker with WebGL animation

This project is a system-tray application that helps to follow
[Pomodoro][pomodoro-wiki] time management technique. The technologies used 
include[WebGL][webgl-mdn] and [JxBrowser][jxbrowser].

![screenshot.png](.github/readme-resources/screenshot.png)

[pomodoro-wiki]: https://en.wikipedia.org/wiki/Pomodoro_Technique
[webgl-mdn]: https://developer.mozilla.org/en-US/docs/Web/API/WebGL_API
[jxbrowser]: https://www.teamdev.com/jxbrowser

## Summary

### Problem

In modern desktop applications, it is often needed to render some graphic models
or display an animated 3D visuals related to the business domain. However, 
integrating sophisticated animation frameworks or libraries is almost always
a challenge, especially, if we speak of modifying an existing, well-established app.
Moreover, ensuring cross-platform compatibility adds another layer of complexity,
as different operating systems usually have their own specifics for supporting 
3D rendering.

### Solution

WebGL is a browser technology, allowing for displaying 2D and 3D objects, 
optionally leveraging the power of GPU hardware acceleration. JxBrowser, being 
built on top of Chromium, supports WebGL as well, and enables JVM apps to render 
still and animated graphic objects. Also, as WebGL is a well-established technology 
in the web world, there are many high-quality JS libraries, that significantly
shorten the development time.

In this project, we demonstrate rendering of 3D models using JxBrowser API,
and, just for fun, visualize Pomodoro timers: each timer is accompanied by
the corresponding rotating 3D model.

Another thing is that this application is built with [Compose Desktop][compose-multiplatform] 
toolkit, being a new approach to building modern desktop applications in Kotlin.

Here are some key features:

1. System-tray application written in Compose Desktop.
2. Cross-platform support for Windows, macOS, and Linux, with various architectures, too.
3. 3D models are rendered with WebGL, with GPU acceleration.
4. Excellent [ThreeJs][three-js] library is showcased to load and animate 3D models.

[compose-multiplatform]: https://github.com/JetBrains/compose-multiplatform
[three-js]: https://github.com/mrdoob/three.js

## Project structure

The project consist of a single Gradle module, but it has two logical parts:

1. Compose application (written in Kotlin), which uses JxBrowser.
2. `webgl` component, containing JavaScript code that loads and animates 
the used models.

During the build, `webgl` component is assembled and put into the resources folder
of Compose application. In runtime, JxBrowser loads the assembled JS file from
the app's resources.

## Requirements

- Java 17 or later.
- Node.js 16 or later.

## Running

To run the application, execute the following command:

```bash
./gradlew :compose:pomodoro:run
```
