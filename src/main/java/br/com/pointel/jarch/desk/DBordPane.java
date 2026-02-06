package br.com.pointel.jarch.desk;

import java.awt.BorderLayout;
import java.awt.Component;

public class DBordPane extends DPane {

    public DBordPane() {
        super(new BorderLayout(2, 2));
    }

    public DBordPane gap(int hgap, int vgap) {
        ((BorderLayout) getLayout()).setHgap(hgap);
        ((BorderLayout) getLayout()).setVgap(vgap);
        return this;
    }
    
    public DBordPane putNorth(DEdit<?> edit) {
        return putNorth(edit.comp());
    }

    public DBordPane putSouth(DEdit<?> edit) {
        return putSouth(edit.comp());
    }

    public DBordPane putEast(DEdit<?> edit) {
        return putEast(edit.comp());
    }

    public DBordPane putWest(DEdit<?> edit) {
        return putWest(edit.comp());
    }

    public DBordPane putCenter(DEdit<?> edit) {
        return putCenter(edit.comp());
    }

    public DBordPane putNorth(Component comp) {
        add(comp, BorderLayout.NORTH);
        return this;
    }

    public DBordPane putSouth(Component comp) {
        add(comp, BorderLayout.SOUTH);
        return this;
    }

    public DBordPane putEast(Component comp) {
        add(comp, BorderLayout.EAST);
        return this;
    }

    public DBordPane putWest(Component comp) {
        add(comp, BorderLayout.WEST);
        return this;
    }

    public DBordPane putCenter(Component comp) {
        add(comp, BorderLayout.CENTER);
        return this;
    }
    

}
