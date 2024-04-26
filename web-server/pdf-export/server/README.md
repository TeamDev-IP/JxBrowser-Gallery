# PDF export with JxBrowser

This project demonstrates how to render and export the table data to PDF 
on the server side using JxBrowser.

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
