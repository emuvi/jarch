package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;


import javax.swing.Icon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * A fluent API wrapper for JMenu to easily build nested menus.
 */
public class DMenu extends JMenu {

    /**
     * Constructs a new DMenu with no text.
     */
    public DMenu() {
        super();
    }

    /**
     * Constructs a new DMenu with the supplied string as its text.
     *
     * @param s the text for the menu label
     */
    public DMenu(String s) {
        super(s);
    }

    /**
     * Constructs a menu whose properties are taken from the Action supplied.
     *
     * @param a an Action
     */
    public DMenu(DAction a) {
        super(a);
    }

    /**
     * Constructs a new DMenu with the supplied string as its text and specified as a tear-off menu or not.
     *
     * @param s the text for the menu label
     * @param b can the menu be torn off (not yet implemented)
     */
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

    /**
     * Sets the text of this menu.
     * 
     * @param text the text to set
     * @return This DMenu instance.
     */
    public DMenu text(String text) {
        setText(text);
        return this;
    }

    /**
     * Sets the icon of this menu.
     * 
     * @param icon the icon to set
     * @return This DMenu instance.
     */
    public DMenu icon(Icon icon) {
        setIcon(icon);
        return this;
    }

    /**
     * Sets whether the menu is enabled.
     * 
     * @param b true to enable, false to disable
     * @return This DMenu instance.
     */
    public DMenu enabled(boolean b) {
        setEnabled(b);
        return this;
    }

    /**
     * Sets whether the menu is visible.
     * 
     * @param b true to make visible, false to hide
     * @return This DMenu instance.
     */
    public DMenu visible(boolean b) {
        setVisible(b);
        return this;
    }

    /**
     * Sets whether the menu is opaque.
     * 
     * @param b true to make opaque, false to make transparent
     * @return This DMenu instance.
     */
    public DMenu opaque(boolean b) {
        setOpaque(b);
        return this;
    }

    /**
     * Sets the name of this menu.
     * 
     * @param name the name to set
     * @return This DMenu instance.
     */
    public DMenu name(String name) {
        setName(name);
        return this;
    }

    /**
     * Sets the preferred size of this menu.
     * 
     * @param preferredSize the preferred size
     * @return This DMenu instance.
     */
    public DMenu preferredSize(Dimension preferredSize) {
        setPreferredSize(preferredSize);
        return this;
    }

    /**
     * Sets the action command for this menu.
     * 
     * @param command the action command
     * @return This DMenu instance.
     */
    public DMenu actionCommand(String command) {
        setActionCommand(command);
        return this;
    }

    /**
     * Sets the delay in milliseconds before the menu's popup menu appears or disappears.
     * 
     * @param d the delay in milliseconds
     * @return This DMenu instance.
     */
    public DMenu delay(int d) {
        setDelay(d);
        return this;
    }
}
