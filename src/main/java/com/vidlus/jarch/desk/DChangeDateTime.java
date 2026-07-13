package com.vidlus.jarch.desk;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * A UI component for editing and selecting a timestamp (date and time).
 * Provides a text field with a timestamp format filter and a button that opens a datetime picker dialog.
 */
public class DChangeDateTime extends DEditChange<LocalDateTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private String dialogTitle = "Select Timestamp";

    /**
     * Constructs a new DChangeDateTime component.
     * Applies a TimestampFilter to restrict input to valid timestamp characters.
     */
    public DChangeDateTime() {
        super("*");
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new TimestampFilter());
    }

    /**
     * Retrieves the parsed timestamp from the text field.
     * 
     * @return the parsed LocalDateTime, or null if empty or invalid
     */
    @Override
    public LocalDateTime getValue() {
        var text = getField().getText();
        try {
            return text.isEmpty() ? null : LocalDateTime.parse(text, FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Sets the timestamp value, formatting it as a string in the text field.
     * 
     * @param value the LocalDateTime to set
     */
    @Override
    public void setValue(LocalDateTime value) {
        getField().setText(value == null ? "" : value.format(FORMATTER));
    }

    /**
     * Handles the action button press event.
     * Opens a spinner-based datetime picker dialog for the user to select a timestamp.
     */
    @Override
    protected void onActionPressed() {
        if (!editable()) return;
        LocalDateTime current = getValue();
        if (current == null) {
            current = LocalDateTime.now();
        }
        
        DEditDateTime editor = new DEditDateTime().value(current);

        DAlert alert = new DAlert()
                .parent(comp())
                .title(dialogTitle)
                .message(editor.comp())
                .plain()
                .okCancel();

        if (alert.confirm() == JOptionPane.OK_OPTION) {
            setValue(editor.getValue());
        }
    }

    /**
     * A document filter that restricts text input to a partial or complete yyyy-MM-dd HH:mm:ss timestamp format.
     */
    private class TimestampFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            var text = fb.getDocument().getText(0, fb.getDocument().getLength());
            var newText = text.substring(0, offset) + string + text.substring(offset);
            if (isValid(newText)) super.insertString(fb, offset, string, attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            var currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            var newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
            if (isValid(newText)) super.replace(fb, offset, length, text, attrs);
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            var currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            var newText = currentText.substring(0, offset) + currentText.substring(offset + length);
            if (isValid(newText)) super.remove(fb, offset, length);
        }

        private boolean isValid(String text) {
            if (text.isEmpty()) return true;
            if (text.length() > 19) return false;
            return text.matches("^(\\d{0,4}(-\\d{0,2}(-\\d{0,2})?)?|\\d{4}-\\d{2}-\\d{2} \\d{0,2}(:\\d{0,2}(:\\d{0,2})?)?)$");
        }
    }

    /**
     * Fluent setter for the timestamp value.
     * 
     * @param timestamp the LocalDateTime to set
     * @return this DChangeDateTime instance
     */
    public DChangeDateTime timestamp(LocalDateTime timestamp) {
        setValue(timestamp);
        return this;
    }

    /**
     * Fluent setter for the dialog title displayed when the action button is pressed.
     * 
     * @param dialogTitle the title for the dialog
     * @return this DChangeDateTime instance
     */
    public DChangeDateTime dialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
        return this;
    }
}
