package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JTabbedPane;
import java.awt.Dimension;
import javax.swing.plaf.TabbedPaneUI;
import javax.swing.event.ChangeListener;

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

    /**
     * Sets the disabled icon at the given index.
     * 
     * @param index the tab index
     * @param icon the icon to display
     * @return This DTabs instance.
     */
    public DTabs disabledIconAt(int index, Icon icon) {
        setDisabledIconAt(index, icon);
        return this;
    }

    /**
     * Sets the icon at the given index.
     * 
     * @param index the tab index
     * @param icon the icon to display
     * @return This DTabs instance.
     */
    public DTabs iconAt(int index, Icon icon) {
        setIconAt(index, icon);
        return this;
    }

    /**
     * Sets the mnemonic at the given index.
     * 
     * @param index the tab index
     * @param mnemonic the mnemonic
     * @return This DTabs instance.
     */
    public DTabs mnemonicAt(int index, int mnemonic) {
        setMnemonicAt(index, mnemonic);
        return this;
    }

    /**
     * Sets the component at the given index.
     * 
     * @param index the tab index
     * @param component the component to display
     * @return This DTabs instance.
     */
    public DTabs componentAt(int index, Component component) {
        setComponentAt(index, component);
        return this;
    }

    /**
     * Sets the component that is responsible for rendering the title for the specified tab.
     * 
     * @param index the tab index
     * @param component the component to render the title
     * @return This DTabs instance.
     */
    public DTabs tabComponentAt(int index, Component component) {
        setTabComponentAt(index, component);
        return this;
    }

    /**
     * Sets the title at the given index.
     * 
     * @param index the tab index
     * @param title the title to display
     * @return This DTabs instance.
     */
    public DTabs titleAt(int index, String title) {
        setTitleAt(index, title);
        return this;
    }

    /**
     * Sets the tool tip text at the given index.
     * 
     * @param index the tab index
     * @param toolTipText the tool tip text to display
     * @return This DTabs instance.
     */
    public DTabs toolTipTextAt(int index, String toolTipText) {
        setToolTipTextAt(index, toolTipText);
        return this;
    }

    /**
     * Sets the UI object which implements the L&F for this component.
     * 
     * @param ui the TabbedPaneUI L&F object
     * @return This DTabs instance.
     */
    public DTabs ui(TabbedPaneUI ui) {
        setUI(ui);
        return this;
    }
    
    /**
     * Adds a ChangeListener to this tabbedpane.
     * 
     * @param listener the ChangeListener to add
     * @return This DTabs instance.
     */
    public DTabs onChange(ChangeListener listener) {
        addChangeListener(listener);
        return this;
    }

    /**
     * Sets whether this component is enabled.
     * 
     * @param b true to enable, false to disable
     * @return This DTabs instance.
     */
    public DTabs enabled(boolean b) {
        setEnabled(b);
        return this;
    }

    /**
     * Sets whether this component is visible.
     * 
     * @param b true to make visible, false to hide
     * @return This DTabs instance.
     */
    public DTabs visible(boolean b) {
        setVisible(b);
        return this;
    }

    /**
     * Sets whether this component is opaque.
     * 
     * @param b true to make opaque, false to make transparent
     * @return This DTabs instance.
     */
    public DTabs opaque(boolean b) {
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
     * @return This DTabs instance.
     */
    public DTabs bounds(int x, int y, int width, int height) {
        setBounds(x, y, width, height);
        return this;
    }

    /**
     * Sets the size of this component.
     * 
     * @param width the new width
     * @param height the new height
     * @return This DTabs instance.
     */
    public DTabs size(int width, int height) {
        setSize(width, height);
        return this;
    }

    /**
     * Sets the size of this component.
     * 
     * @param d the new size
     * @return This DTabs instance.
     */
    public DTabs size(Dimension d) {
        setSize(d);
        return this;
    }

    /**
     * Sets the preferred size of this component.
     * 
     * @param width the new preferred width
     * @param height the new preferred height
     * @return This DTabs instance.
     */
    public DTabs preferredSize(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        return this;
    }

    /**
     * Sets the preferred size of this component.
     * 
     * @param d the new preferred size
     * @return This DTabs instance.
     */
    public DTabs preferredSize(Dimension d) {
        setPreferredSize(d);
        return this;
    }

    /**
     * Sets the name of this component.
     * 
     * @param name the name to set
     * @return This DTabs instance.
     */
    public DTabs name(String name) {
        setName(name);
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
     * Registers the text to display in a tool tip.
     * 
     * @param hint the string to display
     * @return This DTabs instance.
     */
    public DTabs hint(String hint) {
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
