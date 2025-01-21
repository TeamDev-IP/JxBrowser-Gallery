# Desktop web applications with JxBrowser

This project demonstrates how to use JxBrowser with modern web UI frameworks such
as [shadcn/ui](https://ui.shadcn.com/) to create desktop applications with Java backend and
TypeScript/React/ShadCN frontend.

## Prerequisites

- [Node.js](https://nodejs.org/en/download) version 20.11.0 or higher.
- Wix Toolset (Windows only) version 3.1 or higher if you are planning to package an installer. 
  We tested with [3.14.1](https://github.com/wixtoolset/wix3/releases/tag/wix3141rtm).

## JxBrowser license

Replace the `LICENSE_KEY` variable in [App.java](src/main/java/com/teamdev/jxbrowser/App.java#L24) with your JxBrowser license key.

## Running the application

To run the application in the development mode, you first need to start Vite development server:

```bash
./gradlew desktop-web-app:startDevServer
```

This command will start a development web server with Hot-Module Reload (HMR).

Then open another terminal and run the following command to start the application:

```bash
./gradlew desktop-web-app:run
```

## Packaging

To build application installer, use the following commands:

- macOS (DMG)

```bash
./gradlew desktop-web-app:clean desktop-web-app:packageDmg
```

- Windows (EXE)

```bash
gradlew.bat desktop-web-app:clean desktop-web-app:packageExe
```

## Protobuf

This demo uses Protobuf for communication between Java and JavaScript. The Protobuf files are located in the `proto` directory.

To generate proto messages for Java and JavaScript run the following command:

```bash
./gradlew desktop-web-app:generateProto
```
