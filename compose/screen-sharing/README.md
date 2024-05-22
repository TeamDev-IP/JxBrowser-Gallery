# Java Remote Screen Sharing via WebRTC

This project demonstrates how to share a screen between two Java 
applications using WebRTC and [JxBrowser](https://www.teamdev.com/jxbrowser).

![Applications](/.github/readme-resources/screenshot.png?raw=true)

## Description

The project consists of three parts: a simplistic WebRTC server and two 
client applications: streamer and receiver.

The server is written with Node.js. The clients are written in Kotlin 
and use JxBrowser.

## Requirements

- Java 17+.
- Node.js 16+.

## Running

To run this example, one needs start the server, streamer and receiver apps
in individual terminals.

By default, the example uses `3000` port. But it is possible to pass a custom
port with CLI option.

Start WebRTC server:

```bash
./gradlew :compose:screen-sharing:server:run [--port=4000]
```

Start the streamer app:

```bash
./gradlew :compose:screen-sharing:streamer:run [-Pport=4000]
```

Start the receiver app:

```bash
./gradlew :compose:screen-sharing:receiver:run [-Pport=4000]
```
