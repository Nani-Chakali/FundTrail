package base;

import com.microsoft.playwright.*;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected Page page;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        System.out.println("======== BaseTest: setUp() Called ========");
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        browser = playwright.chromium().launch();
        page = browser.newPage();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        System.out.println("======== BaseTest: tearDown() Called ========");
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
