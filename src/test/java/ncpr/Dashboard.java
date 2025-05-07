package ncpr;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import base.BaseTest;
import pages.LoginPage;

public class Dashboard extends BaseTest {

	@Test
	public void DashBoardComponents() {

		LoginPage login = new LoginPage(page);
		login.ncrpLogin();

		page.locator("(//button[text()='Select'])[3]").click();

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Dashboard")).click();

		Locator kpiCards = page.locator(".kpi-card"); // adjust this selector if needed
		int count = kpiCards.count();
		Map<String, String> kpiMap = new HashMap<>();

		for (int i = 0; i < count; i++) {
			Locator card = kpiCards.nth(i);

			// Print full card text for debugging
			System.out.println("Card #" + (i + 1));
			System.out.println("Full Text: " + card.innerText());

			// Try extracting using more flexible locator
			Locator labelLocator = card.locator("p").first(); // label
			Locator valueLocator = card.locator("p").nth(1); // value

			String label = labelLocator.innerText().trim();
			String value = valueLocator.innerText().trim();

			// Log extracted text
			System.out.println("Label: " + label);
			System.out.println("Value: " + value);

			// Add to map if both are non-empty
			if (!label.isEmpty() && !value.isEmpty()) {
				kpiMap.put(label, value);
			}
		}

		// Final map output
		System.out.println("KPI Map: " + kpiMap);

	}

}
