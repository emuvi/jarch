package br.com.pointel.jarch.desk;

import javax.swing.JComponent;

public abstract class DEdit<T> implements DValue<T> {

    private final JComponent comp;

    public DEdit(JComponent comp) {
        this.comp = comp;
    }

    public JComponent comp() {
        return comp;
    }

    public String name() {
        return comp().getName();
    }

    public DEdit<T> name(String name) {
        comp().setName(name);
        return this;
    }

}
