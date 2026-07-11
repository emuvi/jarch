package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.Action;
import javax.swing.JToolBar;

/**
 * A fluent API wrapper for JToolBar to easily create and configure toolbars.
 */
public class DToolBar extends JToolBar {

    /**
     * Creates a new toolbar; orientation defaults to HORIZONTAL.
     */
    public DToolBar() {
        super();
    }

    /**
     * Creates a new toolbar with the specified orientation.
     * 
     * @param orientation the initial orientation -- it must be either HORIZONTAL or VERTICAL
     */
    public DToolBar(int orientation) {
        super(orientation);
    }

    /**
     * Creates a new toolbar with the specified name.
     * 
     * @param name the name of the toolbar
     */
    public DToolBar(String name) {
        super(name);
    }

    /**
     * Creates a new toolbar with a specified name and orientation.
     * 
     * @param name the name of the toolbar
     * @param orientation the initial orientation -- it must be either HORIZONTAL or VERTICAL
     */
    public DToolBar(String name, int orientation) {
        super(name, orientation);
    }

    /**
     * Appends a component to the end of this toolbar.
     * 
     * @param comp the component to be added
     * @return This DToolBar instance.
     */
    public DToolBar item(Component comp) {
        add(comp);
        return this;
    }

    /**
     * Adds a new JButton which dispatches the action.
     * 
     * @param a the Action object to add as a new menu item
     * @return This DToolBar instance.
     */
    public DToolBar action(Action a) {
        add(a);
        return this;
    }

    /**
     * Appends a separator of default size to the end of the tool bar.
     * 
     * @return This DToolBar instance.
     */
    public DToolBar separator() {
        addSeparator();
        return this;
    }

    /**
     * Appends a separator of a specified size to the end of the tool bar.
     * 
     * @param size the Dimension of the separator
     * @return This DToolBar instance.
     */
    public DToolBar separator(Dimension size) {
        addSeparator(size);
        return this;
    }

    /**
     * Sets the floatable property, which must be true for the user to move the tool bar.
     * 
     * @param b if true, the tool bar can be moved; false otherwise
     * @return This DToolBar instance.
     */
    public DToolBar floatable(boolean b) {
        setFloatable(b);
        return this;
    }

    /**
     * Sets the rollover state of this toolbar.
     * 
     * @param rollover if true, rollover borders are drawn; otherwise not
     * @return This DToolBar instance.
     */
    public DToolBar rollover(boolean rollover) {
        setRollover(rollover);
        return this;
    }

    /**
     * Sets the orientation of the tool bar.
     * 
     * @param o the new orientation -- either HORIZONTAL or VERTICAL
     * @return This DToolBar instance.
     */
    public DToolBar orientation(int o) {
        setOrientation(o);
        return this;
    }

    /**
     * Sets the margin between the tool bar's border and its buttons.
     * 
     * @param m an Insets object that defines the space between the border and the buttons
     * @return This DToolBar instance.
     */
    public DToolBar margin(Insets m) {
        setMargin(m);
        return this;
    }

    /**
     * Sets whether the border should be painted.
     * 
     * @param b if true, the border is painted; otherwise not
     * @return This DToolBar instance.
     */
    public DToolBar borderPainted(boolean b) {
        setBorderPainted(b);
        return this;
    }

    /**
     * Sets the font of this component.
     * 
     * @param font the desired Font for this component
     * @return This DToolBar instance.
     */
    public DToolBar font(Font font) {
        setFont(font);
        return this;
    }

    /**
     * Sets the foreground color of this component.
     * 
     * @param fg the desired foreground Color
     * @return This DToolBar instance.
     */
    public DToolBar foreground(Color fg) {
        setForeground(fg);
        return this;
    }

    /**
     * Sets the background color of this component.
     * 
     * @param bg the desired background Color
     * @return This DToolBar instance.
     */
    public DToolBar background(Color bg) {
        setBackground(bg);
        return this;
    }
}
