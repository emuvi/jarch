package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;


import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * A fluent API wrapper for JMenuItem to easily build items for menus.
 */
public class DMenuItem extends JMenuItem {

    public DMenuItem() {
        super();
    }

    public DMenuItem(Icon icon) {
        super(icon);
    }

    public DMenuItem(String text) {
        super(text);
    }

    public DMenuItem(DAction a) {
        super(a);
    }

    public DMenuItem(String text, Icon icon) {
        super(text, icon);
    }

    public DMenuItem(String text, int mnemonic) {
        super(text, mnemonic);
    }

    /**
     * Adds an ActionListener to the menu item.
     * 
     * @param listener the ActionListener to be added
     * @return This DMenuItem instance.
     */
    public DMenuItem onAction(ActionListener listener) {
        addActionListener(listener);
        return this;
    }

    /**
     * Sets the Action for the menu item.
     * 
     * @param a the Action for the menu item
     * @return This DMenuItem instance.
     */
    public DMenuItem action(DAction a) {
        setAction(a);
        return this;
    }

    /**
     * Sets the key combination which invokes the menu item's action listeners without navigating the menu hierarchy.
     * 
     * @param keyStroke the KeyStroke which will serve as an accelerator
     * @return This DMenuItem instance.
     */
    public DMenuItem accelerator(KeyStroke keyStroke) {
        setAccelerator(keyStroke);
        return this;
    }

    /**
     * Sets the keyboard mnemonic on the current model.
     * 
     * @param mnemonic the key code which represents the mnemonic
     * @return This DMenuItem instance.
     */
    public DMenuItem mnemonic(int mnemonic) {
        setMnemonic(mnemonic);
        return this;
    }

    /**
     * Registers the text to display in a tool tip.
     * 
     * @param text the string to display
     * @return This DMenuItem instance.
     */
    public DMenuItem toolTip(String text) {
        setToolTipText(text);
        return this;
    }

    /**
     * Sets the font of this component.
     * 
     * @param font the desired Font for this component
     * @return This DMenuItem instance.
     */
    public DMenuItem font(Font font) {
        setFont(font);
        return this;
    }

    /**
     * Sets the foreground color of this component.
     * 
     * @param fg the desired foreground Color
     * @return This DMenuItem instance.
     */
    public DMenuItem foreground(Color fg) {
        setForeground(fg);
        return this;
    }

    /**
     * Sets the background color of this component.
     * 
     * @param bg the desired background Color
     * @return This DMenuItem instance.
     */
    public DMenuItem background(Color bg) {
        setBackground(bg);
        return this;
    }
}
