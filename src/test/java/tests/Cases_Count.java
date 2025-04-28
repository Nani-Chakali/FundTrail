package tests;

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
		login.login("apriltest3", "Xalted@123");

		List<ElementHandle> caseRows = page
				.querySelectorAll("//table[@id='caseManagementMainPage:caseManagementTable']/tbody[1]/tr");
		// //table[@id='caseManagementMainPage:caseManagementTable']/tbody[1]/tr

		// Step 2: Initialize counters
		int totalCases = 0;
		int openCases = 0;
		int closedCases = 0;

		// Step 3: Loop through all rows
//		for (ElementHandle row : caseRows) {
//			List<ElementHandle> status = page.querySelectorAll(
//					"xpath=//table[@id='caseManagementMainPage:caseManagementTable']/tbody[1]/tr/td[2]/span");
//			totalCases++;
//
//			if (status.contains("Open")) {
//				openCases++;
//			} else if (status.contains("Closed")) {
//				closedCases++;
//			}
//		}
		
		for (ElementHandle row : caseRows) {
	        totalCases++;
	        
	        // Get status from the 2nd column of the current row
	        ElementHandle statusCell = row.querySelector("xpath=//table[@id='caseManagementMainPage:caseManagementTable']/tbody[1]/tr/td[2]/span");
	        String status = statusCell.textContent().trim();
	        
	        if (status.equalsIgnoreCase("Open")) {
	            openCases++;
	        } else if (status.equalsIgnoreCase("Closed")) {
	            closedCases++;
	        }
	    }
		
		System.out.println(totalCases);

		// Step 4: Fetch displayed counts

		Locator totalCasesNumber = page.locator(
				"//label[normalize-space(text())='Total Cases']/following-sibling::br/following-sibling::label");

		// Get the text content (the number, e.g., "2")
		String number = totalCasesNumber.textContent().trim();
		System.out.println("Total Cases: " + number); // Output: "Total Cases: 2"

		// If you need it as an integer
		int displayedTotalCases = Integer.parseInt(number);
		System.out.println("Total Cases (int): " + displayedTotalCases);

//		int displayedOpenCases = Integer
//				.parseInt(page.getByText("Open Cases").locator("..").locator("css=div.value").innerText().trim());
//		int displayedClosedCases = Integer
//				.parseInt(page.getByText("Closed Cases").locator("..").locator("css=div.value").innerText().trim());

		// Step 5: Assertions
		assert totalCases == displayedTotalCases : "Mismatch in total cases!";
//		assert openCases == displayedOpenCases : "Mismatch in open cases!";
//		assert closedCases == displayedClosedCases : "Mismatch in closed cases!";

		System.out.println("Counts validation successful ✅");

	}

}
