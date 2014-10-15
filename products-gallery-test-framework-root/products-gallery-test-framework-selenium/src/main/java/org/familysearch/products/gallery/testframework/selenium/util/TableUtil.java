/**
 * Represents a table, at a shot in time.  The timeshot can be updated.
 * 
 */

package org.familysearch.products.gallery.testframework.selenium.util;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TableUtil {
	
	WebElement element;
	String[][] contentAsText;
	
	public TableUtil(WebElement element) {
		this.element = element;
		this.contentAsText = getTableAsStringArray(element);
	}
	
	// Get the new content of the table.  This is in case the table
	// has been updated.  (I don't know for sure show this works with ajax.
	public void refresh() {
		this.contentAsText = getTableAsStringArray(element);
	}
	
	// Only to be used by this object.
	// gets the table content, and stores it as in instance object.
	private String[][] getTableAsStringArray(WebElement element) {
		
		// NOTE: This is assuming that each row has the same number
		// of columns.
		int numRows = 0;
		int numColumns = 0;
			
		// TODO: For now assuming that the table has at least one row and 
		// one column.
		
		// TODO: Might want to make more robust. This might not work
		// correctly with table headers.
		// Note:  This row list will be used later, after the table size 
		// has been calculated.
		List <WebElement> rows = element.findElements(By.tagName("tr"));
		if ( rows.size() > 0 ) {
			numRows = rows.size();
			// Only checking how many columns are in the first row.
			WebElement rowElement = rows.get(0);
			List <WebElement> columns = rowElement.findElements(By.tagName("td"));
			numColumns = columns.size();
		}
		else {
			// There weren't any columns.
			numRows = 0;
			numColumns = 0;
		}

		// Got the row and column count.  Now allocate a String array
		// for the size and move the text content into the array.
				
		String[][] content = new String[numRows][numColumns];

		// Use the previous row list.
		for( int r = 0; r < numRows; r++ ) {
			WebElement curRow = rows.get(r);
			// Get the columns.
			List<WebElement> columns = curRow.findElements(By.tagName("td"));
			// TODO: Don't know how this works with spanned columns.
			for( int c = 0; c < numColumns; c++) {
				content[r][c] = columns.get(c).getText();
			}
		}
		
		return content;
	}
	
	
	// TODO: Should change this to getContentAsStringArray.
	public String[][] getTableAsStringArray() {
		return contentAsText;
	}
	
	public int getRowCount() {
		return contentAsText.length;
	}
	
	public int getColumnCount() {
		return contentAsText[0].length;
	}
	

	
	public String[] getColumnAsStringArray(int columnNum) {
		
		// TODO: Insure that there is no out of bounds exception.
		String[] columnContent = new String[contentAsText.length];
		
		for ( int i = 0; i < contentAsText.length; i++ ) {
			columnContent[i] = contentAsText[i][columnNum];
			
		}
		
		return columnContent;
	}
	
	
	
	// Column entry CONTAINS
	
	public boolean columnEntryContains(int columnNum, String searchText) {
		boolean bContains = false;
		
		// TODO: Watch out for out of bounds exception.
		String[] columnContent = getColumnAsStringArray( columnNum );
		for ( String rowText : columnContent ) {
			if ( rowText.contains( searchText ) ) {
				bContains = true;
				break;
			}
		}
		
		return bContains;
	}
	
		
	public int getColumnEntryContainsCount(int columnNum, String searchText) {
		int numMatches = 0;
		
		String[] columnContent = getColumnAsStringArray( columnNum );
		for ( String rowText : columnContent ) {
			if ( rowText.contains( searchText ) ) {
				numMatches++;
			}
		}
		
		return numMatches;
	}

	
	
	// Column entry EQUALS
	
	public boolean columnEntryEquals(int columnNum, String searchText) {
		boolean bEquals = false;
		
		String[] columnContent = getColumnAsStringArray( columnNum );
		for ( String rowText : columnContent ) {
			if ( rowText.equals( searchText ) ) {
				bEquals = true;
				break;
			}
		}
		
		return bEquals;
	}
	
	
	public int getColumnEntryEqualsCount(int columnNum, String searchText) {
		int numMatches = 0;
		
		String[] columnContent = getColumnAsStringArray( columnNum );
		for ( String rowText : columnContent ) {
			if ( rowText.contains( searchText ) ) {
				numMatches++;
			}
		}
		
		return numMatches;
	}

	
	
}
