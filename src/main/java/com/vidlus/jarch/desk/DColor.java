package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.border.Border;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.ColorSelectionModel;

/**
 * A fluent API wrapper for {@link javax.swing.JColorChooser} that provides a
 * streamlined way to create, configure, and display color chooser dialogs.
 * 
 * <p>
 * This class extends {@code JColorChooser} and introduces method chaining
 * (a fluent interface) for configuration options. This allows developers to 
 * set up colors, custom panels, and generic component properties in a single, 
 * highly readable statement.
 * </p>
 * 
 * <p>
 * <b>Example usage:</b>
 * <pre>{@code
 * Color selectedColor = new DColor(Color.RED)
 *         .parent(myFrame)
 *         .title("Choose Background Color")
 *         .dragEnabled(true)
 *         .showDialog();
 * }</pre>
 * </p>
 * 
 * @see javax.swing.JColorChooser
 */
public class DColor extends JColorChooser {

    private Component parentComponent;
    private String dialogTitle = "Select a Color";

    /**
     * Creates a new, default color chooser with an initial color of white.
     */
    public DColor() {
        super();
    }

    /**
     * Creates a new color chooser with the specified initial color.
     * 
     * @param initialColor the initial {@link java.awt.Color} to display in the chooser
     */
    public DColor(Color initialColor) {
        super(initialColor);
    }

    /**
     * Sets the parent component for the modal dialog.
     * The dialog will be centered relative to this component.
     * 
     * @param parent the parent {@link java.awt.Component}
     * @return this {@code DColor} instance for chaining
     */
    public DColor parent(Component parent) {
        this.parentComponent = parent;
        return this;
    }

    /**
     * Sets the title for the modal dialog.
     * 
     * @param title the dialog title string
     * @return this {@code DColor} instance for chaining
     */
    public DColor title(String title) {
        this.dialogTitle = title;
        return this;
    }

    /**
     * Sets the current color of the color chooser.
     * 
     * @param c the new {@link java.awt.Color}
     * @return this {@code DColor} instance for chaining
     * @see #getColor()
     */
    public DColor color(Color c) {
        setColor(c);
        return this;
    }

    /**
     * Specifies the color panels used to choose a color value.
     * Replaces the default color panels with the provided array.
     * 
     * @param panels an array of {@link javax.swing.colorchooser.AbstractColorChooserPanel} objects
     * @return this {@code DColor} instance for chaining
     */
    public DColor panels(AbstractColorChooserPanel[] panels) {
        setChooserPanels(panels);
        return this;
    }

    /**
     * Sets the current preview panel.
     * This panel displays the currently selected color.
     * 
     * @param preview the {@link javax.swing.JComponent} which displays the current color
     * @return this {@code DColor} instance for chaining
     */
    public DColor previewPanel(JComponent preview) {
        setPreviewPanel(preview);
        return this;
    }

    /**
     * Sets the {@code dragEnabled} property, which must be {@code true} to enable
     * automatic drag handling on this component.
     * 
     * @param b the {@code dragEnabled} property
     * @return this {@code DColor} instance for chaining
     */
    public DColor dragEnabled(boolean b) {
        setDragEnabled(b);
        return this;
    }

    /**
     * Sets the current color of the color chooser using specific RGB values.
     * 
     * @param r the red component (0-255)
     * @param g the green component (0-255)
     * @param b the blue component (0-255)
     * @return this {@code DColor} instance for chaining
     */
    public DColor color(int r, int g, int b) {
        setColor(r, g, b);
        return this;
    }

    /**
     * Sets the current color of the color chooser using an integer RGB value.
     * 
     * @param c the new color represented as an integer
     * @return this {@code DColor} instance for chaining
     */
    public DColor color(int c) {
        setColor(c);
        return this;
    }

    /**
     * Sets the model containing the selected color.
     * 
     * @param model the new {@link javax.swing.colorchooser.ColorSelectionModel}
     * @return this {@code DColor} instance for chaining
     */
    public DColor selectionModel(ColorSelectionModel model) {
        setSelectionModel(model);
        return this;
    }

    /**
     * Adds a color chooser panel to the color chooser.
     * Useful for extending the chooser with custom color selection UIs.
     * 
     * @param panel the {@link javax.swing.colorchooser.AbstractColorChooserPanel} to add
     * @return this {@code DColor} instance for chaining
     */
    public DColor addPanel(AbstractColorChooserPanel panel) {
        addChooserPanel(panel);
        return this;
    }

    /**
     * Removes a color chooser panel from the color chooser.
     * 
     * @param panel the {@link javax.swing.colorchooser.AbstractColorChooserPanel} to remove
     * @return this {@code DColor} instance for chaining
     */
    public DColor removePanel(AbstractColorChooserPanel panel) {
        removeChooserPanel(panel);
        return this;
    }

    /**
     * Sets the background color of this component.
     * 
     * @param bg the desired background {@link java.awt.Color}
     * @return this {@code DColor} instance for chaining
     */
    public DColor background(Color bg) {
        setBackground(bg);
        return this;
    }

    /**
     * Sets the foreground color of this component.
     * 
     * @param fg the desired foreground {@link java.awt.Color}
     * @return this {@code DColor} instance for chaining
     */
    public DColor foreground(Color fg) {
        setForeground(fg);
        return this;
    }

    /**
     * Sets the font for this component.
     * 
     * @param font the desired {@link java.awt.Font}
     * @return this {@code DColor} instance for chaining
     */
    public DColor font(Font font) {
        setFont(font);
        return this;
    }

    /**
     * Sets the tool tip text for this component.
     * 
     * @param text the string to display when hovering over the component
     * @return this {@code DColor} instance for chaining
     */
    public DColor tooltip(String text) {
        setToolTipText(text);
        return this;
    }

    /**
     * Sets the border of this component.
     * 
     * @param border the {@link javax.swing.border.Border} object
     * @return this {@code DColor} instance for chaining
     */
    public DColor border(Border border) {
        setBorder(border);
        return this;
    }

    /**
     * Sets the cursor that is displayed when the mouse pointer is over this component.
     * 
     * @param cursor the {@link java.awt.Cursor} object
     * @return this {@code DColor} instance for chaining
     */
    public DColor cursor(Cursor cursor) {
        setCursor(cursor);
        return this;
    }

    /**
     * Sets whether this component is enabled.
     * Disabled components typically do not respond to user input and appear grayed out.
     * 
     * @param enabled {@code true} if this component should be enabled
     * @return this {@code DColor} instance for chaining
     */
    public DColor enabled(boolean enabled) {
        setEnabled(enabled);
        return this;
    }

    /**
     * Sets whether this component is visible.
     * 
     * @param visible {@code true} if this component should be visible
     * @return this {@code DColor} instance for chaining
     */
    public DColor visible(boolean visible) {
        setVisible(visible);
        return this;
    }

    /**
     * Sets whether this component is opaque.
     * If {@code true}, the component paints every pixel within its bounds.
     * 
     * @param opaque {@code true} if this component should be opaque
     * @return this {@code DColor} instance for chaining
     */
    public DColor opaque(boolean opaque) {
        setOpaque(opaque);
        return this;
    }

    /**
     * Sets the preferred size of this component.
     * 
     * @param preferredSize the preferred {@link java.awt.Dimension}
     * @return this {@code DColor} instance for chaining
     */
    public DColor preferredSize(Dimension preferredSize) {
        setPreferredSize(preferredSize);
        return this;
    }

    /**
     * Sets the minimum size of this component.
     * 
     * @param minimumSize the minimum {@link java.awt.Dimension}
     * @return this {@code DColor} instance for chaining
     */
    public DColor minimumSize(Dimension minimumSize) {
        setMinimumSize(minimumSize);
        return this;
    }

    /**
     * Sets the maximum size of this component.
     * 
     * @param maximumSize the maximum {@link java.awt.Dimension}
     * @return this {@code DColor} instance for chaining
     */
    public DColor maximumSize(Dimension maximumSize) {
        setMaximumSize(maximumSize);
        return this;
    }

    /**
     * Sets the name of this component.
     * This is useful for identifying the component, especially in UI testing.
     * 
     * @param name the string name to assign to this component
     * @return this {@code DColor} instance for chaining
     */
    public DColor name(String name) {
        setName(name);
        return this;
    }

    /**
     * Adds an arbitrary key/value "client property" to this component.
     * Client properties are useful for storing metadata or flags directly on the component.
     * 
     * @param key the property key
     * @param value the property value
     * @return this {@code DColor} instance for chaining
     */
    public DColor clientProperty(Object key, Object value) {
        putClientProperty(key, value);
        return this;
    }

    /**
     * Shows a modal color-chooser dialog and blocks until the dialog is hidden.
     * <p>
     * This method wraps {@link javax.swing.JColorChooser#createDialog} but ensures 
     * that <em>this</em> instance (and all its configured custom panels, preview panels, 
     * and styling) is used rather than creating a new default {@code JColorChooser}.
     * </p>
     * 
     * @return the selected {@link java.awt.Color}, or {@code null} if the user cancelled the dialog
     */
    public Color showDialog() {
        final Color[] result = {null};
        ActionListener okListener = e -> result[0] = getColor();
        JDialog dialog = JColorChooser.createDialog(parentComponent, dialogTitle, true, this, okListener, null);
        dialog.setVisible(true);
        dialog.dispose();
        return result[0];
    }
}
