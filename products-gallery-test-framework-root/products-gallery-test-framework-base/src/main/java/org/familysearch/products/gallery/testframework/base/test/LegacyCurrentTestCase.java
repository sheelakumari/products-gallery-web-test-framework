/**
 * Stores static values about the current test so the values can be
 * accessed by other classes.  
 * 
 * NOTE:  Since this is static, this only works for a single-threaded environment.
 * 
 */

package org.familysearch.products.gallery.testframework.base.test;

public class LegacyCurrentTestCase {
	
	static String screenshotDir;
	static String testSetName;
	static String testCaseName;
	
	
	
	public static String getScreenshotDir() {
		return screenshotDir;
	}
	
	public static void setScreenshotDir(String path) {
		screenshotDir = path;
	}
	

	
	public static String getTestSetName() {
		return testSetName;
	}
	
	public static void setTestSetName(String name) {
		testSetName = name;
	}
	
	

	public static String getTestCaseName() {
		return testCaseName;
	}
	
	public static void setTestCaseName(String name) {
		testCaseName = name;
	}
	
	public static void reset() {
		setScreenshotDir(null);
		setTestSetName(null);
		setTestCaseName(null);
	}
	
	
}
