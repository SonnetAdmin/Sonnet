package sonnet.reference.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import sonnet.reference.selenium.locators.Target;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for interacting with HTML tables in UI automation.
 * Provides methods to focus, sort, and extract data from tables.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     Table table = Table.getInstance();
 *     table.tfocus(1, "Status");
 *     table.tsort("Name");
 * </pre>
 */
public class TableHandler {

    /////////////////////////////////////////////
    // PROTECTED ACCESS - FOR TABLE CLASS ONLY //
    /////////////////////////////////////////////

    protected static Table getTable(int index) {
        WebElement table = findTable(index);
        return populate(table);
    }

    protected Table getTable(Enums.Index index) {
        WebElement table = findTable(index.ordinal());
        return populate(table);
    }

    protected static Table getTable(Target target) {
        WebElement table = getElement(target);
        return populate(table);
    }

    // Click on demand from Table class
    // Parameters are Java list indexes, not the Table indexes that start from 1
    protected static void clickRowColumn(WebElement webRow, int column) {
        List<WebElement> columns = webRow.findElements(By.tagName("td"));
        columns.get(column).click();
    }

    // Click on demand from Table class
    // Parameters are Java list indexes, not the Table indexes that start from 1
    protected static void clickColumnHeader(WebElement webHeader) {
        webHeader.click();
    }

    /////////////
    // PRIVATE //
    /////////////

    /**
     * Convert Web Table to text Table
     * @param webTable
     * @return Table
     */
    private static synchronized Table populate(WebElement webTable) {
        List<String> headers = new ArrayList<>();
        Map<Integer, List<Cell>> grid = new HashMap<>();
        List<WebElement> webHeaders;
        List<WebElement> webRows;
        List<Cell> cells;
        List<WebElement> webCells;
        Table table = new Table(false);
        int tableRowIndex = 0;

        webHeaders = webTable.findElements(By.tagName("th"));
        for(WebElement header : webHeaders) {
            headers.add(header.getText());
        }
        // Add headers to the table object
        table.setHeaders(headers);
        table.setWebHeaders(webHeaders);
        // Loop through the table body
        webRows = webTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
        for(WebElement row : webRows) {
            webCells = row.findElements(By.tagName(("td")));
            cells = new ArrayList<>();
            for(WebElement webCell : webCells) {
                // Add cell text
                cells.add(new Cell(webCell.getText()));
            }
            grid.put(tableRowIndex++, cells);
        }
        table.setWebRows(webRows);
        table.setGrid(grid);
        return table;
    }

    private static WebElement getElement(Target target) {
        WebElement element;
        try {
            element = CommonPage.driver.findElement(target.getBy());
        } catch (WebDriverException wde) {
            throw new CPOException("Target not found");
        }
        return element;
    }

    private List<WebElement> getColumnHeaders(WebElement table) {
        return table.findElement(By.tagName("thead")).findElements(By.tagName("th"));
    }

    private static WebElement findTable(int tableIndex) {
        List<WebElement> tables = CommonPage.driver.findElements(By.tagName("table"));
        if (tables.isEmpty() || tableIndex > tables.size()) {
            throw new CPOException("Table not found");
        }
        return tables.get(tableIndex);
    }

}
