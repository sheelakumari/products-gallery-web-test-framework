/**
 * Represents a container object (an object that contains other objects).
 * 
 * The class is meant to be extended by other classes.  The purpose of this class is
 * to combine common code that otherwise would have to be duplicated in subclasses.
 * 
 */
package org.familysearch.products.gallery.testframework.selenium.fw;


import java.util.List;

import org.familysearch.products.gallery.testframework.selenium.JQuery;
import org.familysearch.products.gallery.testframework.selenium.Pause;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class FwBasicComponent {
		
	protected WebDriver driver;
	protected WebElement thisElement;
	protected By findMeBy;
	
	
	// Note:  A container has to have a find by.
	
	
	/**
	 * Constructor for when the search for the container object is to be searched
	 * for relative to the page.  This constructor can be used for finding dialogs
	 * that are represented using divs on a page, or some other container object.
	 * 
	 * @param driver The Selenium WebDriver to be used for driving the web browser.
	 * @param findMeBy The By object that specifies how to find the container element.
	 */
	protected FwBasicComponent(WebDriver driver, By findMeBy) {
		this.driver = driver;
		this.findMeBy = findMeBy;
		this.thisElement = driver.findElement( this.findMeBy );
	}
	
	
	
	
	
	
	
	/**
	 * Finds an element on the page.  This works just like the WebDriver and
	 * WebElement findElement method. If multiple elements are found, the first 
	 * matching element is found.
	 * 
	 * An exception is thrown if a matching element is not found.
	 * 
	 * @param findElementBy A By object that specifies how to find the element in the page.
	 * @return A WebElement representing the found element.
	 */
	public WebElement findElement(By findElementBy) {		
		return getContainerElement().findElement(findElementBy);
	}
	
	
	/**
	 * Gets a list of elements that meet the search criteria. This can return zero or
	 * more elements. This does not throw an exception when no element is found.  Instead,
	 * it returns an empty list.
	 * 
	 * @param findElementsBy The By object that specifies how to find the elements.
	 * @return A list of WebElemets that match the search criteria. 
	 */
	public List <WebElement> findElements(By findElementsBy) {
		return getContainerElement().findElements(findElementsBy);
	}
	
	
	/**
	 * Returns the WebElement that represents this container.  In the case where
	 * this container represents a web page, null will be returned.
	 * 
	 * @return A WebElement representing this container, or null.
	 */
	public WebElement getContainerElement() {
		return thisElement;
	}

	/**
	 * Returns the WebDriver that is being used to drive the browser. This allows code that
	 * needs to get a reference to the current driver to get the driver from any of the 
	 * FwContainers.
	 *  
	 * Note: The driver is being passed around, instead of using a singleton object because
	 * there may be times when you want to be driving more than one browser, so each will
	 * need its own driver.
	 * 
	 * @return The WebDriver that this container is using.
	 */
	public WebDriver getDriver() {
		return driver;
	}	
	

	/** 
	 * Convenience method for pausing.
	 * @param seconds Number of seconds to pause.
	 */
	public static void pause(int seconds) {
		Pause.pause(seconds);
	}
	
	/**
	 * Convenience method for pausing. Can be used in cases where
	 * you need a pause time that is less than a second. One second
	 * is represented by 1000. 
	 * 
	 * @param milliSeconds number of milliseconds to wait.
	 */
	public static void pauseMillis(int milliSeconds) {
		Pause.pauseMillis(milliSeconds);
	}
	
	/**
	 * A method designed to represent when the user wants to 
	 * stop and look at the application.  This can be used during
	 * development of the test so you can visually inspect the objects
	 * more the specified time, before continuing. The idea is that 
	 * there will be some way to specify that the think wait can be
	 * ignored by when the test is being run in an automated test run.
	 * 
	 * @param seconds
	 */
	public static void pauseAndThink(int seconds) {
		Pause.thinkPause(seconds);
	}
	
	public void waitForElement(WebElement webelement){
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		/*WebElement element =*/ wait.until(
		        ExpectedConditions.visibilityOfElementLocated((By) webelement));
	}

	
	
	public void waitForJqNotActive() {
		JQuery.waitForJqNotActive(getDriver());
	}
	
	public void waitForPageLoad() {
		JQuery.waitForPageLoad(getDriver());
	}
	
	
	// I don't think this belongs here.
	public void handleAlertPopup() {
		Alert alert = driver.switchTo().alert();
	    alert.accept();
	}
	
	/**
	 * Does a JQuery wait, but it needs a driver.  This is needed when the
	 * FwContainer has not been instantiated yet. Such as in the subclass'
	 * getInstance method.
	 * 
	 * @param driver
	 */
	public static void waitForJqNotActive(WebDriver driver) {
		JQuery.waitForJqNotActive(driver);
	}

	
	
	/**
	 * Determines if the instanceElement is displayed.  It does not
	 * determine if the contained elements are displayed.
	 * 
	 * Note: This may not be the most useful method. For example if the
	 * application shows each alert with the same dialog div and doesn't 
	 * have a way to separately identify the dialog, you can't tell which
	 * dialog is actually showing. 
	 * 
	 * @return
	 */
	public boolean isDisplayed() {
		return getContainerElement().isDisplayed();
	}
	
	
}
