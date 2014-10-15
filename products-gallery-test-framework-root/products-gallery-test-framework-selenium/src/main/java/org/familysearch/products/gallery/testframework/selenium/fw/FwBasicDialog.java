/**
 * Represents a dialog element.  Dialogs are not relative to another container, so
 * the only constructor is one that takes a drier, not a  prarent.
 * 
 * The class is meant to be extended by other classes.  The purpose of this class is
 * to combine common code that otherwise would have to be duplicated in subclasses.
 * 
 */
package org.familysearch.products.gallery.testframework.selenium.fw;




import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



public class FwBasicDialog {
		
	protected WebDriver driver;
	protected WebElement dialogElement;
	protected By dialogBy;
	
		
	/**
	 * Constructor for when the search for the container object is to be searched
	 * for relative to the page.  This constructor can be used for finding dialogs
	 * that are represented using divs on a page, or some other container object.
	 * 
	 * @param driver The Selenium WebDriver to be used for driving the web browser.
	 * @param findMeBy The By object that specifies how to find the container element.
	 */
	protected FwBasicDialog(WebDriver driver, By dialogBy) {
		this.driver = driver;
		this.dialogBy = dialogBy;
		this.dialogElement = driver.findElement( this.dialogBy );
	}
	

	/**
	 * Finds an element on the page.  This works just like the WebDriver and
	 * WebElement findElement method. If multiple elements are found, the first 
	 * matching element is found.
	 * 
	 * An exception is thrown if a matching element is not found.
	 * 
	 * @param findBy A By object that specifies how to find the element in the page.
	 * @return A WebElement representing the found element.
	 */
	public WebElement findElement(By findBy) {
		return dialogElement.findElement(findBy);
	}
	
	
	/**
	 * Gets a list of elements that meet the search criteria. This can return zero or
	 * more elements. This does not throw an exception when no element is found.  Instead,
	 * it returns an empty list.
	 * 
	 * @param findBy The By object that specifies how to find the elements.
	 * @return A list of WebElemets that match the search criteria. 
	 */
	public List <WebElement> findElements(By findBy) {
		return dialogElement.findElements(findBy);
	}
	
	
	/**
	 * Returns the WebElement that represents this container.  In the case where
	 * this container represents a web page, null will be returned.
	 * 
	 * @return A WebElement representing this container, or null.
	 */
	public WebElement getInstanceElement() {
		return dialogElement;
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
	 * Determines if the dialog element is displayed.  It does not
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
		return dialogElement.isDisplayed();
	}
	
	
}
