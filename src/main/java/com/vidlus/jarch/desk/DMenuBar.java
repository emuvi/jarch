package com.vidlus.jarch.desk;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import java.awt.Color;
import java.awt.Font;

/**
 * A fluent API wrapper for JMenuBar to easily build top-level application menus.
 */
public class DMenuBar extends JMenuBar {

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
}
