/**
 * Represents a table, at a shot in time.  The timeshot can be updated.
 * 
 */

package org.familysearch.products.gallery.testframework.selenium.util;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TableRowFixture {
	
	WebElement rowElement;
	
	public TableRowFixture(WebElement rowElement) {
		this.rowElement = rowElement;
	}
	
	public WebElement element() {
		return rowElement;
	}
	
	List<WebElement> getDisplayedCells() {
		return rowElement.findElements(By.tagName("td"));
	}

	public int size() {
		return getDisplayedCells().size();
	}
			

	public WebElement getCellAt(int columnNumber) {
		// TODO: Check for out of bounds:  <= 0 and > the columnCount
		
		return rowElement.findElement(By.xpath("./td[" + columnNumber + "]"));
	}
	
	String getCellTextAt(int columnNumber) {
		// TODO: Check for out of bounds:  <= 0 and > the columnCount
		
		return rowElement.findElement(By.xpath("./td[" + columnNumber + "]")).getText();
	}
	
	
	public String[] getCellsAsStringArray() {
		
		List<WebElement> cells = getDisplayedCells();
		String[] rowContent = new String[ cells.size() ];
		int i = 0;
		for (WebElement cell : cells) {
			rowContent[i] = cell.getText();
			i++;
		}
		
		return rowContent;
	}
	
	
	
	
	
	// TODO: Decide if the search should be case sensitive.
	public boolean cellTextContains(int columnNum,  String searchText) {
		boolean bContains = false;
		
		WebElement cell = getCellAt( columnNum );
		if ( cell.getText().contains( searchText ) ) {
			bContains = true;
		}
		
		return bContains;
	}
	
	
	// TODO: Decide if the search should be case insensitive.
	public boolean cellTextMatches(int columnNum,  String searchText) {
		boolean bMatches = false;
		
		WebElement cell = getCellAt( columnNum );
		if ( cell.getText().equals( searchText ) ) {
			bMatches = true;
		}
		
		return bMatches;
	}
	
	
	
	// TODO: Decide if the search should be case insensitive.
	public WebElement getCellContainingText(int columnNum, String searchText) {
		WebElement foundCell = null;
		
		List<WebElement> cells = getDisplayedCells();
		for( WebElement cell : cells ) {
			String text = cell.getText();
			if ( text.contains(searchText) ) {
				foundCell = cell;
				break;
			}
		}
			
		// Will return null if no cell is found.
		return foundCell;		
	}

	
	
	// TODO: Decide if the search should be case insensitive.
	public WebElement getColumnMatchingText(int columnNum, String searchText ) {
		WebElement foundCell = null;
		
		List<WebElement> cells = getDisplayedCells();
		for( WebElement cell : cells ) {
			String text = cell.getText();
			if ( text.equals(searchText) ) {
				foundCell = cell;
				break;
			}
		}
			
		// Will return null if no cell is found.
		return foundCell;		
		
	}
	
	
}
