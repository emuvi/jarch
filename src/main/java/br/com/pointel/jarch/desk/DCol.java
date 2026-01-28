package br.com.pointel.jarch.desk;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;

public class DCol extends DPane {

    private final GridBagConstraints layout = new GridBagConstraints();

    public DCol() {
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

    public DCol anchorNorth() {
        layout.anchor = GridBagConstraints.NORTH;
        return this;
    }

    public DCol anchorNorthEast() {
        layout.anchor = GridBagConstraints.NORTHEAST;
        return this;
    }

    public DCol anchorNorthWest() {
        layout.anchor = GridBagConstraints.NORTHWEST;
        return this;
    }

    public DCol anchorSouth() {
        layout.anchor = GridBagConstraints.SOUTH;
        return this;
    }

    public DCol anchorSouthEast() {
        layout.anchor = GridBagConstraints.SOUTHEAST;
        return this;
    }

    public DCol anchorSouthWest() {
        layout.anchor = GridBagConstraints.SOUTHWEST;
        return this;
    }

    public DCol anchorEast() {
        layout.anchor = GridBagConstraints.EAST;
        return this;
    }

    public DCol anchorWest() {
        layout.anchor = GridBagConstraints.WEST;
        return this;
    }

    public DCol anchorCenter() {
        layout.anchor = GridBagConstraints.CENTER;
        return this;
    }

    public DCol ipad(int size) {
        layout.ipadx = size;
        layout.ipady = size;
        return this;
    }

    public DCol ipadx(int size) {
        layout.ipadx = size;
        return this;
    }

    public DCol ipady(int size) {
        layout.ipady = size;
        return this;
    }

    public DCol insets(int size) {
        layout.insets.top = size;
        layout.insets.left = size;
        layout.insets.bottom = size;
        layout.insets.right = size;
        return this;
    }

    public DCol insetsTop(int size) {
        layout.insets.top = size;
        return this;
    }

    public DCol insetsLeft(int size) {
        layout.insets.left = size;
        return this;
    }

    public DCol insetsBottom(int size) {
        layout.insets.bottom = size;
        return this;
    }

    public DCol insetsRight(int size) {
        layout.insets.right = size;
        return this;
    }

    public DCol insets(int topBottom, int leftRight) {
        layout.insets.top = topBottom;
        layout.insets.left = leftRight;
        layout.insets.bottom = topBottom;
        layout.insets.right = leftRight;
        return this;
    }

    public DCol insets(int top, int left, int bottom, int right) {
        layout.insets.top = top;
        layout.insets.left = left;
        layout.insets.bottom = bottom;
        layout.insets.right = right;
        return this;
    }

    public DCol growNone() {
        layout.weightx = 0;
        layout.weighty = 0;
        layout.fill = GridBagConstraints.NONE;
        return this;
    }

    public DCol growBoth() {
        layout.weightx = 1;
        layout.weighty = 1;
        layout.fill = GridBagConstraints.BOTH;
        return this;
    }

    public DCol growHorizontal() {
        layout.weightx = 1;
        layout.weighty = 0;
        layout.fill = GridBagConstraints.HORIZONTAL;
        return this;
    }

    public DCol growVertical() {
        layout.weightx = 0;
        layout.weighty = 1;
        layout.fill = GridBagConstraints.VERTICAL;
        return this;
    }

    public DCol weightBoth(double weight) {
        layout.weightx = weight;
        layout.weighty = weight;
        return this;
    }

    public DCol weightHorizontal(double weight) {
        layout.weightx = weight;
        layout.weighty = 0;
        return this;
    }

    public DCol weightVertical(double weight) {
        layout.weightx = 0;
        layout.weighty = weight;
        return this;
    }

    public DCol weightEach(double weightX, double weightY) {
        layout.weightx = weightX;
        layout.weighty = weightY;
        return this;
    }

    public DCol put(Component component) {
        layout.gridy += 1;
        add(component, layout);
        return this;
    }

}
