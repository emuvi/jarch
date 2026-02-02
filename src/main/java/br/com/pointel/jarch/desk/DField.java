package br.com.pointel.jarch.desk;

import javax.swing.JTextField;

public abstract class DField<T> extends JTextField {

    public DField() {
        super();
    }

    public DField(String text) {
        super(text);
    }

    public DField(Integer columns) {
        super(columns);
    }

    public DField(String text, Integer columns) {
        super(text, columns);
    }

    public abstract T getValue();

    public abstract void setValue(T value);

    public T value() {
        return getValue();
    }

    public DField<T> value(T value) {
        setValue(value);
        return this;
    }

    public int cols() {
        return getColumns();
    }

    public DField<T> cols(int cols) {
        setColumns(cols);
        return this;
    }

    public String name() {
        return getName();
    }

    public DField<T> name(String name) {
        setName(name);
        return this;
    }

}
