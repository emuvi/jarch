package com.vidlus.jarch.desk;

import java.awt.Color;

/**
 * A UI component for editing and selecting a color.
 * It presents a text field displaying the hex code and a button that opens a color picker dialog.
 */
public class DChangeColor extends DEditChange<Color> {

    private Color currentColor;

    /**
     * Constructs a new DChangeColor component.
     * The text field is set to non-editable to prevent manual invalid input.
     */
    public DChangeColor() {
        super("■");
        getField().setEditable(false);
    }

    /**
     * Retrieves the currently selected color.
     * 
     * @return the selected Color, or null if none is set
     */
    @Override
    public Color getValue() {
        return currentColor;
    }

    /**
     * Sets the color value.
     * Updates the text field with the hex string and the button foreground color.
     * 
     * @param value the Color to set
     */
    @Override
    public void setValue(Color value) {
        this.currentColor = value;
        if (value != null) {
            getField().setText(String.format("#%02X%02X%02X", value.getRed(), value.getGreen(), value.getBlue()));
            getButton().setForeground(value);
        } else {
            getField().setText("");
            getButton().setForeground(Color.BLACK);
        }
    }

    /**
     * Sets whether this component is editable.
     * The internal text field remains non-editable regardless, to enforce selection via the dialog.
     * 
     * @param editable true to enable the action button, false to disable
     * @return this DChangeColor instance
     */
    @Override
    public DChangeColor editable(boolean editable) {
        super.editable(editable);
        getField().setEditable(false);
        return this;
    }

    /**
     * Handles the action button press event.
     * Opens a DColor dialog for the user to select a color and updates the value upon confirmation.
     */
    @Override
    protected void onActionPressed() {
        if (!editable()) return;
        DColor colorDialog = new DColor();
        colorDialog.parent(comp());
        if (currentColor != null) {
            colorDialog.color(currentColor);
        }
        
        Color result = colorDialog.showDialog();
        if (result != null) {
            setValue(result);
        }
    }

    /**
     * Fluent setter for the color value.
     * 
     * @param color the Color to set
     * @return this DChangeColor instance
     */
    public DChangeColor color(Color color) {
        setValue(color);
        return this;
    }
}
