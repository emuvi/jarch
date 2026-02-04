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

    public T getSelectedValue() {
        return comp().getSelectedValue();
    }

    public void add(T value) {
        model.addElement(value);
    }

    public void add(int index, T value) {
        model.add(index, value);
    }

    public void addAtSelection(T value) {
        model.add(comp().getSelectedIndex() + 1, value);
    }

    public void set(int index, T value) {
        model.set(index, value);
    }

    public void setAtSelection(T item) {
        model.set(comp().getSelectedIndex(), item);
    }

    public void del(int index) {
        model.remove(index);
    }

    public void del(T value) {
        model.removeElement(value);
    }

    public void delAtSelection() {
        if (comp().getSelectedIndex() >= 0) {
            model.remove(comp().getSelectedIndex());
        }
    }

    public void clear() {
        model.clear();
    }

}
