package com.vidlus.jarch.desk;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import javax.swing.JTextField;

public abstract class DEditField<T> extends DEdit<T> {

    public DEditField() {
        super(new JTextField());
    }

    public DEditField(String text) {
        super(new JTextField(text));
    }

    public DEditField(int cols) {
        super(new JTextField(cols));
    }

    public DEditField(String text, int cols) {
        super(new JTextField(text, cols));
    }

    @Override
    public JTextField comp() {
        return (JTextField) super.comp();
    }

    public int cols() {
        return comp().getColumns();
    }

    public DEditField<T> cols(int cols) {
        comp().setColumns(cols);
        return this;
    }

    public boolean editable() {
        return comp().isEditable();
    }

    public DEditField<T> editable(boolean editable) {
        comp().setEditable(editable);
        return this;
    }

    @Override
    public T value() {
        return getValue();
    }

    @Override
    public DEditField<T> value(T value) {
        setValue(value);
        return this;
    }

    public DEditField<T> cut() {
        comp().cut();
        return this;
    }

    public DEditField<T> copy() {
        comp().copy();
        return this;
    }

    public DEditField<T> paste() {
        comp().paste();
        return this;
    }

    public DEditField<T> select(int selectionStart, int selectionEnd) {
        comp().select(selectionStart, selectionEnd);
        return this;
    }

    public DEditField<T> selectAll() {
        comp().selectAll();
        return this;
    }

    public int selectionStart() {
        return comp().getSelectionStart();
    }

    public DEditField<T> selectionStart(int selectionStart) {
        comp().setSelectionStart(selectionStart);
        return this;
    }

    public int selectionEnd() {
        return comp().getSelectionEnd();
    }

    public DEditField<T> selectionEnd(int selectionEnd) {
        comp().setSelectionEnd(selectionEnd);
        return this;
    }

    public String selectedText() {
        return comp().getSelectedText();
    }

    public DEditField<T> replaceSelection(String content) {
        comp().replaceSelection(content);
        return this;
    }

    public int caretPosition() {
        return comp().getCaretPosition();
    }

    public DEditField<T> caretPosition(int position) {
        comp().setCaretPosition(position);
        return this;
    }

    public DEditField<T> moveCaretPosition(int position) {
        comp().moveCaretPosition(position);
        return this;
    }

    public int horizontalAlignment() {
        return comp().getHorizontalAlignment();
    }

    public DEditField<T> horizontalAlignment(int alignment) {
        comp().setHorizontalAlignment(alignment);
        return this;
    }

    public DEditField<T> horizontalAlignmentLeft() {
        comp().setHorizontalAlignment(JTextField.LEFT);
        return this;
    }

    public DEditField<T> horizontalAlignmentCenter() {
        comp().setHorizontalAlignment(JTextField.CENTER);
        return this;
    }

    public DEditField<T> horizontalAlignmentRight() {
        comp().setHorizontalAlignment(JTextField.RIGHT);
        return this;
    }

    public DEditField<T> horizontalAlignmentLeading() {
        comp().setHorizontalAlignment(JTextField.LEADING);
        return this;
    }

    public DEditField<T> horizontalAlignmentTrailing() {
        comp().setHorizontalAlignment(JTextField.TRAILING);
        return this;
    }

    public DEditField<T> onMouseClicked(Consumer<MouseEvent> consumer) {
        comp().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    public DEditField<T> onMousePressed(Consumer<MouseEvent> consumer) {
        comp().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    public DEditField<T> onMouseReleased(Consumer<MouseEvent> consumer) {
        comp().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    public DEditField<T> onMouseEntered(Consumer<MouseEvent> consumer) {
        comp().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    public DEditField<T> onMouseExited(Consumer<MouseEvent> consumer) {
        comp().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    public DEditField<T> onKeyTyped(Consumer<KeyEvent> consumer) {
        comp().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    public DEditField<T> onKeyPressed(Consumer<KeyEvent> consumer) {
        comp().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    public DEditField<T> onKeyReleased(Consumer<KeyEvent> consumer) {
        comp().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

}
