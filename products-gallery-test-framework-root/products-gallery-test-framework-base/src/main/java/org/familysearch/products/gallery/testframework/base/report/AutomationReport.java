package org.familysearch.products.gallery.testframework.base.report;

import org.apache.log4j.Logger;


/**
 * Class to report Pass or Fail.  
 */
public class AutomationReport {
	
	private static Logger logger = Logger.getLogger( AutomationReport.class );
	
	public static void pass( String message ) {
		logger.info(message + " : PASSED");
	}
	
	public static void fail( String message ) {
		logger.error(message + " : FAILED");
	}
	
	public static void logTestClass(String className) {
		logger.info("Test Class: " + className);
	}

}
