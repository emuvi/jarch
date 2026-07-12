package com.vidlus.jarch.desk;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;

/**
 * A UI component for selecting an item from a list of options.
 * Provides a read-only text field and a button that opens a searchable list dialog.
 * 
 * @param <E> the type of elements in the search list
 */
public class DChangeSearch<E> extends DEditChange<E> {

    private E currentSelection;
    private List<E> options;
    private String dialogTitle = "Select Item";

    /**
     * Constructs a new DChangeSearch component with the given options.
     * The internal text field is set to non-editable.
     * 
     * @param options the List of available options
     */
    public DChangeSearch(List<E> options) {
        super("?");
        this.options = options;
        getField().setEditable(false);
    }

    /**
     * Retrieves the currently selected item.
     * 
     * @return the selected item, or null if none is selected
     */
    @Override
    public E getValue() {
        return currentSelection;
    }

    /**
     * Sets the selected item, updating the text field with its string representation.
     * 
     * @param value the item to set
     */
    @Override
    public void setValue(E value) {
        this.currentSelection = value;
        getField().setText(value == null ? "" : value.toString());
    }

    /**
     * Updates the available options for the search dialog.
     * 
     * @param options the new List of options
     * @return this DChangeSearch instance
     */
    public DChangeSearch<E> options(List<E> options) {
        this.options = options;
        return this;
    }

    /**
     * Sets whether this component is editable.
     * The internal text field remains non-editable to enforce selection via the dialog.
     * 
     * @param editable true to enable the action button, false to disable
     * @return this DChangeSearch instance
     */
    @Override
    public DChangeSearch<E> editable(boolean editable) {
        super.editable(editable);
        getField().setEditable(false);
        return this;
    }

    /**
     * Handles the action button press event.
     * Opens a list dialog displaying all available options for the user to choose from.
     */
    @Override
    protected void onActionPressed() {
        if (!editable()) return;
        if (options == null || options.isEmpty()) {
            new DAlert().parent(comp()).title("Empty").message("No options available.").show();
            return;
        }
        
        @SuppressWarnings("unchecked")
        JList<E> list = new JList<>(options.toArray((E[]) new Object[0]));
        if (currentSelection != null) {
            list.setSelectedValue(currentSelection, true);
        }
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(250, 300));
        
        DAlert alert = new DAlert()
            .parent(comp())
            .title(dialogTitle)
            .message(panel)
            .plain()
            .okCancel();
            
        if (alert.confirm() == JOptionPane.OK_OPTION) {
            E selected = list.getSelectedValue();
            if (selected != null) {
                setValue(selected);
            }
        }
    }

    /**
     * Fluent setter for the selected item.
     * 
     * @param selection the item to set
     * @return this DChangeSearch instance
     */
    public DChangeSearch<E> search(E selection) {
        setValue(selection);
        return this;
    }

    /**
     * Fluent setter for the dialog title displayed when the action button is pressed.
     * 
     * @param dialogTitle the title for the dialog
     * @return this DChangeSearch instance
     */
    public DChangeSearch<E> dialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
        return this;
    }
}
