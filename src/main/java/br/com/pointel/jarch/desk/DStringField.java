package br.com.pointel.jarch.desk;

public class DStringField extends DFieldEdit<String> {

    public DStringField() {
        this("");
    }

    public DStringField(String value) {
        super(value);
    }

    public String getValue() {
        return comp().getText();
    }

    public void setValue(String value) {
        comp().setText(value);
    }

}
