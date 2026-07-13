package com.vidlus.jarch.desk;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

/**
 * A fluent API wrapper for JMenuBar to easily build top-level application menus.
 */
public class DMenuBar extends JMenuBar {

    /**
     * Creates a new DMenuBar.
     */
    public DMenuBar() {
        super();
    }

    /**
     * Appends the specified menu to the end of the menu bar.
     * 
     * @param c the JMenu component to add
     * @return This DMenuBar instance.
     */
    public DMenuBar menu(JMenu c) {
        add(c);
        return this;
    }

    /**
     * Sets the font of this component.
     * 
     * @param font the desired Font for this component
     * @return This DMenuBar instance.
     */
    public DMenuBar font(Font font) {
        setFont(font);
        return this;
    }

    /**
     * Sets the foreground color of this component.
     * 
     * @param fg the desired foreground Color
     * @return This DMenuBar instance.
     */
    public DMenuBar foreground(Color fg) {
        setForeground(fg);
        return this;
    }

    /**
     * Sets the background color of this component.
     * 
     * @param bg the desired background Color
     * @return This DMenuBar instance.
     */
    public DMenuBar background(Color bg) {
        setBackground(bg);
        return this;
    }

    /**
     * Sets whether the menu bar is enabled.
     * 
     * @param b true to enable, false to disable
     * @return This DMenuBar instance.
     */
    public DMenuBar enabled(boolean b) {
        setEnabled(b);
        return this;
    }

    /**
     * Sets whether the menu bar is visible.
     * 
     * @param b true to make visible, false to hide
     * @return This DMenuBar instance.
     */
    public DMenuBar visible(boolean b) {
        setVisible(b);
        return this;
    }

    /**
     * Sets whether the menu bar is opaque.
     * 
     * @param b true to make opaque, false to make transparent
     * @return This DMenuBar instance.
     */
    public DMenuBar opaque(boolean b) {
        setOpaque(b);
        return this;
    }

    /**
     * Sets the name of this menu bar.
     * 
     * @param name the name to set
     * @return This DMenuBar instance.
     */
    public DMenuBar name(String name) {
        setName(name);
        return this;
    }

    /**
     * Sets the preferred size of this menu bar.
     * 
     * @param preferredSize the preferred size
     * @return This DMenuBar instance.
     */
    public DMenuBar preferredSize(Dimension preferredSize) {
        setPreferredSize(preferredSize);
        return this;
    }

    /**
     * Sets whether the border should be painted.
     * 
     * @param b if true, the border is painted
     * @return This DMenuBar instance.
     */
    public DMenuBar borderPainted(boolean b) {
        setBorderPainted(b);
        return this;
    }

    /**
     * Sets the margin between the menu bar's border and its menus.
     * 
     * @param m the margin
     * @return This DMenuBar instance.
     */
    public DMenuBar margin(Insets m) {
        setMargin(m);
        return this;
    }

    /**
     * Registers the text to display in a tool tip.
     * 
     * @param text the string to display
     * @return This DMenuBar instance.
     */
    public DMenuBar toolTip(String text) {
        setToolTipText(text);
        return this;
    }
}
