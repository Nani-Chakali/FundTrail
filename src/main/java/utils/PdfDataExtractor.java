package utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class PdfDataExtractor {

	private String pdfText;

	public PdfDataExtractor(String filePath) throws IOException {
		PDDocument document = PDDocument.load(new File(filePath));
		PDFTextStripper stripper = new PDFTextStripper();
		this.pdfText = stripper.getText(document);
		document.close();
	}

	/**
	 * Extracts a value by searching for a partial keyword in the PDF text. It looks
	 * for the first line containing that keyword, and extracts the text after ':'
	 * or keyword.
	 */
	public String getValueByKeyword(String partialKeyword) {
		String[] lines = pdfText.split("\\r?\\n");

		for (String line : lines) {
			if (line.toLowerCase().contains(partialKeyword.toLowerCase())) {
				// Extract full line first
				String value;
				if (line.contains(":")) {
					value = line.substring(line.indexOf(":") + 1).trim();
				} else {
					int idx = line.toLowerCase().indexOf(partialKeyword.toLowerCase());
					value = line.substring(idx + partialKeyword.length()).trim();
				}

				// Clean unwanted prefixes like "No.", "Number"
				value = value.replaceAll("(?i)^no\\.?", "") // Remove leading "No."
						.replaceAll("(?i)^number:?\\s*", "") // Remove leading "Number:"
						.trim();

				return value;
			}
		}
		return "Not Found";
	}

	// Optional: print all lines (for debugging)
	public void printAllLines() {
		Arrays.stream(pdfText.split("\\r?\\n")).forEach(System.out::println);
	}
}
