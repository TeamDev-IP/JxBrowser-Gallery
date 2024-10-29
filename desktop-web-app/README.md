# Desktop web applications with JxBrowser

This project demonstrates how to use JxBrowser with modern web UI frameworks such
as [shadcn/ui](https://ui.shadcn.com/) to create desktop applications with Java backend and
TypeScript/React/shadcn frontend.

## Prerequisites

- [Node.js](https://nodejs.org/en/download) version 20.11.0 or higher.

## JxBrowser license

Replace the `LICENSE_KEY` variable in [App.java](src/main/java/com/teamdev/jxbrowser/App.java#L24) with your JxBrowser license key.

## Running the application

Run the application in the development mode:

```bash
./gradlew desktop-web-app:run
```

This command will start a development web server with Hot-Module Reload (HMR) enabled and load its
address in JxBrowser.

## Packaging

To build application installer for macOS (DMG), use the following command:

```bash
./gradlew desktop-web-app:clean desktop-web-app:packageDmg
```

## Protobuf

This demo uses Protobuf for communication between Java and JavaScript. The Protobuf files are located in the `proto` directory.

To generate proto messages for Java and JavaScript run the following command:

```bash
./gradlew desktop-web-app:generateProto
```
