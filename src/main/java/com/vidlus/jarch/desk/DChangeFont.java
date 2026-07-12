package com.vidlus.jarch.desk;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * A UI component for editing and selecting a font.
 * Provides a text field displaying font details and a button that opens a custom font picker dialog.
 */
public class DChangeFont extends DEditChange<Font> {

    private Font currentFont = new JLabel().getFont();
    private String dialogTitle = "Select Font";

    /**
     * Constructs a new DChangeFont component.
     */
    public DChangeFont() {
        super("F");
    }

    /**
     * Retrieves the currently selected font.
     * 
     * @return the selected Font
     */
    @Override
    public Font getValue() {
        return currentFont;
    }

    /**
     * Sets the font value. Updates the text field to display the font family and size,
     * and sets the component's font.
     * 
     * @param value the Font to set
     */
    @Override
    public void setValue(Font value) {
        this.currentFont = value == null ? new JLabel().getFont() : value;
        getField().setText(currentFont.getFamily() + ", " + currentFont.getSize() + "pt");
        getField().setFont(currentFont);
    }

    /**
     * Handles the action button press event.
     * Opens a custom dialog listing system fonts and a size spinner to let the user select a font.
     */
    @Override
    protected void onActionPressed() {
        if (!editable()) return;
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JList<String> fontList = new JList<>(fonts);
        fontList.setSelectedValue(currentFont.getFamily(), true);
        
        JSpinner sizeSpinner = new JSpinner(new SpinnerNumberModel(currentFont.getSize(), 8, 72, 1));
        
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(new JScrollPane(fontList), BorderLayout.CENTER);
        panel.add(sizeSpinner, BorderLayout.SOUTH);
        panel.setPreferredSize(new Dimension(250, 300));
        
        DAlert alert = new DAlert()
            .parent(comp())
            .title(dialogTitle)
            .message(panel)
            .plain()
            .okCancel();
            
        if (alert.confirm() == JOptionPane.OK_OPTION) {
            String family = fontList.getSelectedValue();
            if (family == null) family = currentFont.getFamily();
            int size = (Integer) sizeSpinner.getValue();
            setValue(new Font(family, currentFont.getStyle(), size));
        }
    }

    /**
     * Fluent setter for the font value.
     * 
     * @param font the Font to set
     * @return this DChangeFont instance
     */
    public DChangeFont font(Font font) {
        setValue(font);
        return this;
    }

    /**
     * Fluent setter for the dialog title displayed when the action button is pressed.
     * 
     * @param dialogTitle the title for the dialog
     * @return this DChangeFont instance
     */
    public DChangeFont dialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
        return this;
    }
}
