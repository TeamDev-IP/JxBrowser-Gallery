# Java Remote Screen Sharing via WebRTC

This project demonstrates how to share a screen between two Java 
applications using [WebRTC][webrtc] and [JxBrowser](jxbrowser).

[webrtc]: https://webrtc.org/
[jxbrowser]: https://www.teamdev.com/jxbrowser
![Screen sharing screenshot](.github/readme-resources/screenshot.jpeg)

## Summary

### Problem

Screen sharing is an essential tool used in many applications and services, 
from web conferencing to remote support. It helps team members collaborate 
in virtual meetings and allows technical support specialists to assist clients 
more effectively by seeing their screens directly. While third-party apps like
TeamViewer or Google Meet offer screen sharing, what if you need this feature
directly in your Java application?

Building screen sharing from scratch in a desktop app can be resource-intensive. 
You have to handle complex network protocols and optimize the user interface 
to stream video, which can be both time-consuming and expensive.

### Solution

Luckily, there's an easier way to implement screen sharing using WebRTC.
It enables peer-to-peer audio, video, and data sharing, providing high-quality
communication between browsers. However, Java doesn't naturally support WebRTC,
and that's where JxBrowser comes in. JxBrowser is a library that allows you to 
integrate web technologies, including WebRTC, into a Java application. 
This project uses JxBrowser to add WebRTC screen-sharing capabilities 
to a Java desktop app.

Using JxBrowser and WebRTC in a desktop app has several advantages:

1. JxBrowser works smoothly on Windows, macOS, and Linux.
2. It uses the reliable Chromium engine, minimizing compatibility issues and bugs.
3. The web world offers various WebRTC libraries, reducing development time.
4. WebRTC is designed for low-latency real-time communication, ensuring smooth 
video streaming with advanced codecs and adaptive bitrate technologies.
5. All media streams are end-to-end encrypted, keeping your screen-sharing 
sessions secure and private.

## Project structure

The project consists of four modules:

1. `server` establishes direct media connections between peers.
2. `sender` is a Compose app that shares the primary screen.
3. `receiver` is a Compose app that shows the shared screen.
4. `common` holds a common code shared among Compose apps.

The [server][signaling-server] itself and Compose apps use PeerJs library, 
which provides an easy-to-use API for working with WebRTC. The library consists
of the [client][peer-js] and [server][peer-js-server] parts accordingly.

[signaling-server]: https://developer.mozilla.org/en-US/docs/Web/API/WebRTC_API/Signaling_and_video_calling#the_signaling_server
[peer-js]: https://github.com/peers/peerjs
[peer-js-server]: https://github.com/peers/peerjs-server

## Requirements

- Java 17 or later.
- Node.js 16 or later.

## Running

To run this example, one needs to start the server, sender, and receiver apps
in individual terminals.

By default, the example uses `3000` port. However, it is possible to pass
a custom port with CLI options. Ensure that you pass the same port to each task.

Start the signaling server:

```bash
./gradlew :compose:screen-share:server:run [--port=4000]
```

Start the sender app:

```bash
./gradlew :compose:screen-share:sender:run [-Pport=4000]
```

Start the receiver app:

```bash
./gradlew :compose:screen-share:receiver:run [-Pport=4000]
```
