package org.familysearch.products.gallery.testframework.selenium;

import org.openqa.selenium.WebDriver;

public class Waiter {

	private WebDriver driver;
	
	public Waiter(WebDriver driver) {
		this.driver = driver;	
	}
	
	
	public void jqWait() {
		JQuery.waitForJqNotActive(driver);		
	}
	
	
}
