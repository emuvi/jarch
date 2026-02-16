package br.com.pointel.jarch.desk;

import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class DComboEdit<T> extends DEdit<T> {

    private final DefaultComboBoxModel<T> model = new DefaultComboBoxModel<>();

    public DComboEdit() {
        super(new JComboBox<>());
        comp().setModel(model);
    }

    @SafeVarargs
    public DComboEdit(T... items) {
        this();
        add(items);
    }

    @Override
    public JComboBox<T> comp() {
        return (JComboBox<T>) super.comp();
    }

    @Override
    public T getValue() {
        return (T) comp().getSelectedItem();
    }

    @Override
    public void setValue(T value) {
        comp().setSelectedItem(value);
    }

    public DComboEdit<T> add(T item) {
        model.addElement(item);
        return this;
    }

    @SafeVarargs
    public final DComboEdit<T> add(T... items) {
        if (items != null) {
            for (T item : items) {
                model.addElement(item);
            }
        }
        return this;
    }

    public DComboEdit<T> del(T item) {
        model.removeElement(item);
        return this;
    }

    public DComboEdit<T> del(int index) {
        model.removeElementAt(index);
        return this;
    }

    public DComboEdit<T> clear() {
        model.removeAllElements();
        return this;
    }

    public int selectedIndex() {
        return comp().getSelectedIndex();
    }

    public DComboEdit<T> selectedIndex(int index) {
        comp().setSelectedIndex(index);
        return this;
    }

    public boolean editable() {
        return comp().isEditable();
    }

    public DComboEdit<T> editable(boolean editable) {
        comp().setEditable(editable);
        return this;
    }

    public boolean enabled() {
        return comp().isEnabled();
    }

    public DComboEdit<T> enabled(boolean enabled) {
        comp().setEnabled(enabled);
        return this;
    }

    @Override
    public T value() {
        return getValue();
    }

    @Override
    public DComboEdit<T> value(T value) {
        setValue(value);
        return this;
    }

    @Override
    public String name() {
        return comp().getName();
    }

    @Override
    public DComboEdit<T> name(String name) {
        comp().setName(name);
        return this;
    }

    public DComboEdit<T> onClick(ActionListener listener) {
        comp().addActionListener(listener);
        return this;
    }

    @Override
    public DComboEdit<T> hint(String hint) {
        comp().setToolTipText(hint);
        return this;
    }

}
