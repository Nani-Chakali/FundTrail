package cases;

import java.util.List;
import org.testng.annotations.Test;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import base.BaseTest;
import pages.LoginPage;

public class Cases_Count extends BaseTest {

	@Test
	public void Verify_Cases_Count() {

		LoginPage login = new LoginPage(page);
		login.login();

		page.waitForSelector("xpath=//table[@id='caseManagementMainPage:caseManagementTable']/tbody[1]/tr");

		List<ElementHandle> caseRows = page
				.querySelectorAll("xpath=//table[@id='caseManagementMainPage:caseManagementTable']/tbody[1]/tr");

		System.out.println("caseRows: " + caseRows);

		// Step 2: Initialize counters
		int totalCases = 0;

		for (ElementHandle row : caseRows) {
			totalCases++;

		}

		System.out.println("totalCasesfound: " + totalCases);

		Locator totalCasesNumber = page.locator(
				"//label[normalize-space(text())='Total Cases']/following-sibling::br/following-sibling::label");

		String number = totalCasesNumber.textContent().trim();
		System.out.println("Total Cases: " + number); // Output: "Total Cases: 2"

		int displayedTotalCases = Integer.parseInt(number);
		System.out.println("Total Cases (int): " + displayedTotalCases);

		// Step 5: Assertions
		assert totalCases == displayedTotalCases : "Mismatch in total cases!";

		System.out.println("Counts validation successful ✅");

	}

	@Test
	public void Verify_Open_Cases_Count() {

		LoginPage login = new LoginPage(page);
		login.login();

		// Use Locator to find case rows directly
		Locator caseRowsLocator = page
				.locator("xpath=//table[@id='caseManagementMainPage:caseManagementTable']/tbody[1]/tr");

		int openCases = 0;

		List<ElementHandle> caseRows = caseRowsLocator.elementHandles();

		for (ElementHandle row : caseRows) {

			// Directly interact with child element using row.locator for XPath
			Locator statusCellLocator = page.locator("xpath=./td[2]/span[not(contains(text(), 'By'))]");

			// Get status from status cell
			String status = statusCellLocator.textContent().trim();

			if (status.equalsIgnoreCase("Open")) {
				openCases++;
			}
		}

			System.out.println("Total Open Cases found: " + openCases);

			// Get open cases count from UI
			Locator openCasesStatus = page.locator(
					"//table[@id='caseManagementMainPage:caseManagementTable']/tbody[1]/tr/td[2]/span[contains(text(), 'Open')]");

			int displayedOpenCases = openCasesStatus.count(); // Get the total number of "Open" elements

			for (int i = 0; i < displayedOpenCases; i++) {
				Locator statusCell = openCasesStatus.nth(i); // Get the "i-th" element
				String statusText = statusCell.textContent(); // Get the text content of the status
				System.out.println("Status Text: " + statusText); // Print status
			}

			assert openCases == displayedOpenCases : "Mismatch in open cases!";
			System.out.println(displayedOpenCases);

			System.out.println("Counts validation successful ✅");
		
	}

	@Test
	public void Verify_Closed_Cases_Count() {

		LoginPage login = new LoginPage(page);
		login.login();

		// Use Locator to find case rows directly
		Locator caseRowsLocator = page
				.locator("xpath=//table[@id='caseManagementMainPage:caseManagementTable']/tbody[1]/tr");

		int closedCases = 0;

		// Fetch all case rows
		List<ElementHandle> caseRows = caseRowsLocator.elementHandles();

		for (ElementHandle row : caseRows) {

			// Directly interact with child element using row.locator for XPath
			Locator statusCellLocator = page.locator("xpath=./td[2]/span[not(contains(text(), 'By'))]");

			// Get status from status cell
			String status = statusCellLocator.textContent().trim();

			if (status.equalsIgnoreCase("Closed")) {
				closedCases++;
			}
		}

		System.out.println("Total Closed Cases found: " + closedCases);

		// Get closed cases count from UI
		Locator ClosedCasesstatus = page.locator(
				"//table[@id='caseManagementMainPage:caseManagementTable']/tbody[1]/tr/td[2]/span[contains(text(), 'Closed')]");
		int displayedClosedCases = ClosedCasesstatus.count(); // Get the total number of "Open" elements

		for (int i = 0; i < displayedClosedCases; i++) {
			Locator statusCell = ClosedCasesstatus.nth(i); // Get the "i-th" element
			String statusText = statusCell.textContent(); // Get the text content of the status
			System.out.println("Status Text: " + statusText); // Print status
		}

		assert closedCases == displayedClosedCases : "Mismatch in closed cases!";

		System.out.println(displayedClosedCases);

		System.out.println("Counts validation successful ✅");
	}

}
