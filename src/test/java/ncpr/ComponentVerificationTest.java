package ncpr;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.LoginPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

public class ComponentVerificationTest extends BaseTest {

	@Test(groups = { "smoke" })
	public void verify_components_load_status() {

		LoginPage login = new LoginPage(page);
		login.ncrpLogin();

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Dashboard")).click();

		// ✅ 1. Verify Global Dashboard from home page
		assertThat(page).hasURL(Pattern.compile(".*dashboard.*"));

		System.out.println("✅ Global Dashboard loaded successfully.");

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(" Go Back")).click();

		// ✅ 2. Open a case from the case list
		page.locator("(//button[text()='Select'])[1]").click();
		System.out.println("✅ Case detail view opened.");

		// ✅ 3. Case Dashboard
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Dashboard")).click();
		assertThat(page.getByText("Acknowledgment No.")).isVisible();
		System.out.println("✅ Case Dashboard loaded.");
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(" Go Back")).click();

		// ✅ 4. Link View
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Link View")).click();
		assertThat(page.getByText("Ack No:")).isVisible();
		System.out.println("✅ Link View loaded.");
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(" Go Back")).click();

		// ✅ 5. Upload NCRP Files
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Upload NCRP Files")).click();
		assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Upload Files"))).isVisible();
		System.out.println("✅ Upload NCRP Files page loaded.");
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(" Go Back")).click();

		// ✅ 6. File Stack
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("File Stack")).click();
		assertThat(page.locator(".ag-root-wrapper")).isVisible();
		System.out.println("✅ File Stack page loaded.");
	}
}
