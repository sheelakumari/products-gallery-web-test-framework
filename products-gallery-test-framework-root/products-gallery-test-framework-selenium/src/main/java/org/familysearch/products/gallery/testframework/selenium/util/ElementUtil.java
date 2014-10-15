package org.familysearch.products.gallery.testframework.selenium.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ElementUtil {

	public static WebElement getBodyAncestor(WebElement element) {
		return element.findElement(By.xpath("ancestor::body"));
	}
	
}
