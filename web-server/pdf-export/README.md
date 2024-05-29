# PDF export with JxBrowser

This project demonstrates how to perform programmatic PDF export of HTML/CSS/JS content
with the help of JxBrowser.

### Summary

#### Problem

Businesses often want to automate their reporting. By far, the most common format for
such reports is PDF. The resulting files may include a variety of data, including tables,
charts, and forms. While the format is very common, setting up an automated PDF export 
can often impose some challenges. The organization may be required to lock into 
a specific reporting tool and/or the developers may need to use a program library 
with a steep learning curve requiring proficiency to use effectively. Also, the fidelity 
of the generated content may not be as high as expected.

In many situations, it's beneficial to use an alternative approach: render the desired 
content as HTML/CSS/JS and print it to PDF via the built-in printing mechanism of a web browser.

#### Solution

This example shows how the latter could be implemented with the help of JxBrowser.
The showcased app is a web server that prints a real dataset visualized as an HTML/CSS table
to PDF upon request. It uses JxBrowser's API to render the web content and programmatically 
export it to a PDF file. The resulting PDFs are the same as if they were printed by a plain 
desktop Chromium browser.

Once the app is built and run, the whole printing process becomes automated and 
requires no human intervention or manipulation with the UI. The app can be hosted on 
a dedicated server or a Cloud VM, generating PDFs upon requests, such as a cron job 
or a REST API request from another program.

To sum up, here is a short list of benefits:

1. Fully automated PDF generation.
2. Independence from the third-party report formats and specific reporting tools, 
   high quality of the generated content.
3. Reusing the same HTML/CSS/JS code for the PDF generation and on the web
   client if necessary, e.g. to generate a preview. The outcome is identical.

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
