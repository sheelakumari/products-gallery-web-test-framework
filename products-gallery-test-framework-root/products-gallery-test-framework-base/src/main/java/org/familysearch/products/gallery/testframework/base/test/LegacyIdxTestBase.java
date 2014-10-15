package org.familysearch.products.gallery.testframework.base.test;



import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.familysearch.products.gallery.testframework.base.data.XmlObjectReader;
import org.familysearch.products.gallery.testframework.base.launch.exec.AppRunner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


// TODO: Put common methods into this class.
public class LegacyIdxTestBase {
	
	public static Logger logger = org.apache.log4j.Logger.getLogger( LegacyIdxTestBase.class );
	
	String customConfigFilePath;
	
	// Currently adding them to get the fest tests working correctly.
	protected LegacyIdxTestConfig testConfig = null;
	protected HashMap testDataMap = null;
    private String projectName = "";
	private String testSetName = "";
	private String testCaseName = "";
	private String testName;
	private String hostEnv = ""; // Host environment - IE: STAGE, Firefox, ipaddress, etc.
	
	boolean bUseAlternateTestData = false;
	protected String alternateTestDataDirectory;
	

	private int warnCount = 0;
	private int errorCount = 0;
	private int fatalCount = 0;
	private int verificationErrorCount = 0;
	
	boolean bSetUpSuccessful = true;


	// NOTE: This is a special case for Executer support for Java apps.
	// Holds the arguments for the test case.
	Map<String,String> argsMap = new HashMap<String, String>();

	public LegacyIdxTestBase() {
		this(null);
	}

	public LegacyIdxTestBase(String customConfigFilePath) {
		// TODO: Make this usable.	
		this.customConfigFilePath = customConfigFilePath;
		testConfig = LegacyIdxTestConfig.getConfig(customConfigFilePath);
		setProjectName( testConfig.getTestProjectName() );
		alternateTestDataDirectory = testConfig.getAlternateTestDataDirectory();
		logger.info("Alternate test dir: " + alternateTestDataDirectory );
		if ( alternateTestDataDirectory != null && !alternateTestDataDirectory.equals("")) {
			bUseAlternateTestData = true;
		}
		logger.info("Use alternate data flag set to: " + bUseAlternateTestData);
		
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
		errorCount++;
		logger.error(getPreLogStackElement() + " " + message);
	}

	
	public void logError(String message, Throwable t) {
		logError( message + t.toString() ); // No need to provide a stack element here.
	}
	
	public void logError(Throwable t) {
		logError( t.toString() ); // No need to provide a stack element here.
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
	 */
	public void pauseTest(int seconds) {
		LegacyPause.pause(seconds);
	}
	

	
	// This is a TestNG annotation.
	// This annotation will be inherited by subclasses.
	@BeforeMethod
	public void getTestName(Method m) {
		String testName = m.getName();
		logger.info("Before starting method: " + testName);
	}
	
	@AfterMethod
	public void afterMethodShowName(Method m) {
		String testName = m.getName();
		logger.info("After running method: " + testName);
	}

	
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String name) {
		this.projectName = name;
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
	
	
	// This is for setting things manually.  Because of TestNG, this is no longer needed.
	// Fluent interface.
	public LegacyIdxTestBase reportAs(String name) {
		setTestCaseName( name );
		return this;
	}
		
	public LegacyIdxTestBase inSet(String set) {
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

// TODO:  Fix this to work.  Not wanting to report the old way.	
	// TODO: Change to reportFinalResults
	public void logResults() {
//		reportFinalResults(true);
	}
	
	

	public void logSetUpStart() {
// TODO: Fix the stubbed out stuff.
		
		String className = this.getClass().getSimpleName();
//		AutomationReport.logTestClass(className);
		logInfo(">>> Starting setUp() for class: " + className +  "  And method: " + getTestCaseName() );
	}
	
	public void logSetUpEnd() {
		String className = this.getClass().getSimpleName();
		logInfo(">>> Finished setUp() for class: " + className);
	}

	public void logTestCaseStart() {
		logInfo(">>> Starting Test Case: " + getTestCaseName());
	}

	public void logTestCaseStart(String description) {
		logInfo("\n\n>>> Starting Test Case: " + getTestCaseName());
		logInfo(">>> Description: " + description );
	}
	
	public void logBadStateFailure(String message) {
		// Set the success flag, so the test cases can examine
		// it to determine if they should run their tests.
		bSetUpSuccessful = false;
		logFatal("*** Bad State Error: " + message);
	}
	
	
	
	public void logSetUpFailure(String message) {
		// Set the success flag, so the test cases can examine
		// it to determine if they should run their tests.
		bSetUpSuccessful = false;
		logFatal("Fatal error in setup: " + message);
	}

	public void logSetUpFailure(Throwable t) {
		// Set the success flag, so the test cases can examine
		// it to determine if they should run their tests.
		bSetUpSuccessful = false;
		logFatal("Fatal error in setup: " + t.toString());
	}
	
	public void logTearDownStart() {
		String className = this.getClass().getSimpleName();
		logInfo(">>> Starting tearDown() for class: " + className );
	}
	
	public void logTestCaseEnd() {
		logInfo(">>> Finished Test Case: " + getTestCaseName());
	}
	
	
	
	
	
	public void logVerificationError(String message) {
		logError( "Verification Error: " + message);
		verificationErrorCount++;
		// TODO: Should these be put in a separate buffer?
		
	}
	
	
	public void logFatal(String message) {
		fatalCount++;
		logger.fatal(getPreLogStackElement() + " " + message);
	}
	
	public void logFatal(String message, Throwable t) {
		logFatal( message + t.toString() ); // No need to provide a stack element here.
	}
	
	public void logFatal(Throwable t) {
		logFatal( t.toString() ); // No need to provide a stack element here.
	}
	
	
	//
	// Assertions with TestNG signatures.
	// 
	
	public void assertNull(Object object) {
		org.testng.Assert.assertNull(object);
	}
	
	public void assertNull(Object object, String message) {
		org.testng.Assert.assertNull(object, message);
	}
	
	
	public void assertNotNull(Object object) {
		org.testng.Assert.assertNotNull(object);
	}
 	
	public void assertNotNull(Object object, String message) {
		org.testng.Assert.assertNotNull(object, message);
	}
	
	
	public void assertTrue(boolean condition) {
		org.testng.Assert.assertTrue(condition);
	}
	
	public void assertTrue(boolean condition, String message) {
		org.testng.Assert.assertTrue(condition, message);
	}
	
	
	public void assertFalse(boolean condition) {
		org.testng.Assert.assertFalse(condition);
	}

	public void assertFalse(boolean condition, String message) {
		org.testng.Assert.assertFalse(condition, message);
	}



	// TODO:  Fix the order of these strings.
	
	// Some more stuff to get tests to work during the refactoring.
	public void tryAssertEquals(String arg1, String arg2 ) {
		reportTry("Trying that Strings are equal.");
		assertTrue(arg1.equals(arg2), "Checking that string content is equal");
		reportTrySuccess("Strings are equal.");
		
	}
	
	/**
	 * Asserts Check: Asserts that an object is null. 
	 * If it isn't an AssertionError is thrown. <br/> 
	 * @param
	 */
	public void tryAssertNull(Object object) {
		reportTry("Trying that the object is null.");
		assertTrue(object == null, "Checking that the object is not null");
		reportTrySuccess("The object is null.");
	}	
	
	/**
	 * Asserts Check: Asserts that an object isn't null. 
	 * If it is an AssertionError is thrown. <br/> 
	 * @param
	 */
	public void tryAssertNotNull(Object object) {
		reportTry("Trying that the object is not null.");
		assertTrue(object != null, "Checking that the object is not null");
		reportTrySuccess("The object is not null.");
	}		
	
	// TODO:  Make these do what they are supposed to do.
	// For error handling.
	public void reportTry(String message) {
		logInfo("Trying: " + message);
		// TODO:  Add to storage. Increment the number of tries.

	}
	
	public void reportTryFailure(String message) {
		//reportFailure(message);
		logError(message);
	}
	
	public void reportTryFailure(String message, Exception e) {
		//reportFailure(message);
		logError(message + " | " + e.getMessage() );
	}

	
	public void reportTrySuccess(String message) {
		//reportFailure(message);
		logInfo(message);
	}
	
	
	//
	// Verifications:  These will check for an error, but will not throw
	// an exception if they fail.
	// 
	
	public boolean verifyTrue(boolean b) {
		boolean bSuccess = false;
				
		if ( b ) {
			logInfo("verifyTrue (passed): " + b + " = true");
			bSuccess = true;
		}
		else {
			logVerificationError("verifyTrue (failed): " + b + " = false");
			bSuccess = false;
		}
				
		return bSuccess;
	}
	
	
	
	public boolean verifyTrue(String message, boolean b ) {
		boolean bSuccess = false;
				
		try {
			assertTrue( b, message );
			logInfo("verifyTrue: " + message + "  >>> PASSED");
			bSuccess = true;
		}
		catch (Throwable t) {
			bSuccess = false;
			logVerificationError("verifyTrue failed: " + t.getMessage());
		}
		
		return bSuccess;
	}
	
	
	public boolean verifyFalse(boolean b) {
		boolean bSuccess = false;
				
		if ( !b ) {
			logInfo("verifyFalse (passed): " + b + " = false");
			bSuccess = true;
		}
		else {
			logVerificationError("verifyFalse (failed): " + b + " = true");
			bSuccess = false;
		}
				
		return bSuccess;
	}
	
	public boolean verifyFalse(String message, boolean b ) {
		boolean bSuccess = false;
		
		if ( !b ) {
			logInfo("verifyFalse (passed): " + b + " = false  >>> " + message);
			bSuccess = true;
		}
		else {
			logVerificationError("verifyFalse (failed): " + b + " = true   >>> " + message);
			bSuccess = false;
		}
				
		return bSuccess;
	}
	
	
	
	
	public boolean verifyTextEquals(String actualText, String expectedText) {
		boolean bSuccess = false;
					
		if ( actualText.equals(expectedText) ) {
			bSuccess = true;
			logInfo("verifyTextContains (passed): <<<" + actualText + ">>> EQUALS <<<" + expectedText + ">>>");
		}
		else {
			bSuccess = false;
			logVerificationError("verifyTextContains (failed): <<<" + actualText + ">>> DOES NOT EQUAL <<<" + expectedText + ">>>" );			
		}
				
		return bSuccess;
	}
	
	public boolean verifyTextContains(String fullText, String searchText) {
		boolean bSuccess = false;
					
		if ( fullText.indexOf( searchText) != -1 ) {
			bSuccess = true;
			logInfo("verifyTextContains (passed): <<<" + fullText + ">>> CONTAINS <<<" + searchText + ">>>");
		}
		else {
			bSuccess = false;
			logVerificationError("verifyTextContains (failed): <<<" + fullText + ">>> DOES NOT CONTAIN <<<" + searchText + ">>>" );			
		}
				
		return bSuccess;
	}
	
	
	public boolean verifyTextDoesNotContain(String fullText, String searchText ) {
		boolean bSuccess = false;
					
		if ( fullText.indexOf( searchText) == -1 ) {
			bSuccess = true;
			logInfo("verifyTextDoesNotContain (passed):  <<<" + fullText + ">>> DOES NOT CONTAIN <<<" + searchText + ">>>");
		}
		else {
			bSuccess = false;
			logVerificationError("verifyTextDoesNotContain (failed): <<<" + fullText + ">>> CONTAINS <<<" + searchText + ">>>");			
		}
				
		return bSuccess;
	}
	

	
	
	public boolean verifyTextDoesNotContain(String message, String fullText, String searchText ) {
		boolean bSuccess = false;
					
		if ( fullText.indexOf( searchText) == -1 ) {
			bSuccess = true;
			logInfo("verifyTextDoesNotContain (passed):  <<<" + fullText + ">>> DOES NOT CONTAIN <<<" + searchText + ">>>" + message);
		}
		else {
			bSuccess = false;
			logVerificationError("verifyTextDoesNotContain (failed): <<<" + fullText + ">>> CONTAINS <<<" + searchText + ">>>" + message);			
		}
				
		return bSuccess;
	}
	
	
	public boolean verifyTextStartsWith(String fullText, String searchText) {
		boolean bSuccess = false;
					
		if ( fullText.startsWith( searchText ) ) {
			bSuccess = true;
			logInfo("verifyTextStartsWith (true): <<<" + fullText + ">>> STARTS WITH <<<" + searchText + ">>>");
		}
		else {
			bSuccess = false;
			logVerificationError("verifyTextStartsWith (failed): <<<" + fullText + ">>> DOES NOT START WITH <<<" + searchText + ">>>" );			
		}
				
		return bSuccess;
	}
	
	
		
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//  Data Support
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	//
	// For reading from XML files
	//
	
	
	// TODO: There is no check here to validate that the file is valid.
	// So there should really be a check here.
	public File getAlternateTestDataDirectory() {
		return alternateTestDataDirectory != null  ? (new File(alternateTestDataDirectory)) : null;
	}
	
	
	/**
	 * Will return either a resource file reader or a file system reader, depending upon the 
	 * settings in the configuration file.
	 * 
	 * @return
	 */
	public XmlObjectReader getXmlObjectReader() {
		if ( !bUseAlternateTestData ) {
			return XmlObjectReader.getResourceReader();
		}
		else {
			return XmlObjectReader.getFileReader( getAlternateTestDataDirectory() );
		}
	}
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////// TODO:  Move test executor out.  
	/// Make it a class and an interface.
	/////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	/**
	 * Executes a test case method that doesn't take any arguments.
	 * 
	 * @param testCaseName Name of the test in the test case database.
	 * @param className Fully qualified name of the class that contains the test methods.
	 * @param methodName Name of the method, in the class, to run.
	 */
	public void ExecuteTestCase(String testCaseName, String className, String methodName) {
		ExecuteTestCase(testCaseName, className, methodName, null);
	}
	
	
	
	/**
	 * Executes a test case method that takes arguments. 
	 * 
	 * @param testCaseName Name of the test in the test case database.
	 * @param className Fully qualified name of the class that contains the test methods.
	 * @param methodName Name of the method, in the class, to run.
	 * @param args Arguments for the test method.
	 */
	public void ExecuteTestCase(String testCaseName, String className, String methodName, String[] args ) {
		ExecuteTestCase(testCaseName, className, methodName, args, AppRunner.NO_INTERVAL, AppRunner.NO_TIMEOUT);
	}	
	
	
	
	/**
	 * Executes a test case method that doesn't take any arguments, and kills the application if the 
	 * test execution time exceeds the timeout value.
	 * 
	 * @param testCaseName Name of the test in the test case database.
	 * @param className Fully qualified name of the class that contains the test methods.
	 * @param methodName Name of the method, in the class, to run.
	 * @param timeoutThreshold Number of seconds that the application will run before it is to be killed by the 
	 * 	AppRunner.
	 * @param timeoutCheckInterval Number of seconds to wait between checks on the timeout value.
	 */
	public void ExecuteTestCase(String testCaseName, String className, String methodName, int timeoutThreshold, int timeoutCheckInterval ) {
		ExecuteTestCase(testCaseName, className, methodName, null, AppRunner.NO_INTERVAL, AppRunner.NO_TIMEOUT);
	}	
	
	
	
	/**
	 * Executes a test case method that takes arguments, and kills the application if the test execution time
	 * exceeds the timeout value.
	 * 
	 * @param testCaseName Name of the test in the test case database.
	 * @param className Fully qualified name of the class that contains the test methods.
	 * @param methodName Name of the method, in the class, to run.
	 * @param args Arguments for the test method.
	 * @param timeoutThreshold Number of seconds that the application will run before it is to be killed by the 
	 * 	AppRunner.
	 * @param timeoutCheckInterval Number of seconds to wait between checks on the timeout value.
	 */
	public void ExecuteTestCase(String testCaseName, String className, String methodName, String[] args, int timeoutThreshold, int timeoutCheckInterval ) {
		resetErrorCounts();
		reportAs(testCaseName);
		logTestCaseStart();

		try {
			int returnValue = AppRunner.getInstance().runJavaMethod(className, methodName, args, timeoutThreshold, timeoutCheckInterval);
			logInfo("The return value was: " + returnValue);
			
			if ( returnValue != 0 ) {
				logError("Test case execution returned: " + returnValue );
			}
		} catch (Throwable t) {
			t.printStackTrace();
			logFatal(t);
		}
		
		logTestCaseEnd();
		logResults();
	}
	
	
	
	/**
	 * Maps the specified array into a map of key/value pairs.
	 *
	 * For example: -username myUser
	 * 
	 * @param args String array containing the keys and values.
	 */
	public void mapArgs(String[] args) {
		
		if ( args == null || args.length == 0 ) {
			return;
		}
		
		int i = 0;
		while( i < args.length ) {
			
			String key=null;
			String value=null;
			
			if ( args[i].startsWith("-") ) {
				// It is a parameter specifier.
				key = args[i];
				// Move to the value.
				if (++i < args.length ) {
					value = args[i];
				}
				else {
					// There aren't any more arguments.
					value = null;
				}
				
				argsMap.put(key, value);
			}
			else {
				// The argument didn't start with a "-".
				System.out.println("Ignoring: " + args[i]);
			}
			
			
			// Move from the value to the next argument name.
			i++;
		}
		
	}

	
	/**
	 * Returns an argument that was specified earlier with mapArgs.
	 * @param argName Key of the argument.  Must start with a "-".
	 * @return Value associated with the key.
	 */
	public String getArgValue(String argName) {
		return argsMap.get(argName);
	}
	
	@BeforeMethod
	public void resetCounters() {
		resetErrorCounts();
	}
	
	public void resetErrorCounts() {
		warnCount = 0;
		errorCount = 0;
		fatalCount = 0;
		verificationErrorCount = 0;

	}

}
