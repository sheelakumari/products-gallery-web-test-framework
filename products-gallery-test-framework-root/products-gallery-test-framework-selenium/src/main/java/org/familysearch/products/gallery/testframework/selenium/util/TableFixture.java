/**
 * Represents a table, at a shot in time.  The timeshot can be updated.
 * 
 */

package org.familysearch.products.gallery.testframework.selenium.util;


import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TableFixture {
	
	WebElement tableElement;
	List <TableRowFixture> tableRows;
	
	public TableFixture(WebElement tableElement) {
		this.tableElement = tableElement;
		initTableRows();
	}
	

	private void initTableRows() {
		this.tableRows = new ArrayList<TableRowFixture>();
		
		List <WebElement> rowElements = tableElement.findElements(By.tagName("tr"));
		for ( WebElement rowElement: rowElements ) {
			tableRows.add( new TableRowFixture( rowElement ) );
		}
	}
	
	/**
	 * Rebuild this object's entire model.  This should be called when the content of the
	 * table has changed.
	 */
	public void refresh() {
		initTableRows();
	}
	
	
	
	public int getRowCount() {
		return tableRows.size();
	}

	/**
	 *  Gets the number of columns.  This assumes that the rows have the same number
	 *  of columns. Only the first number of columns is checked to see how many columns
	 *  it contains. 
	 *  
	 * @return
	 */
	public int getColumnCount() {
		return tableRows.get(0).size();
	}
	
	
	
	
	
	public String[][] getRowsAsStringArray() {

		String[][] contents = new String[tableRows.size()][];
		
		int i = 0;
		for ( TableRowFixture tableRow: tableRows ) {
			contents[i] = tableRow.getCellsAsStringArray();
			i++;
		}
		
		return contents;
	}
	
		

	
	public String[] getColumnAsStringArray(int columnNumber) {
		
		String[] columnText = new String[ tableRows.size() ];
		int i = 0;
		for ( TableRowFixture rowFix: tableRows ) {
			columnText[i] = rowFix.getCellTextAt(columnNumber);
			i++;
		}
		
		return columnText;
	}	
		
	
	public WebElement getCellWhereTextContains(int columnNum, String searchText) {
		WebElement foundElement = null;
		
		for( TableRowFixture rowFix : tableRows ) {
			WebElement currentCell = rowFix.getCellAt(columnNum);
			if ( currentCell.getText().contains(searchText)) {
				foundElement = currentCell;
				break;
			}
		}
		
		return foundElement;
	}
	
	
	public WebElement getCellWhereTextEquals(int columnNum, String searchText) {
		WebElement foundElement = null;
		
		for( TableRowFixture rowFix : tableRows ) {
			WebElement currentCell = rowFix.getCellAt(columnNum);
			if ( currentCell.getText().contains(searchText)) {
				foundElement = currentCell;
				break;
			}
		}
		
		return foundElement;
	}
	
	public TableRowFixture getRowWhereCellTextContains(int columnNum, String searchText) {
		TableRowFixture foundRow = null;
		
		for( TableRowFixture testRow : tableRows ) {
			WebElement currentCell = testRow.getCellAt(columnNum);
			if ( currentCell.getText().contains(searchText)) {
				foundRow = testRow;
				break;
			}
		}
		
		return foundRow;
	}
	
	public TableRowFixture getRowWhereCellTextEquals(int columnNum, String searchText) {
		TableRowFixture foundRow = null;
		
		for( TableRowFixture testRow : tableRows ) {
			WebElement currentCell = testRow.getCellAt(columnNum);
			if ( currentCell.getText().equals(searchText)) {
				foundRow = testRow;
				break;
			}
		}
		
		return foundRow;
	}
	
	
	
	
}
