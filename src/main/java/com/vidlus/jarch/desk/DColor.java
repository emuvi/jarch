package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.colorchooser.AbstractColorChooserPanel;

/**
 * A fluent API wrapper for JColorChooser to easily prompt the user for colors.
 */
public class DColor extends JColorChooser {

    private Component parentComponent;
    private String dialogTitle = "Select a Color";

    public DColor() {
        super();
    }

    public DColor(Color initialColor) {
        super(initialColor);
    }

    /**
     * Sets the parent component for the modal dialog.
     * 
     * @param parent the parent component
     * @return This DColor instance.
     */
    public DColor parent(Component parent) {
        this.parentComponent = parent;
        return this;
    }

    /**
     * Sets the title for the modal dialog.
     * 
     * @param title the dialog title
     * @return This DColor instance.
     */
    public DColor title(String title) {
        this.dialogTitle = title;
        return this;
    }

    /**
     * Sets the current color of the color chooser.
     * 
     * @param c the new color
     * @return This DColor instance.
     */
    public DColor color(Color c) {
        setColor(c);
        return this;
    }

    /**
     * Specifies the color panels used to choose a color value.
     * 
     * @param panels an array of AbstractColorChooserPanel objects
     * @return This DColor instance.
     */
    public DColor panels(AbstractColorChooserPanel[] panels) {
        setChooserPanels(panels);
        return this;
    }

    /**
     * Sets the current preview panel.
     * 
     * @param preview the JComponent which displays the current color
     * @return This DColor instance.
     */
    public DColor previewPanel(JComponent preview) {
        setPreviewPanel(preview);
        return this;
    }

    /**
     * Sets the dragEnabled property, which must be true to enable automatic drag handling.
     * 
     * @param b the dragEnabled property
     * @return This DColor instance.
     */
    public DColor dragEnabled(boolean b) {
        setDragEnabled(b);
        return this;
    }

    /**
     * Shows a modal color-chooser dialog and blocks until the dialog is hidden.
     * 
     * @return the selected color or null if the user opted out
     */
    public Color showDialog() {
        return JColorChooser.showDialog(parentComponent, dialogTitle, getColor());
    }
}
