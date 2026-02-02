package br.com.pointel.jarch.desk;

public class DString extends DField<String> {

    public DString() {
        this("");
    }

    public DString(String value) {
        super(value);
    }

    public String getValue() {
        return getText();
    }

    public void setValue(String value) {
        setText(value);
    }

}
