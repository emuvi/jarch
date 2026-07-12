package com.vidlus.jarch.desk;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class DChangeTime extends DEditChange<LocalTime> {

    public DChangeTime() {
        super("*");
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new TimeFilter());
    }

    @Override
    public LocalTime getValue() {
        var text = getField().getText();
        try {
            return text.isEmpty() ? null : LocalTime.parse(text);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setValue(LocalTime value) {
        getField().setText(value == null ? "" : value.toString());
    }

    @Override
    protected void onActionPressed() {
        if (!editable()) return;
        LocalTime current = getValue();
        if (current == null) {
            current = LocalTime.now();
        }
        
        Date initDate = Date.from(current.atDate(java.time.LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant());
        SpinnerDateModel model = new SpinnerDateModel(initDate, null, null, java.util.Calendar.MINUTE);
        JSpinner spinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "HH:mm:ss");
        spinner.setEditor(editor);

        DAlert alert = new DAlert()
                .parent(comp())
                .title("Select Time")
                .message(spinner)
                .plain()
                .okCancel();

        if (alert.confirm() == JOptionPane.OK_OPTION) {
            Date selected = (Date) spinner.getValue();
            setValue(selected.toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
        }
    }

    private class TimeFilter extends DocumentFilter {
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
            if (text.length() > 8) return false;
            return text.matches("^\\d{0,2}(:\\d{0,2}(:\\d{0,2})?)?$");
        }
    }
}
