package homepage;

import base.BaseTest;
import pages.LoginPage;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
				
public class Recon_Tollerence extends BaseTest {

	@Test
	public void VerifyReconTollerence() {

		LoginPage login = new LoginPage(page);
		login.login();
		
		Locator button = page.locator("//*[@id=\"caseManagementMainPage:GBTRCId\"]");
		
		System.out.println(button);
		
		page.waitForSelector("//*[@id=\"caseManagementMainPage:GBTRCId\"]");

		assertThat(page.locator("//*[@id=\"caseManagementMainPage:GBTRCId\"]/text()")).isVisible();

	}

}
