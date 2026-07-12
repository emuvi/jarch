package com.vidlus.jarch.desk;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class DFieldByte extends DEditField<Byte> {

    public DFieldByte() {
        this((byte) 0);
    }

    public DFieldByte(Byte value) {
        super(value == null ? "" : value.toString());
        ((AbstractDocument) comp().getDocument()).setDocumentFilter(new ByteFilter());
    }

    @Override
    public Byte getValue() {
        var text = comp().getText();
        if (text == null || text.isEmpty() || "-".equals(text)) {
            return null;
        }
        try {
            return Byte.parseByte(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public void setValue(Byte value) {
        comp().setText(value == null ? "" : value.toString());
    }

    private class ByteFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            var text = fb.getDocument().getText(0, fb.getDocument().getLength());
            var newText = text.substring(0, offset) + string + text.substring(offset);
            if (isValidByte(newText)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            var currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            var newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
            if (isValidByte(newText)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            var currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            var newText = currentText.substring(0, offset) + currentText.substring(offset + length);
            if (isValidByte(newText)) {
                super.remove(fb, offset, length);
            }
        }

        private boolean isValidByte(String text) {
            if (text.isEmpty() || "-".equals(text)) {
                return true;
            }
            try {
                Byte.parseByte(text);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }
}
