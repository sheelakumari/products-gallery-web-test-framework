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
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * The main use of this class is to avoid having to use thisElement.findby.
 * Provides findBy.
 * 
 * @author stoddardbd
 *
 */
public class FwBasicItem {		

	protected WebElement thisElement;

	
	
	/**
	 * Constructor for creating an object representing a web page. 
	 * 
	 * @param driver The Selenium WebDriver to be used for driving the web browser.
	 */
	
	protected FwBasicItem(WebElement element) {
		this.thisElement = element;
	}
	
	// NOTE:  There shouldn't be a constructor where a driver is passed in.
	
		
	
	/**
	 * Returns the WebElement that represents this container.  In the case where
	 * this container represents a web page, null will be returned.
	 * 
	 * @return A WebElement representing this container, or null.
	 */
	public WebElement getInstanceElement() {
		return thisElement;
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
		return thisElement.findElement(findElementBy);
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
		
		return thisElement.findElements(findElementsBy);
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
		return thisElement.isDisplayed();
	}
	
	
}
