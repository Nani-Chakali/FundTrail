package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import utils.ConfigReader;

public class LoginPage {
	private final Page page;

	private final String usernameField = "input[name='myForm\\:userId']";
	private final String passwordField = "[id='myForm\\:password']";
	private final AriaRole loginButtonRole = AriaRole.BUTTON;
	private final String loginButtonName = "Login";
	private final String welcomeTextLocator = "[id='mainformId\\:mainPanelId']";
	private final AriaRole ncrp_username = AriaRole.TEXTBOX;

	public LoginPage(Page page) {
		this.page = page;
	}

	public void login() {
		page.navigate(ConfigReader.get("url"));
		page.locator(usernameField).fill(ConfigReader.get("username"));
		page.waitForSelector(passwordField);
		page.locator(passwordField).click();
		page.locator(passwordField).fill(ConfigReader.get("password"));
		page.getByRole(loginButtonRole, new Page.GetByRoleOptions().setName(loginButtonName)).click();
	}
	
	public void ncrpLogin() {
		
		page.navigate(ConfigReader.get("ncrp_url"));
		page.getByRole(ncrp_username, new Page.GetByRoleOptions().setName("Username")).fill(ConfigReader.get("ncrp_creds"));
		page.getByRole(ncrp_username, new Page.GetByRoleOptions().setName("Password")).fill(ConfigReader.get("ncrp_creds"));
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
