package org.familysearch.products.gallery.testframework.base;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.familysearch.products.gallery.testframework.base.util.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class productsGalleryTestBase {
	
	public static Logger logger = org.apache.log4j.Logger.getLogger( productsGalleryTestBase.class );
	
	String customConfigFilePath;
	
	// Some of these might not be needed.
	// Currently adding them to get the fest tests working correctly.
	protected productsGalleryConfig testConfig = null;
	protected HashMap testDataMap = null;
    private String projectName = "";
	private String testSetName = "";
	private String testCaseName = "";
	private String hostEnv = ""; // Host environment - IE: STAGE, Firefox, ipaddress, etc.
	
	boolean bUseAlternateTestData = false;
	String alternateTestDataDirectory;
	

	private int warnCount = 0;
	private int errorCount = 0;
	private int fatalCount = 0;
	private int verificationErrorCount = 0;
	
	boolean bSetUpSuccessful = true;

	
	public productsGalleryTestBase() {
		this(null);
	}

	public productsGalleryTestBase(String customConfigFilePath) {
		// TODO: Make this usable.		
		this.customConfigFilePath = customConfigFilePath;
	}
	
	// Methods for logging in test cases

	public void logTrace(String message) {	
		logger.trace(getPreLogStackElement() + " " + message);
	}

	public void logDebug(String message) {
		logger.debug(getPreLogStackElement() + " " + message);
	}
	
	public void logInfo(String message) {
		logger.info(getPreLogStackElement() + " " + message);
	}

	
	public void logWarn(String message) {
		logger.warn(getPreLogStackElement() + " " + message);
	}

	public void logError(String message) {
		logger.error(getPreLogStackElement() + " " + message);
	}


	//
	// Method for trying to get some sort of source line number to help debug tests, to show where the error occurred.
	// The current stack location can't be used because it shows inside this test base class. 
	
	public static String getPreLogStackElement() {

		String returnValue = null;

		Throwable t = new Throwable();
		StackTraceElement[] trace = t.getStackTrace();
		
		boolean bFirstElementSkipped = false;
		for ( StackTraceElement el : trace ) {
			String methodName = el.getMethodName();
			String className = el.getClassName();
			
			if ( bFirstElementSkipped ) {
				if (  !(methodName.startsWith("log") && className.endsWith("IndexingTestBase")) ) {
					returnValue = "(" + el.getFileName() + ":" + el.getLineNumber() + ")" ;		
					break;
				}
				else {
					// It's a logging method in IndexingTestBase, so skip it.
				}
			}
			else {
				// This will be done on the first element.
				bFirstElementSkipped = true;
			}
		}	

		if ( returnValue == null ) {
			returnValue = "ERROR: Couild not determine correct stack element.";
		}
		
		return returnValue;
	}
	
	/**
	 * Pauses a test for a specified number of seconds.
	 * 
	 * @param seconds number of seconds to wait.
	 */
	public void pauseTest(int seconds) {
		Pause.pause(seconds);
	}

	// This is a TestNG annotation.
	// This annotation will be inherited by subclasses.
	@BeforeMethod
	public void determineTestName(Method m) {
		setTestCaseName(m.getName());
		logger.info("Before starting method: " + getTestCaseName());
	}
	
	
	// This is a TestNG annotation.
	// This annotation will be inherited by subclasses.
	@AfterMethod
	public void afterMethodShowName(Method m) {
		String testName = m.getName();
		logger.info("After running method: " + testName);
	}
	
	public String getTestSetName() {
		return this.testSetName;
	}

	public void setTestSetName(String name) {
		this.testSetName = name;
	}

	public String getTestCaseName() {
		return testCaseName;
	}

	public void setTestCaseName(String name) {
		// Note this is done automatically with the determineTestName annotated method.
		this.testCaseName = name;
	}

	public String getHostEnv() {
		return this.hostEnv;
	}

	public void setHostEnv(String hostEnvironment) {
		final int RN_HOST_SIZE = 50; // Size of the field in the database

		if (hostEnvironment.length() > RN_HOST_SIZE) {
			hostEnvironment = hostEnvironment.substring(0, RN_HOST_SIZE - 1);
		}
		this.hostEnv = hostEnvironment;
	}
	
	
	// Note: may depricate these
	// This is for setting things manually.  Because of TestNG, this is no longer needed.
	// Fluent interface.
	public productsGalleryTestBase reportAs(String name) {
		setTestCaseName( name );
		return this;
	}
		
	public productsGalleryTestBase inSet(String set) {
		setTestSetName(set);
		return this;
	}
	
	// End of fluent interface.
	//
	// Custom properties that are set in the configuration file.
	// 
	
	/**
	 * Allow the test case to get custom information that is
	 * specified in the test's configuration file.
	 * 
	 * @param propertyKey The property name, such as "test.user.one".
	 * @return The value of the property, if it has been specified.  Otherwise, null.
	 */
	public String getConfigurationProperty(String propertyKey) {
		return testConfig.getConfigurationProperty(propertyKey);
	}
		
	// TODO:  These might be deprecated.
	
	
	// Return whether the setup method succeeded.
	public boolean getSetUpStatus() {
		return bSetUpSuccessful;
	}
	
	// Tell if the test case failed.
	public boolean setUpFailed() {
		return !bSetUpSuccessful;
	}
	
	public void failIfSetUpErrorsExist() {
		if ( setUpFailed() ) {
			logInfo("Failing test because the setup failed.");
			logResults();
		}
	}
	public void logResults() {
//		reportFinalResults(true);
	}

}