# Desktop web apps with JxBrowser

This project demonstrates how to create a cross-platform desktop app with Java backend and [shadcn/ui](https://ui.shadcn.com/) frontend.

The application represents a simple preferences dialog that allows users to change the application's settings. Here's how the dialog looks like:

![App screenshot](.github/readme-resources/prefs.png)

## Prerequisites

- Java 17 or higher.
- [Node.js](https://nodejs.org/en/download) 20.11.0 or higher.
- [WiX Toolset](https://github.com/wixtoolset/wix3/releases/tag/wix3141rtm) 3.14.1 or higher if you are planning to package an installer on Windows.

## Protobuf

The app uses Protobuf for communication between Java backend and TypeScript frontend. The Protobuf files are located in the `proto` directory. Java and TypeScript code for Protobuf messages are generated automatically during the build process.

To generate proto messages manually, run the following command:

```bash
./gradlew desktop-web-app-vue:generateProto
```

## Running the application

To run the application in the development mode, you first need to start the [Vite](https://vite.dev/) dev server with enabled **Hot-Module Reload (HMR)**:

```bash
./gradlew desktop-web-app-vue:startDevServer
```

Then open another **Terminal** window and run the following command to start the application:

```bash
./gradlew desktop-web-app-vue:run
```

## Packaging

To build native installers for macOS and Windows, use the following commands.

To build macOS DMG run:

```bash
./gradlew desktop-web-app-vue:clean desktop-web-app-vue:packageDmg
```

To build Windows EXE installer run:

```bash
gradlew.bat desktop-web-app-vue:clean desktop-web-app-vue:packageExe
```

## About the project

### Problem

Nowadays, developing and supporting desktop applications based on traditional solutions like
Swing/JavaFX/SWT can be challenging. They become extremely hard to scale and maintain due to 
lack of experienced developers in this area. That is why an increasing number of old legacy 
applications switch to web-based UI to eliminate everyday maintenance challenges and improve 
scalability and user experience. With millions of developers experienced in JavaScript, TypeScript,
and frameworks like React, finding talent and speeding up development is much easier.

The modern web browsers like Chromium make this transition toward web-based UIs easier. Chromium
allows rendering web content seamlessly, and supports the latest HTML and CSS standards, ensuring 
compatibility with modern UI frameworks.

### Solution

JxBrowser allows embedding a Chromium-based browser into Java desktop applications. It allows developers to create cross-platform desktop apps that combine the power of Java platform and the flexibility of web technologies.

Communication between Java backend and TypeScript frontend is implemented using Protobuf and gRPC. These technologies provide an effective solution, enabling fast and reliable communication with a well-structured approach to data exchange.