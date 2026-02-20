package br.com.pointel.jarch.desk;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.function.Consumer;
import javax.swing.JTextArea;

public class DText extends DEdit<String> {

    public DText() {
        super(new JTextArea());
    }

    public DText(String text) {
        super(new JTextArea(text));
    }

    public DText(int rows, int cols) {
        super(new JTextArea(rows, cols));
    }

    public DText(String text, int rows, int cols) {
        super(new JTextArea(text, rows, cols));
    }

    @Override
    public JTextArea comp() {
        return (JTextArea) super.comp();
    }

    @Override
    public String getValue() {
        return comp().getText();
    }

    @Override
    public void setValue(String value) {
        comp().setText(value);
    }

    public String value() {
        return getValue();
    }

    public DText value(String value) {
        setValue(value);
        return this;
    }

    public int rows() {
        return comp().getRows();
    }

    public DText rows(int rows) {
        comp().setRows(rows);
        return this;
    }

    public int cols() {
        return comp().getColumns();
    }

    public DText cols(int cols) {
        comp().setColumns(cols);
        return this;
    }

    public boolean editable() {
        return comp().isEditable();
    }

    public DText editable(boolean editable) {
        comp().setEditable(editable);
        return this;
    }

    public DText text(String text) {
        comp().setText(text);
        return this;
    }

    public DText append(String text) {
        comp().append(text);
        return this;
    }

    public DText insert(String text, int pos) {
        comp().insert(text, pos);
        return this;
    }

    public DText replaceRange(String text, int start, int end) {
        comp().replaceRange(text, start, end);
        return this;
    }

    public boolean lineWrap() {
        return comp().getLineWrap();
    }

    public DText lineWrap(boolean lineWrap) {
        comp().setLineWrap(lineWrap);
        return this;
    }

    public boolean wrapStyleWord() {
        return comp().getWrapStyleWord();
    }

    public DText wrapStyleWord(boolean wrapStyleWord) {
        comp().setWrapStyleWord(wrapStyleWord);
        return this;
    }

    public int tabSize() {
        return comp().getTabSize();
    }

    public DText tabSize(int size) {
        comp().setTabSize(size);
        return this;
    }

    public boolean enabled() {
        return comp().isEnabled();
    }

    public DText enabled(boolean enabled) {
        comp().setEnabled(enabled);
        return this;
    }

    public boolean focusable() {
        return comp().isFocusable();
    }

    public DText focusable(boolean focusable) {
        comp().setFocusable(focusable);
        return this;
    }

    public DText onFocusGained(Consumer<FocusEvent> consumer) {
        comp().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                consumer.accept(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        return this;
    }

    public DText onFocusLost(Consumer<FocusEvent> consumer) {
        comp().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    public DText requestFocus() {
        comp().requestFocus();
        return this;
    }

    public DText requestFocusInWindow() {
        comp().requestFocusInWindow();
        return this;
    }

    public DText select(int selectionStart, int selectionEnd) {
        comp().select(selectionStart, selectionEnd);
        return this;
    }

    public DText selectAll() {
        comp().selectAll();
        return this;
    }

    public int selectionStart() {
        return comp().getSelectionStart();
    }

    public DText selectionStart(int selectionStart) {
        comp().setSelectionStart(selectionStart);
        return this;
    }

    public int selectionEnd() {
        return comp().getSelectionEnd();
    }

    public DText selectionEnd(int selectionEnd) {
        comp().setSelectionEnd(selectionEnd);
        return this;
    }

    public String selectedText() {
        return comp().getSelectedText();
    }

    public DText replaceSelection(String content) {
        comp().replaceSelection(content);
        return this;
    }

    public int caretPosition() {
        return comp().getCaretPosition();
    }

    public DText caretPosition(int position) {
        comp().setCaretPosition(position);
        return this;
    }

    public DText moveCaretPosition(int position) {
        comp().moveCaretPosition(position);
        return this;
    }

    @Override
    public String name() {
        return comp().getName();
    }

    @Override
    public DText name(String name) {
        comp().setName(name);
        return this;
    }

    @Override
    public DText hint(String hint) {
        comp().setToolTipText(hint);
        return this;
    }

}
