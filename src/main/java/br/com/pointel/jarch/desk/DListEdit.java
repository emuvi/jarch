package br.com.pointel.jarch.desk;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

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

    public DListEdit<T> selectionSingle() {
        comp().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return this;
    }

    public DListEdit<T> selectionInterval() {
        comp().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        return this;
    }

    public DListEdit<T> selectionMultiple() {
        comp().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        return this;
    }

    public int getSelectedIndex() {
        return comp().getSelectedIndex();
    }

    public DListEdit<T> setSelectedIndex(int index) {
        comp().setSelectedIndex(index);
        return this;
    }

    public T getSelectedValue() {
        return comp().getSelectedValue();
    }

    public DListEdit<T> setSelectedValue(T value) {
        comp().setSelectedValue(value, true);
        return this;
    }

    public int[] getSelectedIndices() {
        return comp().getSelectedIndices();
    }

    public DListEdit<T> setSelectedIndices(int[] indices) {
        comp().setSelectedIndices(indices);
        return this;
    }

    public List<T> getSelectedValuesList() {
        return comp().getSelectedValuesList();
    }

    public DListEdit<T> add(T value) {
        model.addElement(value);
        return this;
    }

    public DListEdit<T> add(int index, T value) {
        model.add(index, value);
        return this;
    }

    public DListEdit<T> addAtSelection(T value) {
        var selected = getSelectedIndex();
        model.add(selected + 1, value);
        setSelectedIndex(selected + 1);
        return this;
    }

    public DListEdit<T> set(int index, T value) {
        model.set(index, value);
        return this;
    }

    public DListEdit<T> setAtSelection(T item) {
        var selected = getSelectedIndex();
        model.set(selected, item);
        setSelectedIndex(selected);
        return this;
    }

    public DListEdit<T> del(int index) {
        model.remove(index);
        return this;
    }

    public DListEdit<T> del(T value) {
        model.removeElement(value);
        return this;
    }

    public DListEdit<T> delAtSelection() {
        var indices = getSelectedIndices();
        if (indices.length > 0) {
            for (int i = indices.length - 1; i >= 0; i--) {
                model.remove(indices[i]);
            }
            if (model.getSize() > 0) {
                var next = indices[0] - 1;
                setSelectedIndex(next < 0 ? 0 : next);
            }
        }
        return this;
    }

    public DListEdit<T> clear() {
        model.clear();
        return this;
    }

    public int size() {
        return model.getSize();
    }
         
    public DListEdit<T> moveUp(int index) {
        if (index > 0 && index < model.getSize()) {
            var item = model.remove(index);
            model.add(index - 1, item);
            setSelectedIndex(index - 1);
        }
        return this;
    }

    public DListEdit<T> moveUpSelection() {
        var indices = getSelectedIndices();
        if (indices.length > 0 && indices[0] > 0) {
            for (int i = 0; i < indices.length; i++) {
                var index = indices[i];
                var item = model.remove(index);
                model.add(index - 1, item);
                indices[i]--;
            }
            setSelectedIndices(indices);
        }
        return this;
    }

    public DListEdit<T> moveDown(int index) {
        if (index >= 0 && index < model.getSize() - 1) {
            var item = model.remove(index);
            model.add(index + 1, item);
            setSelectedIndex(index + 1);
        }
        return this;
    }

    public DListEdit<T> moveDownSelection() {
        var indices = getSelectedIndices();
        if (indices.length > 0 && indices[indices.length - 1] < model.getSize() - 1) {
            for (int i = indices.length - 1; i >= 0; i--) {
                var index = indices[i];
                var item = model.remove(index);
                model.add(index + 1, item);
                indices[i]++;
            }
            setSelectedIndices(indices);
        }
        return this;
    }

}
