# Server-Side Chart Rendering with JxBrowser

This project demonstrates how to render and export HTML/CSS/JS charts on the server side using JxBrowser.

### Prerequisites:
1. [Java 17+][java].
2. [NPM][npm]

### Running locally:
```shell
   ./gradlew build
   ./gradlew :server:chart-rendering:server:run -D"jxbrowser.license.key"=your_license_key
```
After this, open `/server/chart-rendering/client/app/index.html` to launch the client side of the application.

The exported files appear under the `/server/chart-rendering/server/exported` directory.

[java]: (https://www.azul.com/downloads/#zulu)
[npm]: (https://nodejs.org/en/download)
