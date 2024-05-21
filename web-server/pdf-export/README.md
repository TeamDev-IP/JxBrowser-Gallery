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
   
   Also, the printing process is adapted to the pages that require long asynchronous 
   operations to finish before being printed. If such operations are absent, the printing 
   process can be simplified to a single call to `browser.mainFrame!!.print()`
   (see `PdfPrinting.kt`).

2. A web server that accepts print requests and generates PDF files using JxBrowser.
   A print request encloses the specifics of the tabular data to be printed. Based
   on these parameters, the server generates an HTML/CSS/JS table by running
   `browser.executeJavaScript(...)` after loading some generic HTML template.

   An alternative approach would be to generate an HTML template with all the
   necessary parameters beforehand and then load it into the browser directly. 
   Both approaches are valid and lead to the same result.

3. A client side that allows tweaking the table and sending it to the server for PDF export.
   The displayed table data can be changed via the designated filter controls.

The technology stack used in this project is [JxBrowser 8][jxbrowser], [Ktor][ktor] 
as a web server, and [Grid.js][gridjs] for the HTML/CSS/JS table rendering.

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
