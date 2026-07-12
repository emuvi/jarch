package com.vidlus.jarch.desk;

import java.text.Format;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

/**
 * A fluent API wrapper for JFormattedTextField, extending DEdit for handling formatted inputs.
 */
public class DEditMask extends DEdit<Object> {

    /**
     * Constructs a new DEditMask with no formatter.
     */
    public DEditMask() {
        super(new JFormattedTextField());
    }

    /**
     * Constructs a new DEditMask with the specified format.
     * 
     * @param format the Format to use
     */
    public DEditMask(Format format) {
        super(new JFormattedTextField(format));
    }

    /**
     * Constructs a new DEditMask with the specified formatter.
     * 
     * @param formatter the AbstractFormatter to use
     */
    public DEditMask(JFormattedTextField.AbstractFormatter formatter) {
        super(new JFormattedTextField(formatter));
    }

    /**
     * Constructs a new DEditMask with the specified factory.
     * 
     * @param factory the AbstractFormatterFactory to use
     */
    public DEditMask(JFormattedTextField.AbstractFormatterFactory factory) {
        super(new JFormattedTextField(factory));
    }

    /**
     * Constructs a new DEditMask with a MaskFormatter created from the specified mask string.
     * 
     * @param mask the string mask, e.g., "(###) ###-####"
     */
    public DEditMask(String mask) {
        super(new JFormattedTextField());
        mask(mask);
    }

    @Override
    public JFormattedTextField comp() {
        return (JFormattedTextField) super.comp();
    }

    @Override
    public Object getValue() {
        return comp().getValue();
    }

    @Override
    public void setValue(Object value) {
        comp().setValue(value);
    }

    /**
     * Returns the mask string if a MaskFormatter is used.
     * 
     * @return the mask string, or null
     */
    public String mask() {
        JFormattedTextField.AbstractFormatter formatter = comp().getFormatter();
        if (formatter instanceof MaskFormatter) {
            return ((MaskFormatter) formatter).getMask();
        }
        return null;
    }

    /**
     * Sets a string mask using MaskFormatter.
     * 
     * @param mask the mask to apply (e.g., "###-##-####")
     * @return This DEditMask instance.
     */
    public DEditMask mask(String mask) {
        try {
            MaskFormatter formatter = new MaskFormatter(mask);
            formatter.setValueContainsLiteralCharacters(false);
            comp().setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(formatter));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Returns the placeholder character used by the MaskFormatter.
     * 
     * @return the placeholder character, or '\0'
     */
    public char placeholder() {
        JFormattedTextField.AbstractFormatter formatter = comp().getFormatter();
        if (formatter instanceof MaskFormatter) {
            return ((MaskFormatter) formatter).getPlaceholderCharacter();
        }
        return '\0';
    }

    /**
     * Sets the placeholder characters used when the value does not completely fill the mask.
     * 
     * @param placeholder the placeholder character
     * @return This DEditMask instance.
     */
    public DEditMask placeholder(char placeholder) {
        JFormattedTextField.AbstractFormatter formatter = comp().getFormatter();
        if (formatter instanceof MaskFormatter) {
            ((MaskFormatter) formatter).setPlaceholderCharacter(placeholder);
        }
        return this;
    }

    /**
     * Returns the current formatter.
     * 
     * @return the formatter
     */
    public JFormattedTextField.AbstractFormatter formatter() {
        return comp().getFormatter();
    }

    /**
     * Sets the formatter used by this component.
     * 
     * @param formatter the formatter
     * @return This DEditMask instance.
     */
    public DEditMask formatter(JFormattedTextField.AbstractFormatter formatter) {
        comp().setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(formatter));
        return this;
    }

    /**
     * Returns the format used by the formatter, if applicable.
     * 
     * @return the format, or null
     */
    public Format format() {
        JFormattedTextField.AbstractFormatter formatter = comp().getFormatter();
        if (formatter instanceof javax.swing.text.InternationalFormatter) {
            return ((javax.swing.text.InternationalFormatter) formatter).getFormat();
        }
        return null;
    }

    /**
     * Sets the format used by this component.
     * 
     * @param format the Format
     * @return This DEditMask instance.
     */
    public DEditMask format(Format format) {
        comp().setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
            new javax.swing.text.InternationalFormatter(format)));
        return this;
    }

    /**
     * Returns the number of columns in this field.
     * 
     * @return the number of columns
     */
    public int cols() {
        return comp().getColumns();
    }

    /**
     * Set the number of columns in this field.
     * 
     * @param cols the number of columns
     * @return This DEditMask instance.
     */
    public DEditMask cols(int cols) {
        comp().setColumns(cols);
        return this;
    }

    /**
     * Returns whether this field is editable.
     * 
     * @return true if editable
     */
    public boolean editable() {
        return comp().isEditable();
    }

    /**
     * Sets whether this field is editable.
     * 
     * @param editable true if the field is editable
     * @return This DEditMask instance.
     */
    public DEditMask editable(boolean editable) {
        comp().setEditable(editable);
        return this;
    }
    
    /**
     * Returns the behavior when focus is lost.
     * 
     * @return the focus lost behavior
     */
    public int focusLostBehavior() {
        return comp().getFocusLostBehavior();
    }

    /**
     * Sets the behavior when focus is lost.
     * 
     * @param behavior JFormattedTextField.COMMIT_OR_REVERT, REVERT, COMMIT, or PERSIST
     * @return This DEditMask instance.
     */
    public DEditMask focusLostBehavior(int behavior) {
        comp().setFocusLostBehavior(behavior);
        return this;
    }

    /**
     * Returns the horizontal alignment of the text.
     * 
     * @return the horizontal alignment
     */
    public int horizontalAlignment() {
        return comp().getHorizontalAlignment();
    }

    /**
     * Sets the horizontal alignment of the text.
     * 
     * @param alignment the alignment
     * @return this DEditMask instance
     */
    public DEditMask horizontalAlignment(int alignment) {
        comp().setHorizontalAlignment(alignment);
        return this;
    }

    /**
     * Forces the current value to be committed.
     * 
     * @return This DEditMask instance.
     */
    public DEditMask commitEdit() {
        try {
            comp().commitEdit();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }
}
