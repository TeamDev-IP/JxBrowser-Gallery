# Java Remote Screen Sharing via WebRTC

This project demonstrates how to share a screen between two Java 
applications using [WebRTC][webrtc] and [JxBrowser](jxbrowser).

[webrtc]: https://webrtc.org/
[jxbrowser]: https://www.teamdev.com/jxbrowser
![Screen sharing screenshot](.github/readme-resources/screenshot.jpeg)

## Summary

Remote screen sharing is used in various applications and services, from web
conferencing to remote access applications. For example, it allows team members
to share screens in virtual meetings and enables technical support specialists 
to efficiently assist clients by directly viewing and guiding them. Third-party 
apps like TeamViewer facilitate this. But what if you need screen sharing
capabilities directly in your Java application?

Developing an in-house screen sharing functionality in a desktop app requires 
a lot of resources. Dealing with complicated network protocols and optimizations
of UI to render a video stream may be challenging in terms of time and budged.

Fortunately, we can build a screen sharing application much easier with WebRTC.
It enables peer-to-peer audio, video, and data sharing, providing high-quality
communication between browser without the need for plugins or external apps.
But Java doesn't naturally support WebRTC. This is where JxBrowser comes into
the picture. JxBrowser is a library that allows seamless integration of web 
technologies into a Java Application, including WebRTC. This project leverages
JxBrowser to integrate WebRTC and provide a screen-sharing feature within 
the Java desktop application.

Employing JxBrowser & WebRTC provides the following benefits:

1. Cross-Platform Compatibility: JxBrowser is designed to work seamlessly 
across Windows, macOS, and Linux.
2. Reliability and Stability: By leveraging JxBrowser's underlying Chromium 
engine for WebRTC, you can benefit from the reliability and stability of Chromium,
reducing the likelihood of compatibility issues and bugs in your screen sharing feature.
3. APIs and Libraries: WebRTC provides a comprehensive set of APIs and libraries 
that simplify the implementation of screen sharing, reducing development time.
4. Optimized Streaming: WebRTC is optimized for low-latency and high-quality 
real-time communication. It leverages various codecs and adaptive bitrate
technologies to ensure smooth and efficient screen sharing.
5. Encryption: WebRTC provides end-to-end encryption for all media streams, 
ensuring that the screen sharing content is secure and protected from eavesdropping.

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
