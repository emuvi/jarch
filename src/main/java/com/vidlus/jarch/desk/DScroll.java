package com.vidlus.jarch.desk;

import java.awt.Component;

import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import javax.swing.plaf.ScrollPaneUI;
import javax.swing.border.Border;

/**
 * A fluent API wrapper for JScrollPane to easily create and configure scroll panes.
 */
public class DScroll extends JScrollPane {
    
    /**
     * Creates an empty DScroll.
     */
    public DScroll() {
        super();
    }

    /**
     * Creates a DScroll that displays the contents of the specified component.
     * 
     * @param comp the component to display in the scroll pane's viewport
     */
    public DScroll(Component comp) {
        super(comp);
    }

    /**
     * Creates a DScroll that displays the contents of the specified DEdit component.
     * 
     * @param edit the DEdit component to display
     */
    public DScroll(DEdit<?> edit) {
        super(edit.comp());
    }

    /**
     * Sets the horizontal scroll bar policy to always show the scroll bar.
     * 
     * @return This DScroll instance.
     */
    public DScroll horizontalAlways() {
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        return this;
    }

    /**
     * Sets the horizontal scroll bar policy to never show the scroll bar.
     * 
     * @return This DScroll instance.
     */
    public DScroll horizontalNever() {
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return this;
    }

    /**
     * Sets the vertical scroll bar policy to always show the scroll bar.
     * 
     * @return This DScroll instance.
     */
    public DScroll verticalAlways() {
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return this;
    }

    /**
     * Sets the vertical scroll bar policy to never show the scroll bar.
     * 
     * @return This DScroll instance.
     */
    public DScroll verticalNever() {
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        return this;
    }

    /**
     * Sets the horizontal scroll bar policy to show the scroll bar as needed.
     * 
     * @return This DScroll instance.
     */
    public DScroll horizontalAsNeeded() {
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        return this;
    }

    /**
     * Sets the vertical scroll bar policy to show the scroll bar as needed.
     * 
     * @return This DScroll instance.
     */
    public DScroll verticalAsNeeded() {
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return this;
    }

    /**
     * Sets the view to be displayed in the viewport.
     * 
     * @param view the component to display in the viewport
     * @return This DScroll instance.
     */
    public DScroll viewportView(Component view) {
        setViewportView(view);
        return this;
    }

    /**
     * Adds a border around the viewport.
     * 
     * @param viewportBorder the border to add
     * @return This DScroll instance.
     */
    public DScroll viewportBorder(Border viewportBorder) {
        setViewportBorder(viewportBorder);
        return this;
    }

    /**
     * Sets the view to be displayed in the row header viewport.
     * 
     * @param view the component to display in the row header
     * @return This DScroll instance.
     */
    public DScroll rowHeaderView(Component view) {
        setRowHeaderView(view);
        return this;
    }

    /**
     * Sets the view to be displayed in the column header viewport.
     * 
     * @param view the component to display in the column header
     * @return This DScroll instance.
     */
    public DScroll columnHeaderView(Component view) {
        setColumnHeaderView(view);
        return this;
    }

    /**
     * Adds a child that will appear in one of the scroll panes corners.
     * 
     * @param key identifies which corner the component will appear in
     * @param corner the component to appear in the corner
     * @return This DScroll instance.
     */
    public DScroll corner(String key, Component corner) {
        setCorner(key, corner);
        return this;
    }

    /**
     * Enables or disables scrolling in response to movement of the mouse wheel.
     * 
     * @param handleWheel true if scrolling should be done automatically for a MouseWheelEvent
     * @return This DScroll instance.
     */
    public DScroll wheelScrollingEnabled(boolean handleWheel) {
        setWheelScrollingEnabled(handleWheel);
        return this;
    }

    /**
     * Sets the UI object which implements the L&F for this component.
     * 
     * @param ui the ScrollPaneUI L&F object
     * @return This DScroll instance.
     */
    public DScroll ui(ScrollPaneUI ui) {
        setUI(ui);
        return this;
    }

    /**
     * Sets whether this component is enabled.
     * 
     * @param b true to enable, false to disable
     * @return This DScroll instance.
     */
    public DScroll enabled(boolean b) {
        setEnabled(b);
        return this;
    }

    /**
     * Sets whether this component is visible.
     * 
     * @param b true to make visible, false to hide
     * @return This DScroll instance.
     */
    public DScroll visible(boolean b) {
        setVisible(b);
        return this;
    }

    /**
     * Sets whether this component is opaque.
     * 
     * @param b true to make opaque, false to make transparent
     * @return This DScroll instance.
     */
    public DScroll opaque(boolean b) {
        setOpaque(b);
        return this;
    }

    /**
     * Sets the bounds of this component.
     * 
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     * @param width the new width
     * @param height the new height
     * @return This DScroll instance.
     */
    public DScroll bounds(int x, int y, int width, int height) {
        setBounds(x, y, width, height);
        return this;
    }

    /**
     * Sets the size of this component.
     * 
     * @param width the new width
     * @param height the new height
     * @return This DScroll instance.
     */
    public DScroll size(int width, int height) {
        setSize(width, height);
        return this;
    }

    /**
     * Sets the size of this component.
     * 
     * @param d the new size
     * @return This DScroll instance.
     */
    public DScroll size(Dimension d) {
        setSize(d);
        return this;
    }

    /**
     * Sets the preferred size of this component.
     * 
     * @param width the new preferred width
     * @param height the new preferred height
     * @return This DScroll instance.
     */
    public DScroll preferredSize(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        return this;
    }

    /**
     * Sets the preferred size of this component.
     * 
     * @param d the new preferred size
     * @return This DScroll instance.
     */
    public DScroll preferredSize(Dimension d) {
        setPreferredSize(d);
        return this;
    }

    /**
     * Sets the font of this component.
     * 
     * @param font the desired Font
     * @return This DScroll instance.
     */
    public DScroll font(Font font) {
        setFont(font);
        return this;
    }

    /**
     * Sets the foreground color of this component.
     * 
     * @param fg the desired foreground Color
     * @return This DScroll instance.
     */
    public DScroll foreground(Color fg) {
        setForeground(fg);
        return this;
    }

    /**
     * Sets the background color of this component.
     * 
     * @param bg the desired background Color
     * @return This DScroll instance.
     */
    public DScroll background(Color bg) {
        setBackground(bg);
        return this;
    }

    /**
     * Gets the name of this component.
     * 
     * @return the name
     */
    public String name() {
        return getName();
    }

    /**
     * Sets the name of this component.
     * 
     * @param name the name to set
     * @return This DScroll instance.
     */
    public DScroll name(String name) {
        setName(name);
        return this;
    }

    /**
     * Registers the text to display in a tool tip.
     * 
     * @param hint the string to display
     * @return This DScroll instance.
     */
    public DScroll hint(String hint) {
        setToolTipText(hint);
        return this;
    }

    /**
     * Gets the tool tip text.
     * 
     * @return the tool tip text
     */
    public String hint() {
        return getToolTipText();
    }

}
