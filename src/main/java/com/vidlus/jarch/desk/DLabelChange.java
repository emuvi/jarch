package com.vidlus.jarch.desk;

import java.awt.BorderLayout;

public class DLabelChange<T> extends DPane implements DValue<T> {

    private final DLabel label = new DLabel();
    private final DEditChange<T> change;

    public DLabelChange(String title, DEditChange<T> change) {
        super(new BorderLayout(2, 2));
        this.label.setText(title);
        this.change = change;
        add(this.label, BorderLayout.NORTH);
        add(change.comp(), BorderLayout.CENTER);
    }

    public DLabel label() {
        return label;
    }

    public DEditChange<T> change() {
        return change;
    }

    @Override
    public T getValue() {
        return change.getValue();
    }

    @Override
    public void setValue(T value) {
        change.setValue(value);
    }

    public DLabelChange<T> text(String text) {
        label.setText(text);
        return this;
    }

    public DLabelChange<T> labelOnNorth() {
        remove(label);
        add(label, BorderLayout.NORTH);
        return this;
    }

    public DLabelChange<T> labelOnSouth() {
        remove(label);
        add(label, BorderLayout.SOUTH);
        return this;
    }

    public DLabelChange<T> labelOnEast() {
        remove(label);
        add(label, BorderLayout.EAST);
        return this;
    }

    public DLabelChange<T> labelOnWest() {
        remove(label);
        add(label, BorderLayout.WEST);
        return this;
    }

}
