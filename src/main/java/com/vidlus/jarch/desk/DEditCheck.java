package com.vidlus.jarch.desk;

import javax.swing.JCheckBox;

public class DEditCheck extends DEdit<Boolean> {

    public DEditCheck() {
        this(false);
    }

    public DEditCheck(Boolean value) {
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

    public boolean isSelected() {
        return comp().isSelected();
    }

    public DEditCheck setSelected(boolean selected) {
        comp().setSelected(selected);
        return this;
    }

    public String text() {
        return comp().getText();
    }

    public DEditCheck text(String text) {
        comp().setText(text);
        return this;
    }

}
