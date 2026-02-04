package br.com.pointel.jarch.desk;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;

public class DRowPane extends DPane {

    private final GridBagConstraints layout = new GridBagConstraints();

    public DRowPane() {
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

    public DRowPane anchorNorth() {
        layout.anchor = GridBagConstraints.NORTH;
        return this;
    }

    public DRowPane anchorNorthEast() {
        layout.anchor = GridBagConstraints.NORTHEAST;
        return this;
    }

    public DRowPane anchorNorthWest() {
        layout.anchor = GridBagConstraints.NORTHWEST;
        return this;
    }

    public DRowPane anchorSouth() {
        layout.anchor = GridBagConstraints.SOUTH;
        return this;
    }

    public DRowPane anchorSouthEast() {
        layout.anchor = GridBagConstraints.SOUTHEAST;
        return this;
    }

    public DRowPane anchorSouthWest() {
        layout.anchor = GridBagConstraints.SOUTHWEST;
        return this;
    }

    public DRowPane anchorEast() {
        layout.anchor = GridBagConstraints.EAST;
        return this;
    }

    public DRowPane anchorWest() {
        layout.anchor = GridBagConstraints.WEST;
        return this;
    }

    public DRowPane anchorCenter() {
        layout.anchor = GridBagConstraints.CENTER;
        return this;
    }

    public DRowPane ipad(int size) {
        layout.ipadx = size;
        layout.ipady = size;
        return this;
    }

    public DRowPane ipadx(int size) {
        layout.ipadx = size;
        return this;
    }

    public DRowPane ipady(int size) {
        layout.ipady = size;
        return this;
    }

    public DRowPane insets(int size) {
        layout.insets.top = size;
        layout.insets.left = size;
        layout.insets.bottom = size;
        layout.insets.right = size;
        return this;
    }

    public DRowPane insetsTop(int size) {
        layout.insets.top = size;
        return this;
    }

    public DRowPane insetsLeft(int size) {
        layout.insets.left = size;
        return this;
    }

    public DRowPane insetsBottom(int size) {
        layout.insets.bottom = size;
        return this;
    }

    public DRowPane insetsRight(int size) {
        layout.insets.right = size;
        return this;
    }

    public DRowPane insets(int topBottom, int leftRight) {
        layout.insets.top = topBottom;
        layout.insets.left = leftRight;
        layout.insets.bottom = topBottom;
        layout.insets.right = leftRight;
        return this;
    }

    public DRowPane insets(int top, int left, int bottom, int right) {
        layout.insets.top = top;
        layout.insets.left = left;
        layout.insets.bottom = bottom;
        layout.insets.right = right;
        return this;
    }

    public DRowPane growNone() {
        layout.weightx = 0;
        layout.weighty = 0;
        layout.fill = GridBagConstraints.NONE;
        return this;
    }

    public DRowPane growBoth() {
        layout.weightx = 1;
        layout.weighty = 1;
        layout.fill = GridBagConstraints.BOTH;
        return this;
    }

    public DRowPane growHorizontal() {
        layout.weightx = 1;
        layout.weighty = 0;
        layout.fill = GridBagConstraints.HORIZONTAL;
        return this;
    }

    public DRowPane growVertical() {
        layout.weightx = 0;
        layout.weighty = 1;
        layout.fill = GridBagConstraints.VERTICAL;
        return this;
    }

    public DRowPane weightBoth(double weight) {
        layout.weightx = weight;
        layout.weighty = weight;
        return this;
    }

    public DRowPane weightHorizontal(double weight) {
        layout.weightx = weight;
        layout.weighty = 0;
        return this;
    }

    public DRowPane weightVertical(double weight) {
        layout.weightx = 0;
        layout.weighty = weight;
        return this;
    }

    public DRowPane weightEach(double weightX, double weightY) {
        layout.weightx = weightX;
        layout.weighty = weightY;
        return this;
    }

    public DRowPane put(DEdit<?> edit) {
        put(edit.comp());
        return this;
    }

    @Override
    public DRowPane put(Component comp) {
        layout.gridx += 1;
        add(comp, layout);
        return this;
    }

}
