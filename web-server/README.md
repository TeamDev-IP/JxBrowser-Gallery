# Apps built with JxBrowser in the Web Server environment

This directory contains gallery apps that use JxBrowser in the web server environment.

### Chart rendering

This app shows how to render and export rich HTML/CSS/JS charts as images using JxBrowser.
By following the approach showcased in this example, the user will be able to:

1. Employ well-established HTML/CSS/JS charting libraries on the server side or 
   outside the web environment. This app chooses Chart.js.
2. Use frontend styling options for the charts, like Bootstrap or Material typography.
3. Perform an easy programmatic export to an image with few lines of code.
4. Re-use the same chart-drawing code on the server and the client side if necessary.

This example application showcases a client-server architecture to highlight how
you can build a user-friendly web UI for inspecting and tweaking the charts and
then reuse the same code to render charts as images on the server side. The similar
mechanism of rendering, however, can also be achieved in a desktop app, a console app,
and in any other environment where JxBrowser is available.

See the project README for more details: [chart-rendering/README.md](chart-rendering/README.md).

### PDF export

This app demonstrates how to print the HTML/CSS/JS content to PDF using JxBrowser.
By following the approach showcased in this example, the user will be able to:

1. Print arbitrary webpage content to PDF programmatically. This app chooses table
   data as an example.
2. Inspect and tweak the content via UI controls before it is submitted for printing.
3. Robustly generate large PDF files (1000+ pages) if necessary.

This example highlights the usage of the JxBrowser Kotlin API, with the identical 
functionality being also offered by the JxBrowser Java API. The printing mechanism
described in this example will work on a web server, a desktop app, a console app,
and in any other environment where JxBrowser is available.

See the project README for more details: [pdf-export/README.md](pdf-export/README.md).
