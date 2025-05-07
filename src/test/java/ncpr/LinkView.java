package ncpr;

import java.nio.file.Path;
import org.testng.annotations.Test;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import base.BaseTest;
import pages.LoginPage;

public class LinkView extends BaseTest {

	@Test
	public void ExcelDownLoadCheck() {

		LoginPage login = new LoginPage(page);
		login.ncrpLogin();

		page.locator("(//button[text()='Select'])[1]").click();

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Link View")).click();

		Download download = page.waitForDownload(() -> {
			page.getByRole(AriaRole.BUTTON).nth(3).click();
		});

		// Get the path of the downloaded file
		Path downloadedFilePath = download.path();

		// Verify the file exists
		if (downloadedFilePath != null && downloadedFilePath.toFile().exists()) {
			System.out.println("✅ File downloaded successfully: " + download.suggestedFilename());
		} else {
			System.out.println("❌ File download failed.");
		}

	}

}
