package org.familysearch.products.gallery.testframework.selenium.driver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.remote.*;
import org.openqa.selenium.remote.BrowserType;


public class SauceBrowserDriverFactory {

	private static final String SAUCE_ON_DEMAND_URL = "@ondemand.saucelabs.com:80/wd/hub";
	private static final String SAUCE_CONNECT_URL = "@localhost:4445/wd/hub";
  private static final List<String> tabletBrowsers = Arrays.asList(BrowserType.IPAD, BrowserType.ANDROID);


  private static RemoteWebDriver createDriver( String sauceURL, String sauceUserName, String sauceAccessKey,
			String browserName, String browserVersion,	String os, String testName ) {
		
		RemoteWebDriver driver = null;
		
		// Specify what the browser and OS should be.
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName(browserName);
		capabilities.setCapability(CapabilityType.VERSION, browserVersion);
		capabilities.setCapability(CapabilityType.PLATFORM, os);
		capabilities.setCapability("name", testName);
    if (tabletBrowsers.contains(browserName)) {
        capabilities.setCapability("device-orientation", "portrait");
    }

		// Return a driver that connects to Sauce Labs.
		try {
			driver =  new RemoteWebDriver( new URL("http://" + sauceUserName + ":" + sauceAccessKey + sauceURL), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error creating sauce driver: " + e, e);
		}

		return driver;
	}

	public static RemoteWebDriver createSauceDriver( String sauceUserName, String sauceAccessKey,
			String browserName, String browserVersion,	String os, String testName ) {

        return createDriver(SAUCE_ON_DEMAND_URL, sauceUserName, sauceAccessKey, browserName, browserVersion, os, testName);
    }

    public static RemoteWebDriver createSauceConnectDriver( String sauceUserName, String sauceAccessKey,
			String browserName, String browserVersion,	String os, String testName ) {

        return createDriver(SAUCE_CONNECT_URL, sauceUserName, sauceAccessKey, browserName, browserVersion, os, testName);
	}

}
