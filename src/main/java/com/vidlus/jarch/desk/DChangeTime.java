package com.vidlus.jarch.desk;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

public class DChangeTime extends DEditChange<LocalTime> {

    public DChangeTime() {
        super("...");
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
        LocalTime current = getValue();
        if (current == null) current = LocalTime.now();
        
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
}
