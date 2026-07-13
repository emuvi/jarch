package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;


import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * A fluent API wrapper for JMenuItem to easily build items for menus.
 */
public class DMenuItem extends JMenuItem {

    /**
     * Creates a DMenuItem with no set text or icon.
     */
    public DMenuItem() {
        super();
    }

    /**
     * Creates a DMenuItem with the specified icon.
     *
     * @param icon the icon of the DMenuItem
     */
    public DMenuItem(Icon icon) {
        super(icon);
    }

    /**
     * Creates a DMenuItem with the specified text.
     *
     * @param text the text of the DMenuItem
     */
    public DMenuItem(String text) {
        super(text);
    }

    /**
     * Creates a menu item whose properties are taken from the specified Action.
     *
     * @param a the action of the DMenuItem
     */
    public DMenuItem(DAction a) {
        super(a);
    }

    /**
     * Creates a DMenuItem with the specified text and icon.
     *
     * @param text the text of the DMenuItem
     * @param icon the icon of the DMenuItem
     */
    public DMenuItem(String text, Icon icon) {
        super(text, icon);
    }

    /**
     * Creates a DMenuItem with the specified text and keyboard mnemonic.
     *
     * @param text the text of the DMenuItem
     * @param mnemonic the keyboard mnemonic for the DMenuItem
     */
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

    /**
     * Sets the text of this menu item.
     * 
     * @param text the text to set
     * @return This DMenuItem instance.
     */
    public DMenuItem text(String text) {
        setText(text);
        return this;
    }

    /**
     * Sets the icon of this menu item.
     * 
     * @param icon the icon to set
     * @return This DMenuItem instance.
     */
    public DMenuItem icon(Icon icon) {
        setIcon(icon);
        return this;
    }

    /**
     * Sets whether the menu item is enabled.
     * 
     * @param b true to enable, false to disable
     * @return This DMenuItem instance.
     */
    public DMenuItem enabled(boolean b) {
        setEnabled(b);
        return this;
    }

    /**
     * Sets whether the menu item is visible.
     * 
     * @param b true to make visible, false to hide
     * @return This DMenuItem instance.
     */
    public DMenuItem visible(boolean b) {
        setVisible(b);
        return this;
    }

    /**
     * Sets whether the menu item is opaque.
     * 
     * @param b true to make opaque, false to make transparent
     * @return This DMenuItem instance.
     */
    public DMenuItem opaque(boolean b) {
        setOpaque(b);
        return this;
    }

    /**
     * Sets the name of this menu item.
     * 
     * @param name the name to set
     * @return This DMenuItem instance.
     */
    public DMenuItem name(String name) {
        setName(name);
        return this;
    }

    /**
     * Sets the preferred size of this menu item.
     * 
     * @param preferredSize the preferred size
     * @return This DMenuItem instance.
     */
    public DMenuItem preferredSize(Dimension preferredSize) {
        setPreferredSize(preferredSize);
        return this;
    }

    /**
     * Sets the action command for this menu item.
     * 
     * @param command the action command
     * @return This DMenuItem instance.
     */
    public DMenuItem actionCommand(String command) {
        setActionCommand(command);
        return this;
    }
}
