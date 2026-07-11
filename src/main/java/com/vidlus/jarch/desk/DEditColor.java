package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.colorchooser.AbstractColorChooserPanel;

/**
 * A fluent API wrapper for JColorChooser extending DEdit for inline color selection fields.
 */
public class DEditColor extends DEdit<Color> {

    private Component parentComponent;
    private String dialogTitle = "Select a Color";

    public DEditColor() {
        super(new JColorChooser());
    }

    public DEditColor(Color initialColor) {
        super(new JColorChooser(initialColor));
    }

    @Override
    public JColorChooser comp() {
        return (JColorChooser) super.comp();
    }

    @Override
    public Color getValue() {
        return comp().getColor();
    }

    @Override
    public void setValue(Color value) {
        comp().setColor(value);
    }

    /**
     * Sets the parent component for the modal dialog.
     * 
     * @param parent the parent component
     * @return This DEditColor instance.
     */
    public DEditColor parent(Component parent) {
        this.parentComponent = parent;
        return this;
    }

    /**
     * Sets the title for the modal dialog.
     * 
     * @param title the dialog title
     * @return This DEditColor instance.
     */
    public DEditColor title(String title) {
        this.dialogTitle = title;
        return this;
    }

    /**
     * Sets the current color of the color chooser.
     * 
     * @param c the new color
     * @return This DEditColor instance.
     */
    public DEditColor color(Color c) {
        comp().setColor(c);
        return this;
    }

    /**
     * Specifies the color panels used to choose a color value.
     * 
     * @param panels an array of AbstractColorChooserPanel objects
     * @return This DEditColor instance.
     */
    public DEditColor panels(AbstractColorChooserPanel[] panels) {
        comp().setChooserPanels(panels);
        return this;
    }

    /**
     * Sets the current preview panel.
     * 
     * @param preview the JComponent which displays the current color
     * @return This DEditColor instance.
     */
    public DEditColor previewPanel(JComponent preview) {
        comp().setPreviewPanel(preview);
        return this;
    }

    /**
     * Sets the dragEnabled property, which must be true to enable automatic drag handling.
     * 
     * @param b the dragEnabled property
     * @return This DEditColor instance.
     */
    public DEditColor dragEnabled(boolean b) {
        comp().setDragEnabled(b);
        return this;
    }

    /**
     * Shows a modal color-chooser dialog and blocks until the dialog is hidden.
     * 
     * @return the selected color or null if the user opted out
     */
    public Color showDialog() {
        return JColorChooser.showDialog(parentComponent, dialogTitle, comp().getColor());
    }
}
