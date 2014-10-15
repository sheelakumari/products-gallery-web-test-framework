package org.familysearch.products.gallery.testframework.selenium.driver;


import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * Creates drivers for browsers that run locally on the calling workstation.  
 * 
 * Note: To create browsers for Sauce Labs, use SauceBrowserDriverFactory
 *
 * @author stoddardbd
 */
public class BrowserDriverFactory {
	private static final Logger logger = Logger.getLogger(BrowserDriverFactory.class);
	

    public WebDriver getLocalBrowserDriver(BrowserType browserType) {
    	return LocalBrowserDriverFactory.getLocalBrowserDriver(browserType);
    }
	
    public WebDriver getSauceBrowserDriver(String sauceUserName, String sauceAccessKey, String browserName, String browserVersion, String os, String testName) {
    	return SauceBrowserDriverFactory.createSauceDriver(sauceUserName, sauceAccessKey, browserName, browserVersion, os, testName);
    }
	
    public WebDriver getSauceConnectBrowserDriver(String sauceUserName, String sauceAccessKey, String browserName, String browserVersion, String os, String testName) {
    	return SauceBrowserDriverFactory.createSauceConnectDriver(sauceUserName, sauceAccessKey, browserName, browserVersion, os, testName);
    }

}
