# FundTrail
# Playwright Java Automation Setup

This README provides instructions for setting up Playwright with Java for browser automation testing.

## Prerequisites

1. **Java**: Ensure that Java 11 or above is installed on your system.
    - To check if Java is installed, run: `java -version`
    - If not installed, download and install from [here](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).

2. **Maven**: Playwright for Java uses Maven for dependency management.
    - To check if Maven is installed, run: `mvn -version`
    - If not installed, download and install from [here](https://maven.apache.org/download.cgi).

3. **IDE**: Use an IDE such as IntelliJ IDEA or Eclipse to work with Java.

## Installation Steps

1. **Create a Maven Project**:
   - Open your IDE and create a new Maven project.
   - Add the following `pom.xml` dependency to your project.

2. **Add Dependencies**:
   Add the Playwright Java dependencies to your `pom.xml` file:

    ```xml
    <dependencies>
        <dependency>
            <groupId>com.microsoft.playwright</groupId>
            <artifactId>playwright</artifactId>
            <version>1.28.0</version> <!-- Update the version as needed -->
        </dependency>
    </dependencies>
    ```

3. **Install Playwright**:
   - Open a terminal or command prompt in your project directory.
   - Run the following command to download the necessary browsers for Playwright:

    ```bash
    mvn clean install
    ```

   This will download the Playwright library and required browser binaries.

4. **Set Up Playwright in Java**:
   Create a new Java class (e.g., `PlaywrightTest.java`) and write a sample script.

    ```java
    import com.microsoft.playwright.*;

    public class PlaywrightTest {
        public static void main(String[] args) {
            try (Playwright playwright = Playwright.create()) {
                Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                Page page = browser.newPage();
                page.navigate("https://example.com");
                System.out.println(page.title());
                browser.close();
            }
        }
    }
    ```

5. **Run the Test**:
   - Run the `PlaywrightTest.java` class to verify the setup.

## Running Tests

You can now run your Playwright tests using Maven commands:

1. To run the tests, use:

    ```bash
    mvn test
    ```

## Additional Setup (Optional)

- **Headless Mode**: If you want to run tests in headless mode (without opening the browser window), set the `headless` option to `true` in the launch configuration:

    ```java
    browser.newPage().launch(new BrowserType.LaunchOptions().setHeadless(true));
    ```

- **Cross-Browser Testing**: Playwright supports multiple browsers (Chromium, Firefox, WebKit). You can choose the browser as follows:

    ```java
    // For Chromium
    browser = playwright.chromium().launch();
    
    // For Firefox
    browser = playwright.firefox().launch();
    
    // For WebKit
    browser = playwright.webkit().launch();
    ```

## Troubleshooting

- If you encounter issues with Playwright installation or browser downloads, ensure that your network connection is stable and retry the installation.
- Ensure your Java version is compatible with Playwright (Java 11 or above).

For detailed documentation and examples, visit [Playwright Java Documentation](https://playwright.dev/docs/intro).

---

Happy Testing!
