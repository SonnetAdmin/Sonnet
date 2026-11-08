package sonnet.reference.selenium;


import org.openqa.selenium.WebElement;
import sonnet.reference.selenium.locators.Target;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sonnet.reference.selenium.TableHandler.*;
import static sonnet.reference.selenium.utils.Core.*;


/**
 * The Table class creates a text-based grid representation of a HTML table.
 * Where hyperlinks are present, these will also be copied into the grid.<br>
 * All indexes start at 1 or FIRST.<br>
 * Values below this, or above the x/y table grid size, will result in an exception<br>
 * All methods will throw an exception if referenced table cells are
 * outside the table grid boundaries.
 */
public class Table {

    private List<String> headers = new ArrayList<>();
    private Map<Integer, List<Cell>> grid = new HashMap<>();
    private List<WebElement> webRows;
    private List<WebElement> webHeaders;

    private enum Locator {
        DEFAULT,
        INDEX_NUM,
        INDEX_INDEX,
        TARGET;
    }

    // How was this Table created ?  Used for reload();
    private Locator locator;
    private int indexNum;
    private Enums.Index indexIndex;
    private Target tableTarget;

    /**
     * Constructor creates new Table from the first table.  The targeted table will be the first, or only, table
     * present on the HTML page.
     * <br>e.g.<br>
     * <code>Table table = new Table()</code>
     */
    public Table() {
        Table table = getTable(0);
        locator = Locator.DEFAULT;
        copy(table);
    }

    /**
     * Constructor creates new Table using index.  The targeted table will be the one indexed.
     * Use when there are multiple tables on the page or when other
     * tables cannot be located by a specific Target
     *
     * @param index The index, from 1, of the required table.
     *              <br>e.g.<br> Table(2) for the second table on the page.
     */
    public Table(int index) {
        throwOnZeroOrMinus(index);
        Table table = getTable(index - 1);
        locator = Locator.INDEX_NUM;
        indexNum = index;
        copy(table);
    }

    /**
     * Constructor creates new Table using Index.  The targeted table will be the one indexed.
     * Use when there are multiple tables on the page or when other
     * tables cannot be located by a specific Target
     *
     * @param index The index, expressed as Enums.Index from FIRST, of the required table
     *              <br>e.g.<br> table(SECOND) for the second table on the page.
     */

    public Table(Enums.Index index) {
        Table table = getTable(index.ordinal());
        locator = Locator.INDEX_INDEX;
        indexIndex = index;
        copy(table);
    }

    /**
     * Constructor creates new Table from a Target.
     *
     * @param target The table Target.
     *               <br>e.g.<br>
     *               <code>Table table = new Table(id("myTable"))</code>
     */
    public Table(Target target) {
        Table table = getTable(target);
        locator = Locator.TARGET;
        tableTarget = target;
        copy(table);
    }

    /**
     * Internal use only
     *
     * @param populateNo
     */
    protected Table(boolean populateNo) {
        // This will not populate the table regardless of the value of the populate parameter.
        // It is a constructor overload that enables TableHandler to construct a Table object.
        // Instantiated Tables are created with the other constructors.
    }

    /**
     * Get number of rows in the table.
     *
     * <br>e.g.<br>
     * assertTrue( table.getRows().size == EXPECTED_SIZE );
     *
     * @return The number of rows
     */
    public int getRows() {
        return grid.size();
    }

    /**
     * Get number of columns in the table.
     * <br>e.g.<br>
     * <code>assertTrue( table.getColumns().size == EXPECTED_SIZE );</code>
     *
     * @return The number of columns
     */
    public int getColumns() {
        return headers.size();
    }

    /**
     * Get row at index
     *
     * @param row index.  Must be >= 1
     * @return The list of elements in the row
     * <br>e.g.<br>
     * <code>List String rows = table.getRow(1)<br>
     * assertEquals( rows.get(1), "Expected Contents" );</code>
     * <br>See also<br>
     * {@link #getCell(int, int) getCell()}
     */
    public List<String> getRow(int row) {
        throwOnZeroOrMinus(row);
        List<Cell> cells = null;
        try {
            cells = grid.get(row - 1);
        } catch (Exception exception) {
            fling("Row does not exist");
        }
        return cellsToStringArray(cells);
    }

    /**
     * Get row at Index
     *
     * @param row Index.
     * @return The list of elements in the row
     * <br>e.g.<br>
     * <code>List String rows = table.getRow(FIRST);<br>
     * assertEquals( rows.get(1), "Expected Contents" );</code>
     * <br>See also<br>
     * {@link #getCell(int, int) getCell()}
     */
    public List<String> getRow(Enums.Index row) {
        List<Cell> cells = grid.get(row.ordinal());
        return cellsToStringArray(cells);
    }

    /**
     * Get column at index
     *
     * @param column index.
     * @return The list of elements in the column
     * <br>e.g.<br>
     * <code>List String columns = table.getColumn(1);<br>
     * assertEquals( columns.get(1), "Expected Contents" );</code>
     * <br>See also<br>
     * {@link #getCell(int, int) getCell()}
     */
    public List<String> getColumn(int column) {
        throwOnZeroOrMinus(column);
        return getCol(column - 1);
    }

    /**
     * Get column at Index
     *
     * @param column Index.
     * @return The list of elements in the column
     * <br>e.g.<br>
     * <code>
     * List String columns = table.getColumn(FIRST);<br>
     * assertEquals( columns.get(1), "Expected Contents" );
     * </code>
     * <br>See also<br>
     * {@link #getCell(int, int) getCell()}
     */
    public List<String> getColumn(Enums.Index column) {
        return getCol(column.ordinal());
    }

    /**
     * Get column by header
     *
     * @param header
     * @return The list of elements in the column for the given header. Does not include the header.
     * <br>e.g.<br>
     * <code>List String  columns = table.getColumn("Column Header");<br>
     * assertEquals( columns.get(1), "Expected Contents" );</code><br>
     * <br>See also<br>
     * {@link #getCell(int, int) getCell()}
     */
    public List<String> getColumn(String header) {
        throwOnNullOrEmpty(header);
        return getCol((header));
    }

    ///////////////////
    // GET CELL TEXT //
    ///////////////////

    /**
     * Get cell text at row / column axis
     *
     * @param row    The row index
     * @param column The column index
     * @return The cell contents
     * <br>e.g<br>
     * <code>assertEquals( getCell(2,2), "Expected Cell Content");</code>
     */
    public String getCell(int row, int column) {
        throwOnZeroOrMinus(row, column);
        return cell(row - 1, column - 1);
    }

    /**
     * Get cell text at row / column axis
     *
     * @param row
     * @param column
     * @return The cell contents
     * <br>e.g<br>
     * <code>assertEquals( getCell(2,SECOND), "Expected Cell Content");</code>
*/
    public String getCell(int row, Enums.Index column) {
        throwOnZeroOrMinus(row);
        return cell(row - 1, column.ordinal());
    }

    /**
     * Get cell text at row / column axis
     *
     * @param row
     * @param column
     * @return The cell contents
     * <br>e.g<br>
     * <code>assertEquals(getCell(SECOND, 2), "Expected Cell Content");</code>
     */
    public String getCell(Enums.Index row, int column) {
        throwOnZeroOrMinus(column);
        return cell(row.ordinal(), column - 1);
    }

    /**
     * Get cell text at row / column axis
     *
     * @param row
     * @param column
     * @return The cell contents
     * <br>e.g<br>
     * <code>assertEquals(getCell(SECOND, SECOND), "Expected Cell Content");</code>
     */
    public String getCell(Enums.Index row, Enums.Index column) {
        return cell(row.ordinal(), column.ordinal());
    }


    /**
     * Get cell text at row / column axis where column is a string
     *
     * @param row
     * @param column
     * @return The cell contents
     * <br>e.g<br>
     * <code>assertEquals( getCell(2,"Totals"), "Expected Cell Content");</code>
     */
    public String getCell(int row, String column) {
        throwOnZeroOrMinus(row);
        throwOnNullOrEmpty(column);
        return cell(row - 1, getColumnIndex(column));
    }

    /**
     * Get cell text at row / column axis  where column is a string
     *
     * @param row
     * @param column
     * @return The cell contents
     * <br>e.g<br>
     * <code>assertEquals( getCell(FIRST,"Totals"), "Expected Cell Content");</code>
     */
    public String getCell(Enums.Index row, String column) {
        throwOnNullOrEmpty(column);
        return cell(row.ordinal(), getColumnIndex(column));
    }

    ////////////////
    // CLICK CELL //
    ////////////////

    /**
     * Click cell text at row / column axis
     *
     * @param row
     * @param column <br>e.g<br>
     *               <code>table.clickCell(2, 2);br>
     *               focus(tagWithText(TITLE, "The hyperlinked page title"));br></code>
     * @throws CPOException if the cell does not contain a hyperlink
     */
    public void clickCell(int row, int column) {
        throwOnZeroOrMinus(row, column);
        clickRowColumn(webRows.get(row-1), column-1);
    }

    /**
     * Click cell text at row / column axis
     *
     * @param row
     * @param column <br>e.g<br>
     *               <code>table.clickCell(2, SECOND);br>
     *               focus(tagWithText(TITLE, "The hyperlinked page title"));br></code>
     * @throws CPOException if the cell does not contain a hyperlink
     */
    public void clickCell(int row, Enums.Index column) {
        throwOnZeroOrMinus(row);
        clickRowColumn(webRows.get(row-1), column.ordinal());
    }

    /**
     * Click cell text at row / column axis
     *
     * @param row
     * @param column <br>e.g<br>
     *               <code>table.clickCell(SECOND, 2);br>
     *               focus(tagWithText(TITLE, "The hyperlinked page title"));br></code>
     * @throws CPOException if the cell does not contain a hyperlink
     */
    public void clickCell(Enums.Index row, int column) {
        throwOnZeroOrMinus(column);
        clickRowColumn(webRows.get(row.ordinal()), column-1);
    }

    /**
     * Click cell text at row / column axis
     *
     * @param row
     * @param column <br>e.g<br>
     *               <code>table.clickCell(SECOND, SECOND);br>
     *               focus(tagWithText(TITLE, "The hyperlinked page title"));br></code>
     * @throws CPOException if the cell does not contain a hyperlink
     */
    public void clickCell(Enums.Index row, Enums.Index column) {
        clickRowColumn(webRows.get(row.ordinal()), column.ordinal());    }

    /**
     * Get header at index
     *
     * @param headerIndex
     * @return The header as a string
     * <br>e.g<br>
     * <code>assertEquals( getHeader(2), "Totals");</code>
     */
    public String getHeader(int headerIndex) {
        throwOnZeroOrMinus(headerIndex);
        if (headerIndex > headers.size()) {
            fling("Header does not exist");
        }
        return headers.get(headerIndex - 1);
    }

    /**
     * Get header index
     *
     * @param columnHeader
     * @return The header index from 1
     * <br>e.g<br>
     * <code>assertEquals( 3, getHeader("Totals"));</code>
     */
    public int getHeader(String columnHeader) {
        throwOnNullOrEmpty(columnHeader);
        int index = getHeaders().indexOf(columnHeader);
        if(index < 0) {
            fling("Column does not exist");
        }
        return index+1;
    }

    //////////////////
    // CLICK HEADER //
    //////////////////

    /**
     * Click header at index
     *
     * @param headerIndex
     * <br>e.g<br>
     * <code>clickHeader(1)</code>
     */
    public void clickHeader(int headerIndex) {
        throwOnZeroOrMinus(headerIndex);
        if (headerIndex > headers.size()) {
            fling("Header does not exist");
        }
        clickColumnHeader(webHeaders.get(headerIndex));
    }

    /**
     * Click header at Index
     *
     * @param headerIndex
     * @return The header as a string
     * <br>e.g<br>
     * <code>assertEquals( getHeader(THIRD), "Totals");</code>
     */
    public void clickHeader(Enums.Index headerIndex) {
        if (headerIndex.ordinal()+1 > headers.size()) {
            fling("Header does not exist");
        }
        clickColumnHeader(webHeaders.get(headerIndex.ordinal()));
    }

    /**
     * Click header identified by text
     *
     * @param columnHeader
     * @return The header as a string
     * <br>e.g<br>
     * <code>clickHeader("Totals");</code>
     */
    public void clickHeader(String columnHeader) {
        throwOnNullOrEmpty(columnHeader);
        int index = getHeaders().indexOf(columnHeader);
        if(index < 0) {
            fling("Header does not exist");
        }
        clickColumnHeader(webHeaders.get(index));
    }

    /**
     * Get header index
     *
     * @param columnHeader
     * @return The header index from 1
     * <br>e.g<br>
     * <code>assertEquals( 3, getHeader("Totals"));</code>
     */
    public int getHeaderIndex(String columnHeader) {
        throwOnNullOrEmpty(columnHeader);
        int index = getHeaders().indexOf(columnHeader);
        if(index < 0) {
            fling("Column does not exist");
        }
        return index+1;
    }

    /**
     * Get header at Index
     *
     * @param headerIndex
     * @return The header as a string
     * <br>e.g<br>
     * <code>assertEquals( getHeader(THIRD), "Totals");</code>
     */
    public String getHeader(Enums.Index headerIndex) {
        if (headerIndex.ordinal()+1 > headers.size()) {
            fling("Header does not exist");
        }
        return headers.get(headerIndex.ordinal());
    }



    /**
     * Get table headers
     *
     * @return The headers as a List
     * <br>e.g<br>
     * <code>List String headers = getHeaders()<br>
     * assertEquals( headers().get(3) "Month");</code>
     */
    public List<String> getHeaders() {
        return headers;
    }

    /**
     * Return the HTML table as a grid
     *
     * @return The table grid
     * <br>e.g<br>
     * <code>Map<Integer, List<String>> grid = getGrid()</code><br>
     */
    // Convert Map<Integer, List<Cell>> to Map<Integer, List<String>>
    public Map<Integer, List<String>> getGrid() {
        Map<Integer, List<String>> textGrid = new HashMap<>();
        // Each row is <Integer, List<Cell>
        for (Map.Entry<Integer, List<Cell>> row : grid.entrySet()) {
            List<String> cellsText = new ArrayList<>();
            for (Cell cell : row.getValue()) {
                cellsText.add(cell.getText());
            }
            textGrid.put(row.getKey(), cellsText);
        }
        return textGrid;
    }

    /**
     * Internal use
     */
    protected void setHeaders(List<String> headers) {
        this.headers = headers;
    }
    public void setWebRows(List<WebElement> webRows) {
        this.webRows = webRows;
    }

    public void setWebHeaders(List<WebElement> webHeaders) {
        this.webHeaders = webHeaders;
    }

    /**
     * Internal use
     */
    protected void setGrid(Map<Integer, List<Cell>> grid) {
        this.grid = grid;
    }

    /**
     * Reload the table from the browser.
     * <p>
     * For example following a column sort action.
     */
    public void reload() {
        clear();
        switch (locator) {
            case DEFAULT -> copy(getTable(0));
            case INDEX_NUM -> copy(getTable(indexNum));
            case INDEX_INDEX -> copy(getTable(indexIndex.ordinal()));
            case TARGET -> copy(getTable(tableTarget));
        }
    }

    public void clear() {
        this.webRows = null;
        this.webHeaders = null;
        this.grid = null;
        this.headers = null;
    }

    private void copy(Table table) {
        this.headers = table.headers;
        this.grid = table.grid;
        this.webHeaders = table.webHeaders;
        this.webRows = table.webRows;
    }


    private List<String> getCol(int index) {
        List<String> cols = new ArrayList<>();
        // Each row is <Integer, List<Cell>
        for (Map.Entry<Integer, List<Cell>> row : grid.entrySet()) {
            cols.add(row.getValue().get(index).getText());
        }
        return cols;
    }

    private List<String> getCol(String header) {
        return getCol(getColumnIndex(header));
    }

    private int getColumnIndex(String header) {
        int index = headers.indexOf(header);
        if (index < 0) {
           fling("Header does not exist");
        }
        return index;
    }

    private String cell(int row, int column) {
        String text = null;
        try {
            text = grid.get(row).get(column).getText();
        } catch (NullPointerException | IndexOutOfBoundsException exception) {
            fling("Cell does not exist");
        }
        return text;
    }

    private List<String> cellsToStringArray(List<Cell> cells) {
        List<String> cellArray = new ArrayList<>();
        for (Cell cell : cells) {
            cellArray.add(cell.getText());
        }
        return cellArray;
    }
}
