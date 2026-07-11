package com.vidlus.jarch.desk;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

public class DChangeDate extends DEditChange<LocalDate> {

    public DChangeDate() {
        super("...");
    }

    @Override
    public LocalDate getValue() {
        var text = getField().getText();
        try {
            return text.isEmpty() ? null : LocalDate.parse(text);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setValue(LocalDate value) {
        getField().setText(value == null ? "" : value.toString());
    }

    @Override
    protected void onActionPressed() {
        LocalDate current = getValue();
        if (current == null) current = LocalDate.now();
        
        Date initDate = Date.from(current.atStartOfDay(ZoneId.systemDefault()).toInstant());
        SpinnerDateModel model = new SpinnerDateModel(initDate, null, null, java.util.Calendar.DAY_OF_MONTH);
        JSpinner spinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "yyyy-MM-dd");
        spinner.setEditor(editor);

        DAlert alert = new DAlert()
            .parent(comp())
            .title("Select Date")
            .message(spinner)
            .plain()
            .okCancel();

        if (alert.confirm() == JOptionPane.OK_OPTION) {
            Date selected = (Date) spinner.getValue();
            setValue(selected.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
    }
}
