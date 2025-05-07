package cases;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import base.BaseTest;
import pages.LoginPage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;
import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class TabularView extends BaseTest {

    @Test
    public void DownloadAndCompareFileWithUITable() throws Exception {
        // Step 1: Login
        LoginPage login = new LoginPage(page);
        login.login();

        // Step 2: Navigate and download
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("new_test")).click();
        page.locator("//*[@id='NewCaseViewForm:j_idt4335']/li").click();

        // Step 3: Download Excel
        Download download = page.waitForDownload(() -> {
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Download data in Excel file")).click();
        });

        Path downloadedPath = download.path();
        System.out.println("📁 Downloaded file path: " + downloadedPath);

        // Step 4: Read and compare data
        List<Map<String, String>> excelData = extractExcelData(downloadedPath.toFile());
        List<Map<String, String>> uiData = extractUIData(page);
        compareData(excelData, uiData);
    }

    private List<Map<String, String>> extractExcelData(File file) throws Exception {
        List<Map<String, String>> excelData = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(file); 
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(3);
            
            // Find column indices
            int categoryIndex = -1, modeIndex = -1;
            for (Cell cell : headerRow) {
                String header = cell.getStringCellValue().trim().toLowerCase();
                if (header.contains("category")) categoryIndex = cell.getColumnIndex();
                if (header.contains("mode")) modeIndex = cell.getColumnIndex();
            }

            // Extract data into Map objects
            for (int i = 4; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Map<String, String> rowData = new HashMap<>();
                    rowData.put("Transaction category", getCellValue(row.getCell(categoryIndex)));
                    rowData.put("Transaction mode", getCellValue(row.getCell(modeIndex)));
                    excelData.add(rowData);
                }
            }
        }
        System.out.println("Excel data extracted: " + excelData.size() + " rows");
        return excelData;
    }

    private List<Map<String, String>> extractUIData(Page page) {
        List<Map<String, String>> uiData = new ArrayList<>();
        
        Locator categoryCells = page.locator("//td[contains(@id,'TransactionCategory')]");
        Locator modeCells = page.locator("//td[contains(@id,'TransactionMode')]");
        
        int rowCount = Math.min(categoryCells.count(), modeCells.count());
        for (int i = 0; i < rowCount; i++) {
            Map<String, String> rowData = new HashMap<>();
            rowData.put("Transaction category", categoryCells.nth(i).innerText().trim());
            rowData.put("Transaction mode", modeCells.nth(i).innerText().trim());
            uiData.add(rowData);
        }
        
        System.out.println("UI data extracted: " + uiData.size() + " rows");
        return uiData;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING: return cell.getStringCellValue().trim();
            case NUMERIC: return String.valueOf(cell.getNumericCellValue()).trim();
            default: return "";
        }
    }

    private void compareData(List<Map<String, String>> excelData, List<Map<String, String>> uiData) {
        System.out.println("\n=== Detailed Column Comparison ===");
        System.out.printf("%-25s %-20s | %-25s %-20s%n", 
            "UI Category", "UI Mode", 
            "Excel Category", "Excel Mode");
        System.out.println("-----------------------------------------|-----------------------------------------");
        
        // Create copies to track unmatched entries
        List<Map<String, String>> unmatchedExcel = new ArrayList<>(excelData);
        List<Map<String, String>> unmatchedUI = new ArrayList<>(uiData);
        List<Map<String, String>> matched = new ArrayList<>();
        
        // Find matches first
        for (Map<String, String> uiEntry : new ArrayList<>(unmatchedUI)) {
            Iterator<Map<String, String>> excelIterator = unmatchedExcel.iterator();
            while (excelIterator.hasNext()) {
                Map<String, String> excelEntry = excelIterator.next();
                if (excelEntry.get("Transaction category").equals(uiEntry.get("Transaction category")) &&
                    excelEntry.get("Transaction mode").equals(uiEntry.get("Transaction mode"))) {
                    matched.add(uiEntry);
                    unmatchedUI.remove(uiEntry);
                    excelIterator.remove();
                    break;
                }
            }
        }
        
        // Print matches
        System.out.println("\nMATCHING ENTRIES:");
        for (Map<String, String> entry : matched) {
            System.out.printf("%-25s %-20s | %-25s %-20s%n",
                entry.get("Transaction category"),
                entry.get("Transaction mode"),
                entry.get("Transaction category"),
                entry.get("Transaction mode"));
        }
        
        // Print mismatches
        System.out.println("\nMISMATCHES:");
        int maxRows = Math.max(unmatchedUI.size(), unmatchedExcel.size());
        for (int i = 0; i < maxRows; i++) {
            String uiCat = i < unmatchedUI.size() ? unmatchedUI.get(i).get("Transaction category") : "---";
            String uiMode = i < unmatchedUI.size() ? unmatchedUI.get(i).get("Transaction mode") : "---";
            String excelCat = i < unmatchedExcel.size() ? unmatchedExcel.get(i).get("Transaction category") : "---";
            String excelMode = i < unmatchedExcel.size() ? unmatchedExcel.get(i).get("Transaction mode") : "---";
            
            System.out.printf("%-25s %-20s | %-25s %-20s%n", 
                uiCat, uiMode, excelCat, excelMode);
        }
        
        // Summary statistics
        System.out.println("\n=== Summary ===");
        System.out.println("Total Excel Records: " + excelData.size());
        System.out.println("Total UI Records:    " + uiData.size());
        System.out.println("Matching Records:    " + matched.size());
        System.out.println("Mismatched Records:  " + (unmatchedUI.size() + unmatchedExcel.size()));
        
        // Optional: Fail test if there are mismatches
        if (!unmatchedUI.isEmpty() || !unmatchedExcel.isEmpty()) {
            throw new AssertionError("Data mismatch between UI and Excel");
        }
    }
}