package br.com.pointel.jarch.desk;

import javax.swing.JTextArea;

public class DText extends DEdit<String> {

    public DText() {
        super(new JTextArea());
    }

    public DText(String text) {
        super(new JTextArea(text));
    }

    public DText(int rows, int cols) {
        super(new JTextArea(rows, cols));
    }

    public DText(String text, int rows, int cols) {
        super(new JTextArea(text, rows, cols));
    }

    @Override
    public JTextArea comp() {
        return (JTextArea) super.comp();
    }

    @Override
    public String getValue() {
        return comp().getText();
    }

    @Override
    public void setValue(String value) {
        comp().setText(value);
    }

    public String value() {
        return getValue();
    }

    public DText value(String value) {
        setValue(value);
        return this;
    }

    public int rows() {
        return comp().getRows();
    }

    public DText rows(int rows) {
        comp().setRows(rows);
        return this;
    }

    public int cols() {
        return comp().getColumns();
    }

    public DText cols(int cols) {
        comp().setColumns(cols);
        return this;
    }

    public boolean editable() {
        return comp().isEditable();
    }

    public DText editable(boolean editable) {
        comp().setEditable(editable);
        return this;
    }

    @Override
    public String name() {
        return comp().getName();
    }

    @Override
    public DText name(String name) {
        comp().setName(name);
        return this;
    }

}
