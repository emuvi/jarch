package com.vidlus.jarch.desk;

public class DFieldString extends DEditField<String> {

    public DFieldString() {
        this("");
    }

    public DFieldString(String value) {
        super(value);
    }

    public String getValue() {
        return comp().getText();
    }

    public void setValue(String value) {
        comp().setText(value);
    }

}
