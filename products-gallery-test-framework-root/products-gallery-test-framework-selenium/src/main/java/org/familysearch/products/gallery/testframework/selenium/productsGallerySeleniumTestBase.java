package org.familysearch.products.gallery.testframework.selenium;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.saucerest.SauceREST;
import com.saucelabs.testng.SauceOnDemandAuthenticationProvider;
import com.saucelabs.testng.SauceOnDemandTestListener;

import org.apache.log4j.Logger;
import org.familysearch.products.gallery.testframework.base.productsGalleryTestBase;
import org.familysearch.products.gallery.testframework.selenium.driver.LocalBrowserDriverFactory;
import org.familysearch.products.gallery.testframework.selenium.driver.SauceBrowserDriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.annotations.Listeners;

import java.util.HashMap;
import java.util.Map;

@Listeners({SauceOnDemandTestListener.class})
public class productsGallerySeleniumTestBase extends productsGalleryTestBase implements
SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {
	
private static final Logger logger = Logger.getLogger(productsGallerySeleniumTestBase.class);

public static final String BROWSER_LOCATION_SAUCE = "saucelabs";
public static final String BROWSER_LOCATION_SAUCE_CONNECT = "sauceconnect";
public static final String BROWSER_LOCATION_LOCAL = "local";

protected WebDriver driver;

protected String applicationUrl;

protected String testBrowserLocation;

// Local browser will only use the browser name.
// Sauce browser will use name, version, and OS.
protected String testBrowserName;
protected String testBrowserVersion;
protected String testBrowserOS;

// Only for Sauce Labs
protected String sauceUserName;
protected String sauceAccessKey;
protected String sauceJobId;
protected SauceREST sauceREST;

// TODO: Create a method with Name and browser to use with Sauce Reporting.

// protected SauceOnDemandAuthentication sauceAuthentication;

public productsGallerySeleniumTestBase() {
// TODO: Should log as debug.
logger.info("In IdxSeleniumTestBase()");
setDefaultConfiguration();
updateConfigSettingsFromSystemProperties();
}

public productsGallerySeleniumTestBase(String testBrowserName,
	String testBrowserVersion, String testBrowserOS) {
// TODO: Should log as debug.
logger.info("In IdxSeleniumTestBase(name, version, os)");
this.testBrowserName = testBrowserName;
this.testBrowserVersion = testBrowserVersion;
this.testBrowserOS = testBrowserOS;

logger.info( "Settings after constructor parameters: \n" + getConfigSettingsAsString());

System.out.println("Setting the default configuration.");
setDefaultConfiguration();

System.out.println("Setting from system properties.");
updateConfigSettingsFromSystemProperties();
System.out.println("Done with constructor.\n");
}





// NOTE: This is designed to be overwritten by subclasses.
public void setDefaultConfiguration() {
logger.info("IdxSeleniumTestBase setDefaultConfiguration() was not overwritten.");
}



protected void updateConfigSettingsFromSystemProperties() {

// NOTE: System properties should overwrite existing settings.

System.out.println(">>>>>>>>> updating system properties <<<<<<<<");

System.out.println( getConfigSettingsAsString() );

System.out.println("\n\nReading System Property Values");
String value = null;
value = System.getProperty("applicationUrl");
System.out.println("System Property applicationUrl: " + value);
if (value != null) {
	applicationUrl = value;
}

value = System.getProperty("testBrowserLocation");
System.out.println("System Property testBrowserLocation: " + value);
if (value != null) {
	testBrowserLocation = value;
}


// If sauce is being used.
value = System.getProperty("sauceUserName");
System.out.println("System Property sauceUserName: " + value);
if (value != null) {
	sauceUserName = value;
}

value = System.getProperty("sauceAccessKey");
System.out.println("System Property sauceAccessKey: " + value);
if (value != null) {
	sauceAccessKey = value;
}

// NOTE: These normally be set by the factory, but this does allow for an override.
// Normally the browser is specified using the @Factory annotation.

value = System.getProperty("testBrowserName");
System.out.println("System Property testBrowserName: " + value);
if (value != null) {
	testBrowserName = value;
}

value = System.getProperty("testBrowserVersion");
System.out.println("System Property testBrowserVersion: " + value);
if (value != null) {
	testBrowserVersion = value;
}

value = System.getProperty("testBrowserOS");
System.out.println("System Property testBrowserOS: " + value);
if (value != null) {
	testBrowserOS = value;
}

System.out.println( "\nFinal Settings: " + getConfigSettingsAsString() );
}


public String getConfigSettingsAsString() {
return String.format(" applicationUrl: %s\n testBrowserLocation: %s\n testBrowserName: %s\n testBrowserOS: %s\n testBrowserVersion: %s\n sauceUserName: %s\n sauceAccessKey: %s\n",
						applicationUrl, testBrowserLocation,
						testBrowserName, testBrowserVersion, testBrowserOS,
						sauceUserName, sauceAccessKey );
}



//
// Getters and setters
//

public WebDriver getDriver() {
return driver;
}


public void setDriver(WebDriver driver) {
this.driver = driver;
}

public String getApplicationUrl() {
return applicationUrl;
}

public void setApplicationUrl(String applicationUrl) {
this.applicationUrl = applicationUrl;
}

public String getTestBrowserLocation() {
return testBrowserLocation;
}

public void setTestBrowserLocation(String testBrowserLocation) {
this.testBrowserLocation = testBrowserLocation;
}

//public String getLocalTestBrowserName() {
//return localTestBrowserName;
//}
//
//public void setLocalTestBrowserName(String localTestBrowserName) {
//this.localTestBrowserName = localTestBrowserName;
//}

public String getTestBrowserName() {
return testBrowserName;
}

public void setTestBrowserName(String testBrowserName) {
this.testBrowserName = testBrowserName;
}

public String getTestBrowserVersion() {
return testBrowserVersion;
}

public void setTestBrowserVersion(String testBrowserVersion) {
this.testBrowserVersion = testBrowserVersion;
}

public String getTestBrowserOS() {
return testBrowserOS;
}

public void setTestBrowserOS(String testBrowserOS) {
this.testBrowserOS = testBrowserOS;
}




public String getSauceUserName() {
return sauceUserName;
}

public void setSauceUserName(String sauceUserName) {
this.sauceUserName = sauceUserName;
}

public String getSauceAccessKey() {
return sauceAccessKey;
}

public void setSauceAccessKey(String sauceAccessKey) {
this.sauceAccessKey = sauceAccessKey;
}

public String getSauceJobId() {
return sauceJobId;
}

public void setSauceJobId(String sauceJobId) {
this.sauceJobId = sauceJobId;
}

public SauceREST getSauceREST() {
return sauceREST;
}

public void setSauceREST(SauceREST sauceREST) {
this.sauceREST = sauceREST;
}


/**
* Will wait for jQuery to not be doing any updates before execution
* continues. This only works if the page is written using jQuery.
* 
* @param driver
*/
public void waitForJqNotActive(WebDriver driver) {
JQuery.waitForJqNotActive(driver);
}


// ///////////////////////
// Sauce Labs Support
// ///////////////////////

public WebDriver getNewTestBrowserDriver() {
if (BROWSER_LOCATION_SAUCE.equals(testBrowserLocation)) {
	return setupSauceLabsBrowserSession();
} else if (BROWSER_LOCATION_SAUCE_CONNECT.equals(testBrowserLocation)) {
return setupSauceConnectBrowserSession();
} else {
	return setupLocalBrowserSession();
}
}

public WebDriver setupSauceLabsBrowserSession() {

driver = SauceBrowserDriverFactory.createSauceDriver(sauceUserName,
		sauceAccessKey, testBrowserName, testBrowserVersion,
		testBrowserOS, getTestCaseName());

sauceJobId = ((RemoteWebDriver) driver).getSessionId().toString();
sauceREST = new SauceREST(sauceUserName, sauceAccessKey);

return driver;

}

public WebDriver setupSauceConnectBrowserSession() {

driver = SauceBrowserDriverFactory.createSauceConnectDriver(sauceUserName,
    sauceAccessKey, testBrowserName, testBrowserVersion,
    testBrowserOS, getTestCaseName());

sauceJobId = ((RemoteWebDriver) driver).getSessionId().toString();
sauceREST = new SauceREST(sauceUserName, sauceAccessKey);

return driver;
}

public WebDriver setupLocalBrowserSession() {
driver = LocalBrowserDriverFactory.getLocalBrowserDriver(testBrowserName);
return driver;
}

// // This might get called by TestNG, but I'm not sure.
// public void reportResults(ITestResult result) {
// if (!bTestFailed) {
// markJobAsPassed();
// } else {
// markJobAsFailed();
// }
// }

public void markJobAsFailed() {
if (testBrowserLocation.equalsIgnoreCase(BROWSER_LOCATION_SAUCE)
		&& sauceREST != null && sauceJobId != null) {
	Map<String, Object> updates = new HashMap<String, Object>();
	updates.put("passed", false);
	sauceREST.updateJobInfo(sauceJobId, updates);
}
}

public void markJobAsPassed() {
if (testBrowserLocation.equalsIgnoreCase(BROWSER_LOCATION_SAUCE)
		&& sauceREST != null && sauceJobId != null) {
	Map<String, Object> updates = new HashMap<String, Object>();
	updates.put("passed", true);
	sauceREST.updateJobInfo(sauceJobId, updates);
}
}

/* Implementation for: SauceOnDemandAuthenticationProvider */
/**
* {@inheritDoc}
* 
* @return
*/
@Override
public SauceOnDemandAuthentication getAuthentication() {
return new SauceOnDemandAuthentication(sauceUserName, sauceAccessKey);
}

/* Implementation for: SauceOnDemandSessionIdProvider */
/**
* {@inheritDoc}
* 
* @return
*/
@Override
public String getSessionId() {
if (driver != null) {
SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
return (sessionId == null) ? null : sessionId.toString();
}
return null;
}

}
