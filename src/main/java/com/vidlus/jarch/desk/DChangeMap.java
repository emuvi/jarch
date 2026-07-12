package com.vidlus.jarch.desk;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DChangeMap extends DEditChange<Map<String, String>> {

    public DChangeMap() {
        super("Edit");
    }

    @Override
    public Map<String, String> getValue() {
        var text = getField().getText();
        Map<String, String> map = new LinkedHashMap<>();
        if (text.isEmpty()) return map;
        
        String[] pairs = text.split(";\\s*");
        for (String pair : pairs) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2) {
                map.put(kv[0].trim(), kv[1].trim());
            } else if (kv.length == 1 && !kv[0].trim().isEmpty()) {
                map.put(kv[0].trim(), "");
            }
        }
        return map;
    }

    @Override
    public void setValue(Map<String, String> value) {
        if (value == null || value.isEmpty()) {
            getField().setText("");
            return;
        }
        List<String> entries = new ArrayList<>();
        for (Map.Entry<String, String> entry : value.entrySet()) {
            entries.add(entry.getKey() + "=" + entry.getValue());
        }
        getField().setText(String.join("; ", entries));
    }

    @Override
    protected void onActionPressed() {
        if (!editable()) return;
        JTextArea textArea = new JTextArea(10, 30);
        Map<String, String> current = getValue();
        if (current != null) {
            List<String> lines = new ArrayList<>();
            for (Map.Entry<String, String> entry : current.entrySet()) {
                lines.add(entry.getKey() + "=" + entry.getValue());
            }
            textArea.setText(String.join("\n", lines));
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(300, 200));

        DAlert alert = new DAlert()
            .parent(comp())
            .title("Edit Map (key=value per line)")
            .message(panel)
            .plain()
            .okCancel();

        if (alert.confirm() == JOptionPane.OK_OPTION) {
            String text = textArea.getText().trim();
            Map<String, String> newMap = new LinkedHashMap<>();
            if (!text.isEmpty()) {
                String[] lines = text.split("\\r?\\n");
                for (String line : lines) {
                    String[] kv = line.split("=", 2);
                    if (kv.length == 2) {
                        newMap.put(kv[0].trim(), kv[1].trim());
                    } else if (kv.length == 1 && !kv[0].trim().isEmpty()) {
                        newMap.put(kv[0].trim(), "");
                    }
                }
            }
            setValue(newMap);
        }
    }
}
