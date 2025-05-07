package cases;

import base.BaseTest;
import java.nio.file.Paths;
import org.testng.annotations.Test;
import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import pages.CreateCase;
import pages.LoginPage;

public class CreateCaseTest extends BaseTest {

	@Test
	public void CaseCreationFlow() {
		LoginPage loginPage = new LoginPage(page);

		loginPage.login();
		CreateCase casePage = new CreateCase(page);

		  page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create New Case")).click();
	      page.locator("[id=\"CreateNewCase\\:caseName\"]").click();
	      page.locator("[id=\"CreateNewCase\\:caseName\"]").fill("case_test");
	      page.getByText("Case Name * Description800 Characters Categories * Add new CategoryMoney").click();
	      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Next >>")).click();   
	      FileChooser fileChooser = page.waitForFileChooser(() -> {
	    	    page.getByText("BrowseUpload").click();
	    	});

	    	fileChooser.setFiles(Paths.get("C:\\Users\\iAcuity\\Downloads\\Buddhadeb_Laha.xlsx"));

	      page.locator("[id=\"CreateNewCase\\:SaveCase\"]").click();
	}
	
	 @Test
	    public void CaseCreationFlowWithAllCombinations() {
	        LoginPage loginPage = new LoginPage(page);

	        // Login
	        loginPage.login();

	        // Define all possible values
	        String[] categories = {"Money Laundering", "Fraud", "Terrorism Financing"};
	        String[] priorities = {"High", "Medium", "Low"};
	        String[] bankStatements = {"HDFC Bank", "ICICI Bank", "Axis Bank"};

	        int caseCounter = 1; // To create unique case names

	        for (String category : categories) {
	            for (String priority : priorities) {
	                for (String bankStatement : bankStatements) {
	                    
	                    // Start creating a new case
	                    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create New Case")).click();

	                    // Enter Case Name
	                    page.locator("[id=\"CreateNewCase\\:caseName\"]").click();
	                    page.locator("[id=\"CreateNewCase\\:caseName\"]").fill("case_" + caseCounter);

	                    // Select Category
	                    selectDropdownValue("#CreateNewCase\\:caseCategory_label", category);

	                    // Select Priority
	                    selectDropdownValue("#CreateNewCase\\:casePriority_label", priority);

	                    // Select Bank Statement
	                    selectDropdownValue("#CreateNewCase\\:bankStatement_label", bankStatement);

	                    // Click Next
	                    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Next >>")).click();

	                    // Upload file
	                    FileChooser fileChooser = page.waitForFileChooser(() -> {
	                        page.getByText("BrowseUpload").click(); // Open file chooser
	                    });
	                    fileChooser.setFiles(Paths.get("C:\\Users\\iAcuity\\Downloads\\Buddhadeb_Laha.xlsx"));

	                    // Save Case
	                    page.locator("[id=\"CreateNewCase\\:SaveCase\"]").click();

	                    // Wait for case creation confirmation if needed
	                    page.waitForTimeout(3000); // Adjust timeout as per your app's speed

	                    caseCounter++; // Increment case number for next case
	                }
	            }
	        }
	    }

	    // Reusable method to select a dropdown value
	    private void selectDropdownValue(String dropdownLocator, String optionText) {
	        page.locator(dropdownLocator).click(); // Open dropdown
	        page.locator("//li[contains(text(),'" + optionText + "')]").click(); // Select value
	    }
	}
