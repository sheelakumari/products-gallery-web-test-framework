package org.familysearch.products.gallery.testframework.base.report;

import org.apache.log4j.Logger;

/**
 * A test report logger that logs results using log4j.  It does not log to a database.
 * 
 */
public class LoggingTestCaseReport {

	public static Logger logger = org.apache.log4j.Logger.getLogger(LoggingTestCaseReport.class);

	public LoggingTestCaseReport() {
		
	}
	
	
	public void reportFailure(String projectName, String testSetName, String testCaseName, String resultString,
			String hostEnv, int runDuration) {

		logger.info("Logging Failure Test Result to Test Director...");
	}

	
	public void reportSuccess(String projectName, String testSetName, String testCaseName, String resultString,
			String hostEnv, int runDuration) {

		logger.info("Logging Success Test Result to Test Director...");
	}
	
}
