/**
 * Represents an entire web page. 
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



public class FwBasicWebPage {
		
	protected WebDriver driver;
	protected final String pageUrl;
	
	
		
	/**
	 * Constructor for creating an object representing a web page. 
	 * 
	 * @param driver The Selenium WebDriver to be used for driving the web browser.
	 */
	
	protected FwBasicWebPage(WebDriver driver) {
		this(driver, null);
	}
	

	protected FwBasicWebPage(WebDriver driver, String pageUrl) {
		this.driver = driver;		
		this.pageUrl = pageUrl;
	}
	
	/**
	 * Returns the page url that was set during construction. This could be null.
	 * 
	 * @return the page url.
	 */
	public String getPageUrl() {
		return pageUrl;
	}
	
	public WebDriver getDriver() {
		return driver;
	}	
	
	
	public String getTitle() {
		return driver.getTitle();
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
		return driver.findElement(findElementBy);
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
		return driver.findElements(findElementsBy);
	}
	
	
	
}
