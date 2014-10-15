package org.familysearch.products.gallery.testframework.selenium.driver;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

/**
 * Creates drivers for browsers that run locally on the calling workstation.  
 * 
 * Note: To create browsers for Sauce Labs, use SauceBrowserDriverFactory
 *
 * @author stoddardbd
 */
public class LocalBrowserDriverFactory {
	private static final Logger logger = Logger.getLogger(LocalBrowserDriverFactory.class);
	
	
    
	
	public static WebDriver getLocalBrowserDriver(String browserName) {
	
		System.out.println(">>>> getLocalBrowserDirver: Browser Name: " + browserName);
		
		
		BrowserType browserType = null;
		
		if ( browserName.equalsIgnoreCase("firefox") ) {
			browserType = BrowserType.FIREFOX;
		}
		else if ( browserName.equalsIgnoreCase("ie") ) {
			browserType = BrowserType.IE;
		}
		else if ( browserName.equalsIgnoreCase("chrome") ) {
			browserType = BrowserType.CHROME;
		}
		else if ( browserName.equalsIgnoreCase("safari") ) {
			browserType = BrowserType.SAFARI;
		}
		else {
			logger.fatal("Invalid Browser specified: " + browserName );
			throw new RuntimeException("Invalid browser: " + browserName );
		}
		
		return getLocalBrowserDriver( browserType );

	}
	
	
	/**
	 * Creates a new WebDriver instance whose type depends upon the specified browser type.
	 * 
	 * @return
	 */
	public static WebDriver getLocalBrowserDriver(BrowserType browserType) {
		
		WebDriver localDriver = null;
		
        switch (browserType) {
        case FIREFOX:
        	localDriver = createFirefoxDriver();
            break;

        case IE:
        	localDriver = createIeDriver();
             break;

        case CHROME:
        	localDriver = createChromeDriver();
            break;

       default:   
    	   // Should throw an error.
    	   localDriver = null;
    	   
            //break;
        }

        return localDriver;
		
	}
	
	
	
	protected static WebDriver createFirefoxDriver() {

        DesiredCapabilities dc = DesiredCapabilities.firefox();
        dc.setJavascriptEnabled(true);
        
        return new FirefoxDriver(dc);
	}

	
	
	
	protected static WebDriver createIeDriver() {
		DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
        dc.setJavascriptEnabled(true);

        return new InternetExplorerDriver(dc);
	}
	

	protected static WebDriver createChromeDriver() {

        DesiredCapabilities dc = DesiredCapabilities.chrome();
        dc.setJavascriptEnabled(true);

        return new ChromeDriver(dc);
	}
	
	
	protected static WebDriver createSafariBrowser() {
        DesiredCapabilities dc = DesiredCapabilities.chrome();
        dc.setJavascriptEnabled(true);
		return new SafariDriver();
	}
	
	
	

	
	
	
//
// This section is to give some extra configurations for Firefox.
// Note: Placed at the bottom of the source file to conceptually put it out of the way.
//
	
		
	/**
	 * Get an instance of the Firefox driver with extensions enabled.  The extensions are usually
	 * disabled, to avoid conflicts with the drivers.
	 * @return
	 */
	protected WebDriver createFirefoxDriverWithExtensions() {
		
        FirefoxProfile ffProfile = new FirefoxProfile();
        addFirefoxExtensions(getFirefoxExtensionPath(), ffProfile);

        ffProfile.setEnableNativeEvents(true);
        ffProfile.setAcceptUntrustedCertificates(true);
        
        // TODO: Testing download box.
        String ffDownloadDir = System.getProperty("qa.firefox.browser.download.dir");
        String ffFolderList = System.getProperty("qa.firefox.browser.download.folderList");
        String ffCloseWhenDone = System.getProperty("qa.firefox.browser.download.manager.closeWhenDone");                
        String ffNeverAskType = System.getProperty("qa.firefox.browser.helperApps.neverAsk.saveToDisk");
        
        if ( ffDownloadDir != null ) {
        	System.out.println( ffDownloadDir );
        	ffProfile.setPreference("browser.download.dir", ffDownloadDir);  
        }
        
        if ( ffFolderList != null ) {
        	System.out.println( ffFolderList );
        	ffProfile.setPreference("browser.download.folderList", Integer.parseInt(ffFolderList));
        }
        
        if ( ffCloseWhenDone != null ) {
        	System.out.println( ffCloseWhenDone );
        	ffProfile.setPreference("browser.download.manager.closeWhenDone", ffCloseWhenDone.equalsIgnoreCase("true")? true : false);                
        }
        
        if ( ffNeverAskType != null ) {
        	System.out.println( ffNeverAskType );
        	ffProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",ffNeverAskType);
        }

//        
//        String downloadDir = "C:\\opt\\familysearch-qa\\idx\\gedxBuilder\\downloads";
//        //String downloadDir = "/opt/familysearch-qa/idx/gedxBuilder/downloads";
//        
//        System.out.println("Should be saving to: " + downloadDir );
//        
//        ffProfile.setPreference("browser.download.dir", downloadDir);  
//        ffProfile.setPreference("browser.download.folderList", 2);
//        ffProfile.setPreference("browser.download.manager.closeWhenDone", true);                
//        ffProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/xml");
//        
        
        // End of download box stuff.
        
        DesiredCapabilities dc = DesiredCapabilities.firefox();
        dc.setCapability(FirefoxDriver.PROFILE, ffProfile);
        dc.setJavascriptEnabled(true);
        
        return new FirefoxDriver(dc);
	}
	
	
	
	
	// TODO: Might not need this.
    private static String getFirefoxExtensionPath() {
        return "./FirefoxExtensions";
    }
    /** Loads Firefox extensions/add-ons from the specified <code>extensionPath</code>.
    *
    * When called <code>extensionPath</code> should point to a folder containing Firefox extensions.
    * The method searches for all files in the specified path that end in '.xpi' and loads
    * them into the specified Firefox <code>profile</code>.
    *
    * @param extensionPath path
    * @param profile profile
    */
    
   private static void addFirefoxExtensions(String extensionPath, FirefoxProfile profile) {
       if (extensionPath != null && extensionPath.length() > 0) {
           File folder = new File(extensionPath);

           if (folder.exists()) {
               for (File file : folder.listFiles()) {
                   //Assumes file name of extension is in the form:
                   // <name>-<ver>[-<extra>].xpi
                   String fName = file.getName().toLowerCase();

                   if (file.isFile() && fName.indexOf(".xpi") != -1) {
                       try {
                           profile.addExtension(file);

                           //strip off extension for the case with no '-<extra>'
                           fName = fName.substring(0, fName.indexOf(".xpi"));
                           //get the version part folowing the first '-'
                           String version = fName.split("-")[1];

                           if (fName.indexOf("firebug") != -1) {
                               profile.setPreference("extensions.firebug.currentVersion", version);
                           } else if (fName.indexOf("web_developer") != -1) {
                               profile.setPreference("webdeveloper.version", version);
                           }
                       } catch (IOException ioex) {
                           logger.fatal("[addFirefoxExtensions] Error adding extension: " + file.getName());
                       }
                   }
               }
           }
       }
   }
	
	
	
	
	
}
