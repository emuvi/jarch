package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * A fluent API wrapper for JTable to easily create and configure tables.
 */
public class DTable extends JTable {

    /**
     * Constructs a default JTable that is initialized with a default data model, a default column model, and a default selection model.
     */
    public DTable() {
        super();
    }

    /**
     * Constructs a JTable that is initialized with dm as the data model, a default column model, and a default selection model.
     * 
     * @param dm the data model for the table
     */
    public DTable(TableModel dm) {
        super(dm);
    }

    /**
     * Constructs a JTable that is initialized with dm as the data model, cm as the column model, and a default selection model.
     * 
     * @param dm the data model for the table
     * @param cm the column model for the table
     */
    public DTable(TableModel dm, TableColumnModel cm) {
        super(dm, cm);
    }

    /**
     * Constructs a JTable that is initialized with dm as the data model, cm as the column model, and sm as the selection model.
     * 
     * @param dm the data model for the table
     * @param cm the column model for the table
     * @param sm the row selection model for the table
     */
    public DTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
        super(dm, cm, sm);
    }

    /**
     * Constructs a JTable with numRows and numColumns of empty cells using DefaultTableModel.
     * 
     * @param numRows the number of rows the table holds
     * @param numColumns the number of columns the table holds
     */
    public DTable(int numRows, int numColumns) {
        super(numRows, numColumns);
    }

    /**
     * Constructs a JTable to display the values in the two dimensional array, rowData, with column names, columnNames.
     * 
     * @param rowData the data for the new table
     * @param columnNames names of each column
     */
    public DTable(Object[][] rowData, Object[] columnNames) {
        super(rowData, columnNames);
    }

    /**
     * Constructs a JTable to display the values in the Vector of Vectors, rowData, with column names, columnNames.
     * 
     * @param rowData the data for the new table
     * @param columnNames names of each column
     */
    public DTable(Vector<? extends Vector> rowData, Vector<?> columnNames) {
        super(rowData, columnNames);
    }

    /**
     * Sets the data model for this table to newModel and registers with it for listener notifications from the new data model.
     * 
     * @param dataModel the new data source for this table
     * @return This DTable instance.
     */
    public DTable model(TableModel dataModel) {
        setModel(dataModel);
        return this;
    }

    /**
     * Specifies whether a RowSorter should be created for the table whenever its model changes.
     * 
     * @param autoCreateRowSorter whether or not a RowSorter should be automatically created
     * @return This DTable instance.
     */
    public DTable autoCreateRowSorter(boolean autoCreateRowSorter) {
        setAutoCreateRowSorter(autoCreateRowSorter);
        return this;
    }

    /**
     * Sets whether the table draws grid lines around cells.
     * 
     * @param showGrid true if table grid lines should be drawn
     * @return This DTable instance.
     */
    public DTable showGrid(boolean showGrid) {
        setShowGrid(showGrid);
        return this;
    }

    /**
     * Sets whether the table draws horizontal grid lines.
     * 
     * @param showHorizontalLines true if horizontal grid lines should be drawn
     * @return This DTable instance.
     */
    public DTable showHorizontalLines(boolean showHorizontalLines) {
        setShowHorizontalLines(showHorizontalLines);
        return this;
    }

    /**
     * Sets whether the table draws vertical grid lines.
     * 
     * @param showVerticalLines true if vertical grid lines should be drawn
     * @return This DTable instance.
     */
    public DTable showVerticalLines(boolean showVerticalLines) {
        setShowVerticalLines(showVerticalLines);
        return this;
    }

    /**
     * Sets the color used to draw grid lines to gridColor and redisplays.
     * 
     * @param gridColor the color of the grid lines
     * @return This DTable instance.
     */
    public DTable gridColor(Color gridColor) {
        setGridColor(gridColor);
        return this;
    }

    /**
     * Sets the height, in pixels, of all cells to rowHeight, revalidates, and repaints.
     * 
     * @param rowHeight new row height
     * @return This DTable instance.
     */
    public DTable rowHeight(int rowHeight) {
        setRowHeight(rowHeight);
        return this;
    }

    /**
     * Sets the rowMargin and the columnMargin -- the height and width of the space between cells -- to intercellSpacing.
     * 
     * @param intercellSpacing a Dimension specifying the new width and height between cells
     * @return This DTable instance.
     */
    public DTable intercellSpacing(Dimension intercellSpacing) {
        setIntercellSpacing(intercellSpacing);
        return this;
    }

    /**
     * Sets the table's selection mode to allow only single selections, a single contiguous interval, or multiple intervals.
     * 
     * @param selectionMode the mode used by the row and column selection models
     * @return This DTable instance.
     */
    public DTable selectionMode(int selectionMode) {
        setSelectionMode(selectionMode);
        return this;
    }

    /**
     * Sets whether this table allows both a column selection and a row selection to exist simultaneously.
     * 
     * @param cellSelectionEnabled true if simultaneous row and column selection is allowed
     * @return This DTable instance.
     */
    public DTable cellSelectionEnabled(boolean cellSelectionEnabled) {
        setCellSelectionEnabled(cellSelectionEnabled);
        return this;
    }

    /**
     * Sets whether the columns in this model can be selected.
     * 
     * @param columnSelectionAllowed true if this model allows column selection
     * @return This DTable instance.
     */
    public DTable columnSelectionAllowed(boolean columnSelectionAllowed) {
        setColumnSelectionAllowed(columnSelectionAllowed);
        return this;
    }

    /**
     * Sets whether the rows in this model can be selected.
     * 
     * @param rowSelectionAllowed true if this model allows row selection
     * @return This DTable instance.
     */
    public DTable rowSelectionAllowed(boolean rowSelectionAllowed) {
        setRowSelectionAllowed(rowSelectionAllowed);
        return this;
    }

    /**
     * Sets the table's auto resize mode when the table is resized.
     * 
     * @param mode One of 5 legal values: AUTO_RESIZE_OFF, AUTO_RESIZE_NEXT_COLUMN, AUTO_RESIZE_SUBSEQUENT_COLUMNS, AUTO_RESIZE_LAST_COLUMN, AUTO_RESIZE_ALL_COLUMNS
     * @return This DTable instance.
     */
    public DTable autoResizeMode(int mode) {
        setAutoResizeMode(mode);
        return this;
    }

    /**
     * Sets whether the table is always made large enough to fill the height of an enclosing viewport.
     * 
     * @param fillsViewportHeight whether or not the table is always made large enough to fill the height of an enclosing viewport
     * @return This DTable instance.
     */
    public DTable fillsViewportHeight(boolean fillsViewportHeight) {
        setFillsViewportHeight(fillsViewportHeight);
        return this;
    }

    /**
     * Sets the preferred size of the viewport for this table.
     * 
     * @param size a Dimension object specifying the preferredSize of a JViewport whose view is this table
     * @return This DTable instance.
     */
    public DTable preferredScrollableViewportSize(Dimension size) {
        setPreferredScrollableViewportSize(size);
        return this;
    }

    /**
     * Sets the font for the table.
     * 
     * @param font the new Font
     * @return This DTable instance.
     */
    public DTable font(Font font) {
        setFont(font);
        return this;
    }

    /**
     * Sets the foreground color of the table.
     * 
     * @param fg the new foreground color
     * @return This DTable instance.
     */
    public DTable foreground(Color fg) {
        setForeground(fg);
        return this;
    }

    /**
     * Sets the background color of the table.
     * 
     * @param bg the new background color
     * @return This DTable instance.
     */
    public DTable background(Color bg) {
        setBackground(bg);
        return this;
    }

    /**
     * Sets the foreground color for selected cells.
     * 
     * @param selectionForeground the Color to use in the foreground for selected list items
     * @return This DTable instance.
     */
    public DTable selectionForeground(Color selectionForeground) {
        setSelectionForeground(selectionForeground);
        return this;
    }

    /**
     * Sets the background color for selected cells.
     * 
     * @param selectionBackground the Color to use for the background of selected cells
     * @return This DTable instance.
     */
    public DTable selectionBackground(Color selectionBackground) {
        setSelectionBackground(selectionBackground);
        return this;
    }
}
