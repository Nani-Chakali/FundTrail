package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage {
    private final Page page;

    // Locators
    private final String usernameField = "input[name='myForm\\:userId']";
    private final String passwordField = "[id='myForm\\:password']";
    private final AriaRole loginButtonRole = AriaRole.BUTTON;
    private final String loginButtonName = "Login";
    private final String welcomeTextLocator = "[id='mainformId\\:mainPanelId']";

    // Constructor
    public LoginPage(Page page) {
        this.page = page;
    }

    // Actions
    public void login(String username, String password) {
        page.navigate("http://148.113.0.235/FinancialAnalytics/login.secure");
        page.locator(usernameField).fill(username);
        page.waitForSelector(passwordField);
        page.locator(passwordField).click();
        page.locator(passwordField).fill(password);
        page.getByRole(loginButtonRole, new Page.GetByRoleOptions().setName(loginButtonName)).click();
    }

    public String getWelcomeText() {
        page.waitForSelector(welcomeTextLocator);
        return page.locator(welcomeTextLocator).textContent();
    }

    public boolean isLoginSuccessful() {
        return page.locator(welcomeTextLocator).textContent().contains("Welcome");
    }
}
