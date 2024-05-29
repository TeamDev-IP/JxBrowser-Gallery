# PDF export with JxBrowser

This project demonstrates how to perform programmatic PDF export of HTML/CSS/JS content 
with the help of JxBrowser.

### Summary

The need to perform an automated PDF export of some content arises in various scenarios. 
A desktop app may have to generate a PDF report based on the user input. Or, a web server 
may have to print a document to PDF on schedule without user interaction. Or, a scientific 
application may have to export a large dataset to PDF for further processing.

With the help of JxBrowser, it's possible to build a robust and universal solution
for these and other scenarios. The showcased app is a web server that prints
a real dataset visualized as an HTML/CSS table to PDF upon request. It uses JxBrowser's
ability to render arbitrary web content and export it to PDF programmatically,
without the need for UI interaction. Once built, such a solution can be hosted on 
a dedicated server or a Cloud VM, generating PDFs on demand when supplied with
the specifics of the HTML/CSS/JS content to render. The triggering mechanism for 
the PDF creation would be a programmatic call from another app, such as a cron job
or a REST API request.

Using JxBrowser to solve this task offers several benefits:

1. A fully automated PDF export solution that requires neither human actions
   nor an open UI to generate PDFs and share them via communication channels of choice.
2. The ability to render any web content to PDF, including complex HTML/CSS/JS
   structures like tables, charts, and forms. This includes large entities that
   span hundreds of pages when printed.
3. Reusing the same HTML/CSS/JS code for the PDF generation and on the web 
   client if necessary.
4. The PDF printing mechanism that would work identically on a web server, a desktop app, 
   a console app, and in any other environment where JxBrowser is available.
5. The ability to switch between Java and Kotlin APIs of JxBrowser, which are 
   interchangeable and offer the same functionality, depending on the project's
   language of choice.

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
   process can be simplified to a single call of `browser.mainFrame!!.print()`
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
