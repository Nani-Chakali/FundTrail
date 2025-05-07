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
	public void GlobalDashBoardComponents() {

		LoginPage login = new LoginPage(page);
		login.ncrpLogin();

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Dashboard")).click();

		page.waitForSelector(".kpi-card");
		Locator kpiCards = page.locator(".kpi-card");
		int count = kpiCards.count();
		Map<String, String> kpiMap = new HashMap<>();
		
		System.out.println("############## Global DashBoard Components #####################");

		for (int i = 0; i < count; i++) {
			Locator card = kpiCards.nth(i);

			Locator labelLocator = card.locator("p").first(); // label
			Locator valueLocator = card.locator("p").nth(1); // value

			String label = labelLocator.innerText().trim();
			String value = valueLocator.innerText().trim();

			System.out.print(label + ":");
			System.out.println(" " + value);

			// Add to map if both are non-empty
			if (!label.isEmpty() && !value.isEmpty()) {
				kpiMap.put(label, value);
			}
		}

		// Final map output
		System.out.println("KPI Map: " + kpiMap);

	}

	@Test
	public void CaseDetailViewDashBoardComponents() {

		LoginPage login = new LoginPage(page);
		login.ncrpLogin();

		page.locator("(//button[text()='Select'])[1]").click();

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Dashboard")).click();

		page.waitForSelector(".kpi-card");
		Locator kpiCards = page.locator(".kpi-card");
		int count = kpiCards.count();
		Map<String, String> kpiMap = new HashMap<>();
		
		System.out.println("############## Case Detail View DashBoard Components #####################");

		for (int i = 0; i < count; i++) {
			Locator card = kpiCards.nth(i);

			Locator labelLocator = card.locator("p").first(); // label
			Locator valueLocator = card.locator("p").nth(1); // value

			String label = labelLocator.innerText().trim();
			String value = valueLocator.innerText().trim();

			System.out.print(label + ":");
			System.out.println(" " + value);

			// Add to map if both are non-empty
			if (!label.isEmpty() && !value.isEmpty()) {
				kpiMap.put(label, value);
			}
		}

		// Final map output
		System.out.println("KPI Map: " + kpiMap);

	}

}
