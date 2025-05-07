package ncpr;

import org.testng.annotations.Test;

import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.nio.file.Paths;

import base.BaseTest;
import pages.LoginPage;

public class CaseOperations extends BaseTest {

	public Locator createCase(String caseName) {
		LoginPage login = new LoginPage(page);
		login.ncrpLogin();

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("New Case")).click();
		page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("*Case Name")).fill(caseName);
		page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("*Category")).fill("test");
		page.locator("#priority").click();
		page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("High")).click();
		page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Complaint Number")).fill("123456");
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select State")).click();
		page.getByText("Andhra Pradesh").click();
		page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Unit")).fill("test_unit");
		page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Invstigator Officer")).fill("QA");
		page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Description")).fill("this is test");

		page.onceDialog(dialog -> dialog.dismiss());
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();

		Locator createdCase = page.locator("//*[contains(text(),'" + caseName + "')]");
		createdCase.waitFor();
		assertThat(createdCase).isVisible();
		return createdCase;
	}

	public void uploadFileToCase(String filePath) {
		page.locator("(//button[text()='Select'])[3]").click();
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Upload NCRP Files")).click();

		FileChooser fileChooser = page.waitForFileChooser(() -> {
			page.locator("#file-input").click();
		});

		fileChooser.setFiles(Paths.get(filePath));
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Upload")).click();

		Locator fileProcessingBanner = page.getByText("Processing", new Page.GetByTextOptions().setExact(true));
		assertThat(fileProcessingBanner).isVisible();
		

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(" Go Back")).click();
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("File Stack")).click();

		Locator queued = page.getByText("Queued");
		assertThat(queued).isVisible();

		Locator queuedPercentage = page.getByRole(AriaRole.GRIDCELL, new Page.GetByRoleOptions().setName("70"));
		assertThat(queuedPercentage).isVisible();

		Locator uploadStatus = page.getByRole(AriaRole.GRIDCELL, new Page.GetByRoleOptions().setName("Uploaded"))
				.first();
		uploadStatus.waitFor(new Locator.WaitForOptions().setTimeout(60000));
		assertThat(uploadStatus);

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(" Go Back")).click();
	}

	public void deleteCase() {
//        Locator caseRow = page.locator("//*[contains(text(),'" + caseName + "')]/ancestor::tr");
		String caseName = "test_deletecase";
		createCase(caseName);
		Locator deleteBtn = page.locator("(//button[@title='Delete'])[3]");
		deleteBtn.click();
		page.onceDialog(dialog -> dialog.accept());

	}

	@Test(groups = { "smoke" })
	public void createCaseAndUploadFile() {
		String caseName = "test_case1";
		createCase(caseName);
		uploadFileToCase("C:\\Users\\iAcuity\\eclipse-workspace\\FundTrail\\src\\main\\resources\\08-25 ncrp.pdf");
	}

	@Test(groups = { "smoke" })
	public void deleteCreatedCase() {
		deleteCase();
	}
}
