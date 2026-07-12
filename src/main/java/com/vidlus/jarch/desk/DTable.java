package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Path;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.AbstractCellEditor;

/**
 * A specialized Swing {@link JTable} designed for rapid and fluent configuration.
 * It wraps many standard JTable properties into a fluent API (returning {@code this}) to enable 
 * method chaining during component initialization.
 * <p>
 * Additionally, it provides high-utility custom features such as:
 * <ul>
 *   <li>Native striped rows (alternating background colors)</li>
 *   <li>Global cell editability toggling</li>
 *   <li>Convenient row and column width selections</li>
 *   <li>Header visibility management</li>
 * </ul>
 */
public class DTable extends JTable {

    /** Flag to enable or disable striped rows (alternate background colors). */
    private boolean striped = false;

    /** The background color used for the alternate striped rows. */
    private Color stripeColor = new Color(245, 245, 245);

    /** Global flag to enable or disable editing for the entire table. */
    private boolean globalEditable = true;

    /** Set of specific column indices that are exclusively allowed to be edited. If null, delegates to the model. */
    private Set<Integer> editableColumns = null;

    /** Set of specific row indices that are exclusively allowed to be edited. If null, delegates to the model. */
    private Set<Integer> editableRows = null;

    /** Set of specific cell coordinates (x=column, y=row) that are exclusively allowed to be edited. If null, delegates to the model. */
    private Set<Point> editableCells = null;

    /** Map of specific row indices to their exclusively assigned TableCellEditors. */
    private Map<Integer, TableCellEditor> rowEditors = null;

    /** Map of specific cell coordinates (x=column, y=row) to their exclusively assigned TableCellEditors. */
    private Map<Point, TableCellEditor> cellEditors = null;

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

    /**
     * Toggles whether the table displays striped rows (alternate row background colors).
     * 
     * @param striped true to enable striped rows, false otherwise
     * @return this DTable instance for fluent method chaining.
     */
    public DTable striped(boolean striped) {
        this.striped = striped;
        repaint();
        return this;
    }

    /**
     * Sets the background color used for the alternate striped rows.
     * 
     * @param stripeColor the Color for the alternate rows
     * @return this DTable instance for fluent method chaining.
     */
    public DTable stripeColor(Color stripeColor) {
        this.stripeColor = stripeColor;
        if (this.striped) repaint();
        return this;
    }

    /**
     * Globally enables or disables editing for all cells in the table, overriding the model's behavior.
     * 
     * @param editable true to allow editing (defers to the model), false to disable all editing
     * @return this DTable instance for fluent method chaining.
     */
    public DTable editable(boolean editable) {
        this.globalEditable = editable;
        return this;
    }

    /**
     * Sets the preferred width for a specific column index.
     * 
     * @param columnIndex the index of the column
     * @param width the preferred width in pixels
     * @return this DTable instance for fluent method chaining.
     */
    public DTable columnWidth(int columnIndex, int width) {
        if (columnIndex >= 0 && columnIndex < getColumnModel().getColumnCount()) {
            getColumnModel().getColumn(columnIndex).setPreferredWidth(width);
        }
        return this;
    }

    /**
     * Selects a single row programmatically.
     * 
     * @param row the index of the row to select
     * @return this DTable instance for fluent method chaining.
     */
    public DTable selectRow(int row) {
        if (row >= 0 && row < getRowCount()) {
            setRowSelectionInterval(row, row);
        }
        return this;
    }

    /**
     * Clears any active selection in the table.
     * 
     * @return this DTable instance for fluent method chaining.
     */
    public DTable clearTableSelection() {
        super.clearSelection();
        return this;
    }

    /**
     * Hides the table header completely by setting it to null.
     * 
     * @return this DTable instance for fluent method chaining.
     */
    public DTable hideHeader() {
        setTableHeader(null);
        return this;
    }

    /**
     * Sets whether the user can drag and drop columns in the table header to reorder them.
     * 
     * @param allowed true to allow reordering, false to prevent it
     * @return this DTable instance for fluent method chaining.
     */
    public DTable columnReorderingAllowed(boolean allowed) {
        if (getTableHeader() != null) {
            getTableHeader().setReorderingAllowed(allowed);
        }
        return this;
    }

    /**
     * Sets the dragEnabled property, which must be true to enable automatic drag handling.
     * 
     * @param b whether or not to enable automatic drag handling
     * @return this DTable instance for fluent method chaining.
     */
    public DTable dragEnabled(boolean b) {
        setDragEnabled(b);
        return this;
    }

    /**
     * Specifies whether the selection should be updated after sorting.
     * 
     * @param update true if the selection should be updated, false otherwise
     * @return this DTable instance for fluent method chaining.
     */
    public DTable updateSelectionOnSort(boolean update) {
        setUpdateSelectionOnSort(update);
        return this;
    }

    /**
     * Sets the cell editor for a specific column using a standard TableCellEditor.
     * 
     * @param columnIndex the index of the column
     * @param editor the TableCellEditor to use
     * @return this DTable instance for fluent method chaining.
     */
    public DTable columnEditor(int columnIndex, TableCellEditor editor) {
        if (columnIndex >= 0 && columnIndex < getColumnModel().getColumnCount()) {
            getColumnModel().getColumn(columnIndex).setCellEditor(editor);
        }
        return this;
    }

    /**
     * Sets the cell editor for a specific column dynamically using any custom DEdit component.
     * This automatically bridges the DEdit's value flow and action events into the JTable.
     * 
     * @param columnIndex the index of the column
     * @param editor the DEdit component to adapt and use as the cell editor
     * @return this DTable instance for fluent method chaining.
     */
    public DTable columnEditor(int columnIndex, DEdit<?> editor) {
        if (columnIndex >= 0 && columnIndex < getColumnModel().getColumnCount()) {
            getColumnModel().getColumn(columnIndex).setCellEditor(new DEditCellEditorAdapter(editor));
        }
        return this;
    }

    /**
     * Sets the cell editor for an entire specific row using a standard TableCellEditor.
     * 
     * @param row the index of the row
     * @param editor the TableCellEditor to use
     * @return this DTable instance for fluent method chaining.
     */
    public DTable rowEditor(int row, TableCellEditor editor) {
        if (this.rowEditors == null) {
            this.rowEditors = new HashMap<>();
        }
        this.rowEditors.put(row, editor);
        return this;
    }

    /**
     * Sets the cell editor for an entire specific row dynamically using any custom DEdit component.
     * This automatically bridges the DEdit's value flow and action events into the JTable.
     * 
     * @param row the index of the row
     * @param editor the DEdit component to adapt and use as the cell editor
     * @return this DTable instance for fluent method chaining.
     */
    public DTable rowEditor(int row, DEdit<?> editor) {
        return rowEditor(row, new DEditCellEditorAdapter(editor));
    }

    /**
     * Sets the cell editor for a specific cell using a standard TableCellEditor.
     * 
     * @param row the index of the row
     * @param column the index of the column
     * @param editor the TableCellEditor to use
     * @return this DTable instance for fluent method chaining.
     */
    public DTable cellEditor(int row, int column, TableCellEditor editor) {
        if (this.cellEditors == null) {
            this.cellEditors = new HashMap<>();
        }
        this.cellEditors.put(new Point(column, row), editor);
        return this;
    }

    /**
     * Sets the cell editor for a specific cell dynamically using any custom DEdit component.
     * This automatically bridges the DEdit's value flow and action events into the JTable.
     * 
     * @param row the index of the row
     * @param column the index of the column
     * @param editor the DEdit component to adapt and use as the cell editor
     * @return this DTable instance for fluent method chaining.
     */
    public DTable cellEditor(int row, int column, DEdit<?> editor) {
        return cellEditor(row, column, new DEditCellEditorAdapter(editor));
    }

    /**
     * Specifies exactly which column indices are allowed to be edited.
     * If called, ONLY these specified columns can be edited, overriding the table model.
     * 
     * @param columns the column indices that can be edited
     * @return this DTable instance for fluent method chaining.
     */
    public DTable editableColumns(int... columns) {
        if (this.editableColumns == null) {
            this.editableColumns = new HashSet<>();
        } else {
            this.editableColumns.clear();
        }
        for (int col : columns) {
            this.editableColumns.add(col);
        }
        return this;
    }

    /**
     * Specifies exactly which row indices are allowed to be edited.
     * If called, ONLY these specified rows can be edited, overriding the table model.
     * 
     * @param rows the row indices that can be edited
     * @return this DTable instance for fluent method chaining.
     */
    public DTable editableRows(int... rows) {
        if (this.editableRows == null) {
            this.editableRows = new HashSet<>();
        } else {
            this.editableRows.clear();
        }
        for (int row : rows) {
            this.editableRows.add(row);
        }
        return this;
    }

    /**
     * Adds a specific cell (by row and column) to the set of explicitly editable cells.
     * If any cell is added using this method, ONLY those specified cells can be edited, overriding the table model.
     * 
     * @param row the row index
     * @param column the column index
     * @return this DTable instance for fluent method chaining.
     */
    public DTable addEditableCell(int row, int column) {
        if (this.editableCells == null) {
            this.editableCells = new HashSet<>();
        }
        this.editableCells.add(new Point(column, row));
        return this;
    }

    /**
     * Returns the appropriate cell editor for the cell specified by row and column.
     * Overridden to seamlessly inject custom row-level editors if defined.
     * 
     * @param row the row of the cell to edit
     * @param column the column of the cell to edit
     * @return the TableCellEditor for this cell
     */
    @Override
    public TableCellEditor getCellEditor(int row, int column) {
        if (cellEditors != null) {
            Point cell = new Point(column, row);
            if (cellEditors.containsKey(cell)) {
                return cellEditors.get(cell);
            }
        }
        if (rowEditors != null && rowEditors.containsKey(row)) {
            return rowEditors.get(row);
        }
        return super.getCellEditor(row, column);
    }

    /**
     * Determines whether the cell at the specified row and column is editable.
     * 
     * @param row the row whose value is to be queried
     * @param column the column whose value is to be queried
     * @return true if the cell is editable (and global editing is enabled), false otherwise
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        // Enforce the global editable flag before querying the underlying model
        if (!globalEditable) return false;
        
        // If specific editable cells were defined, strictly enforce them
        if (editableCells != null && !editableCells.contains(new Point(column, row))) {
            return false;
        }

        // If specific editable columns were defined, strictly enforce them
        if (editableColumns != null && !editableColumns.contains(column)) {
            return false;
        }

        // If specific editable rows were defined, strictly enforce them
        if (editableRows != null && !editableRows.contains(row)) {
            return false;
        }

        // If any restrictive set was defined and the cell passed all filters, it is explicitly editable
        if (editableCells != null || editableColumns != null || editableRows != null) {
            return true;
        }
        
        return super.isCellEditable(row, column);
    }

    /**
     * Prepares the renderer by querying the data model for the value and selection state 
     * of the cell at row, column.
     * <p>
     * Overridden to seamlessly inject alternating striped row background colors when the
     * striped feature is enabled, without interfering with the active selection highlighting.
     * 
     * @param renderer the TableCellRenderer to prepare
     * @param row the row of the cell to render
     * @param column the column of the cell to render
     * @return the Component under the event location
     */
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        
        // Only modify backgrounds for unselected cells so we don't break selection highlights
        if (!isCellSelected(row, column)) {
            // Apply alternate stripe color on odd rows
            if (striped && row % 2 != 0) {
                c.setBackground(stripeColor);
            } else {
                c.setBackground(getBackground());
            }
        }
        return c;
    }

    /**
     * Overrides standard initialization to map default Java types to custom desk DEdit components.
     */
    @Override
    protected void createDefaultEditors() {
        super.createDefaultEditors();
        
        // Text/String
        setDefaultEditor(Object.class, new DEditCellEditorAdapter(new DFieldString()));
        setDefaultEditor(String.class, new DEditCellEditorAdapter(new DFieldString()));
        
        // Booleans
        setDefaultEditor(Boolean.class, new DEditCellEditorAdapter(new DEditCheck()));
        
        // Numerics
        setDefaultEditor(Number.class, new DEditCellEditorAdapter(new DFieldDouble()));
        setDefaultEditor(Double.class, new DEditCellEditorAdapter(new DFieldDouble()));
        setDefaultEditor(Float.class, new DEditCellEditorAdapter(new DFieldFloat()));
        setDefaultEditor(Integer.class, new DEditCellEditorAdapter(new DFieldInteger()));
        setDefaultEditor(Long.class, new DEditCellEditorAdapter(new DFieldLong()));
        setDefaultEditor(Short.class, new DEditCellEditorAdapter(new DFieldShort()));
        setDefaultEditor(Byte.class, new DEditCellEditorAdapter(new DFieldByte()));
        setDefaultEditor(BigDecimal.class, new DEditCellEditorAdapter(new DFieldBigDecimal()));
        setDefaultEditor(BigInteger.class, new DEditCellEditorAdapter(new DFieldBigInteger()));
        
        // Complex types
        setDefaultEditor(Color.class, new DEditCellEditorAdapter(new DChangeColor()));
        setDefaultEditor(File.class, new DEditCellEditorAdapter(new DChangeFile()));
        setDefaultEditor(Date.class, new DEditCellEditorAdapter(new DChangeDate()));
        setDefaultEditor(java.sql.Date.class, new DEditCellEditorAdapter(new DChangeDate()));
        setDefaultEditor(Time.class, new DEditCellEditorAdapter(new DChangeTime()));
        setDefaultEditor(Timestamp.class, new DEditCellEditorAdapter(new DChangeTimestamp()));
        setDefaultEditor(Font.class, new DEditCellEditorAdapter(new DChangeFont()));
        setDefaultEditor(Image.class, new DEditCellEditorAdapter(new DChangeImage()));
        setDefaultEditor(Icon.class, new IconCellEditorAdapter());
        setDefaultEditor(ImageIcon.class, new IconCellEditorAdapter());
        setDefaultEditor(List.class, new DEditCellEditorAdapter(new DChangeList()));
        setDefaultEditor(Map.class, new DEditCellEditorAdapter(new DChangeMap()));
        
        // Custom complex type adapters
        setDefaultEditor(Path.class, new PathCellEditorAdapter());
        setDefaultEditor(char[].class, new PassCellEditorAdapter());
        
        // Note: DChangeSearch must be explicitly bound per-cell/column since it requires custom options.
        
        // Enums
        setDefaultEditor(Enum.class, new EnumCellEditorAdapter());
    }

    /**
     * An adapter that allows any DEdit component to function as a native JTable TableCellEditor.
     * This class bridges the Swing JTable cell editing lifecycle with the custom event and data 
     * flow of the desk package's DEdit components.
     */
    public static class DEditCellEditorAdapter extends AbstractCellEditor implements TableCellEditor {
        
        /** The underlying DEdit component doing the actual editing. */
        protected final DEdit<?> editor;

        /**
         * Constructs a new cell editor adapter wrapping the given DEdit component.
         * 
         * @param editor the DEdit component to use for editing
         */
        public DEditCellEditorAdapter(DEdit<?> editor) {
            this.editor = editor;
            // Hook into DEdit's custom action pipeline to gracefully stop editing
            // when the user triggers a primary action (e.g., pressing enter or picking a value).
            editor.onAction(e -> stopCellEditing());
        }

        /**
         * Returns the value contained in the editor.
         * 
         * @return the current value from the DEdit component
         */
        @Override
        public Object getCellEditorValue() {
            return editor.value();
        }

        /**
         * Sets an initial value for the editor and returns the component that should be added to the client's Component hierarchy.
         * 
         * @param table the JTable that is asking the editor to edit
         * @param value the value of the cell to be edited
         * @param isSelected true if the cell is to be rendered with highlighting
         * @param row the row of the cell being edited
         * @param column the column of the cell being edited
         * @return the component for editing
         */
        @SuppressWarnings("unchecked")
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            try {
                ((DEdit<Object>) editor).value(value);
            } catch (Exception ex) {
                // Ignore class cast or mismatch exceptions during initial value set.
                // This ensures the editor doesn't crash if the cell contains unexpected data types.
            }
            return editor.comp();
        }
    }

    /**
     * An adapter that specifically allows DEditCombo to dynamically populate Enum constants for JTable editing.
     * Unlike standard inputs, Enums require the editor to know the possible values dynamically based on the 
     * specific Enum type being edited at runtime.
     */
    public static class EnumCellEditorAdapter extends DEditCellEditorAdapter {
        
        /**
         * Constructs a new EnumCellEditorAdapter using an empty DEditCombo.
         */
        public EnumCellEditorAdapter() {
            super(new DEditCombo<>());
        }

        /**
         * Configures the DEditCombo with the correct Enum constants before returning the editor component.
         */
        @SuppressWarnings("unchecked")
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            DEditCombo<Object> combo = (DEditCombo<Object>) super.editor;
            
            // Clear previous items to avoid pollution between different Enum columns
            combo.clear();
            
            Class<?> enumClass = table.getColumnClass(column);
            
            // Handle enums with bodies (anonymous inner classes of enums) by climbing to the superclass
            if (!enumClass.isEnum() && enumClass.getSuperclass() != null && enumClass.getSuperclass().isEnum()) {
                enumClass = enumClass.getSuperclass(); 
            }
            
            if (enumClass.isEnum()) {
                // Populate the combo box with the native enum constants
                combo.add((Object[]) enumClass.getEnumConstants());
            } else if (value != null) {
                // Fallback to evaluating the runtime type of the actual value if the column class is generic (e.g., Object.class)
                Class<?> valueClass = value.getClass();
                if (!valueClass.isEnum() && valueClass.getSuperclass() != null && valueClass.getSuperclass().isEnum()) {
                    valueClass = valueClass.getSuperclass();
                }
                if (valueClass.isEnum()) {
                    combo.add((Object[]) valueClass.getEnumConstants());
                }
            }
            
            return super.getTableCellEditorComponent(table, value, isSelected, row, column);
        }
    }

    /**
     * An adapter that allows DChangeDir to dynamically edit Path objects for JTable editing.
     * It handles the conversion between the File used by DChangeDir and the Path object expected by the model.
     */
    public static class PathCellEditorAdapter extends DEditCellEditorAdapter {
        
        /**
         * Constructs a new PathCellEditorAdapter using a DChangeDir component.
         */
        public PathCellEditorAdapter() {
            super(new DChangeDir());
        }

        /**
         * Retrieves the edited File from DChangeDir and converts it to a Path.
         */
        @Override
        public Object getCellEditorValue() {
            File f = (File) super.getCellEditorValue();
            return f != null ? f.toPath() : null;
        }

        /**
         * Intercepts the Path value from the JTable and converts it to a File for the DChangeDir component.
         */
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            File f = null;
            if (value instanceof Path) {
                f = ((Path) value).toFile();
            } else if (value instanceof File) {
                f = (File) value;
            }
            return super.getTableCellEditorComponent(table, f, isSelected, row, column);
        }
    }

    /**
     * An adapter that allows DChangePass to dynamically edit char[] objects for JTable editing.
     * It seamlessly handles the conversion between the String used by DChangePass and the char[] array expected by the model.
     */
    public static class PassCellEditorAdapter extends DEditCellEditorAdapter {
        
        /**
         * Constructs a new PassCellEditorAdapter using a DChangePass component.
         */
        public PassCellEditorAdapter() {
            super(new DChangePass());
        }

        /**
         * Retrieves the edited String from DChangePass and converts it to a char array.
         */
        @Override
        public Object getCellEditorValue() {
            String s = (String) super.getCellEditorValue();
            return s != null ? s.toCharArray() : null;
        }

        /**
         * Intercepts the char[] value from the JTable and converts it to a String for the DChangePass component.
         */
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            String s = null;
            if (value instanceof char[]) {
                s = new String((char[]) value);
            } else if (value instanceof String) {
                s = (String) value;
            }
            return super.getTableCellEditorComponent(table, s, isSelected, row, column);
        }
    }

    /**
     * An adapter that allows DChangeImage to dynamically edit Icon and ImageIcon objects for JTable editing.
     * It handles extracting the raw Image for editing and re-wrapping it into an ImageIcon.
     */
    public static class IconCellEditorAdapter extends DEditCellEditorAdapter {
        
        /**
         * Constructs a new IconCellEditorAdapter using a DChangeImage component.
         */
        public IconCellEditorAdapter() {
            super(new DChangeImage());
        }

        /**
         * Retrieves the edited Image from DChangeImage and wraps it safely into an ImageIcon.
         */
        @Override
        public Object getCellEditorValue() {
            Image img = (Image) super.getCellEditorValue();
            return img != null ? new ImageIcon(img) : null;
        }

        /**
         * Intercepts the Icon/ImageIcon from the JTable, extracts the raw Image, and feeds it into the DChangeImage component.
         */
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            Image img = null;
            if (value instanceof ImageIcon) {
                img = ((ImageIcon) value).getImage();
            } else if (value instanceof Image) {
                img = (Image) value;
            } else if (value instanceof Icon) {
                // If it is a generic Icon, we must paint it to a BufferedImage to extract its pixel data
                try {
                    Icon icon = (Icon) value;
                    BufferedImage bImg = new BufferedImage(
                            icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
                    Graphics g = bImg.createGraphics();
                    icon.paintIcon(null, g, 0, 0);
                    g.dispose();
                    img = bImg;
                } catch (Exception e) {
                    // Fallback to null image on paint failure (e.g., zero size icons)
                }
            }
            return super.getTableCellEditorComponent(table, img, isSelected, row, column);
        }
    }
}


