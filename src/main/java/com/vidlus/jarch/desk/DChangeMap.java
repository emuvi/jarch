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

/**
 * A UI component for editing and selecting a map of string key-value pairs.
 * Provides a text field for semicolon-separated pairs and a button that opens a multiline text area dialog.
 */
public class DChangeMap extends DEditChange<Map<String, String>> {

    private String dialogTitle = "Edit Map (key=value per line)";

    /**
     * Constructs a new DChangeMap component.
     */
    public DChangeMap() {
        super("Edit");
    }

    /**
     * Retrieves the map parsed from the text field (semicolon-separated `key=value` pairs).
     * 
     * @return the Map of key-value string pairs, or an empty map if blank
     */
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

    /**
     * Sets the map value, formatting it as `key=value` separated by semicolons in the text field.
     * 
     * @param value the Map to set
     */
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

    /**
     * Handles the action button press event.
     * Opens a dialog with a text area allowing the user to edit the map with one `key=value` per line.
     */
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
            .title(dialogTitle)
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

    /**
     * Fluent setter for the map value.
     * 
     * @param map the Map to set
     * @return this DChangeMap instance
     */
    public DChangeMap map(Map<String, String> map) {
        setValue(map);
        return this;
    }

    /**
     * Fluent setter for the dialog title displayed when the action button is pressed.
     * 
     * @param dialogTitle the title for the dialog
     * @return this DChangeMap instance
     */
    public DChangeMap dialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
        return this;
    }
}
