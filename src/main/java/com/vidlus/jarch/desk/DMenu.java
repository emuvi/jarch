package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Font;


import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * A fluent API wrapper for JMenu to easily build nested menus.
 */
public class DMenu extends JMenu {

    public DMenu() {
        super();
    }

    public DMenu(String s) {
        super(s);
    }

    public DMenu(DAction a) {
        super(a);
    }

    public DMenu(String s, boolean b) {
        super(s, b);
    }

    /**
     * Appends a menu item to the end of this menu.
     * 
     * @param menuItem the JMenuItem to be added
     * @return This DMenu instance.
     */
    public DMenu item(JMenuItem menuItem) {
        add(menuItem);
        return this;
    }

    /**
     * Creates a new menu item attached to the specified Action object and appends it to the end of this menu.
     * 
     * @param a the Action for the menu item to be added
     * @return This DMenu instance.
     */
    public DMenu item(DAction a) {
        add(a);
        return this;
    }

    /**
     * Creates a new menu item with the specified text and appends it to the end of this menu.
     * 
     * @param s the string for the menu item to be added
     * @return This DMenu instance.
     */
    public DMenu item(String s) {
        add(s);
        return this;
    }

    /**
     * Appends a new separator to the end of the menu.
     * 
     * @return This DMenu instance.
     */
    public DMenu separator() {
        addSeparator();
        return this;
    }

    /**
     * Sets the keyboard mnemonic on the current model.
     * 
     * @param mnemonic the key code which represents the mnemonic
     * @return This DMenu instance.
     */
    public DMenu mnemonic(int mnemonic) {
        setMnemonic(mnemonic);
        return this;
    }

    /**
     * Registers the text to display in a tool tip.
     * 
     * @param text the string to display
     * @return This DMenu instance.
     */
    public DMenu toolTip(String text) {
        setToolTipText(text);
        return this;
    }

    /**
     * Sets the font of this component.
     * 
     * @param font the desired Font for this component
     * @return This DMenu instance.
     */
    public DMenu font(Font font) {
        setFont(font);
        return this;
    }

    /**
     * Sets the foreground color of this component.
     * 
     * @param fg the desired foreground Color
     * @return This DMenu instance.
     */
    public DMenu foreground(Color fg) {
        setForeground(fg);
        return this;
    }

    /**
     * Sets the background color of this component.
     * 
     * @param bg the desired background Color
     * @return This DMenu instance.
     */
    public DMenu background(Color bg) {
        setBackground(bg);
        return this;
    }
}
