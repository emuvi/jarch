package br.com.pointel.jarch.desk;

import javax.swing.JTextField;

public abstract class DFieldEdit<T> extends DEdit<T> {

    public DFieldEdit() {
        super(new JTextField());
    }

    public DFieldEdit(String text) {
        super(new JTextField(text));
    }

    public DFieldEdit(int cols) {
        super(new JTextField(cols));
    }

    public DFieldEdit(String text, int cols) {
        super(new JTextField(text, cols));
    }

    @Override
    public JTextField comp() {
        return (JTextField) super.comp();
    }

    public int cols() {
        return comp().getColumns();
    }

    public DFieldEdit<T> cols(int cols) {
        comp().setColumns(cols);
        return this;
    }

}
