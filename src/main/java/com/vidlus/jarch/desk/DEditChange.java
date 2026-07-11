package com.vidlus.jarch.desk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * A fluent API abstract wrapper for a text field with a built-in action button on the right.
 * Ideal for complex values (e.g., File, Color, List) that require opening a dialog to edit.
 */
public abstract class DEditChange<T> extends DEdit<T> {

    protected JPanel panel;
    protected JTextField field;
    protected JButton actionButton;

    public DEditChange() {
        this("*");
    }

    public DEditChange(String buttonText) {
        super(new JPanel(new BorderLayout()));
        this.panel = (JPanel) super.comp();
        
        this.field = new JTextField();
        this.actionButton = new JButton(buttonText);
        
        // Make the outer panel look like a text field
        panel.setBackground(UIManager.getColor("TextField.background"));
        panel.setBorder(UIManager.getBorder("TextField.border"));
        
        // Strip the border from the inner text field so it blends perfectly
        field.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
        field.setBackground(panel.getBackground());
        field.setOpaque(false); // Let panel background show through
        
        // Style the button
        actionButton.setMargin(new Insets(0, 4, 0, 4));
        actionButton.setFocusable(false);
        actionButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        panel.add(field, BorderLayout.CENTER);
        panel.add(actionButton, BorderLayout.EAST);
        
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onActionPressed();
            }
        });
    }

    @Override
    public JComponent comp() {
        return panel;
    }

    /**
     * Gets the inner text field component.
     * 
     * @return the JTextField
     */
    public JTextField getField() {
        return field;
    }

    /**
     * Gets the inner action button component.
     * 
     * @return the JButton
     */
    public JButton getButton() {
        return actionButton;
    }

    /**
     * Set the number of columns in this field.
     * 
     * @param cols the number of columns
     * @return This DEditFieldAction instance.
     */
    public DEditChange<T> cols(int cols) {
        field.setColumns(cols);
        return this;
    }

    /**
     * Returns the number of columns in this field.
     * 
     * @return the number of columns
     */
    public int cols() {
        return field.getColumns();
    }

    /**
     * Sets whether this field is editable.
     * 
     * @param editable true if the field is editable
     * @return This DEditFieldAction instance.
     */
    public DEditChange<T> editable(boolean editable) {
        field.setEditable(editable);
        Color bg = editable ? UIManager.getColor("TextField.background") 
                            : UIManager.getColor("TextField.inactiveBackground");
        panel.setBackground(bg);
        return this;
    }

    /**
     * Returns whether this field is editable.
     * 
     * @return true if the field is editable
     */
    public boolean editable() {
        return field.isEditable();
    }

    /**
     * Changes the text on the action button.
     * 
     * @param text the button text
     * @return This DEditFieldAction instance.
     */
    public DEditChange<T> buttonText(String text) {
        actionButton.setText(text);
        return this;
    }

    /**
     * Method called when the action button is pressed. Subclasses must implement this
     * to open the appropriate dialog and update the field's value.
     */
    protected abstract void onActionPressed();
}
