package com.vidlus.jarch.desk;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class DFieldFloat extends DEditField<Float> {

    public DFieldFloat() {
        this(0f);
    }

    public DFieldFloat(Float value) {
        super(value == null ? "" : value.toString());
        ((AbstractDocument) comp().getDocument()).setDocumentFilter(new FloatFilter());
    }

    @Override
    public Float getValue() {
        var text = comp().getText();
        if (text == null || text.isEmpty() || "-".equals(text) || ".".equals(text) || "-.".equals(text)) {
            return null;
        }
        try {
            return Float.parseFloat(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public void setValue(Float value) {
        comp().setText(value == null ? "" : value.toString());
    }

    private class FloatFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            var text = fb.getDocument().getText(0, fb.getDocument().getLength());
            var newText = text.substring(0, offset) + string + text.substring(offset);
            if (isValidFloat(newText)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            var currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            var newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
            if (isValidFloat(newText)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            var currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            var newText = currentText.substring(0, offset) + currentText.substring(offset + length);
            if (isValidFloat(newText)) {
                super.remove(fb, offset, length);
            }
        }

        private boolean isValidFloat(String text) {
            if (text.isEmpty() || "-".equals(text) || ".".equals(text) || "-.".equals(text)) {
                return true;
            }
            try {
                Float.parseFloat(text);
                return true;
            } catch (NumberFormatException e) {
                return text.matches("-?\\d+(\\.\\d*)?[eE][+-]?");
            }
        }
    }
}
