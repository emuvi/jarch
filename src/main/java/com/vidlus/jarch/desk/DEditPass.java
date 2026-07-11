package com.vidlus.jarch.desk;

import javax.swing.JPasswordField;

/**
 * A fluent API wrapper for JPasswordField, extending DEdit for handling sensitive string inputs.
 */
public class DEditPass extends DEdit<String> {

    /**
     * Constructs a new DPass, with a default document, null starting text string, and 0 column width.
     */
    public DEditPass() {
        super(new JPasswordField());
    }

    /**
     * Constructs a new DPass initialized with the specified text.
     * 
     * @param text the text to be displayed, or null
     */
    public DEditPass(String text) {
        super(new JPasswordField(text));
    }

    /**
     * Constructs a new empty DPass with the specified number of columns.
     * 
     * @param columns the number of columns >= 0
     */
    public DEditPass(int columns) {
        super(new JPasswordField(columns));
    }

    /**
     * Constructs a new DPass initialized with the specified text and columns.
     * 
     * @param text the text to be displayed, or null
     * @param columns the number of columns >= 0
     */
    public DEditPass(String text, int columns) {
        super(new JPasswordField(text, columns));
    }

    @Override
    public JPasswordField comp() {
        return (JPasswordField) super.comp();
    }

    @Override
    public String getValue() {
        return new String(comp().getPassword());
    }

    @Override
    public void setValue(String value) {
        comp().setText(value);
    }

    /**
     * Sets the echo character for this password field.
     * 
     * @param c the echo character to display
     * @return This DPass instance.
     */
    public DEditPass echoChar(char c) {
        comp().setEchoChar(c);
        return this;
    }

    /**
     * Returns the character that is to be used for echoing.
     * 
     * @return the echo character, 0 if unset
     */
    public char echoChar() {
        return comp().getEchoChar();
    }

    /**
     * Sets the number of columns in this password field, and then invalidates the layout.
     * 
     * @param cols the number of columns >= 0
     * @return This DPass instance.
     */
    public DEditPass cols(int cols) {
        comp().setColumns(cols);
        return this;
    }

    /**
     * Returns the number of columns in this password field.
     * 
     * @return the number of columns
     */
    public int cols() {
        return comp().getColumns();
    }

    /**
     * Sets whether this password field is editable.
     * 
     * @param editable if true, the field is editable
     * @return This DPass instance.
     */
    public DEditPass editable(boolean editable) {
        comp().setEditable(editable);
        return this;
    }

    /**
     * Returns whether this password field is editable.
     * 
     * @return true if the field is editable
     */
    public boolean editable() {
        return comp().isEditable();
    }
}
