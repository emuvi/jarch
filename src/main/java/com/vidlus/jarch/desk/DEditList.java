package com.vidlus.jarch.desk;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

/**
 * A fluent API wrapper for {@link JList}, extending {@link DEdit} for handling a list of items.
 *
 * @param <T> the type of the elements of this list
 */
public class DEditList<T> extends DEdit<ArrayList<T>> {

    private final DefaultListModel<T> model = new DefaultListModel<>();
    
    /**
     * Constructs a new empty DEditList.
     */
    public DEditList() {
        this(null);
    }

    /**
     * Constructs a new DEditList with the specified initial values.
     * 
     * @param value the initial values
     */
    public DEditList(ArrayList<T> value) {
        super(new JList<T>());
        comp().setModel(model);
        setValue(value);
    }

    /**
     * Returns the underlying JList component.
     * 
     * @return the JList component
     */
    @Override
    public JList<T> comp() {
        return (JList<T>) super.comp();
    }

    /**
     * Gets all the items in the list.
     * 
     * @return an ArrayList containing all items
     */
    @Override
    public ArrayList<T> getValue() {
        var result = new ArrayList<T>(model.getSize());
        for (int i = 0; i < model.getSize(); i++) {
            result.add(model.getElementAt(i));
        }
        return result;
    }

    /**
     * Sets the items in the list, replacing any existing items.
     * 
     * @param value the new items
     */
    @Override
    public void setValue(ArrayList<T> value) {
        model.clear();
        if (value != null) {
            for (T item : value) {
                model.addElement(item);
            }
        }
    }

    /**
     * Sets the selection mode to single selection.
     * 
     * @return this DEditList instance
     */
    public DEditList<T> selectionSingle() {
        comp().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return this;
    }

    /**
     * Sets the selection mode to single interval selection.
     * 
     * @return this DEditList instance
     */
    public DEditList<T> selectionInterval() {
        comp().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        return this;
    }

    /**
     * Sets the selection mode to multiple interval selection.
     * 
     * @return this DEditList instance
     */
    public DEditList<T> selectionMultiple() {
        comp().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        return this;
    }

    /**
     * Returns the selected index.
     * 
     * @return the selected index
     */
    public int selectedIndex() {
        return comp().getSelectedIndex();
    }

    /**
     * Sets the selected index.
     * 
     * @param index the index to select
     * @return this DEditList instance
     */
    public DEditList<T> selectedIndex(int index) {
        comp().setSelectedIndex(index);
        return this;
    }

    /**
     * Returns the selected value.
     * 
     * @return the selected value
     */
    public T selectedValue() {
        return comp().getSelectedValue();
    }

    /**
     * Sets the selected value.
     * 
     * @param value the value to select
     * @return this DEditList instance
     */
    public DEditList<T> selectedValue(T value) {
        comp().setSelectedValue(value, true);
        return this;
    }

    /**
     * Returns the selected indices.
     * 
     * @return an array of selected indices
     */
    public int[] selectedIndices() {
        return comp().getSelectedIndices();
    }

    /**
     * Sets the selected indices.
     * 
     * @param indices an array of indices to select
     * @return this DEditList instance
     */
    public DEditList<T> selectedIndices(int[] indices) {
        comp().setSelectedIndices(indices);
        return this;
    }

    /**
     * Returns a list of all selected values.
     * 
     * @return a list of selected values
     */
    public List<T> selectedValuesList() {
        return comp().getSelectedValuesList();
    }

    /**
     * Returns the current selection mode.
     * 
     * @return the selection mode
     */
    public int selectionMode() {
        return comp().getSelectionMode();
    }

    /**
     * Sets the selection mode.
     * 
     * @param mode the selection mode (e.g. ListSelectionModel.SINGLE_SELECTION)
     * @return this DEditList instance
     */
    public DEditList<T> selectionMode(int mode) {
        comp().setSelectionMode(mode);
        return this;
    }

    /**
     * Returns the visible row count.
     * 
     * @return the visible row count
     */
    public int visibleRowCount() {
        return comp().getVisibleRowCount();
    }

    /**
     * Sets the preferred number of visible rows.
     * 
     * @param count the number of rows
     * @return this DEditList instance
     */
    public DEditList<T> visibleRowCount(int count) {
        comp().setVisibleRowCount(count);
        return this;
    }

    /**
     * Returns the fixed cell width.
     * 
     * @return the fixed cell width
     */
    public int fixedCellWidth() {
        return comp().getFixedCellWidth();
    }

    /**
     * Sets the fixed cell width.
     * 
     * @param width the width in pixels
     * @return this DEditList instance
     */
    public DEditList<T> fixedCellWidth(int width) {
        comp().setFixedCellWidth(width);
        return this;
    }

    /**
     * Returns the fixed cell height.
     * 
     * @return the fixed cell height
     */
    public int fixedCellHeight() {
        return comp().getFixedCellHeight();
    }

    /**
     * Sets the fixed cell height.
     * 
     * @param height the height in pixels
     * @return this DEditList instance
     */
    public DEditList<T> fixedCellHeight(int height) {
        comp().setFixedCellHeight(height);
        return this;
    }

    /**
     * Returns the layout orientation.
     * 
     * @return the layout orientation
     */
    public int layoutOrientation() {
        return comp().getLayoutOrientation();
    }

    /**
     * Sets the layout orientation.
     * 
     * @param layoutOrientation the layout orientation (e.g. JList.VERTICAL)
     * @return this DEditList instance
     */
    public DEditList<T> layoutOrientation(int layoutOrientation) {
        comp().setLayoutOrientation(layoutOrientation);
        return this;
    }

    /**
     * Returns whether drag and drop is enabled.
     * 
     * @return true if drag and drop is enabled
     */
    public boolean dragEnabled() {
        return comp().getDragEnabled();
    }

    /**
     * Sets whether drag and drop is enabled.
     * 
     * @param b true to enable drag and drop
     * @return this DEditList instance
     */
    public DEditList<T> dragEnabled(boolean b) {
        comp().setDragEnabled(b);
        return this;
    }

    /**
     * Returns the cell renderer.
     * 
     * @return the cell renderer
     */
    public javax.swing.ListCellRenderer<? super T> cellRenderer() {
        return comp().getCellRenderer();
    }

    /**
     * Sets the cell renderer.
     * 
     * @param cellRenderer the cell renderer
     * @return this DEditList instance
     */
    public DEditList<T> cellRenderer(javax.swing.ListCellRenderer<? super T> cellRenderer) {
        comp().setCellRenderer(cellRenderer);
        return this;
    }

    /**
     * Adds an item to the end of the list.
     * 
     * @param value the value to add
     * @return this DEditList instance
     */
    public DEditList<T> add(T value) {
        model.addElement(value);
        return this;
    }

    /**
     * Inserts an item at the specified index.
     * 
     * @param index the index at which to insert
     * @param value the value to insert
     * @return this DEditList instance
     */
    public DEditList<T> add(int index, T value) {
        model.add(index, value);
        return this;
    }

    /**
     * Adds an item immediately following the currently selected index.
     * 
     * @param value the value to add
     * @return this DEditList instance
     */
    public DEditList<T> addAtSelection(T value) {
        var selected = selectedIndex();
        model.add(selected + 1, value);
        selectedIndex(selected + 1);
        return this;
    }

    /**
     * Replaces the item at the specified index.
     * 
     * @param index the index to replace
     * @param value the new value
     * @return this DEditList instance
     */
    public DEditList<T> set(int index, T value) {
        model.set(index, value);
        return this;
    }

    /**
     * Replaces the currently selected item.
     * 
     * @param item the new value
     * @return this DEditList instance
     */
    public DEditList<T> setAtSelection(T item) {
        var selected = selectedIndex();
        model.set(selected, item);
        selectedIndex(selected);
        return this;
    }

    /**
     * Removes the item at the specified index.
     * 
     * @param index the index to remove
     * @return this DEditList instance
     */
    public DEditList<T> del(int index) {
        model.remove(index);
        return this;
    }

    /**
     * Removes the first occurrence of the specified value.
     * 
     * @param value the value to remove
     * @return this DEditList instance
     */
    public DEditList<T> del(T value) {
        model.removeElement(value);
        return this;
    }

    /**
     * Removes all currently selected items.
     * 
     * @return this DEditList instance
     */
    public DEditList<T> delAtSelection() {
        var indices = selectedIndices();
        if (indices.length > 0) {
            for (int i = indices.length - 1; i >= 0; i--) {
                model.remove(indices[i]);
            }
            if (model.getSize() > 0) {
                var next = indices[0] - 1;
                selectedIndex(next < 0 ? 0 : next);
            }
        }
        return this;
    }

    /**
     * Removes all elements from the list.
     * 
     * @return this DEditList instance
     */
    public DEditList<T> clear() {
        model.clear();
        return this;
    }

    /**
     * Returns the number of items in the list.
     * 
     * @return the number of items
     */
    public int size() {
        return model.getSize();
    }
         
    /**
     * Moves the item at the specified index up one position.
     * 
     * @param index the index of the item to move
     * @return this DEditList instance
     */
    public DEditList<T> moveUp(int index) {
        if (index > 0 && index < model.getSize()) {
            var item = model.remove(index);
            model.add(index - 1, item);
            selectedIndex(index - 1);
        }
        return this;
    }

    /**
     * Moves all selected items up one position.
     * 
     * @return this DEditList instance
     */
    public DEditList<T> moveUpSelection() {
        var indices = selectedIndices();
        if (indices.length > 0 && indices[0] > 0) {
            for (int i = 0; i < indices.length; i++) {
                var index = indices[i];
                var item = model.remove(index);
                model.add(index - 1, item);
                indices[i]--;
            }
            selectedIndices(indices);
        }
        return this;
    }

    /**
     * Moves the item at the specified index down one position.
     * 
     * @param index the index of the item to move
     * @return this DEditList instance
     */
    public DEditList<T> moveDown(int index) {
        if (index >= 0 && index < model.getSize() - 1) {
            var item = model.remove(index);
            model.add(index + 1, item);
            selectedIndex(index + 1);
        }
        return this;
    }

    /**
     * Moves all selected items down one position.
     * 
     * @return this DEditList instance
     */
    public DEditList<T> moveDownSelection() {
        var indices = selectedIndices();
        if (indices.length > 0 && indices[indices.length - 1] < model.getSize() - 1) {
            for (int i = indices.length - 1; i >= 0; i--) {
                var index = indices[i];
                var item = model.remove(index);
                model.add(index + 1, item);
                indices[i]++;
            }
            selectedIndices(indices);
        }
        return this;
    }

}
