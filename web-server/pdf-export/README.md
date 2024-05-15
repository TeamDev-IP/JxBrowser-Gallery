# PDF export with JxBrowser

This project demonstrates how to render and export the table data to PDF 
on the server side using JxBrowser.

### Description

This application demonstrates how to print the HTML/CSS/JS tables to PDF using JxBrowser.

The main components of the application are:

1. A JxBrowser instance, which can print an arbitrary webpage to PDF programmatically.
   How to do it is shown in `PdfPrinting.kt`. This particular example highlights
   the usage of the JxBrowser Kotlin API but the identical process can be executed
   using the JxBrowser Java API.
2. A web server that accepts print requests and generates web pages to print
   via JxBrowser. The print requests enclose the specifics of the tabular data to be
   printed. The generated files represent the rendered tables built with HTML/CSS/JS.
3. A client side that renders the table and sends it to the server for PDF export.
   The table data can be tweaked before printing via the designated filter controls.

The technology stack used in this particular project is JxBrowser 8, Ktor as a web server,
and Grid.js for the table rendering.

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

[jdk]: https://www.azul.com/downloads/#zulu
[gradle]: https://gradle.org/install
