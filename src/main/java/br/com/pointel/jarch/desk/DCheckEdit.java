package br.com.pointel.jarch.desk;

import javax.swing.JCheckBox;

public class DCheckEdit extends DEdit<Boolean> {

    public DCheckEdit() {
        this(false);
    }

    public DCheckEdit(Boolean value) {
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

    public DCheckEdit text(String text) {
        comp().setText(text);
        return this;
    }

}
