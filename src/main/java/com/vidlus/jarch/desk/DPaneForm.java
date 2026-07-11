package com.vidlus.jarch.desk;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * A fluent wrapper for GridBagLayout.
 * Eliminates the verbosity of GridBagConstraints by maintaining an internal constraint state
 * that you can fluently modify before putting components into the grid.
 */
public class DPaneForm extends DPane {

    private final GridBagLayout layout;
    private final GridBagConstraints constraints;

    public DPaneForm() {
        this.layout = new GridBagLayout();
        this.constraints = new GridBagConstraints();
        this.constraints.fill = GridBagConstraints.BOTH; // Default to filling cells
        this.constraints.insets = new Insets(2, 2, 2, 2); // Default slight padding
        super.layout(layout);
    }

    // --- State Modifiers (apply to all subsequent put() calls) ---

    public DPaneForm insets(int top, int left, int bottom, int right) {
        constraints.insets = new Insets(top, left, bottom, right);
        return this;
    }

    public DPaneForm insets(int size) {
        return insets(size, size, size, size);
    }

    public DPaneForm fill(int fillMode) {
        constraints.fill = fillMode;
        return this;
    }

    public DPaneForm fillNone() { return fill(GridBagConstraints.NONE); }
    public DPaneForm fillBoth() { return fill(GridBagConstraints.BOTH); }
    public DPaneForm fillHorizontal() { return fill(GridBagConstraints.HORIZONTAL); }
    public DPaneForm fillVertical() { return fill(GridBagConstraints.VERTICAL); }

    public DPaneForm weight(double weightx, double weighty) {
        constraints.weightx = weightx;
        constraints.weighty = weighty;
        return this;
    }

    public DPaneForm weightX(double weightx) {
        constraints.weightx = weightx;
        return this;
    }

    public DPaneForm weightY(double weighty) {
        constraints.weighty = weighty;
        return this;
    }

    public DPaneForm anchor(int anchor) {
        constraints.anchor = anchor;
        return this;
    }

    public DPaneForm anchorCenter() { return anchor(GridBagConstraints.CENTER); }
    public DPaneForm anchorNorth() { return anchor(GridBagConstraints.NORTH); }
    public DPaneForm anchorSouth() { return anchor(GridBagConstraints.SOUTH); }
    public DPaneForm anchorEast() { return anchor(GridBagConstraints.EAST); }
    public DPaneForm anchorWest() { return anchor(GridBagConstraints.WEST); }
    public DPaneForm anchorNorthWest() { return anchor(GridBagConstraints.NORTHWEST); }
    public DPaneForm anchorNorthEast() { return anchor(GridBagConstraints.NORTHEAST); }
    public DPaneForm anchorSouthWest() { return anchor(GridBagConstraints.SOUTHWEST); }
    public DPaneForm anchorSouthEast() { return anchor(GridBagConstraints.SOUTHEAST); }

    // --- Put Methods ---

    /**
     * Puts a component at the specified grid X and Y coordinates.
     */
    public DPaneForm put(Component comp, int x, int y) {
        return put(comp, x, y, 1, 1);
    }

    public DPaneForm put(DEdit<?> edit, int x, int y) {
        return put(edit.comp(), x, y);
    }

    /**
     * Puts a component at the specified grid X and Y coordinates, spanning multiple columns.
     */
    public DPaneForm putSpan(Component comp, int x, int y, int widthSpan) {
        return put(comp, x, y, widthSpan, 1);
    }

    public DPaneForm putSpan(DEdit<?> edit, int x, int y, int widthSpan) {
        return putSpan(edit.comp(), x, y, widthSpan);
    }

    /**
     * Puts a component at the specified grid X and Y coordinates, spanning multiple columns and rows.
     */
    public DPaneForm put(Component comp, int x, int y, int widthSpan, int heightSpan) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = widthSpan;
        constraints.gridheight = heightSpan;
        
        // GridBagLayout clones the constraints, so modifying the state object later is perfectly safe.
        layout.setConstraints(comp, constraints);
        add(comp);
        return this;
    }

    public DPaneForm put(DEdit<?> edit, int x, int y, int widthSpan, int heightSpan) {
        return put(edit.comp(), x, y, widthSpan, heightSpan);
    }
}
