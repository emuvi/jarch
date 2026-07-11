package com.vidlus.jarch.desk;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * A master DPane class for GridBagLayout.
 * Utilizes the Curiously Recurring Template Pattern (CRTP) to allow subclasses 
 * (like DPaneRow, DPaneColumn, and DPaneForm) to inherit fluent constraint modifiers 
 * without losing their specific subclass type context.
 */
@SuppressWarnings("unchecked")
public abstract class DPaneGridBag<T extends DPaneGridBag<T>> extends DPane {

    protected final GridBagConstraints constraints = new GridBagConstraints();

    public DPaneGridBag() {
        super(new GridBagLayout());
    }

    public T anchorNorth() {
        constraints.anchor = GridBagConstraints.NORTH;
        return (T) this;
    }

    public T anchorNorthEast() {
        constraints.anchor = GridBagConstraints.NORTHEAST;
        return (T) this;
    }

    public T anchorNorthWest() {
        constraints.anchor = GridBagConstraints.NORTHWEST;
        return (T) this;
    }

    public T anchorSouth() {
        constraints.anchor = GridBagConstraints.SOUTH;
        return (T) this;
    }

    public T anchorSouthEast() {
        constraints.anchor = GridBagConstraints.SOUTHEAST;
        return (T) this;
    }

    public T anchorSouthWest() {
        constraints.anchor = GridBagConstraints.SOUTHWEST;
        return (T) this;
    }

    public T anchorEast() {
        constraints.anchor = GridBagConstraints.EAST;
        return (T) this;
    }

    public T anchorWest() {
        constraints.anchor = GridBagConstraints.WEST;
        return (T) this;
    }

    public T anchorCenter() {
        constraints.anchor = GridBagConstraints.CENTER;
        return (T) this;
    }

    public T margin(int size) {
        constraints.insets = new Insets(size, size, size, size);
        return (T) this;
    }

    public T margin(int top, int left, int bottom, int right) {
        constraints.insets = new Insets(top, left, bottom, right);
        return (T) this;
    }

    public T growNone() {
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.NONE;
        return (T) this;
    }

    public T growBoth() {
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        return (T) this;
    }

    public T growHorizontal() {
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        return (T) this;
    }

    public T growVertical() {
        constraints.weightx = 0;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.VERTICAL;
        return (T) this;
    }

    public T padding(int ipadx, int ipady) {
        constraints.ipadx = ipadx;
        constraints.ipady = ipady;
        return (T) this;
    }

    public T fill(int fillMode) {
        constraints.fill = fillMode;
        return (T) this;
    }

    public T fillNone() { return fill(GridBagConstraints.NONE); }
    public T fillBoth() { return fill(GridBagConstraints.BOTH); }
    public T fillHorizontal() { return fill(GridBagConstraints.HORIZONTAL); }
    public T fillVertical() { return fill(GridBagConstraints.VERTICAL); }

    public T weight(double weightx, double weighty) {
        constraints.weightx = weightx;
        constraints.weighty = weighty;
        return (T) this;
    }

    public T weightX(double weightx) {
        constraints.weightx = weightx;
        return (T) this;
    }

    public T weightY(double weighty) {
        constraints.weighty = weighty;
        return (T) this;
    }
}
