# PDF export with JxBrowser

This project demonstrates how to render and export the table data to PDF 
on the server side using JxBrowser.

### Description

The goal of the application is to show how to print HTML/CSS/JS tables to PDF 
using JxBrowser.

The main components of this application are:

1. A JxBrowser instance, which can print an arbitrary webpage to PDF programmatically.
   How to do it is shown in `PdfPrinting.kt`. This particular example highlights
   the usage of the JxBrowser Kotlin API but the identical process can be executed
   using the JxBrowser Java API.
2. A web server that accepts print requests and generates web pages to print
   via JxBrowser. The print requests enclose the specifics of the tabular data to be
   printed, while the generated files represent the rendered tables built with HTML/CSS/JS.
3. A client side that allows to tweak the table and send it to the server for PDF export.
   The table data is tweaked via the designated filter controls.

The technology stack used in this project is [JxBrowser 8][jxbrowser], [Ktor][ktor] 
as a web server, and [Grid.js][gridjs] for the table rendering.

### Prerequisites
1. [JDK 17+][jdk].
2. [Gradle][gradle].

### Running locally
```shell
   ./gradlew build
   ./gradlew :web-server:pdf-export:server:run
```
After this, open the URL specified in the console to access the server side of the application.

The exported files are available for download in the browser and also appear 
under the `{project root}/web-server/pdf-export/server/exported` directory 
on the local file system.

[jxbrowser]: https://teamdev.com/jxbrowser
[ktor]: https://ktor.io
[gridjs]: https://gridjs.io
[jdk]: https://www.azul.com/downloads/#zulu
[gradle]: https://gradle.org/install
