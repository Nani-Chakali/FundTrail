package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

	@Test
	public void testValidLogin() {
		LoginPage loginPage = new LoginPage(page);

		loginPage.login("apriltest3", "Xalted@123");

		Assert.assertTrue(loginPage.isLoginSuccessful(), "Login was not successful.");
		System.out.println("Login successful. Welcome text: " + loginPage.getWelcomeText());

	}

	@Test(dataProvider = "loginData")
	public void testInvalidLogin(String username, String password, boolean isExpectedToPass) {
		LoginPage loginPage = new LoginPage(page);

		loginPage.login(username, password);

		boolean loginResult = loginPage.isLoginSuccessful();

		if (isExpectedToPass) {
			Assert.assertTrue(loginResult, "Login was expected to succeed, but failed.");
		} else {
			Assert.assertFalse(loginResult, "Login was expected to fail, but succeeded.");
		}

		System.out.println("Tested with Username: " + username + " | Result: " + (loginResult ? "Success" : "Failed"));
	}

	@DataProvider(name = "loginData")
	public Object[][] dataSets() {

		return new Object[][] { { "invaliduser", "Xalted@123", false }, // invalid username
				{ "apriltest3", "wrongpass", false }, // invalid password
				{ "", "", false } // empty credentials
		};
	}
}
