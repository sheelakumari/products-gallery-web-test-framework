package org.familysearch.products.gallery.testframework.selenium.driver;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;



public class TestBrowserDataProvider {

	private static final Logger logger = Logger.getLogger(TestBrowserDataProvider.class);
	
	
	@DataProvider(name="testBrowserDataProvider")
	public static Object[][] testBrowserDataProvider() {
		return getBrowserData();
	}
	

	/**
	 * Returns an array of browser data that has been parsed
	 * by as JSON formatted string. The string may contain 
	 * more than one browser specification.
	 * @return
	 */
	public static Object[][] getBrowserData() {

		Object[][] browserList;

		//read browsers from JSON-formatted environment variable if specified
		String json = System.getProperty("testBrowserJson");

		if (json == null || json.equals("")) {
			logger.info("No json specified. Return an array of nulls.");
			// Create an array element with all nulls.
			browserList = new Object[1][3];
			browserList[0][0] = null;
			browserList[0][1] = null;
			browserList[0][2] = null;
		}
		else {
			try {
				List<Object[]> data = new ArrayList<Object[]>();

				JSONArray browsers = (JSONArray) new JSONParser().parse(json);
				for (Object object : browsers) {
					JSONObject jsonObject = (JSONObject) object;
					data.add(new Object[]{
							jsonObject.get("browser"),
							jsonObject.get("version"),
							jsonObject.get("os")});
				}

				int listSize = data.size();
				browserList = new Object[listSize][];
				for( int i = 0; i < listSize; i++ ) {
					browserList[i] = data.get(i);
				}

			} catch (ParseException e) {
				throw new IllegalArgumentException("Error parsing JSON String", e);
			}

			logger.info("Parsed the data correctly.");
		}

		return browserList;
	}	

}

