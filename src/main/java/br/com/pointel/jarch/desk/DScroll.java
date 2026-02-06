package br.com.pointel.jarch.desk;

import java.awt.Component;

import javax.swing.JScrollPane;

public class DScroll extends JScrollPane {
    
    public DScroll() {
        super();
    }

    public DScroll(Component comp) {
        super(comp);
    }

    public DScroll(DEdit<?> edit) {
        super(edit.comp());
    }
    public DScroll horizontalAlways() {
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        return this;
    }

    public DScroll horizontalNever() {
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return this;
    }

    public DScroll verticalAlways() {
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return this;
    }

    public DScroll verticalNever() {
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        return this;
    }

    public DScroll horizontalAsNeeded() {
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        return this;
    }

    public DScroll verticalAsNeeded() {
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return this;
    }

    public String name() {
        return getName();
    }

    public DScroll name(String name) {
        setName(name);
        return this;
    }

}
