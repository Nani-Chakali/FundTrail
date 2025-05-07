package usermanagement;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import base.BaseTest;
import pages.LoginPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PasswordPolicy extends BaseTest {

	@BeforeClass
	public void login() {

		LoginPage login = new LoginPage(page);
//		login.login("superadmin1", "aaaaA1!");
		page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("User Management")).click();

	}

	@Test
	public void verifyArrowTopButtonTest() {
		Locator arrowTop = page.locator("//a[@id='arrowtop']");
		assertThat(arrowTop).isVisible();
		assertThat(arrowTop).isEnabled();
	}

	public void verifyUserManagementLabel() {
		Locator label = page.locator("//h4[@class='ng-star-inserted']");
		assertThat(label).isVisible();
	}

	public void verifyPasswordPolicyScreenLabel() {
		Locator label = page.locator("//label[text()='Password Policy']");
		assertThat(label).isVisible();
	}

	public void verifyPasswordLengthPolicy() {
		Locator policy = page.locator("//label[text()='Password length policy']");
		assertThat(policy).isVisible();
	}

	public void verifyPasswordLengthPolicyTextbox() {
		Locator textbox = page.locator("#passLength");
		assertThat(textbox).isVisible();
		assertThat(textbox).isEnabled();
	}

	public void verifyMinimumLengthLabel() {
		Locator label = page.locator("//label[text()='Minimum length']");
		assertThat(label).isVisible();
	}

	public void verifyMaximumLengthLabel() {
		Locator label = page.locator("//label[text()='Maximum length']");
		assertThat(label).isVisible();
	}

	public void verifyPasswordComplexityPolicy() {
		Locator policy = page.locator("//label[text()='Password complexity policy']");
		assertThat(policy).isVisible();
	}

	public void verifyCheckboxesAndLabels() {
		String[] checkboxLabels = { "One upper case character", "One lower case character", "One special character",
				"One digit", "Disallow username" };

		for (String label : checkboxLabels) {
			Locator checkbox = page.locator("//label[text()='" + label + "']/preceding-sibling::input");
			assertThat(checkbox).isVisible();
			assertThat(checkbox).isEnabled();
		}
	}

	public void verifyPasswordHistoryPolicy() {
		Locator policy = page.locator("//label[text()='Password history policy']");
		assertThat(policy).isVisible();
	}

	public void verifyPasswordHistoryTextbox() {
		Locator textbox = page.locator("#passHistory");
		assertThat(textbox).isVisible();
		assertThat(textbox).isEnabled();
	}

	public void verifySaveAndResetButtons() {
		Locator save = page.locator("#saveBtn");
		Locator reset = page.locator("#resetBtn");
		assertThat(save).isVisible();
		assertThat(save).isEnabled();
		assertThat(reset).isVisible();
		assertThat(reset).isEnabled();
	}

	public void verifyMandatoryFields() {
		Locator passLength = page.locator("#passLength");
		Locator passHistory = page.locator("#passHistory");
		passLength.fill("");
		passHistory.fill("");
		page.click("#saveBtn");

		Locator error1 = page.locator("#passLengthError");
		Locator error2 = page.locator("#passHistoryError");
		assertThat(error1).isVisible();
		assertThat(error2).isVisible();
	}

	public void verifyResetButtonFunctionality() {
		page.fill("#passLength", "10");
		page.fill("#passHistory", "3");
		page.click("#resetBtn");

		String passLengthValue = page.inputValue("#passLength");
		String passHistoryValue = page.inputValue("#passHistory");

		assert passLengthValue.isEmpty();
		assert passHistoryValue.isEmpty();
	}

	public void verifySaveButtonFunctionality() {
		page.fill("#passLength", "12");
		page.fill("#passHistory", "5");
		page.click("#saveBtn");

		Locator toast = page.locator(".toast-message");
		assertThat(toast).isVisible();
		assertThat(toast).containsText("updated successfully");
	}
}
