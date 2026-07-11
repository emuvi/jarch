package com.vidlus.jarch.desk;

import java.math.BigDecimal;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class DFieldNumeric extends DEditField<BigDecimal> {

    private int integerPlaces;
    private int decimalPlaces;

    public DFieldNumeric(int integerPlaces, int decimalPlaces) {
        this(BigDecimal.ZERO, integerPlaces, decimalPlaces);
    }

    public DFieldNumeric(BigDecimal value, int integerPlaces, int decimalPlaces) {
        super(value == null ? "" : value.toPlainString());
        this.integerPlaces = integerPlaces;
        this.decimalPlaces = decimalPlaces;
        ((AbstractDocument) comp().getDocument()).setDocumentFilter(new NumericFilter());
    }

    @Override
    public BigDecimal getValue() {
        var text = comp().getText();
        if (text == null || text.isEmpty() || "-".equals(text) || ".".equals(text) || "-.".equals(text)) {
            return null;
        }
        try {
            return new BigDecimal(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public void setValue(BigDecimal value) {
        comp().setText(value == null ? "" : value.toPlainString());
    }

    /**
     * Sets the maximum number of digits allowed before the decimal point.
     * 
     * @param integerPlaces the maximum number of integer places
     * @return This DFieldNumeric instance.
     */
    public DFieldNumeric integerPlaces(int integerPlaces) {
        this.integerPlaces = integerPlaces;
        return this;
    }

    /**
     * Sets the maximum number of digits allowed after the decimal point.
     * 
     * @param decimalPlaces the maximum number of decimal places
     * @return This DFieldNumeric instance.
     */
    public DFieldNumeric decimalPlaces(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
        return this;
    }

    private class NumericFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            var text = fb.getDocument().getText(0, fb.getDocument().getLength());
            var newText = text.substring(0, offset) + string + text.substring(offset);
            if (isValidNumeric(newText)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            var currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            var newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
            if (isValidNumeric(newText)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            var currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            var newText = currentText.substring(0, offset) + currentText.substring(offset + length);
            if (isValidNumeric(newText)) {
                super.remove(fb, offset, length);
            }
        }

        private boolean isValidNumeric(String text) {
            if (text.isEmpty()) {
                return true;
            }
            
            String regex;
            if (decimalPlaces > 0) {
                // Allows up to 'integerPlaces' digits before the decimal, and up to 'decimalPlaces' after
                regex = "^-?\\d{0," + integerPlaces + "}(\\.\\d{0," + decimalPlaces + "})?$";
            } else {
                // Strictly integer if decimalPlaces is 0
                regex = "^-?\\d{0," + integerPlaces + "}$";
            }
            
            return text.matches(regex);
        }
    }
}
