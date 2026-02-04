package br.com.pointel.jarch.desk;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;

public class DColPane extends DPane {

    private final GridBagConstraints layout = new GridBagConstraints();

    public DColPane() {
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

    public DColPane anchorNorth() {
        layout.anchor = GridBagConstraints.NORTH;
        return this;
    }

    public DColPane anchorNorthEast() {
        layout.anchor = GridBagConstraints.NORTHEAST;
        return this;
    }

    public DColPane anchorNorthWest() {
        layout.anchor = GridBagConstraints.NORTHWEST;
        return this;
    }

    public DColPane anchorSouth() {
        layout.anchor = GridBagConstraints.SOUTH;
        return this;
    }

    public DColPane anchorSouthEast() {
        layout.anchor = GridBagConstraints.SOUTHEAST;
        return this;
    }

    public DColPane anchorSouthWest() {
        layout.anchor = GridBagConstraints.SOUTHWEST;
        return this;
    }

    public DColPane anchorEast() {
        layout.anchor = GridBagConstraints.EAST;
        return this;
    }

    public DColPane anchorWest() {
        layout.anchor = GridBagConstraints.WEST;
        return this;
    }

    public DColPane anchorCenter() {
        layout.anchor = GridBagConstraints.CENTER;
        return this;
    }

    public DColPane ipad(int size) {
        layout.ipadx = size;
        layout.ipady = size;
        return this;
    }

    public DColPane ipadx(int size) {
        layout.ipadx = size;
        return this;
    }

    public DColPane ipady(int size) {
        layout.ipady = size;
        return this;
    }

    public DColPane insets(int size) {
        layout.insets.top = size;
        layout.insets.left = size;
        layout.insets.bottom = size;
        layout.insets.right = size;
        return this;
    }

    public DColPane insetsTop(int size) {
        layout.insets.top = size;
        return this;
    }

    public DColPane insetsLeft(int size) {
        layout.insets.left = size;
        return this;
    }

    public DColPane insetsBottom(int size) {
        layout.insets.bottom = size;
        return this;
    }

    public DColPane insetsRight(int size) {
        layout.insets.right = size;
        return this;
    }

    public DColPane insets(int topBottom, int leftRight) {
        layout.insets.top = topBottom;
        layout.insets.left = leftRight;
        layout.insets.bottom = topBottom;
        layout.insets.right = leftRight;
        return this;
    }

    public DColPane insets(int top, int left, int bottom, int right) {
        layout.insets.top = top;
        layout.insets.left = left;
        layout.insets.bottom = bottom;
        layout.insets.right = right;
        return this;
    }

    public DColPane growNone() {
        layout.weightx = 0;
        layout.weighty = 0;
        layout.fill = GridBagConstraints.NONE;
        return this;
    }

    public DColPane growBoth() {
        layout.weightx = 1;
        layout.weighty = 1;
        layout.fill = GridBagConstraints.BOTH;
        return this;
    }

    public DColPane growHorizontal() {
        layout.weightx = 1;
        layout.weighty = 0;
        layout.fill = GridBagConstraints.HORIZONTAL;
        return this;
    }

    public DColPane growVertical() {
        layout.weightx = 0;
        layout.weighty = 1;
        layout.fill = GridBagConstraints.VERTICAL;
        return this;
    }

    public DColPane weightBoth(double weight) {
        layout.weightx = weight;
        layout.weighty = weight;
        return this;
    }

    public DColPane weightHorizontal(double weight) {
        layout.weightx = weight;
        layout.weighty = 0;
        return this;
    }

    public DColPane weightVertical(double weight) {
        layout.weightx = 0;
        layout.weighty = weight;
        return this;
    }

    public DColPane weightEach(double weightX, double weightY) {
        layout.weightx = weightX;
        layout.weighty = weightY;
        return this;
    }

    public DColPane put(DEdit<?> edit) {
        put(edit.comp());
        return this;
    }

    @Override
    public DColPane put(Component comp) {
        layout.gridy += 1;
        add(comp, layout);
        return this;
    }

}
