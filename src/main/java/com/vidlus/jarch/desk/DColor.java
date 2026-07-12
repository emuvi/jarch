package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * A UI component that serves as a solid-colored rectangular box on the desktop.
 * <p>
 * {@code DColor} extends {@link JPanel} and implements {@link DValue} to seamlessly
 * integrate with the jarch framework's value management system. It provides a highly 
 * fluent API for rapid configuration (e.g., setting dimensions, borders, and colors).
 * </p>
 * <p>
 * <b>Useful implementation detail:</b> This component forces itself to be opaque 
 * internally during initialization to ensure the color is always drawn properly by Swing.
 * </p>
 */
public class DColor extends JPanel implements DValue<Color> {

    /** The current color displayed by this box. Defaults to white. */
    private Color currentColor = Color.WHITE;

    /**
     * Constructs a new {@code DColor} box with an initial color of white.
     */
    public DColor() {
        super();
        init();
    }

    /**
     * Constructs a new {@code DColor} box initialized to the specified color.
     * 
     * @param initialColor the initial {@link java.awt.Color} to display in the box
     */
    public DColor(Color initialColor) {
        super();
        this.currentColor = initialColor;
        init();
    }
    
    /**
     * Internal initialization routine.
     * Ensures the panel is opaque and applies the starting background color.
     */
    private void init() {
        setOpaque(true);
        setBackground(currentColor);
    }
    
    /**
     * Retrieves the currently displayed color.
     * 
     * @return the current {@link Color} of the box
     */
    @Override
    public Color getValue() {
        return currentColor;
    }
    
    /**
     * Updates the color of the box and repaints the component's background.
     * 
     * @param value the new {@link Color} to display
     */
    @Override
    public void setValue(Color value) {
        this.currentColor = value;
        setBackground(value);
    }

    /**
     * Fluent setter for updating the current color of the color box.
     * <p>
     * <b>Note:</b> This delegates directly to {@link #setValue(Color)}.
     * </p>
     * 
     * @param c the new {@link Color} to set
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor color(Color c) {
        setValue(c);
        return this;
    }

    /**
     * Fluent setter for updating the current color using specific RGB values.
     * 
     * @param r the red component (0-255)
     * @param g the green component (0-255)
     * @param b the blue component (0-255)
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor color(int r, int g, int b) {
        setValue(new Color(r, g, b));
        return this;
    }

    /**
     * Fluent setter for updating the current color using an integer RGB value.
     * 
     * @param c the new color represented as an integer
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor color(int c) {
        setValue(new Color(c));
        return this;
    }

    /**
     * Fluent setter for updating the current color using a hex string.
     * 
     * @param hex the hex string (e.g., "#FF0000" or "FF0000")
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor color(String hex) {
        if (hex != null && !hex.isEmpty()) {
            if (!hex.startsWith("#")) {
                hex = "#" + hex;
            }
            setValue(Color.decode(hex));
        }
        return this;
    }

    /**
     * Fluent setter for updating the current color using Hue, Saturation, Brightness.
     * 
     * @param h the hue component
     * @param s the saturation component
     * @param b the brightness component
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor colorHSB(float h, float s, float b) {
        setValue(Color.getHSBColor(h, s, b));
        return this;
    }

    /**
     * Fluent setter for the background color of this component.
     * 
     * @param bg the desired background {@link Color}
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor background(Color bg) {
        setBackground(bg);
        return this;
    }

    /**
     * Fluent setter for the foreground color of this component.
     * 
     * @param fg the desired foreground {@link Color}
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor foreground(Color fg) {
        setForeground(fg);
        return this;
    }

    /**
     * Fluent setter for the font of this component.
     * 
     * @param font the desired {@link Font}
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor font(Font font) {
        setFont(font);
        return this;
    }

    /**
     * Fluent setter for the tool tip text displayed when hovering over this component.
     * 
     * @param text the string to display
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor tooltip(String text) {
        setToolTipText(text);
        return this;
    }

    /**
     * Fluent setter for the border of this component.
     * Useful for framing the color box on the UI.
     * 
     * @param border the {@link Border} object to apply
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor border(Border border) {
        setBorder(border);
        return this;
    }

    /**
     * Fluent setter for the cursor displayed when the mouse hovers over this component.
     * 
     * @param cursor the {@link Cursor} object
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor cursor(Cursor cursor) {
        setCursor(cursor);
        return this;
    }

    /**
     * Fluent setter to enable or disable this component.
     * 
     * @param enabled {@code true} to enable, {@code false} to disable
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor enabled(boolean enabled) {
        setEnabled(enabled);
        return this;
    }

    /**
     * Fluent setter to control the visibility of this component.
     * 
     * @param visible {@code true} to show, {@code false} to hide
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor visible(boolean visible) {
        setVisible(visible);
        return this;
    }

    /**
     * Fluent setter to control the opacity of this component.
     * 
     * @param opaque {@code true} to paint every pixel within its bounds
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor opaque(boolean opaque) {
        setOpaque(opaque);
        return this;
    }

    /**
     * Fluent setter for the preferred size of this component.
     * 
     * @param preferredSize the desired preferred {@link Dimension}
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor preferredSize(Dimension preferredSize) {
        setPreferredSize(preferredSize);
        return this;
    }

    /**
     * Fluent setter for the minimum size of this component.
     * 
     * @param minimumSize the minimum {@link Dimension}
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor minimumSize(Dimension minimumSize) {
        setMinimumSize(minimumSize);
        return this;
    }

    /**
     * Fluent setter for the maximum size of this component.
     * 
     * @param maximumSize the maximum {@link Dimension}
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor maximumSize(Dimension maximumSize) {
        setMaximumSize(maximumSize);
        return this;
    }

    /**
     * Fluent setter for the bounds of this component.
     * 
     * @param x the new x-coordinate of this component
     * @param y the new y-coordinate of this component
     * @param width the new width of this component
     * @param height the new height of this component
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor bounds(int x, int y, int width, int height) {
        setBounds(x, y, width, height);
        return this;
    }

    /**
     * Fluent setter for the location of this component.
     * 
     * @param x the new x-coordinate of this component
     * @param y the new y-coordinate of this component
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor location(int x, int y) {
        setLocation(x, y);
        return this;
    }

    /**
     * Fluent setter for the size of this component.
     * 
     * @param width the new width of this component
     * @param height the new height of this component
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor size(int width, int height) {
        setSize(width, height);
        return this;
    }

    /**
     * Fluent setter for the layout manager of this component.
     * 
     * @param mgr the specified layout manager
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor layout(LayoutManager mgr) {
        setLayout(mgr);
        return this;
    }

    /**
     * Fluent setter for the X alignment of this component.
     * 
     * @param alignmentX the alignment value (0.0f to 1.0f)
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor alignmentX(float alignmentX) {
        setAlignmentX(alignmentX);
        return this;
    }

    /**
     * Fluent setter for the Y alignment of this component.
     * 
     * @param alignmentY the alignment value (0.0f to 1.0f)
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor alignmentY(float alignmentY) {
        setAlignmentY(alignmentY);
        return this;
    }

    /**
     * Fluent setter to enable or disable focusability.
     * 
     * @param focusable {@code true} if this component should be focusable
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor focusable(boolean focusable) {
        setFocusable(focusable);
        return this;
    }

    /**
     * Fluent setter for the name of this component.
     * Useful for UI automated testing or component lookups.
     * 
     * @param name the string name to assign
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor name(String name) {
        setName(name);
        return this;
    }

    /**
     * Adds an arbitrary key/value client property to this component.
     * 
     * @param key the property key
     * @param value the property value
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor clientProperty(Object key, Object value) {
        putClientProperty(key, value);
        return this;
    }

    /**
     * Binds a click listener to this component.
     * 
     * @param consumer the code to execute when this component is clicked
     * @return this {@code DColor} instance to allow method chaining
     */
    public DColor onClick(Consumer<MouseEvent> consumer) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }
}
