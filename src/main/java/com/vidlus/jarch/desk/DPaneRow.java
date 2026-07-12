package com.vidlus.jarch.desk;

import java.awt.GridBagLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;

public class DPaneRow extends DPane {

    private final GridBagConstraints layout = new GridBagConstraints();

    public DPaneRow() {
        super(new GridBagLayout());
        layout.gridx = -1;
        layout.gridy = 0;
        layout.gridwidth = 1;
        layout.gridheight = 1;
        layout.anchor = GridBagConstraints.CENTER;
        layout.ipadx = 0;
        layout.ipady = 0;
        layout.insets = new java.awt.Insets(0, 0, 0, 0);
        layout.weightx = 0;
        layout.weighty = 0;
        layout.fill = GridBagConstraints.NONE;
    }

    public DPaneRow anchorNorth() {
        layout.anchor = GridBagConstraints.NORTH;
        return this;
    }

    public DPaneRow anchorNorthEast() {
        layout.anchor = GridBagConstraints.NORTHEAST;
        return this;
    }

    public DPaneRow anchorNorthWest() {
        layout.anchor = GridBagConstraints.NORTHWEST;
        return this;
    }

    public DPaneRow anchorSouth() {
        layout.anchor = GridBagConstraints.SOUTH;
        return this;
    }

    public DPaneRow anchorSouthEast() {
        layout.anchor = GridBagConstraints.SOUTHEAST;
        return this;
    }

    public DPaneRow anchorSouthWest() {
        layout.anchor = GridBagConstraints.SOUTHWEST;
        return this;
    }

    public DPaneRow anchorEast() {
        layout.anchor = GridBagConstraints.EAST;
        return this;
    }

    public DPaneRow anchorWest() {
        layout.anchor = GridBagConstraints.WEST;
        return this;
    }

    public DPaneRow anchorCenter() {
        layout.anchor = GridBagConstraints.CENTER;
        return this;
    }

    public DPaneRow ipad(int size) {
        layout.ipadx = size;
        layout.ipady = size;
        return this;
    }

    public DPaneRow ipadx(int size) {
        layout.ipadx = size;
        return this;
    }

    public DPaneRow ipady(int size) {
        layout.ipady = size;
        return this;
    }

    public DPaneRow insets(int size) {
        layout.insets.top = size;
        layout.insets.left = size;
        layout.insets.bottom = size;
        layout.insets.right = size;
        return this;
    }

    public DPaneRow insetsTop(int size) {
        layout.insets.top = size;
        return this;
    }

    public DPaneRow insetsLeft(int size) {
        layout.insets.left = size;
        return this;
    }

    public DPaneRow insetsBottom(int size) {
        layout.insets.bottom = size;
        return this;
    }

    public DPaneRow insetsRight(int size) {
        layout.insets.right = size;
        return this;
    }

    public DPaneRow insets(int topBottom, int leftRight) {
        layout.insets.top = topBottom;
        layout.insets.left = leftRight;
        layout.insets.bottom = topBottom;
        layout.insets.right = leftRight;
        return this;
    }

    public DPaneRow insets(int top, int left, int bottom, int right) {
        layout.insets.top = top;
        layout.insets.left = left;
        layout.insets.bottom = bottom;
        layout.insets.right = right;
        return this;
    }

    public DPaneRow growNone() {
        layout.weightx = 0;
        layout.weighty = 0;
        layout.fill = GridBagConstraints.NONE;
        return this;
    }

    public DPaneRow growBoth() {
        layout.weightx = 1;
        layout.weighty = 1;
        layout.fill = GridBagConstraints.BOTH;
        return this;
    }

    public DPaneRow growHorizontal() {
        layout.weightx = 1;
        layout.weighty = 0;
        layout.fill = GridBagConstraints.HORIZONTAL;
        return this;
    }

    public DPaneRow growVertical() {
        layout.weightx = 0;
        layout.weighty = 1;
        layout.fill = GridBagConstraints.VERTICAL;
        return this;
    }

    public DPaneRow weightBoth(double weight) {
        layout.weightx = weight;
        layout.weighty = weight;
        return this;
    }

    public DPaneRow weightHorizontal(double weight) {
        layout.weightx = weight;
        layout.weighty = 0;
        return this;
    }

    public DPaneRow weightVertical(double weight) {
        layout.weightx = 0;
        layout.weighty = weight;
        return this;
    }

    public DPaneRow weightEach(double weightX, double weightY) {
        layout.weightx = weightX;
        layout.weighty = weightY;
        return this;
    }

    public DPaneRow put(DEdit<?> edit) {
        put(edit.comp());
        return this;
    }

    @Override
    public DPaneRow put(Component comp) {
        layout.gridx += 1;
        add(comp, layout);
        return this;
    }

}
