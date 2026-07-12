package com.vidlus.jarch.desk;

import java.awt.Color;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.ColorSelectionModel;

/**
 * A fluent API wrapper around {@link JColorChooser} that extends {@link DEdit}.
 * <p>
 * This component is intended to be embedded directly into a desktop layout, allowing 
 * users to select colors inline without spawning external dialogs. Because it inherits 
 * from {@code DEdit<Color>}, it natively supports value retrieval, setting, and standard 
 * framework events (like double clicks or enters acting as primary actions).
 * </p>
 */
public class DEditColor extends DEdit<Color> {

    /**
     * Constructs a new inline color editor using a default {@link JColorChooser}.
     */
    public DEditColor() {
        super(new JColorChooser());
    }

    /**
     * Constructs a new inline color editor initialized to the given color.
     * 
     * @param initialColor the starting {@link Color} to display in the chooser
     */
    public DEditColor(Color initialColor) {
        super(new JColorChooser(initialColor));
    }

    /**
     * Returns the underlying Swing component used for editing.
     * 
     * @return the embedded {@link JColorChooser} instance
     */
    @Override
    public JColorChooser comp() {
        return (JColorChooser) super.comp();
    }

    /**
     * Retrieves the currently selected color from the chooser.
     * 
     * @return the selected {@link Color}
     */
    @Override
    public Color getValue() {
        return comp().getColor();
    }

    /**
     * Updates the selected color in the underlying chooser.
     * 
     * @param value the {@link Color} to select
     */
    @Override
    public void setValue(Color value) {
        comp().setColor(value);
    }

    /**
     * Fluent setter to dynamically update the currently selected color.
     * 
     * @param c the new {@link Color} to apply
     * @return this {@code DEditColor} instance to allow method chaining
     */
    public DEditColor color(Color c) {
        comp().setColor(c);
        return this;
    }

    /**
     * Fluent setter for updating the current color using specific RGB values.
     * 
     * @param r the red component (0-255)
     * @param g the green component (0-255)
     * @param b the blue component (0-255)
     * @return this {@code DEditColor} instance to allow method chaining
     */
    public DEditColor color(int r, int g, int b) {
        comp().setColor(new Color(r, g, b));
        return this;
    }

    /**
     * Fluent setter for updating the current color using an integer RGB value.
     * 
     * @param c the new color represented as an integer
     * @return this {@code DEditColor} instance to allow method chaining
     */
    public DEditColor color(int c) {
        comp().setColor(new Color(c));
        return this;
    }

    /**
     * Fluent setter for updating the current color using a hex string.
     * 
     * @param hex the hex string (e.g., "#FF0000" or "FF0000")
     * @return this {@code DEditColor} instance to allow method chaining
     */
    public DEditColor color(String hex) {
        if (hex != null && !hex.isEmpty()) {
            if (!hex.startsWith("#")) {
                hex = "#" + hex;
            }
            comp().setColor(Color.decode(hex));
        }
        return this;
    }

    /**
     * Fluent setter for updating the current color using Hue, Saturation, Brightness.
     * 
     * @param h the hue component
     * @param s the saturation component
     * @param b the brightness component
     * @return this {@code DEditColor} instance to allow method chaining
     */
    public DEditColor colorHSB(float h, float s, float b) {
        comp().setColor(Color.getHSBColor(h, s, b));
        return this;
    }

    /**
     * Replaces the default color panels in the chooser with a custom set.
     * 
     * @param panels an array of {@link AbstractColorChooserPanel} objects
     * @return this {@code DEditColor} instance to allow method chaining
     */
    public DEditColor panels(AbstractColorChooserPanel[] panels) {
        comp().setChooserPanels(panels);
        return this;
    }

    /**
     * Adds a color chooser panel to the color chooser.
     * Useful for extending the chooser with custom color selection UIs.
     * 
     * @param panel the {@link AbstractColorChooserPanel} to add
     * @return this {@code DEditColor} instance to allow method chaining
     */
    public DEditColor addPanel(AbstractColorChooserPanel panel) {
        comp().addChooserPanel(panel);
        return this;
    }

    /**
     * Removes a color chooser panel from the color chooser.
     * 
     * @param panel the {@link AbstractColorChooserPanel} to remove
     * @return this {@code DEditColor} instance to allow method chaining
     */
    public DEditColor removePanel(AbstractColorChooserPanel panel) {
        comp().removeChooserPanel(panel);
        return this;
    }

    /**
     * Sets a custom preview panel that displays the currently selected color.
     * 
     * @param preview the custom {@link JComponent} to act as the preview panel
     * @return this {@code DEditColor} instance to allow method chaining
     */
    public DEditColor previewPanel(JComponent preview) {
        comp().setPreviewPanel(preview);
        return this;
    }

    /**
     * Hides the default preview panel by replacing it with an empty JPanel.
     * 
     * @return this {@code DEditColor} instance to allow method chaining
     */
    public DEditColor hidePreviewPanel() {
        comp().setPreviewPanel(new JPanel());
        return this;
    }

    /**
     * Sets the model containing the selected color.
     * 
     * @param model the new {@link ColorSelectionModel}
     * @return this {@code DEditColor} instance to allow method chaining
     */
    public DEditColor selectionModel(ColorSelectionModel model) {
        comp().setSelectionModel(model);
        return this;
    }

    /**
     * Enables or disables automatic drag handling on the underlying chooser component.
     * 
     * @param b {@code true} to enable drag handling, {@code false} to disable
     * @return this {@code DEditColor} instance to allow method chaining
     */
    public DEditColor dragEnabled(boolean b) {
        comp().setDragEnabled(b);
        return this;
    }
}
