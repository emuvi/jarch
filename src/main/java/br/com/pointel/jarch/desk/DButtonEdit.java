package br.com.pointel.jarch.desk;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

public class DButtonEdit<T> extends DPane implements DValue<T> {

    private final DButton button = new DButton();
    private final DEdit<T> edit;

    public DButtonEdit(DEdit<T> edit) {
        this("*", edit);
    }

    public DButtonEdit(String title, DEdit<T> edit) {
        super(new BorderLayout(2, 2));
        this.button.setText(title);
        this.edit = edit;
        add(this.button, BorderLayout.NORTH);
        add(edit.comp(), BorderLayout.CENTER);
    }

    public DButton button() {
        return button;
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

    public DButtonEdit<T> onClick(ActionListener listener) {
        button.addActionListener(listener);
        return this;
    }

    public DButtonEdit<T> text(String text) {
        button.setText(text);
        return this;
    }

    public DButtonEdit<T> enabled(boolean enabled) {
        button.setEnabled(enabled);
        return this;
    }

    public DButtonEdit<T> buttonOnNorth() {
        remove(button);
        add(button, BorderLayout.NORTH);
        return this;
    }

    public DButtonEdit<T> buttonOnSouth() {
        remove(button);
        add(button, BorderLayout.SOUTH);
        return this;
    }

    public DButtonEdit<T> buttonOnEast() {
        remove(button);
        add(button, BorderLayout.EAST);
        return this;
    }

    public DButtonEdit<T> buttonOnWest() {
        remove(button);
        add(button, BorderLayout.WEST);
        return this;
    }

}
