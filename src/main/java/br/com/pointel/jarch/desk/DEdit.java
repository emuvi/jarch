package br.com.pointel.jarch.desk;

import javax.swing.JComponent;

public abstract class DEdit<T> implements DValue<T> {

    private JComponent comp;

    public DEdit() {
        this.comp = null;
    }

    public DEdit(JComponent comp) {
        this.comp = comp;
    }

    public JComponent comp() {
        return comp;
    }

    public DEdit<T> comp(JComponent comp) {
        this.comp = comp;
        return this;
    }

    public T value() {
        return getValue();
    }

    public DEdit<T> value(T value) {
        setValue(value);
        return this;
    }

    public String name() {
        return comp().getName();
    }

    public DEdit<T> name(String name) {
        comp().setName(name);
        return this;
    }

    public DEdit<T> hint(String hint) {
        comp().setToolTipText(hint);
        return this;
    }

    public String hint() {
        return comp().getToolTipText();
    }

}
