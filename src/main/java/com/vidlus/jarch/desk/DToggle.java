package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;


import javax.swing.Icon;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeListener;

/**
 * A fluent API wrapper for JToggleButton to easily create and configure toggle buttons.
 */
public class DToggle extends JToggleButton {

    /**
     * Creates an initially unselected toggle button without setting the text or image.
     */
    public DToggle() {
        super();
    }

    /**
     * Creates an initially unselected toggle button with the specified image but no text.
     * 
     * @param icon the image that the button should display
     */
    public DToggle(Icon icon) {
        super(icon);
    }

    /**
     * Creates a toggle button with the specified image and selection state, but no text.
     * 
     * @param icon the image that the button should display
     * @param selected if true, the button is initially selected; otherwise, the button is initially unselected
     */
    public DToggle(Icon icon, boolean selected) {
        super(icon, selected);
    }

    /**
     * Creates an unselected toggle button with the specified text.
     * 
     * @param text the string displayed on the toggle button
     */
    public DToggle(String text) {
        super(text);
    }

    /**
     * Creates a toggle button with the specified text and selection state.
     * 
     * @param text the string displayed on the toggle button
     * @param selected if true, the button is initially selected; otherwise, the button is initially unselected
     */
    public DToggle(String text, boolean selected) {
        super(text, selected);
    }

    /**
     * Creates a toggle button where properties are taken from the Action supplied.
     * 
     * @param a the Action used to specify the new button
     */
    public DToggle(DAction a) {
        super(a);
    }

    /**
     * Creates a toggle button that has the specified text and image, and that is initially unselected.
     * 
     * @param text the string displayed on the button
     * @param icon the image that the button should display
     */
    public DToggle(String text, Icon icon) {
        super(text, icon);
    }

    /**
     * Creates a toggle button with the specified text, image, and selection state.
     * 
     * @param text the string displayed on the toggle button
     * @param icon the image that the button should display
     * @param selected if true, the button is initially selected; otherwise, the button is initially unselected
     */
    public DToggle(String text, Icon icon, boolean selected) {
        super(text, icon, selected);
    }

    /**
     * Sets the state of the button.
     * 
     * @param b true if the button is selected, otherwise false
     * @return This DToggle instance.
     */
    public DToggle selected(boolean b) {
        setSelected(b);
        return this;
    }

    /**
     * Sets the Action for the button.
     * 
     * @param a the Action for the button
     * @return This DToggle instance.
     */
    public DToggle action(DAction a) {
        setAction(a);
        return this;
    }

    /**
     * Adds an ActionListener to the button.
     * 
     * @param listener the ActionListener to be added
     * @return This DToggle instance.
     */
    public DToggle onAction(ActionListener listener) {
        addActionListener(listener);
        return this;
    }

    /**
     * Adds a ChangeListener to the button.
     * 
     * @param listener the ChangeListener to be added
     * @return This DToggle instance.
     */
    public DToggle onChange(ChangeListener listener) {
        addChangeListener(listener);
        return this;
    }

    /**
     * Sets the margin between the button's border and the label.
     * 
     * @param m the space between the border and the label
     * @return This DToggle instance.
     */
    public DToggle margin(Insets m) {
        setMargin(m);
        return this;
    }

    /**
     * Sets the focusable state of this component.
     * 
     * @param focusable indicates whether this Component is focusable
     * @return This DToggle instance.
     */
    public DToggle focusable(boolean focusable) {
        setFocusable(focusable);
        return this;
    }

    /**
     * Registers the text to display in a tool tip.
     * 
     * @param text the string to display
     * @return This DToggle instance.
     */
    public DToggle toolTip(String text) {
        setToolTipText(text);
        return this;
    }

    /**
     * Attaches a DPopup to this button, which will be shown on action performed.
     * The popup is shown directly under the button.
     *
     * @param popup The DPopup to show.
     * @return This DToggle instance.
     */
    public DToggle popup(DPopup popup) {
        addActionListener(e -> popup.show(this, 0, getHeight()));
        return this;
    }

    /**
     * Sets the font of this component.
     * 
     * @param font the desired Font for this component
     * @return This DToggle instance.
     */
    public DToggle font(Font font) {
        setFont(font);
        return this;
    }

    /**
     * Sets the foreground color of this component.
     * 
     * @param fg the desired foreground Color
     * @return This DToggle instance.
     */
    public DToggle foreground(Color fg) {
        setForeground(fg);
        return this;
    }

    /**
     * Sets the background color of this component.
     * 
     * @param bg the desired background Color
     * @return This DToggle instance.
     */
    public DToggle background(Color bg) {
        setBackground(bg);
        return this;
    }
}
