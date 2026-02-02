package br.com.pointel.jarch.desk;

public class DInteger extends DField<Integer> {

    public DInteger() {
        this(0);
    }

    public DInteger(Integer value) {
        super(value == null ? "" : value.toString());
    }

    public Integer getValue() {
        return getText() == null || getText().isEmpty() ? null : Integer.parseInt(getText());
    }

    public void setValue(Integer value) {
        setText(value == null ? "" : value.toString());
    }

}
