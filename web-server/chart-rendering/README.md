# Server-Side Chart Rendering with JxBrowser

This project demonstrates how to render and export HTML/CSS/JS charts 
on the server side using JxBrowser.

### Summary

Modern web environment provides a wide variety of well-established tools to visualize
a dataset as a chart. However, in some scenarios, just displaying the chart
on a web page is not what's needed. The chart may have to be created and shared 
daily/weekly/monthly, with the human intervention in the process minimized. 
The chart rendering can also be a part of a larger server pipeline, further calling
for automation. Or, the chart may have to be displayed as a part of a desktop application.

This example demonstrates how to tackle these challenges with the help of JxBrowser.
The showcased application is a web server that utilizes the power of JxBrowser
to render an arbitrary web page and then export it to an image. With such an approach,
the application renders the HTML/CSS/JS charts directly on the server side, offering
their export as images to any caller. The export happens upon a programmatic request, 
once built and run, this solution requires no human interaction or intervention. 
It can be hosted on a dedicated server or a Cloud VM, rendering and sharing charts 
upon requests such as a cron job or a REST API call from another program.

Using JxBrowser to build an application like this entails the following benefits:

1. A fully automated chart-drawing solution, which requires neither human actions
   nor an open UI to draw the charts and share them via communication channels of choice.
2. The access to a rich web ecosystem of charting libraries like [Chart.js][chart.js]
   when rendering charts from within a Java/Kotlin app. This includes the frontend 
   styling options, like [Bootstrap][bootstrap] or [Material][material] typography.
3. The ability to re-use the same chart-drawing code on the web server and
   the web client if necessary.
4. The chart-drawing mechanism that works identically on a web server, a desktop app,
   a console app, and in any other environment where JxBrowser is available.
5. The ability to switch between Java and Kotlin APIs of JxBrowser, which are
   interchangeable and offer the same functionality, depending on the project's
   language of choice.

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
[bootstrap]: https://getbootstrap.com/
[material]: https://m3.material.io/
[micronaut]: https://micronaut.io/
[java]: https://www.azul.com/downloads/#zulu
[npm]: https://nodejs.org/en/download
[gradle]: https://gradle.org/install

