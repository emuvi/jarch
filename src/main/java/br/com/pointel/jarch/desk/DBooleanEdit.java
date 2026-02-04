package br.com.pointel.jarch.desk;

import javax.swing.JCheckBox;

public class DBooleanEdit extends DEdit<Boolean> {

    public DBooleanEdit() {
        this(false);
    }

    public DBooleanEdit(Boolean value) {
        super(new JCheckBox());
        setValue(value);
    }

    @Override
    public JCheckBox comp() {
        return (JCheckBox) super.comp();
    }

    @Override
    public Boolean getValue() {
        return comp().isSelected();
    }

    @Override
    public void setValue(Boolean value) {
        comp().setSelected(value != null && value);
    }

    public String text() {
        return comp().getText();
    }

    public DBooleanEdit text(String text) {
        comp().setText(text);
        return this;
    }

}
