package com.vidlus.jarch.desk;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 * A fluent API wrapper for {@link JComboBox}, extending {@link DEdit} for handling selection from a list of items.
 *
 * @param <T> the type of the elements of this combo box
 */
public class DEditCombo<T> extends DEdit<T> {

    private final DefaultComboBoxModel<T> model = new DefaultComboBoxModel<>();

    /**
     * Constructs a new empty DEditCombo.
     */
    public DEditCombo() {
        super(new JComboBox<>());
        comp().setModel(model);
    }

    /**
     * Constructs a new DEditCombo with the specified items.
     * 
     * @param items the items to add
     */
    @SafeVarargs
    public DEditCombo(T... items) {
        this();
        add(items);
    }

    /**
     * Constructs a new DEditCombo populated with the constants of the specified enum class.
     * 
     * @param enumClass the enum class whose constants will populate the combo box
     */
    public DEditCombo(Class<T> enumClass) {
        this();
        if (enumClass != null && enumClass.isEnum()) {
            add(enumClass.getEnumConstants());
        }
    }
    
    /**
     * Returns the underlying JComboBox component.
     * 
     * @return the JComboBox component
     */
    @Override
    public JComboBox<T> comp() {
        return (JComboBox<T>) super.comp();
    }

    /**
     * Gets the currently selected value.
     * 
     * @return the selected value
     */
    @Override
    public T getValue() {
        return (T) comp().getSelectedItem();
    }

    /**
     * Sets the currently selected value.
     * 
     * @param value the value to select
     */
    @Override
    public void setValue(T value) {
        comp().setSelectedItem(value);
    }

    /**
     * Adds an item to the end of the combo box model.
     * 
     * @param item the item to add
     * @return this DEditCombo instance
     */
    public DEditCombo<T> add(T item) {
        model.addElement(item);
        return this;
    }

    /**
     * Adds multiple items to the combo box model.
     * 
     * @param items the items to add
     * @return this DEditCombo instance
     */
    @SafeVarargs
    public final DEditCombo<T> add(T... items) {
        if (items != null) {
            for (T item : items) {
                model.addElement(item);
            }
        }
        return this;
    }

    /**
     * Inserts an item at the specified index.
     * 
     * @param atIndex the index at which to insert the item
     * @param item the item to insert
     * @return this DEditCombo instance
     */
    public DEditCombo<T> add(int atIndex, T item) {
        model.insertElementAt(item, atIndex);
        return this;
    }

    /**
     * Adds the constants of the specified enum class to the combo box.
     * 
     * @param enumClass the enum class whose constants will be added
     * @return this DEditCombo instance
     */
    public DEditCombo<T> add(Class<T> enumClass) {
        if (enumClass != null && enumClass.isEnum()) {
            add(enumClass.getEnumConstants());
        }
        return this;
    }

    /**
     * Removes the specified item from the combo box.
     * 
     * @param item the item to remove
     * @return this DEditCombo instance
     */
    public DEditCombo<T> del(T item) {
        model.removeElement(item);
        return this;
    }

    /**
     * Removes the item at the specified index.
     * 
     * @param index the index of the item to remove
     * @return this DEditCombo instance
     */
    public DEditCombo<T> del(int index) {
        model.removeElementAt(index);
        return this;
    }

    /**
     * Removes all items from the combo box.
     * 
     * @return this DEditCombo instance
     */
    public DEditCombo<T> clear() {
        model.removeAllElements();
        return this;
    }

    /**
     * Returns the index of the currently selected item.
     * 
     * @return the selected index, or -1 if none is selected
     */
    public int selectedIndex() {
        return comp().getSelectedIndex();
    }

    /**
     * Sets the selected item by its index.
     * 
     * @param index the index of the item to select
     * @return this DEditCombo instance
     */
    public DEditCombo<T> selectedIndex(int index) {
        comp().setSelectedIndex(index);
        return this;
    }

    /**
     * Returns the currently selected item.
     * 
     * @return the selected item
     */
    public T selectedItem() {
        return (T) comp().getSelectedItem();
    }

    /**
     * Sets the selected item.
     * 
     * @param element the item to select
     * @return this DEditCombo instance
     */
    public DEditCombo<T> selectedItem(T element) {
        comp().setSelectedItem(element);
        return this;
    }

    /**
     * Returns the maximum number of rows the popup can display.
     * 
     * @return the maximum row count
     */
    public int maximumRowCount() {
        return comp().getMaximumRowCount();
    }

    /**
     * Sets the maximum number of rows the popup can display.
     * 
     * @param count the maximum row count
     * @return this DEditCombo instance
     */
    public DEditCombo<T> maximumRowCount(int count) {
        comp().setMaximumRowCount(count);
        return this;
    }

    /**
     * Returns the renderer used to display items.
     * 
     * @return the list cell renderer
     */
    public javax.swing.ListCellRenderer<? super T> renderer() {
        return comp().getRenderer();
    }

    /**
     * Sets the renderer used to display items.
     * 
     * @param renderer the list cell renderer
     * @return this DEditCombo instance
     */
    public DEditCombo<T> renderer(javax.swing.ListCellRenderer<? super T> renderer) {
        comp().setRenderer(renderer);
        return this;
    }

    /**
     * Returns whether lightweight popups are enabled.
     * 
     * @return true if lightweight popups are enabled
     */
    public boolean lightWeightPopupEnabled() {
        return comp().isLightWeightPopupEnabled();
    }

    /**
     * Sets whether lightweight popups are enabled.
     * 
     * @param b true to enable lightweight popups
     * @return this DEditCombo instance
     */
    public DEditCombo<T> lightWeightPopupEnabled(boolean b) {
        comp().setLightWeightPopupEnabled(b);
        return this;
    }

    /**
     * Returns whether the popup is currently visible.
     * 
     * @return true if popup is visible
     */
    public boolean popupVisible() {
        return comp().isPopupVisible();
    }

    /**
     * Sets the visibility of the popup.
     * 
     * @param b true to show the popup, false to hide it
     * @return this DEditCombo instance
     */
    public DEditCombo<T> popupVisible(boolean b) {
        comp().setPopupVisible(b);
        return this;
    }

    /**
     * Returns the number of items in the combo box.
     * 
     * @return the item count
     */
    public int itemsCount() {
        return model.getSize();
    }

    /**
     * Returns the item at the specified index.
     * 
     * @param index the index
     * @return the item
     */
    public T itemAt(int index) {
        return model.getElementAt(index);
    }

    /**
     * Returns whether the combo box is editable.
     * 
     * @return true if editable
     */
    public boolean editable() {
        return comp().isEditable();
    }

    /**
     * Sets whether the combo box is editable.
     * 
     * @param editable true to make editable
     * @return this DEditCombo instance
     */
    public DEditCombo<T> editable(boolean editable) {
        comp().setEditable(editable);
        return this;
    }

    /**
     * Alias for {@link #getValue()}.
     * 
     * @return the selected value
     */
    @Override
    public T value() {
        return getValue();
    }

    /**
     * Alias for {@link #setValue(Object)}.
     * 
     * @param value the value to select
     * @return this DEditCombo instance
     */
    @Override
    public DEditCombo<T> value(T value) {
        setValue(value);
        return this;
    }

    @Override
    public DEditCombo<T> onAction(Consumer<ActionEvent> consumer) {
        comp().addActionListener(e -> consumer.accept(e));
        return this;
    }

    @Override
    public DEditCombo<T> onMouseClicked(Consumer<MouseEvent> consumer) {
        var listener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                consumer.accept(e);
            }
        };
        comp().addMouseListener(listener);
        comp().getEditor().getEditorComponent().addMouseListener(listener);
        return this;
    }

    @Override
    public DEditCombo<T> onMousePressed(Consumer<MouseEvent> consumer) {
        var listener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                consumer.accept(e);
            }
        };
        comp().addMouseListener(listener);
        comp().getEditor().getEditorComponent().addMouseListener(listener);
        return this;
    }

    @Override
    public DEditCombo<T> onMouseReleased(Consumer<MouseEvent> consumer) {
        var listener = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                consumer.accept(e);
            }
        };
        comp().addMouseListener(listener);
        comp().getEditor().getEditorComponent().addMouseListener(listener);
        return this;
    }

    @Override
    public DEditCombo<T> onMouseEntered(Consumer<MouseEvent> consumer) {
        var listener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                consumer.accept(e);
            }
        };
        comp().addMouseListener(listener);
        comp().getEditor().getEditorComponent().addMouseListener(listener);
        return this;
    }

    @Override
    public DEditCombo<T> onMouseExited(Consumer<MouseEvent> consumer) {
        var listener = new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                consumer.accept(e);
            }
        };
        comp().addMouseListener(listener);
        comp().getEditor().getEditorComponent().addMouseListener(listener);
        return this;
    }

    @Override
    public DEditCombo<T> onKeyTyped(Consumer<KeyEvent> consumer) {
        var listener = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                consumer.accept(e);
            }
        };
        comp().addKeyListener(listener);
        comp().getEditor().getEditorComponent().addKeyListener(listener);
        return this;
    }

    @Override
    public DEditCombo<T> onKeyPressed(Consumer<KeyEvent> consumer) {
        var listener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                consumer.accept(e);
            }
        };
        comp().addKeyListener(listener);
        comp().getEditor().getEditorComponent().addKeyListener(listener);
        return this;
    }

    @Override
    public DEditCombo<T> onKeyReleased(Consumer<KeyEvent> consumer) {
        var listener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                consumer.accept(e);
            }
        };
        comp().addKeyListener(listener);
        comp().getEditor().getEditorComponent().addKeyListener(listener);
        return this;
    }

}
