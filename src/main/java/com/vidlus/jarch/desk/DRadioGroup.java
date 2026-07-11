package com.vidlus.jarch.desk;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

/**
 * A fluent API wrapper for ButtonGroup to easily group radio buttons and toggle buttons.
 */
public class DRadioGroup extends ButtonGroup {

    /**
     * Creates a new empty ButtonGroup.
     */
    public DRadioGroup() {
        super();
    }

    /**
     * Creates a new ButtonGroup and populates it with the specified buttons.
     * 
     * @param buttons the buttons to group together
     */
    public DRadioGroup(AbstractButton... buttons) {
        super();
        radios(buttons);
    }

    /**
     * Adds the button to the group.
     * 
     * @param b the button to be added
     * @return This DRadioGroup instance.
     */
    public DRadioGroup radio(AbstractButton b) {
        add(b);
        return this;
    }

    /**
     * Adds multiple buttons to the group.
     * 
     * @param buttons the buttons to be added
     * @return This DRadioGroup instance.
     */
    public DRadioGroup radios(AbstractButton... buttons) {
        for (AbstractButton b : buttons) {
            add(b);
        }
        return this;
    }
}
