# Java Remote Screen Sharing via WebRTC

This project demonstrates how to share a screen between two Java 
applications using WebRTC and [JxBrowser](https://www.teamdev.com/jxbrowser).

![Screen sharing screenshot](.github/readme-resources/screenshot.jpeg)

## Description

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
