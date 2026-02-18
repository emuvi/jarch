package br.com.pointel.jarch.desk;

import javax.swing.JTextField;

public abstract class DFieldEdit<T> extends DEdit<T> {

    public DFieldEdit() {
        super(new JTextField());
    }

    public DFieldEdit(String text) {
        super(new JTextField(text));
    }

    public DFieldEdit(int cols) {
        super(new JTextField(cols));
    }

    public DFieldEdit(String text, int cols) {
        super(new JTextField(text, cols));
    }

    @Override
    public JTextField comp() {
        return (JTextField) super.comp();
    }

    public int cols() {
        return comp().getColumns();
    }

    public DFieldEdit<T> cols(int cols) {
        comp().setColumns(cols);
        return this;
    }

    public boolean editable() {
        return comp().isEditable();
    }

    public DFieldEdit<T> editable(boolean editable) {
        comp().setEditable(editable);
        return this;
    }

    public boolean enabled() {
        return comp().isEnabled();
    }

    public DFieldEdit<T> enabled(boolean enabled) {
        comp().setEnabled(enabled);
        return this;
    }

    @Override
    public T value() {
        return getValue();
    }

    @Override
    public DFieldEdit<T> value(T value) {
        setValue(value);
        return this;
    }

    @Override
    public String name() {
        return comp().getName();
    }

    @Override
    public DFieldEdit<T> name(String name) {
        comp().setName(name);
        return this;
    }

    @Override
    public String hint() {
        return comp().getToolTipText();
    }

    @Override
    public DFieldEdit<T> hint(String hint) {
        comp().setToolTipText(hint);
        return this;
    }

    public boolean focusable() {
        return comp().isFocusable();
    }

    public DFieldEdit<T> focusable(boolean focusable) {
        comp().setFocusable(focusable);
        return this;
    }

    public DFieldEdit<T> select(int selectionStart, int selectionEnd) {
        comp().select(selectionStart, selectionEnd);
        return this;
    }

    public DFieldEdit<T> selectAll() {
        comp().selectAll();
        return this;
    }

    public int selectionStart() {
        return comp().getSelectionStart();
    }

    public DFieldEdit<T> selectionStart(int selectionStart) {
        comp().setSelectionStart(selectionStart);
        return this;
    }

    public int selectionEnd() {
        return comp().getSelectionEnd();
    }

    public DFieldEdit<T> selectionEnd(int selectionEnd) {
        comp().setSelectionEnd(selectionEnd);
        return this;
    }

    public String selectedText() {
        return comp().getSelectedText();
    }

    public DFieldEdit<T> replaceSelection(String content) {
        comp().replaceSelection(content);
        return this;
    }

    public int caretPosition() {
        return comp().getCaretPosition();
    }

    public DFieldEdit<T> caretPosition(int position) {
        comp().setCaretPosition(position);
        return this;
    }

    public DFieldEdit<T> moveCaretPosition(int position) {
        comp().moveCaretPosition(position);
        return this;
    }

    public int horizontalAlignment() {
        return comp().getHorizontalAlignment();
    }

    public DFieldEdit<T> horizontalAlignment(int alignment) {
        comp().setHorizontalAlignment(alignment);
        return this;
    }

    public DFieldEdit<T> horizontalAlignmentLeft() {
        comp().setHorizontalAlignment(JTextField.LEFT);
        return this;
    }

    public DFieldEdit<T> horizontalAlignmentCenter() {
        comp().setHorizontalAlignment(JTextField.CENTER);
        return this;
    }

    public DFieldEdit<T> horizontalAlignmentRight() {
        comp().setHorizontalAlignment(JTextField.RIGHT);
        return this;
    }

    public DFieldEdit<T> horizontalAlignmentLeading() {
        comp().setHorizontalAlignment(JTextField.LEADING);
        return this;
    }

    public DFieldEdit<T> horizontalAlignmentTrailing() {
        comp().setHorizontalAlignment(JTextField.TRAILING);
        return this;
    }

}
