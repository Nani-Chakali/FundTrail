package ncpr;

import org.testng.annotations.Test;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import base.BaseTest;
import pages.LoginPage;

public class HomePageValidations extends BaseTest {

	@Test(groups = {"smoke"})
	public void loginFromFundTrail() {

		LoginPage login = new LoginPage(page);
		login.login();

		Locator caseLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("ncrp_case"));
		caseLink.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

		// Then click it
		caseLink.click();

		BrowserContext context = page.context();
		Page newTab = context.waitForPage(() -> {
			page.locator("[id=\"NewCaseViewForm\\:j_idt3293\"]").click();
		});

		// Wait for content to load in the new tab
		newTab.waitForLoadState(LoadState.DOMCONTENTLOADED);

		// Verify content in new tab
		assertThat(newTab.getByText("Home >> Case Management")).isVisible();

	}

	@Test(groups = {"smoke"})
	public void ncrpLoginLogout() {

		LoginPage login = new LoginPage(page);
		login.ncrpLogin();

		assertThat(page.getByText("Home >> Case Management")).isVisible();

		Locator logoutButton = page.locator("//button[@class='logout-button']");

		// Wait until the button is visible and enabled
		logoutButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
		logoutButton.click();

		assertThat(page.locator("div").filter(new Locator.FilterOptions().setHasText("Log InLoginForget Password?"))
				.nth(2)).isVisible();

	}

}
