package br.com.pointel.jarch.desk;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class DListEdit<T> extends DEdit<ArrayList<T>> {

    private final DefaultListModel<T> model = new DefaultListModel<>();
    
    public DListEdit() {
        this(null);
    }

    public DListEdit(ArrayList<T> value) {
        super(new JList<T>());
        comp().setModel(model);
        setValue(value);
    }

    @Override
    public JList<T> comp() {
        return (JList<T>) super.comp();
    }

    @Override
    public ArrayList<T> getValue() {
        var result = new ArrayList<T>(model.getSize());
        for (int i = 0; i < model.getSize(); i++) {
            result.add(model.getElementAt(i));
        }
        return result;
    }

    @Override
    public void setValue(ArrayList<T> value) {
        model.clear();
        if (value != null) {
            for (T item : value) {
                model.addElement(item);
            }
        }
    }

    public int getSelectedIndex() {
        return comp().getSelectedIndex();
    }

    public void setSelectedIndex(int index) {
        comp().setSelectedIndex(index);
    }

    public T getSelectedValue() {
        return comp().getSelectedValue();
    }

    public void setSelectedValue(T value) {
        comp().setSelectedValue(value, true);
    }

    public void add(T value) {
        model.addElement(value);
    }

    public void add(int index, T value) {
        model.add(index, value);
    }

    public void addAtSelection(T value) {
        var selected = getSelectedIndex();
        model.add(selected + 1, value);
        setSelectedIndex(selected + 1);
    }

    public void set(int index, T value) {
        model.set(index, value);
    }

    public void setAtSelection(T item) {
        var selected = getSelectedIndex();
        model.set(selected, item);
        setSelectedIndex(selected);
    }

    public void del(int index) {
        model.remove(index);
    }

    public void del(T value) {
        model.removeElement(value);
    }

    public void delAtSelection() {
        if (getSelectedIndex() >= 0) {
            var selected = getSelectedIndex();
            model.remove(selected);
            setSelectedIndex(selected - 1);
        }
    }

    public void clear() {
        model.clear();
    }
         
    public void moveUp(int index) {
        if (index > 0 && index < model.getSize()) {
            var item = model.remove(index);
            model.add(index - 1, item);
            setSelectedIndex(index - 1);
        }
    }

    public void moveUpSelection() {
        moveUp(getSelectedIndex());
    }

    public void moveDown(int index) {
        if (index >= 0 && index < model.getSize() - 1) {
            var item = model.remove(index);
            model.add(index + 1, item);
            setSelectedIndex(index + 1);
        }
    }

    public void moveDownSelection() {
        moveDown(getSelectedIndex());
    }

}
