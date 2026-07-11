package com.vidlus.jarch.desk;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DChangeList extends DEditChange<List<String>> {

    public DChangeList() {
        super("Edit");
    }

    @Override
    public List<String> getValue() {
        var text = getField().getText();
        if (text.isEmpty()) return new ArrayList<>();
        return new ArrayList<>(Arrays.asList(text.split(",\\s*")));
    }

    @Override
    public void setValue(List<String> value) {
        if (value == null || value.isEmpty()) {
            getField().setText("");
        } else {
            getField().setText(String.join(", ", value));
        }
    }

    @Override
    protected void onActionPressed() {
        JTextArea textArea = new JTextArea(10, 30);
        List<String> current = getValue();
        if (current != null) {
            textArea.setText(String.join("\n", current));
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(300, 200));

        DAlert alert = new DAlert()
            .parent(comp())
            .title("Edit List (One item per line)")
            .message(panel)
            .plain()
            .okCancel();

        if (alert.confirm() == JOptionPane.OK_OPTION) {
            String text = textArea.getText().trim();
            if (text.isEmpty()) {
                setValue(new ArrayList<>());
            } else {
                setValue(new ArrayList<>(Arrays.asList(text.split("\\r?\\n"))));
            }
        }
    }
}
