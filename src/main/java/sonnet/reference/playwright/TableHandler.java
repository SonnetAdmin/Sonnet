package sonnet.reference.playwright;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import sonnet.reference.playwright.locators.Target;

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
    private Page page;
    private static TableHandler instance;
    static Table table;

    public static synchronized TableHandler getInstance() {
        if (instance == null) {
            instance = new TableHandler();
        }
        return instance;
    }

    /////////////////////////////////////////////
    // PROTECTED ACCESS - FOR TABLE CLASS ONLY //
    /////////////////////////////////////////////

    protected Table getTable(int index) {
        Locator table = findTable(index);
        return populate(table);
    }

    protected Table getTable(Enums.Index index) {
        Locator table = findTable(index.ordinal());
        return populate(table);
    }

    protected Table getTable(Target target) {
        Locator table = getElement(target);
        return populate(table);
    }

    // Click on demand from Table class
    // Parameters are Java list indexes, not the Table indexes that start from 1
    protected void clickRowColumn(Locator webRow, int column) {
        List<Locator> columns = webRow.locator("td").all();
        columns.get(column).click();
    }

    // Click on demand from Table class
    // Parameters are Java list indexes, not the Table indexes that start from 1
    protected static void clickColumnHeader(Locator webHeader) {
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
    private static synchronized Table populate(Locator webTable) {
        List<String> headers = new ArrayList<>();
        Map<Integer, List<Cell>> grid = new HashMap<>();
        List<Locator> webHeaders;
        List<Locator> webRows;
        List<Cell> cells;
        List<Locator> webCells;
        int tableRowIndex = 0;

        webHeaders = webTable.locator("th").all();
        for(Locator header : webHeaders) {
            headers.add(header.textContent());
        }
        // Add headers to the table object
        table.setHeaders(headers);
        table.setWebHeaders(webHeaders);
        // Loop through the table body
        webRows = webTable.locator("tbody").locator("tr").all();
        for(Locator row : webRows) {
            webCells = row.locator("td").all();
            cells = new ArrayList<>();
            for(Locator webCell : webCells) {
                // Add cell text
                cells.add(new Cell(webCell.textContent()));
            }
            grid.put(tableRowIndex++, cells);
        }
        table.setWebRows(webRows);
        table.setGrid(grid);
        return table;
    }

    private Locator getElement(Target target) {
        Locator element;
        try {
            element = page.locator(target.getSelector());
        } catch (PlaywrightException wde) {
            throw new CPOException("Target not found");
        }
        return element;
    }

    private List<Locator> getColumnHeaders(Locator table) {
        return table.locator("thead").locator("th").all();
    }

    private Locator findTable(int tableIndex) {
        List<Locator> tables = page.locator("table").all();
        if (tables.isEmpty() || tableIndex > tables.size()) {
            throw new CPOException("Table not found");
        }
        return tables.get(tableIndex);
    }
}
