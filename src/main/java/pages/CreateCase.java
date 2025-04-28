package pages;

import java.nio.file.Paths;

import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CreateCase {

	private final Page page;

	// Locators (adjust as per actual IDs/names/roles)
//	private final String createCaseBtn = "AriaRole.BUTTON, new Page.GetByRoleOptions().setName('Create New Case')";
	private final String caseNameField = "[id=\'CreateNewCase\\:caseName\']";
	private final String categoryDropdown = "[id=\'CreateNewCase\\:SelectCategoryInput\']";
	private final String priorityDropdown = "[id=\'CreateNewCase\\:CasePriority_Panel_body\']";
	private final String nextBtn = "AriaRole.BUTTON, new Page.GetByRoleOptions().setName('Next >>')";
	private final String transactionTypeDropdown = "#transactionType";
//    private final String fileInput = "input[type='file']";
	private final String uploadBtn = "AriaRole.BUTTON, new Page.GetByRoleOptions().setName('Choose File')";
	private final String saveBtn = "[id=\'CreateNewCase\\:SaveCase\']";
	private final String successMessage = "#successBanner";
	
		private final String MoneyLaundringOption = "Money Laundering";

	public CreateCase(Page page) {
		this.page = page;
	}

	public void createNewCase(String caseName, String category, String priority, String transactionType,
			String filePath) {
		
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create New Case")).click();


		page.locator(caseNameField).fill(caseName);
		CreateCase cs = new CreateCase(page);
		cs.getMoneyLaundring();
		page.locator(categoryDropdown).selectOption(category);
		page.locator(priorityDropdown).selectOption(priority);
		page.locator(nextBtn).click();

		page.locator(transactionTypeDropdown).selectOption(transactionType);

		FileChooser fileChooser = page.waitForFileChooser(() -> {
			page.locator(uploadBtn).click(); // Click the Browse button
		});

		fileChooser.setFiles(Paths.get(filePath)); // Set the file to upload
		
		page.locator(saveBtn);
	}
	

	public boolean isCaseCreatedSuccessfully() {
		return page.locator(successMessage).isVisible();
	}
	
		public void getMoneyLaundring() {
			
			page.getByText(MoneyLaundringOption).click();
		}
		
		
}


//page.getByText("Add new Category").click();
//page.getByText("Money Laundering").click();
//page.getByText("Ponzi scheme").click();
//page.getByText("Embezzlement").click();
//page.getByText("General").click();
//page.getByText("High").click();
//page.getByText("Low", new Page.GetByTextOptions().setExact(true)).click();
//page.getByText("Normal", new Page.GetByTextOptions().setExact(true)).click();
//}
//}
