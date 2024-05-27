# Server-Side Chart Rendering with JxBrowser

This project demonstrates how to render and export HTML/CSS/JS charts 
on the server side using JxBrowser.

### Summary

This app shows how to render and export rich HTML/CSS/JS charts as images using JxBrowser.
By following the approach demonstrated in this example, the user can:

1. Employ well-established HTML/CSS/JS charting libraries on the server side or
   outside the web environment. This app showcases [Chart.js][chart.js].
2. Use frontend styling options for the charts, like Bootstrap or Material typography.
3. Perform an easy programmatic export to an image with few lines of code.
4. Re-use the same chart-drawing code on the server side and the client side 
   if necessary.

This example application showcases a client-server architecture to highlight how
you can build a user-friendly web UI for inspecting and tweaking the charts and
then reuse the same code to render charts as images on the server side. The same
mechanism of rendering can also be utilized in a desktop app, a console app,
and in any other environment where JxBrowser is available.

### Description

The project is a web application that hosts [Chart.js][chart.js] charts visualizing data 
for a couple of different datasets. The charts are initially rendered on the client side 
where they can be tweaked and sent to the server for server-side rendering. The server side 
runs the client-side JS in a headless JxBrowser instance to render the charts and export 
them to PNG format.

#### Client side

The client side of the application is a simple web page that displays the dataset 
information and provides the necessary controls for tweaking and exporting the charts. 
The client side is implemented in JavaScript and uses the [Chart.js][chart.js] library 
to render the charts. The app is styled with [Material 3][material] components.

#### Server side

The server side of the application is a [Micronaut][micronaut] web server that 
uses the JxBrowser library to render charts as images on the server side. Using 
the client-side chart-drawing code, the server generates a widget that represents 
a rendered chart and exports it to PNG format with the help of JxBrowser:
```java
browser.navigation()
       .loadUrlAndWait("generated-widget.html");
       
var image = browser.bitmap();       
```
The image is then returned to the client side as a byte array.

### Prerequisites
1. [Java 17+][java].
2. [NPM][npm].
3. [Gradle][gradle].

### Running locally
```shell
   ./gradlew build
   ./gradlew :web-server:chart-rendering:server:run
```
After this, open `/server/chart-rendering/client/app/index.html` to launch 
the client side of the application.

The exported files are available for download in the browser and also appear 
under the `{project root}/web-server/chart-rendering/server/images` directory 
on the local file system.

[chart.js]: https://www.chartjs.org/
[material]: https://m3.material.io/
[micronaut]: https://micronaut.io/
[java]: https://www.azul.com/downloads/#zulu
[npm]: https://nodejs.org/en/download
[gradle]: https://gradle.org/install

