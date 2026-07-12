package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;


import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeListener;

/**
 * A fluent API wrapper for JRadioButton to easily create and configure radio buttons.
 */
public class DRadio extends JRadioButton {

    /**
     * Creates an initially unselected radio button with no set text or image.
     */
    public DRadio() {
        super();
    }

    /**
     * Creates an initially unselected radio button with the specified image but no text.
     * 
     * @param icon the image that the button should display
     */
    public DRadio(Icon icon) {
        super(icon);
    }

    /**
     * Creates a radio button with the specified image and selection state, but no text.
     * 
     * @param icon the image that the button should display
     * @param selected if true, the button is initially selected; otherwise, the button is initially unselected
     */
    public DRadio(Icon icon, boolean selected) {
        super(icon, selected);
    }

    /**
     * Creates an unselected radio button with the specified text.
     * 
     * @param text the string displayed on the radio button
     */
    public DRadio(String text) {
        super(text);
    }

    /**
     * Creates a radio button with the specified text and selection state.
     * 
     * @param text the string displayed on the radio button
     * @param selected if true, the button is initially selected; otherwise, the button is initially unselected
     */
    public DRadio(String text, boolean selected) {
        super(text, selected);
    }

    /**
     * Creates a radio button where properties are taken from the Action supplied.
     * 
     * @param a the Action used to specify the new button
     */
    public DRadio(DAction a) {
        super(a);
    }

    /**
     * Creates a radio button that has the specified text and image, and that is initially unselected.
     * 
     * @param text the string displayed on the button
     * @param icon the image that the button should display
     */
    public DRadio(String text, Icon icon) {
        super(text, icon);
    }

    /**
     * Creates a radio button with the specified text, image, and selection state.
     * 
     * @param text the string displayed on the radio button
     * @param icon the image that the button should display
     * @param selected if true, the button is initially selected; otherwise, the button is initially unselected
     */
    public DRadio(String text, Icon icon, boolean selected) {
        super(text, icon, selected);
    }

    /**
     * Sets the state of the button.
     * 
     * @param b true if the button is selected, otherwise false
     * @return This DRadio instance.
     */
    public DRadio selected(boolean b) {
        setSelected(b);
        return this;
    }

    /**
     * Adds this radio button to a specified ButtonGroup.
     * 
     * @param group the ButtonGroup to join
     * @return This DRadio instance.
     */
    public DRadio group(ButtonGroup group) {
        group.add(this);
        return this;
    }

    /**
     * Sets the Action for the button.
     * 
     * @param a the Action for the button
     * @return This DRadio instance.
     */
    public DRadio action(DAction a) {
        setAction(a);
        return this;
    }

    /**
     * Adds an ActionListener to the button.
     * 
     * @param listener the ActionListener to be added
     * @return This DRadio instance.
     */
    public DRadio onAction(ActionListener listener) {
        addActionListener(listener);
        return this;
    }

    /**
     * Adds a ChangeListener to the button.
     * 
     * @param listener the ChangeListener to be added
     * @return This DRadio instance.
     */
    public DRadio onChange(ChangeListener listener) {
        addChangeListener(listener);
        return this;
    }

    /**
     * Sets the margin between the button's border and the label.
     * 
     * @param m the space between the border and the label
     * @return This DRadio instance.
     */
    public DRadio margin(Insets m) {
        setMargin(m);
        return this;
    }

    /**
     * Sets the focusable state of this component.
     * 
     * @param focusable indicates whether this Component is focusable
     * @return This DRadio instance.
     */
    public DRadio focusable(boolean focusable) {
        setFocusable(focusable);
        return this;
    }

    /**
     * Registers the text to display in a tool tip.
     * 
     * @param text the string to display
     * @return This DRadio instance.
     */
    public DRadio toolTip(String text) {
        setToolTipText(text);
        return this;
    }

    /**
     * Sets the font of this component.
     * 
     * @param font the desired Font for this component
     * @return This DRadio instance.
     */
    public DRadio font(Font font) {
        setFont(font);
        return this;
    }

    /**
     * Sets the foreground color of this component.
     * 
     * @param fg the desired foreground Color
     * @return This DRadio instance.
     */
    public DRadio foreground(Color fg) {
        setForeground(fg);
        return this;
    }

    /**
     * Sets the background color of this component.
     * 
     * @param bg the desired background Color
     * @return This DRadio instance.
     */
    public DRadio background(Color bg) {
        setBackground(bg);
        return this;
    }
}
