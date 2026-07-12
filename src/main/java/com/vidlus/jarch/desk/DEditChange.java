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

    /** The main panel that encapsulates the field and button. */
    protected JPanel panel;
    
    /** The text field used for displaying or editing the string representation of the value. */
    protected JTextField field;
    
    /** The button that triggers the action to change the value, such as opening a dialog. */
    protected JButton actionButton;

    /**
     * Constructs a new DEditChange with a default button text "*".
     */
    public DEditChange() {
        this("*");
    }

    /**
     * Constructs a new DEditChange with the specified text for the action button.
     * 
     * @param buttonText the text to display on the action button
     */
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

    /**
     * Returns the underlying panel component that contains both the text field and the action button.
     * 
     * @return the main panel component
     */
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
     * @return This DEditChange instance.
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

    /** A flag indicating whether the field and button are currently editable. */
    protected boolean isEditable = true;

    /**
     * Sets whether this field is editable.
     * 
     * @param editable true if the field is editable
     * @return This DEditChange instance.
     */
    public DEditChange<T> editable(boolean editable) {
        this.isEditable = editable;
        field.setEditable(editable);
        actionButton.setEnabled(editable);
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
        return isEditable;
    }

    /**
     * Changes the text on the action button.
     * 
     * @param text the button text
     * @return This DEditChange instance.
     */
    public DEditChange<T> buttonText(String text) {
        actionButton.setText(text);
        return this;
    }

    /**
     * Returns the text on the action button.
     * 
     * @return the button text
     */
    public String buttonText() {
        return actionButton.getText();
    }

    /**
     * Sets the icon on the action button.
     * 
     * @param icon the button icon
     * @return This DEditChange instance.
     */
    public DEditChange<T> buttonIcon(javax.swing.Icon icon) {
        actionButton.setIcon(icon);
        return this;
    }

    /**
     * Returns the icon on the action button.
     * 
     * @return the button icon
     */
    public javax.swing.Icon buttonIcon() {
        return actionButton.getIcon();
    }

    /**
     * Sets the tooltip text on the action button.
     * 
     * @param text the tooltip text
     * @return This DEditChange instance.
     */
    public DEditChange<T> buttonTooltip(String text) {
        actionButton.setToolTipText(text);
        return this;
    }

    /**
     * Returns the tooltip text on the action button.
     * 
     * @return the button tooltip text
     */
    public String buttonTooltip() {
        return actionButton.getToolTipText();
    }

    /**
     * Sets the font on the action button.
     * 
     * @param font the button font
     * @return This DEditChange instance.
     */
    public DEditChange<T> buttonFont(java.awt.Font font) {
        actionButton.setFont(font);
        return this;
    }

    /**
     * Returns the font on the action button.
     * 
     * @return the button font
     */
    public java.awt.Font buttonFont() {
        return actionButton.getFont();
    }

    /**
     * Sets whether the action button is visible.
     * 
     * @param visible true if the button should be visible
     * @return This DEditChange instance.
     */
    public DEditChange<T> buttonVisible(boolean visible) {
        actionButton.setVisible(visible);
        return this;
    }

    /**
     * Returns whether the action button is visible.
     * 
     * @return true if the button is visible
     */
    public boolean buttonVisible() {
        return actionButton.isVisible();
    }

    /**
     * Sets the text of the inner text field.
     * 
     * @param text the text
     * @return This DEditChange instance.
     */
    public DEditChange<T> text(String text) {
        field.setText(text);
        return this;
    }

    /**
     * Returns the text of the inner text field.
     * 
     * @return the text
     */
    public String text() {
        return field.getText();
    }

    /**
     * Sets the horizontal alignment of the text in the inner text field.
     * 
     * @param alignment the alignment (e.g., JTextField.LEFT, JTextField.CENTER, JTextField.RIGHT)
     * @return This DEditChange instance.
     */
    public DEditChange<T> horizontalAlignment(int alignment) {
        field.setHorizontalAlignment(alignment);
        return this;
    }

    /**
     * Returns the horizontal alignment of the text in the inner text field.
     * 
     * @return the alignment
     */
    public int horizontalAlignment() {
        return field.getHorizontalAlignment();
    }

    /**
     * Sets the font of the inner text field.
     * 
     * @param font the font
     * @return This DEditChange instance.
     */
    public DEditChange<T> font(java.awt.Font font) {
        field.setFont(font);
        return this;
    }

    /**
     * Returns the font of the inner text field.
     * 
     * @return the font
     */
    public java.awt.Font font() {
        return field.getFont();
    }

    /**
     * Sets the foreground color of the inner text field.
     * 
     * @param color the foreground color
     * @return This DEditChange instance.
     */
    public DEditChange<T> foreground(Color color) {
        field.setForeground(color);
        return this;
    }

    /**
     * Returns the foreground color of the inner text field.
     * 
     * @return the foreground color
     */
    public Color foreground() {
        return field.getForeground();
    }

    /**
     * Sets the caret position in the inner text field.
     * 
     * @param position the position
     * @return This DEditChange instance.
     */
    public DEditChange<T> caretPosition(int position) {
        field.setCaretPosition(position);
        return this;
    }

    /**
     * Returns the caret position in the inner text field.
     * 
     * @return the position
     */
    public int caretPosition() {
        return field.getCaretPosition();
    }

    /**
     * Sets the selection start in the inner text field.
     * 
     * @param position the selection start
     * @return This DEditChange instance.
     */
    public DEditChange<T> selectionStart(int position) {
        field.setSelectionStart(position);
        return this;
    }

    /**
     * Returns the selection start in the inner text field.
     * 
     * @return the selection start
     */
    public int selectionStart() {
        return field.getSelectionStart();
    }

    /**
     * Sets the selection end in the inner text field.
     * 
     * @param position the selection end
     * @return This DEditChange instance.
     */
    public DEditChange<T> selectionEnd(int position) {
        field.setSelectionEnd(position);
        return this;
    }

    /**
     * Returns the selection end in the inner text field.
     * 
     * @return the selection end
     */
    public int selectionEnd() {
        return field.getSelectionEnd();
    }

    /**
     * Method called when the action button is pressed. Subclasses must implement this
     * to open the appropriate dialog and update the field's value.
     */
    protected abstract void onActionPressed();
}
