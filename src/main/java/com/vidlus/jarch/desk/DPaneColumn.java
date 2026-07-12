package com.vidlus.jarch.desk;

import java.awt.GridBagLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;

public class DPaneColumn extends DPane {

    private final GridBagConstraints layout = new GridBagConstraints();

    public DPaneColumn() {
        super(new GridBagLayout());
        layout.gridx = 0;
        layout.gridy = -1;
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

    public DPaneColumn anchorNorth() {
        layout.anchor = GridBagConstraints.NORTH;
        return this;
    }

    public DPaneColumn anchorNorthEast() {
        layout.anchor = GridBagConstraints.NORTHEAST;
        return this;
    }

    public DPaneColumn anchorNorthWest() {
        layout.anchor = GridBagConstraints.NORTHWEST;
        return this;
    }

    public DPaneColumn anchorSouth() {
        layout.anchor = GridBagConstraints.SOUTH;
        return this;
    }

    public DPaneColumn anchorSouthEast() {
        layout.anchor = GridBagConstraints.SOUTHEAST;
        return this;
    }

    public DPaneColumn anchorSouthWest() {
        layout.anchor = GridBagConstraints.SOUTHWEST;
        return this;
    }

    public DPaneColumn anchorEast() {
        layout.anchor = GridBagConstraints.EAST;
        return this;
    }

    public DPaneColumn anchorWest() {
        layout.anchor = GridBagConstraints.WEST;
        return this;
    }

    public DPaneColumn anchorCenter() {
        layout.anchor = GridBagConstraints.CENTER;
        return this;
    }

    public DPaneColumn ipad(int size) {
        layout.ipadx = size;
        layout.ipady = size;
        return this;
    }

    public DPaneColumn ipadx(int size) {
        layout.ipadx = size;
        return this;
    }

    public DPaneColumn ipady(int size) {
        layout.ipady = size;
        return this;
    }

    public DPaneColumn insets(int size) {
        layout.insets.top = size;
        layout.insets.left = size;
        layout.insets.bottom = size;
        layout.insets.right = size;
        return this;
    }

    public DPaneColumn insetsTop(int size) {
        layout.insets.top = size;
        return this;
    }

    public DPaneColumn insetsLeft(int size) {
        layout.insets.left = size;
        return this;
    }

    public DPaneColumn insetsBottom(int size) {
        layout.insets.bottom = size;
        return this;
    }

    public DPaneColumn insetsRight(int size) {
        layout.insets.right = size;
        return this;
    }

    public DPaneColumn insets(int topBottom, int leftRight) {
        layout.insets.top = topBottom;
        layout.insets.left = leftRight;
        layout.insets.bottom = topBottom;
        layout.insets.right = leftRight;
        return this;
    }

    public DPaneColumn insets(int top, int left, int bottom, int right) {
        layout.insets.top = top;
        layout.insets.left = left;
        layout.insets.bottom = bottom;
        layout.insets.right = right;
        return this;
    }

    public DPaneColumn growNone() {
        layout.weightx = 0;
        layout.weighty = 0;
        layout.fill = GridBagConstraints.NONE;
        return this;
    }

    public DPaneColumn growBoth() {
        layout.weightx = 1;
        layout.weighty = 1;
        layout.fill = GridBagConstraints.BOTH;
        return this;
    }

    public DPaneColumn growHorizontal() {
        layout.weightx = 1;
        layout.weighty = 0;
        layout.fill = GridBagConstraints.HORIZONTAL;
        return this;
    }

    public DPaneColumn growVertical() {
        layout.weightx = 0;
        layout.weighty = 1;
        layout.fill = GridBagConstraints.VERTICAL;
        return this;
    }

    public DPaneColumn weightBoth(double weight) {
        layout.weightx = weight;
        layout.weighty = weight;
        return this;
    }

    public DPaneColumn weightHorizontal(double weight) {
        layout.weightx = weight;
        layout.weighty = 0;
        return this;
    }

    public DPaneColumn weightVertical(double weight) {
        layout.weightx = 0;
        layout.weighty = weight;
        return this;
    }

    @Override
    public DPaneColumn put(DEdit<?> edit) {
        put(edit.comp());
        return this;
    }

    @Override
    public DPaneColumn put(Component comp) {
        layout.gridy += 1;
        add(comp, layout);
        return this;
    }

}
