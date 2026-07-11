package com.vidlus.jarch.desk;

import java.math.BigDecimal;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class DFieldBigDecimal extends DEditField<BigDecimal> {

    public DFieldBigDecimal() {
        this(BigDecimal.ZERO);
    }

    public DFieldBigDecimal(BigDecimal value) {
        super(value == null ? "" : value.toPlainString());
        ((AbstractDocument) comp().getDocument()).setDocumentFilter(new BigDecimalFilter());
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

    private class BigDecimalFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            var text = fb.getDocument().getText(0, fb.getDocument().getLength());
            var newText = text.substring(0, offset) + string + text.substring(offset);
            if (isValidBigDecimal(newText)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            var currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            var newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
            if (isValidBigDecimal(newText)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            var currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            var newText = currentText.substring(0, offset) + currentText.substring(offset + length);
            if (isValidBigDecimal(newText)) {
                super.remove(fb, offset, length);
            }
        }

        private boolean isValidBigDecimal(String text) {
            if (text.isEmpty() || "-".equals(text) || ".".equals(text) || "-.".equals(text)) {
                return true;
            }
            try {
                new BigDecimal(text);
                return true;
            } catch (NumberFormatException e) {
                return text.matches("-?\\d+(\\.\\d*)?[eE][+-]?");
            }
        }
    }
}
