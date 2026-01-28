package br.com.pointel.jarch.desk;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;

public class DRow extends DPane {

    private final GridBagConstraints layout = new GridBagConstraints();

    public DRow() {
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

    public DRow anchorNorth() {
        layout.anchor = GridBagConstraints.NORTH;
        return this;
    }

    public DRow anchorNorthEast() {
        layout.anchor = GridBagConstraints.NORTHEAST;
        return this;
    }

    public DRow anchorNorthWest() {
        layout.anchor = GridBagConstraints.NORTHWEST;
        return this;
    }

    public DRow anchorSouth() {
        layout.anchor = GridBagConstraints.SOUTH;
        return this;
    }

    public DRow anchorSouthEast() {
        layout.anchor = GridBagConstraints.SOUTHEAST;
        return this;
    }

    public DRow anchorSouthWest() {
        layout.anchor = GridBagConstraints.SOUTHWEST;
        return this;
    }

    public DRow anchorEast() {
        layout.anchor = GridBagConstraints.EAST;
        return this;
    }

    public DRow anchorWest() {
        layout.anchor = GridBagConstraints.WEST;
        return this;
    }

    public DRow anchorCenter() {
        layout.anchor = GridBagConstraints.CENTER;
        return this;
    }

    public DRow ipad(int size) {
        layout.ipadx = size;
        layout.ipady = size;
        return this;
    }

    public DRow ipadx(int size) {
        layout.ipadx = size;
        return this;
    }

    public DRow ipady(int size) {
        layout.ipady = size;
        return this;
    }

    public DRow insets(int size) {
        layout.insets.top = size;
        layout.insets.left = size;
        layout.insets.bottom = size;
        layout.insets.right = size;
        return this;
    }

    public DRow insetsTop(int size) {
        layout.insets.top = size;
        return this;
    }

    public DRow insetsLeft(int size) {
        layout.insets.left = size;
        return this;
    }

    public DRow insetsBottom(int size) {
        layout.insets.bottom = size;
        return this;
    }

    public DRow insetsRight(int size) {
        layout.insets.right = size;
        return this;
    }

    public DRow insets(int topBottom, int leftRight) {
        layout.insets.top = topBottom;
        layout.insets.left = leftRight;
        layout.insets.bottom = topBottom;
        layout.insets.right = leftRight;
        return this;
    }

    public DRow insets(int top, int left, int bottom, int right) {
        layout.insets.top = top;
        layout.insets.left = left;
        layout.insets.bottom = bottom;
        layout.insets.right = right;
        return this;
    }

    public DRow growNone() {
        layout.weightx = 0;
        layout.weighty = 0;
        layout.fill = GridBagConstraints.NONE;
        return this;
    }

    public DRow growBoth() {
        layout.weightx = 1;
        layout.weighty = 1;
        layout.fill = GridBagConstraints.BOTH;
        return this;
    }

    public DRow growHorizontal() {
        layout.weightx = 1;
        layout.weighty = 0;
        layout.fill = GridBagConstraints.HORIZONTAL;
        return this;
    }

    public DRow growVertical() {
        layout.weightx = 0;
        layout.weighty = 1;
        layout.fill = GridBagConstraints.VERTICAL;
        return this;
    }

    public DRow weightBoth(double weight) {
        layout.weightx = weight;
        layout.weighty = weight;
        return this;
    }

    public DRow weightHorizontal(double weight) {
        layout.weightx = weight;
        layout.weighty = 0;
        return this;
    }

    public DRow weightVertical(double weight) {
        layout.weightx = 0;
        layout.weighty = weight;
        return this;
    }

    public DRow weightEach(double weightX, double weightY) {
        layout.weightx = weightX;
        layout.weighty = weightY;
        return this;
    }

    public DRow put(Component component) {
        layout.gridx += 1;
        add(component, layout);
        return this;
    }

}
