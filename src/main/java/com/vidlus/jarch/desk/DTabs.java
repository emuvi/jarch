package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

/**
 * A fluent API wrapper for JTabbedPane to easily create and configure tabbed components.
 */
public class DTabs extends JTabbedPane {

    /**
     * Creates an empty TabbedPane with a default tab placement of JTabbedPane.TOP.
     */
    public DTabs() {
        super();
    }

    /**
     * Creates an empty TabbedPane with the specified tab placement of either:
     * JTabbedPane.TOP, JTabbedPane.BOTTOM, JTabbedPane.LEFT, or JTabbedPane.RIGHT.
     * 
     * @param tabPlacement the placement for the tabs relative to the content
     */
    public DTabs(int tabPlacement) {
        super(tabPlacement);
    }

    /**
     * Creates an empty TabbedPane with the specified tab placement and tab layout policy.
     * 
     * @param tabPlacement the placement for the tabs relative to the content
     * @param tabLayoutPolicy the policy for laying out tabs when all tabs will not fit on one run
     */
    public DTabs(int tabPlacement, int tabLayoutPolicy) {
        super(tabPlacement, tabLayoutPolicy);
    }

    /**
     * Adds a component represented by a title and no icon.
     * 
     * @param title the title to be displayed in this tab
     * @param component the component to be displayed when this tab is clicked
     * @return This DTabs instance.
     */
    public DTabs tab(String title, Component component) {
        addTab(title, component);
        return this;
    }

    /**
     * Adds a component represented by a title and an icon.
     * 
     * @param title the title to be displayed in this tab
     * @param icon the icon to be displayed in this tab
     * @param component the component to be displayed when this tab is clicked
     * @return This DTabs instance.
     */
    public DTabs tab(String title, Icon icon, Component component) {
        addTab(title, icon, component);
        return this;
    }

    /**
     * Adds a component and tooltip represented by a title and an icon.
     * 
     * @param title the title to be displayed in this tab
     * @param icon the icon to be displayed in this tab
     * @param component the component to be displayed when this tab is clicked
     * @param tip the tooltip to be displayed for this tab
     * @return This DTabs instance.
     */
    public DTabs tab(String title, Icon icon, Component component, String tip) {
        addTab(title, icon, component, tip);
        return this;
    }

    /**
     * Sets the tab placement for this tabbedpane.
     * 
     * @param tabPlacement the placement for the tabs relative to the content
     * @return This DTabs instance.
     */
    public DTabs tabPlacement(int tabPlacement) {
        setTabPlacement(tabPlacement);
        return this;
    }

    /**
     * Sets the policy which the tabbedpane will use in laying out the tabs when all the tabs will not fit within a single run.
     * 
     * @param tabLayoutPolicy the policy used to layout the tabs
     * @return This DTabs instance.
     */
    public DTabs tabLayoutPolicy(int tabLayoutPolicy) {
        setTabLayoutPolicy(tabLayoutPolicy);
        return this;
    }

    /**
     * Sets the selected index for this tabbedpane.
     * 
     * @param index the index to be selected
     * @return This DTabs instance.
     */
    public DTabs selectedIndex(int index) {
        setSelectedIndex(index);
        return this;
    }

    /**
     * Sets the selected component for this tabbedpane.
     * 
     * @param c the Component to be selected
     * @return This DTabs instance.
     */
    public DTabs selectedComponent(Component c) {
        setSelectedComponent(c);
        return this;
    }

    /**
     * Sets the font of this component.
     * 
     * @param font the desired Font for this component
     * @return This DTabs instance.
     */
    public DTabs font(Font font) {
        setFont(font);
        return this;
    }

    /**
     * Sets the foreground color of this component.
     * 
     * @param fg the desired foreground Color
     * @return This DTabs instance.
     */
    public DTabs foreground(Color fg) {
        setForeground(fg);
        return this;
    }

    /**
     * Sets the background color of this component.
     * 
     * @param bg the desired background Color
     * @return This DTabs instance.
     */
    public DTabs background(Color bg) {
        setBackground(bg);
        return this;
    }
}
