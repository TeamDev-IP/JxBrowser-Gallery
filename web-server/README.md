# Apps built with JxBrowser in the Web Server environment

This directory contains gallery apps that use JxBrowser in the web server environment.

### Chart Rendering

The chart rendering app shows how to build and export rich HTML/CSS/JS charts 
on the server side using JxBrowser. This enables:

1. Using well-established chart-drawing UI libraries for rendering charts. This 
   example uses Chart.js.
2. Using frontend styling options, like Material or Bootstrap.
3. Re-using the same chart-drawing code on the server side and the client side
   if necessary.

The server-side rendering of charts is achieved by loading a webpage containing
the chart-drawing code into a JxBrowser instance and then exporting the current 
frame to an image using `browser.bitmap()`. This approach is simple and efficient 
and has no prerequisites regarding browser configuration or the surrounding environment.

The ability to customize the webpage before loading it into JxBrowser also makes 
the exported charts highly configurable. The server accepts the client’s parameters
and uses them to generate the chart.

The client-server nature of this application is mostly utilized to provide a user-friendly 
interface for inspecting and tweaking the charts. The identical approach to chart 
rendering can be used in desktop apps, console apps, and any other environment where 
JxBrowser is available.

See the project README for more details: [chart-rendering/README.md](chart-rendering/README.md).

### PDF Export

The PDF export app that demonstrates how to render and export the table data to PDF
on the server side using JxBrowser.

See the project README for more details: [pdf-export/README.md](pdf-export/README.md).
