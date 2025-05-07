package cases;

import org.testng.annotations.Test;
import base.BaseTest;
import pages.LoginPage;

public class Maximum_Cases_Allowed_Test extends BaseTest {

	@Test
	public void Verfiy_Max_Cases_Allowed() {

		LoginPage login = new LoginPage(page);
		login.login();
//		page.pause();
		String actualText = page.getByText("Maximum simultaneous cases").innerText();
		if (!actualText.contains("Maximum simultaneous cases allowed:- 3")) {
			throw new AssertionError("Expected text not found! Actual text: " + actualText);
		}

	}

}
