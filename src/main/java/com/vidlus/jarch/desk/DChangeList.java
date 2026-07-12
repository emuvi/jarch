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

/**
 * A UI component for editing and selecting a list of strings.
 * Provides a text field for comma-separated items and a button that opens a multiline text area dialog.
 */
public class DChangeList extends DEditChange<List<String>> {

    private String dialogTitle = "Edit List (One item per line)";

    /**
     * Constructs a new DChangeList component.
     */
    public DChangeList() {
        super("Edit");
    }

    /**
     * Retrieves the list of strings parsed from the text field (comma-separated).
     * 
     * @return the List of strings, or an empty list if blank
     */
    @Override
    public List<String> getValue() {
        var text = getField().getText();
        if (text.isEmpty()) return new ArrayList<>();
        return new ArrayList<>(Arrays.asList(text.split(",\\s*")));
    }

    /**
     * Sets the list value, joining them with commas and updating the text field.
     * 
     * @param value the List of strings to set
     */
    @Override
    public void setValue(List<String> value) {
        if (value == null || value.isEmpty()) {
            getField().setText("");
        } else {
            getField().setText(String.join(", ", value));
        }
    }

    /**
     * Handles the action button press event.
     * Opens a dialog with a text area allowing the user to edit the list with one item per line.
     */
    @Override
    protected void onActionPressed() {
        if (!editable()) return;
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
            .title(dialogTitle)
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

    /**
     * Fluent setter for the list value.
     * 
     * @param list the List of strings to set
     * @return this DChangeList instance
     */
    public DChangeList list(List<String> list) {
        setValue(list);
        return this;
    }

    /**
     * Fluent setter for the list items via varargs.
     * 
     * @param items the strings to set as the list
     * @return this DChangeList instance
     */
    public DChangeList items(String... items) {
        setValue(Arrays.asList(items));
        return this;
    }

    /**
     * Fluent setter for the dialog title displayed when the action button is pressed.
     * 
     * @param dialogTitle the title for the dialog
     * @return this DChangeList instance
     */
    public DChangeList dialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
        return this;
    }
}
