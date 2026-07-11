package com.vidlus.jarch.desk;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class DEditCombo<T> extends DEdit<T> {

    private final DefaultComboBoxModel<T> model = new DefaultComboBoxModel<>();

    public DEditCombo() {
        super(new JComboBox<>());
        comp().setModel(model);
    }

    @SafeVarargs
    public DEditCombo(T... items) {
        this();
        add(items);
    }

    public DEditCombo(Class<T> enumClass) {
        this();
        if (enumClass != null && enumClass.isEnum()) {
            add(enumClass.getEnumConstants());
        }
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

    public DEditCombo<T> add(T item) {
        model.addElement(item);
        return this;
    }

    @SafeVarargs
    public final DEditCombo<T> add(T... items) {
        if (items != null) {
            for (T item : items) {
                model.addElement(item);
            }
        }
        return this;
    }

    public DEditCombo<T> add(int atIndex, T item) {
        model.insertElementAt(item, atIndex);
        return this;
    }

    public DEditCombo<T> add(Class<T> enumClass) {
        if (enumClass != null && enumClass.isEnum()) {
            add(enumClass.getEnumConstants());
        }
        return this;
    }

    public DEditCombo<T> del(T item) {
        model.removeElement(item);
        return this;
    }

    public DEditCombo<T> del(int index) {
        model.removeElementAt(index);
        return this;
    }

    public DEditCombo<T> clear() {
        model.removeAllElements();
        return this;
    }

    public int selectedIndex() {
        return comp().getSelectedIndex();
    }

    public T selectedItem() {
        return (T) comp().getSelectedItem();
    }

    public DEditCombo<T> select(int index) {
        comp().setSelectedIndex(index);
        return this;
    }

    public DEditCombo<T> select(T element) {
        comp().setSelectedItem(element);
        return this;
    }

    public int itemsCount() {
        return model.getSize();
    }

    public T itemAt(int index) {
        return model.getElementAt(index);
    }

    public boolean editable() {
        return comp().isEditable();
    }

    public DEditCombo<T> editable(boolean editable) {
        comp().setEditable(editable);
        return this;
    }

    @Override
    public T value() {
        return getValue();
    }

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
