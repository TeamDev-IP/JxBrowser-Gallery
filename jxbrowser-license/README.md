# jxbrowser-license

This module is a license provider for internal usage within the repository.
Use it where a JxBrowser license is needed: tests or example modules.

It is a separate Gradle build because we need the license in both the build 
logic and example modules. The build logic passes the license as a JVM option
to tests that verify how `Engine` instance is assembled with Kotlin DSL.

Having it as an included build addresses the following inconveniences:

1. Passing of `jxbrowser-license` file to both the build logic and modules.
2. Duplication of code that reads the license in the build logic and modules.

Please note, dependency on `:kotlin` module for having `JxBrowserLicense` value
class is impossible. This would introduce a cyclic dependency between the builds.
