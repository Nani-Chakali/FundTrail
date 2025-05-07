package ncpr;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import utils.PdfDataExtractor;

public class CaseDetailViewValidations extends BaseTest {

	@Test(groups = { "smoke" })
	public void DetailViewComponents() {

		LoginPage login = new LoginPage(page);
		login.ncrpLogin();

		page.locator("(//button[text()='Select'])[1]").click();
		assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Dashboard"))).isVisible();
		assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Link View"))).isVisible();
		assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Upload NCRP Files")))
				.isVisible();
		assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("File Stack"))).isVisible();
//		assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Logs"))).isVisible();

	}

	@Test(groups = { "smoke" })
	public void test_verify_pdf_data_matches_ui() throws IOException {

		LoginPage login = new LoginPage(page);
		login.ncrpLogin();

		page.locator("(//button[text()='Select'])[1]").click();

		// Extract from PDF
		PdfDataExtractor extractor = new PdfDataExtractor(
				"C:\\Users\\iAcuity\\eclipse-workspace\\FundTrail\\src\\main\\resources\\08-25 ncrp.pdf");
		String pdfAckNumber = extractor.getValueByKeyword("acknowledgement");
		System.out.println("PDF Acknowledgement Number: " + pdfAckNumber);

		// Extract from UI
		String uiAckNumber = page
				.locator("//div[contains(@class, 'tagcloud-container')]//*[name()='svg']/*[name()='text']").first()
				.textContent().trim();
		System.out.println("First extracted number: " + uiAckNumber);

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("1 ")).click();

		String viewAckNumber = page.getByRole(AriaRole.GRIDCELL, new Page.GetByRoleOptions().setName(pdfAckNumber))
				.textContent().trim();

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Cancel")).click();

		// Assertion
		Assert.assertEquals(uiAckNumber, pdfAckNumber, "Acknowledgement numbers do not match!");

		Assert.assertEquals(viewAckNumber, pdfAckNumber, "Acknowledgement numbers do not match!");
	}

}
