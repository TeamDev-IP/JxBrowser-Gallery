# Java Remote Screen Sharing via WebRTC

This example project demonstrates how to share a screen between two Java applications using WebRTC and [JxBrowser](https://www.teamdev.com/jxbrowser).

![Applications](/.github/readme-resources/screenshot.png?raw=true)

## Description

The project consists of two parts: a simplistic WebRTC [server](./server) and [client applications](./clients).

The server is written in Node.js. The clients are written in Java and use JxBrowser.

## Requirements

- Java 8+
- Node.js 16.15.0

## Setup

Open a terminal and run the following commands:

```bash
cd server
npm install
```

## Run

Start the server:

```bash
cd server
node server.js [-p 3000]
```

In different terminals, run Java clients:

```bash
cd clients
./gradlew runStreamer [-Pport=3000]
```

```bash
cd clients
./gradlew runReceiver [-Pport=3000]
```
