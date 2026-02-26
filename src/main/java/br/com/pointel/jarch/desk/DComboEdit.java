package br.com.pointel.jarch.desk;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

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

    public DComboEdit(Class<T> enumClass) {
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

    public DComboEdit<T> add(Class<T> enumClass) {
        if (enumClass != null && enumClass.isEnum()) {
            add(enumClass.getEnumConstants());
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

    public DComboEdit<T> onAction(Consumer<ActionEvent> consumer) {
        comp().addActionListener(e -> consumer.accept(e));
        return this;
    }

    public DComboEdit<T> onMouseClicked(Consumer<MouseEvent> consumer) {
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

    public DComboEdit<T> onMousePressed(Consumer<MouseEvent> consumer) {
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

    public DComboEdit<T> onMouseReleased(Consumer<MouseEvent> consumer) {
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

    public DComboEdit<T> onMouseEntered(Consumer<MouseEvent> consumer) {
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

    public DComboEdit<T> onMouseExited(Consumer<MouseEvent> consumer) {
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

    public DComboEdit<T> onKeyTyped(Consumer<KeyEvent> consumer) {
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

    public DComboEdit<T> onKeyPressed(Consumer<KeyEvent> consumer) {
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

    public DComboEdit<T> onKeyReleased(Consumer<KeyEvent> consumer) {
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

    @Override
    public DComboEdit<T> hint(String hint) {
        comp().setToolTipText(hint);
        return this;
    }

}
