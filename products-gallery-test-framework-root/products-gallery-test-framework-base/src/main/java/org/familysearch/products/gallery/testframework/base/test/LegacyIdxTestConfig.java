package org.familysearch.products.gallery.testframework.base.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Properties;
import java.util.Set;

//import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;

// TODO: This object isn't as usefull as it used to be since the number of suites using it has
// changed.  Might need to make it more generic.
// 

/**
 * Stores configuration information that can be used to configure a test run.
 * 
 * @author StoddardBD
 * 
 */
public class LegacyIdxTestConfig {
	
	public static Logger logger = org.apache.log4j.Logger.getLogger( LegacyIdxTestConfig.class );
	
	
	// System Properties common to all test suites
	public static final String SYSTEM_PROPERTY_APP_SERVER_PROTOCOL = "qa.app.server.protocol";
	public static final String SYSTEM_PROPERTY_APP_SERVER_ADDRESS = "qa.app.server.address";
	public static final String SYSTEM_PROPERTY_APP_SERVER_PORT = "qa.app.server.port";
	public static final String SYSTEM_PROPERTY_APPLICATION_NAME = "qa.application.context";
	public static final String SYSTEM_PROPERTY_APPLICATION_HOMEPAGE_URL = "qa.application.homepage.url";

	public static final String SYSTEM_PROPERTY_TEST_PROJECT_NAME = "qa.test.project.name";
	public static final String SYSTEM_PROPERTY_BASE_SCREENSHOT_DIR ="qa.base.screenshot.dir";

	public static final String SYSTEM_PROPERTY_ALTERNATE_TEST_DATA_DIRECTORY = "qa.alternate.test.data.directory";

	
	// The testConfig is a singleton.  I don't want users
	// constructing more than one, because the setup time is taken
	// reading files, and the content in the files doesn't change anyway.
	protected static LegacyIdxTestConfig config = null; 

	
	
	// DEFAULT VALUES:  They can be changed with system properties
	// or with a CustomSettings.properties file.
	
	// Application
	private String appServerProtocol;
	private String appServerAddress;
	private String appServerPort;
	private String applicationContext;
	private String applicationHomePageUrl;
	private String testProjectName;
	private String alternateTestDataDirectory;
	
	
	// Screenshots
	private String baseScreenshotDir;
	

	
	// 
	//  And finally, the Constructor
	// 
	
	/**
	 * Creates a test configuration that uses the properties that are specified in the
	 * specified configuration file.
	 * 
	 * @param configFilePath;
	 */
	private LegacyIdxTestConfig(String configFilePath) {
		
		// TODO: Throw an error if this path can't be configured.
			
		if ( configFilePath != null ) {
			// If this file is specified on startup, the system properties in this file will
			// override and other settings. (This does not include customFilePath);
			// TODO: Determine if this should override the custom file path.
			setConfigurationSystemProperties(configFilePath);
			logger.info("Using configuration file: " + configFilePath );
		}
		else {
			logger.error("No configuration file specified.");
			throw new RuntimeException("ERROR: No configuration file specified for TestConfig.");

			// TODO: Determine if there should be a custom resource file.
		}

		
		//
		// Application Host and Application properties.  (Note: not all will be set. It depends on the application.)
		// 
			
		appServerProtocol = System.getProperty(SYSTEM_PROPERTY_APP_SERVER_PROTOCOL);
		appServerAddress = System.getProperty(SYSTEM_PROPERTY_APP_SERVER_ADDRESS);
		appServerPort = System.getProperty(SYSTEM_PROPERTY_APP_SERVER_PORT);
		applicationContext = System.getProperty(SYSTEM_PROPERTY_APPLICATION_NAME);
		applicationHomePageUrl = System.getProperty(SYSTEM_PROPERTY_APPLICATION_HOMEPAGE_URL);

		
		//
		// Test Project Properties
		// 
		
		testProjectName = System.getProperty(SYSTEM_PROPERTY_TEST_PROJECT_NAME);
			
			
		// 
		// Screen Shots
		// 
		
		baseScreenshotDir = System.getProperty(SYSTEM_PROPERTY_BASE_SCREENSHOT_DIR);		
		
		//
		// Alternate data location
		//
		alternateTestDataDirectory = System.getProperty(SYSTEM_PROPERTY_ALTERNATE_TEST_DATA_DIRECTORY);
	
	}
	
	
	
	private boolean setConfigurationSystemProperties(String configFilePath) {
		// TODO: Should not allow customFilePath to be null.
		
		boolean bSuccess = true;
		
		Properties configProps = new Properties();
		try {
			// Will throw an exception if it doesn't exist.
			// But the file not existing is not an error, it
			// just means that the user is not using a custom file.
			logger.info("Loading custom properties from file: " + configFilePath );
			configProps.load(new FileInputStream( configFilePath ));
			
			Set<String> keys = configProps.stringPropertyNames();
			for ( String key : keys ) {
				String propertyValue = configProps.getProperty(key);
				logger.info("Setting " + key + " to " + propertyValue);
				System.setProperty(key, propertyValue);
			}
			logger.info("Custom properties set.");
			bSuccess = true;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("Did not find the configuration file: " + e);
			bSuccess = false;
		} catch (IOException e) {
			logger.error("IOException when reading custom properties file.");
			bSuccess = false;
		}
		
		return bSuccess;
	}
	
	
		
	public static LegacyIdxTestConfig getConfig(String customFilePath) {	
		
		// TODO: Throw an exception or something in customFilePath is null.
		// Don't allow null to be passed in.
	
		// It's a singleton, so if it is already been instanciated, don't do it again.
		if ( config == null ) {
			logger.info("Creating a new test configuration.");
			config = new LegacyIdxTestConfig( customFilePath );
		}
		else
		{
			// It's already been instantiated, so return the current configuration.
			logger.info("Using the existing test configuration.");
		}
		
		return config;
	}
	
	
	
	
	
	public String getAppServerProtocol() {
		return appServerProtocol;
	}
	
	public String getAppServerAddress() {
		return appServerAddress;
	}
	
	public String getAppServerPort() {
		return appServerPort;
	}
	
	public String getApplicationContext() {
		return applicationContext;
	}
	
	public String getApplicationHomePageUrl() {
		return applicationHomePageUrl;
	}
	
	public String getTestProjectName() {
		return testProjectName;
	}
	
	
	public String getBaseScreenshotDir() {
		return baseScreenshotDir;
	}
	
	public String getAlternateTestDataDirectory() {
		return alternateTestDataDirectory;
	}
	
	
	
	
	// Get a System Property.
	/**
	 * Returns the value of the system property associated with the specified key. If no System 
	 * property is associated with the key, a <code>null</code> value is returned.
	 * 
	 * @param key Name of the System property.
	 * 
	 * @return The value associated with the specified system property.  Otherwise, null.
	 */
	public String getConfigurationProperty(String key) {
		return System.getProperty(key);
	}
	
		
}
