package br.com.pointel.jarch.desk;

public class DIntegerField extends DFieldEdit<Integer> {

    public DIntegerField() {
        this(0);
    }

    public DIntegerField(Integer value) {
        super(value == null ? "" : value.toString());
    }

    public Integer getValue() {
        return comp().getText() == null || comp().getText().isEmpty() 
                ? null : Integer.parseInt(comp().getText());
    }

    public void setValue(Integer value) {
        comp().setText(value == null ? "" : value.toString());
    }

}
