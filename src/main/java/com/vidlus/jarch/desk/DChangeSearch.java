package com.vidlus.jarch.desk;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;

public class DChangeSearch<E> extends DEditChange<E> {

    private E currentSelection;
    private List<E> options;

    public DChangeSearch(List<E> options) {
        super("?");
        this.options = options;
        getField().setEditable(false);
    }

    @Override
    public E getValue() {
        return currentSelection;
    }

    @Override
    public void setValue(E value) {
        this.currentSelection = value;
        getField().setText(value == null ? "" : value.toString());
    }

    public DChangeSearch<E> options(List<E> options) {
        this.options = options;
        return this;
    }

    @Override
    public DChangeSearch<E> editable(boolean editable) {
        super.editable(editable);
        getField().setEditable(false);
        return this;
    }

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
            .title("Select Item")
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
}
