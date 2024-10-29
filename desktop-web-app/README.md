## Development

This is a simple example of how to use JxBrowser with a web application running on a local server.

### Working with code

If you want to develop the application, you need to generate proto messages used for communication between 
Java and JavaScript. To edit or add your own messages, check out the `proto` directory:

```bash
./gradlew generateProto
```

This command will generate code for both Java and JavaScript. 

### Starting application

To start the application, you first need to install required `npm` dependencies for the web application:

```bash
cd web-app/shadcn
npm install
```

Then, open `src/main/java/com/teamdev/jxbrowser/App.java`, find the `LICENSE_KEY` variable and set your 
JxBrowser license key.

After this, you are ready to run the application in the development mode:

```bash
cd ..
./gradlew desktop-web-app:run
```

This command will start the development web server and load it in JxBrowser. Also, it generates proto messages
under the hood.

## Production

To build application installer for macOS, use the following command:

```bash
cd ..
./gradlew desktop-web-app:clean desktop-web-app:packageDmg
```
