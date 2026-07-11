package com.vidlus.jarch.desk;

import java.awt.BorderLayout;
import java.awt.Component;

public class DPaneBord extends DPane {

    public DPaneBord() {
        super(new BorderLayout(2, 2));
    }

    public DPaneBord gap(int hgap, int vgap) {
        ((BorderLayout) getLayout()).setHgap(hgap);
        ((BorderLayout) getLayout()).setVgap(vgap);
        return this;
    }
    
    public DPaneBord putNorth(DEdit<?> edit) {
        return putNorth(edit.comp());
    }

    public DPaneBord putSouth(DEdit<?> edit) {
        return putSouth(edit.comp());
    }

    public DPaneBord putEast(DEdit<?> edit) {
        return putEast(edit.comp());
    }

    public DPaneBord putWest(DEdit<?> edit) {
        return putWest(edit.comp());
    }

    public DPaneBord putCenter(DEdit<?> edit) {
        return putCenter(edit.comp());
    }

    public DPaneBord putNorth(Component comp) {
        add(comp, BorderLayout.NORTH);
        return this;
    }

    public DPaneBord putSouth(Component comp) {
        add(comp, BorderLayout.SOUTH);
        return this;
    }

    public DPaneBord putEast(Component comp) {
        add(comp, BorderLayout.EAST);
        return this;
    }

    public DPaneBord putWest(Component comp) {
        add(comp, BorderLayout.WEST);
        return this;
    }

    public DPaneBord putCenter(Component comp) {
        add(comp, BorderLayout.CENTER);
        return this;
    }
    
}
