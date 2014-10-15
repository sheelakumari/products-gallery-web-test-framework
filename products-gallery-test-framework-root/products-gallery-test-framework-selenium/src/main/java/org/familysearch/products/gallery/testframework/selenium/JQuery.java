package org.familysearch.products.gallery.testframework.selenium;



import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JQuery {
	 
	
	public static void waitForJqNotActive(WebDriver driver) {
	    Wait<WebDriver> wait = new WebDriverWait(driver, 60);
	    wait.until(jQueryExists(driver));
	    wait.until(jQueryNotActive(driver));
	  }
	
	public static void waitForPageLoad(WebDriver driver) {
	    Wait<WebDriver> wait = new WebDriverWait(driver, 60);
	    wait.until(isPageLoaded(driver));
	  }
	
	public static ExpectedCondition<Boolean> jQueryExists(WebDriver driver) {
		return new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return((Boolean) ((JavascriptExecutor) driver).executeScript("return (typeof window.jQuery == 'function')"));
			}
		};
	}
	
	public static ExpectedCondition<Boolean> jQueryNotActive(WebDriver driver) {
		return new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return (Boolean) ((JavascriptExecutor) driver).executeScript("return window.jQuery.active == 0");
			}
		};
	}
	
	public static ExpectedCondition<Boolean> isPageLoaded(WebDriver driver) {
		return new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return (Boolean) ((JavascriptExecutor) driver).executeScript("return document.readyState == 'complete'"); //("return ($(document).ready(function() {FS.Controls.init(document);}))");
			}
		};
	}	
}




