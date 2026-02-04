package br.com.pointel.jarch.desk;

import javax.swing.JTextField;

public abstract class DFieldEdit<T> extends DEdit<T> {

    public DFieldEdit() {
        super(new JTextField());
    }

    public DFieldEdit(String text) {
        super(new JTextField(text));
    }

    public DFieldEdit(Integer columns) {
        super(new JTextField(columns));
    }

    public DFieldEdit(String text, Integer columns) {
        super(new JTextField(text, columns));
    }

    @Override
    public JTextField comp() {
        return (JTextField) super.comp();
    }

    public T value() {
        return getValue();
    }

    public DFieldEdit<T> value(T value) {
        setValue(value);
        return this;
    }

    public int cols() {
        return comp().getColumns();
    }

    public DFieldEdit<T> cols(int cols) {
        comp().setColumns(cols);
        return this;
    }

}
