package com.vidlus.jarch.desk;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * A fluent wrapper for GridBagLayout.
 * Eliminates the verbosity of GridBagConstraints by maintaining an internal constraint state
 * that you can fluently modify before putting components into the grid.
 */
public class DPaneForm extends DPaneGridBag<DPaneForm> {

    public DPaneForm() {
        super();
        this.constraints.fill = GridBagConstraints.BOTH; // Default to filling cells
        this.constraints.insets = new Insets(2, 2, 2, 2); // Default slight padding
    }

    // --- Put Methods ---

    /**
     * Puts a component at the specified grid X and Y coordinates.
     */
    public DPaneForm put(Component comp, int x, int y) {
        return put(comp, x, y, 1, 1);
    }

    public DPaneForm put(DEdit<?> edit, int x, int y) {
        return put(edit.comp(), x, y);
    }

    /**
     * Puts a component at the specified grid X and Y coordinates, spanning multiple columns.
     */
    public DPaneForm putSpan(Component comp, int x, int y, int widthSpan) {
        return put(comp, x, y, widthSpan, 1);
    }

    public DPaneForm putSpan(DEdit<?> edit, int x, int y, int widthSpan) {
        return putSpan(edit.comp(), x, y, widthSpan);
    }

    /**
     * Puts a component at the specified grid X and Y coordinates, spanning multiple columns and rows.
     */
    public DPaneForm put(Component comp, int x, int y, int widthSpan, int heightSpan) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = widthSpan;
        constraints.gridheight = heightSpan;
        
        // GridBagLayout clones the constraints, so modifying the state object later is perfectly safe.
        GridBagLayout gbl = (GridBagLayout) getLayout();
        gbl.setConstraints(comp, constraints);
        add(comp);
        return this;
    }

    public DPaneForm put(DEdit<?> edit, int x, int y, int widthSpan, int heightSpan) {
        return put(edit.comp(), x, y, widthSpan, heightSpan);
    }
}
