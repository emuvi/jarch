package br.com.pointel.jarch.desk;

import java.awt.BorderLayout;

public class DLabelEdit<T> extends DPane implements DValue<T> {

    private final DLabel label = new DLabel();
    private final DEdit<T> edit;

    public DLabelEdit(String title, DEdit<T> edit) {
        super(new BorderLayout(2, 2));
        this.label.setText(title);
        this.edit = edit;
        add(this.label, BorderLayout.NORTH);
        add(edit.comp(), BorderLayout.CENTER);
    }

    public DLabel label() {
        return label;
    }

    public DEdit<T> edit() {
        return edit;
    }

    @Override
    public T getValue() {
        return edit.getValue();
    }

    @Override
    public void setValue(T value) {
        edit.setValue(value);
    }

    public DLabelEdit<T> text(String text) {
        label.setText(text);
        return this;
    }

    public DLabelEdit<T> labelOnNorth() {
        remove(label);
        add(label, BorderLayout.NORTH);
        return this;
    }

    public DLabelEdit<T> labelOnSouth() {
        remove(label);
        add(label, BorderLayout.SOUTH);
        return this;
    }

    public DLabelEdit<T> labelOnEast() {
        remove(label);
        add(label, BorderLayout.EAST);
        return this;
    }

    public DLabelEdit<T> labelOnWest() {
        remove(label);
        add(label, BorderLayout.WEST);
        return this;
    }

}
