**Playwright Automation**
Installation :
✅ Add Playwright Dependency to pom.xml
<dependency>
  <groupId>com.microsoft.playwright</groupId>
  <artifactId>playwright</artifactId>
  <version>1.44.0</version> <!-- Use latest stable -->
</dependency>
✅ Download Playwright Browsers
Run this command to download Chromium, Firefox, and WebKit browsers:
mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"
