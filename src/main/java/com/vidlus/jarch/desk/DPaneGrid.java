package com.vidlus.jarch.desk;

import java.awt.Component;
import java.awt.GridLayout;

/**
 * A fluent wrapper for GridLayout.
 * Ideal for arranging components into a rigid grid where every cell is exactly the same size.
 */
public class DPaneGrid extends DPane {

    private final GridLayout layout;

    /**
     * Creates a single-row grid layout with no gaps.
     */
    public DPaneGrid() {
        this(1, 0, 0, 0);
    }

    /**
     * Creates a grid layout with the specified number of rows and columns.
     * 
     * @param rows the number of rows (0 means any number, determined by columns)
     * @param cols the number of columns (0 means any number, determined by rows)
     */
    public DPaneGrid(int rows, int cols) {
        this(rows, cols, 0, 0);
    }

    /**
     * Creates a grid layout with the specified number of rows, columns, and gaps.
     * 
     * @param rows the number of rows
     * @param cols the number of columns
     * @param hgap the horizontal gap between components
     * @param vgap the vertical gap between components
     */
    public DPaneGrid(int rows, int cols, int hgap, int vgap) {
        this.layout = new GridLayout(rows, cols, hgap, vgap);
        super.layout(this.layout);
    }

    /**
     * Sets the number of rows in this grid.
     * 
     * @param rows the number of rows
     * @return This DPaneGrid instance.
     */
    public DPaneGrid rows(int rows) {
        layout.setRows(rows);
        layout(layout);
        return this;
    }

    /**
     * Sets the number of columns in this grid.
     * 
     * @param cols the number of columns
     * @return This DPaneGrid instance.
     */
    public DPaneGrid cols(int cols) {
        layout.setColumns(cols);
        layout(layout);
        return this;
    }

    /**
     * Sets the horizontal and vertical gaps between grid components.
     * 
     * @param hgap the horizontal gap in pixels
     * @param vgap the vertical gap in pixels
     * @return This DPaneGrid instance.
     */
    public DPaneGrid gap(int hgap, int vgap) {
        layout.setHgap(hgap);
        layout.setVgap(vgap);
        layout(layout);
        return this;
    }

    @Override
    public DPaneGrid put(Component comp) {
        super.put(comp);
        return this;
    }

    @Override
    public DPaneGrid put(DEdit<?> edit) {
        super.put(edit);
        return this;
    }
}
