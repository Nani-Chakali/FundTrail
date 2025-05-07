package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	private static final Properties prop = new Properties();

	static {
		try {

			FileInputStream fis = new FileInputStream(
					"C:\\Users\\iAcuity\\eclipse-workspace\\FundTrail\\config.properties");
			prop.load(fis);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public static String get(String key) {

		return prop.getProperty(key);
	}

}
