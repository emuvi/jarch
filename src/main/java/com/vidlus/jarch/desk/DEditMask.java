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
