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

    public int horizontalAlignment() {
        return comp().getHorizontalAlignment();
    }

    public DFieldEdit<T> horizontalAlignment(int alignment) {
        comp().setHorizontalAlignment(alignment);
        return this;
    }

    public DFieldEdit<T> horizontalAlignmentLeft() {
        comp().setHorizontalAlignment(JTextField.LEFT);
        return this;
    }

    public DFieldEdit<T> horizontalAlignmentCenter() {
        comp().setHorizontalAlignment(JTextField.CENTER);
        return this;
    }

    public DFieldEdit<T> horizontalAlignmentRight() {
        comp().setHorizontalAlignment(JTextField.RIGHT);
        return this;
    }

    public DFieldEdit<T> horizontalAlignmentLeading() {
        comp().setHorizontalAlignment(JTextField.LEADING);
        return this;
    }

    public DFieldEdit<T> horizontalAlignmentTrailing() {
        comp().setHorizontalAlignment(JTextField.TRAILING);
        return this;
    }

}
