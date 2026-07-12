package com.vidlus.jarch.desk;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class DFieldLong extends DEditField<Long> {

    public DFieldLong() {
        this(0L);
    }

    public DFieldLong(Long value) {
        super(value == null ? "" : value.toString());
        ((AbstractDocument) comp().getDocument()).setDocumentFilter(new LongFilter());
    }

    @Override
    public Long getValue() {
        var text = comp().getText();
        if (text == null || text.isEmpty() || "-".equals(text)) {
            return null;
        }
        try {
            return Long.parseLong(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public void setValue(Long value) {
        comp().setText(value == null ? "" : value.toString());
    }

    private class LongFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            var text = fb.getDocument().getText(0, fb.getDocument().getLength());
            var newText = text.substring(0, offset) + string + text.substring(offset);
            if (isValidLong(newText)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            var currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            var newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
            if (isValidLong(newText)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            var currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            var newText = currentText.substring(0, offset) + currentText.substring(offset + length);
            if (isValidLong(newText)) {
                super.remove(fb, offset, length);
            }
        }

        private boolean isValidLong(String text) {
            if (text.isEmpty() || "-".equals(text)) {
                return true;
            }
            try {
                Long.parseLong(text);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }
}
