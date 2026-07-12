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

    public DEditField<T> onFocusGained(Consumer<java.awt.event.FocusEvent> consumer) {
        super.onFocusGained(consumer);
        return this;
    }

    public DEditField<T> onFocusLost(Consumer<java.awt.event.FocusEvent> consumer) {
        super.onFocusLost(consumer);
        return this;
    }

    @Override
    public DEditField<T> onAction(Consumer<java.awt.event.ActionEvent> consumer) {
        comp().addActionListener(e -> consumer.accept(e));
        return this;
    }

    public String text() {
        return comp().getText();
    }

    public DEditField<T> text(String text) {
        comp().setText(text);
        return this;
    }

    public java.awt.Font font() {
        return comp().getFont();
    }

    public DEditField<T> font(java.awt.Font font) {
        comp().setFont(font);
        return this;
    }

    public java.awt.Color foreground() {
        return comp().getForeground();
    }

    public DEditField<T> foreground(java.awt.Color color) {
        comp().setForeground(color);
        return this;
    }

    public java.awt.Color background() {
        return comp().getBackground();
    }

    public DEditField<T> background(java.awt.Color color) {
        comp().setBackground(color);
        return this;
    }

    public java.awt.Color caretColor() {
        return comp().getCaretColor();
    }

    public DEditField<T> caretColor(java.awt.Color c) {
        comp().setCaretColor(c);
        return this;
    }

    public java.awt.Color disabledTextColor() {
        return comp().getDisabledTextColor();
    }

    public DEditField<T> disabledTextColor(java.awt.Color c) {
        comp().setDisabledTextColor(c);
        return this;
    }

    public java.awt.Color selectedTextColor() {
        return comp().getSelectedTextColor();
    }

    public DEditField<T> selectedTextColor(java.awt.Color c) {
        comp().setSelectedTextColor(c);
        return this;
    }

    public java.awt.Color selectionColor() {
        return comp().getSelectionColor();
    }

    public DEditField<T> selectionColor(java.awt.Color c) {
        comp().setSelectionColor(c);
        return this;
    }

    public java.awt.Insets margin() {
        return comp().getMargin();
    }

    public DEditField<T> margin(java.awt.Insets m) {
        comp().setMargin(m);
        return this;
    }

    public boolean dragEnabled() {
        return comp().getDragEnabled();
    }

    public DEditField<T> dragEnabled(boolean b) {
        comp().setDragEnabled(b);
        return this;
    }

    public javax.swing.DropMode dropMode() {
        return comp().getDropMode();
    }

    public DEditField<T> dropMode(javax.swing.DropMode m) {
        comp().setDropMode(m);
        return this;
    }

    public javax.swing.text.Document document() {
        return comp().getDocument();
    }

    public DEditField<T> document(javax.swing.text.Document doc) {
        comp().setDocument(doc);
        return this;
    }

    public javax.swing.text.Highlighter highlighter() {
        return comp().getHighlighter();
    }

    public DEditField<T> highlighter(javax.swing.text.Highlighter h) {
        comp().setHighlighter(h);
        return this;
    }

    public javax.swing.text.Keymap keymap() {
        return comp().getKeymap();
    }

    public DEditField<T> keymap(javax.swing.text.Keymap map) {
        comp().setKeymap(map);
        return this;
    }

    public javax.swing.text.Caret caret() {
        return comp().getCaret();
    }

    public DEditField<T> caret(javax.swing.text.Caret c) {
        comp().setCaret(c);
        return this;
    }

    public javax.swing.text.NavigationFilter navigationFilter() {
        return comp().getNavigationFilter();
    }

    public DEditField<T> navigationFilter(javax.swing.text.NavigationFilter filter) {
        comp().setNavigationFilter(filter);
        return this;
    }

    public int scrollOffset() {
        return comp().getScrollOffset();
    }

    public DEditField<T> scrollOffset(int scrollOffset) {
        comp().setScrollOffset(scrollOffset);
        return this;
    }

    public DEditField<T> actionCommand(String command) {
        comp().setActionCommand(command);
        return this;
    }

    public char focusAccelerator() {
        return comp().getFocusAccelerator();
    }

    public DEditField<T> focusAccelerator(char aKey) {
        comp().setFocusAccelerator(aKey);
        return this;
    }

    public boolean opaque() {
        return comp().isOpaque();
    }

    public DEditField<T> opaque(boolean isOpaque) {
        comp().setOpaque(isOpaque);
        return this;
    }

    public javax.swing.border.Border border() {
        return comp().getBorder();
    }

    public DEditField<T> border(javax.swing.border.Border border) {
        comp().setBorder(border);
        return this;
    }

}
