package com.vidlus.jarch.desk;

import javax.swing.JTextArea;

/**
 * A fluent API wrapper for {@link JTextArea}, extending {@link DEdit}.
 * It provides methods for editing text, styling, and text manipulation.
 */
public class DText extends DEdit<String> {

    /**
     * Constructs a new empty {@code DText}.
     */
    public DText() {
        super(new JTextArea());
    }

    /**
     * Constructs a new {@code DText} with the specified text.
     *
     * @param text the initial text
     */
    public DText(String text) {
        super(new JTextArea(text));
    }

    /**
     * Constructs a new empty {@code DText} with the specified number of rows and columns.
     *
     * @param rows the number of rows
     * @param cols the number of columns
     */
    public DText(int rows, int cols) {
        super(new JTextArea(rows, cols));
    }

    /**
     * Constructs a new {@code DText} with the specified text, rows, and columns.
     *
     * @param text the initial text
     * @param rows the number of rows
     * @param cols the number of columns
     */
    public DText(String text, int rows, int cols) {
        super(new JTextArea(text, rows, cols));
    }

    /**
     * Returns the underlying {@link JTextArea} component.
     *
     * @return the {@link JTextArea}
     */
    @Override
    public JTextArea comp() {
        return (JTextArea) super.comp();
    }

    /**
     * Returns the text value of this component.
     *
     * @return the text value
     */
    @Override
    public String getValue() {
        return comp().getText();
    }

    /**
     * Sets the text value of this component, preserving the current selection if possible.
     *
     * @param value the new text value
     */
    @Override
    public void setValue(String value) {
        var start = comp().getSelectionStart();
        var end = comp().getSelectionEnd();
        comp().setText(value);
        comp().setSelectionStart(start);
        comp().setSelectionEnd(end);
    }

    /**
     * Returns the text value of this component.
     *
     * @return the text value
     */
    public String value() {
        return getValue();
    }

    /**
     * Sets the text value of this component.
     *
     * @param value the new text value
     * @return this {@code DText} instance for method chaining
     */
    public DText value(String value) {
        setValue(value);
        return this;
    }

    /**
     * Returns the number of rows.
     *
     * @return the number of rows
     */
    public int rows() {
        return comp().getRows();
    }

    /**
     * Sets the number of rows.
     *
     * @param rows the number of rows
     * @return this {@code DText} instance for method chaining
     */
    public DText rows(int rows) {
        comp().setRows(rows);
        return this;
    }

    /**
     * Returns the number of columns.
     *
     * @return the number of columns
     */
    public int cols() {
        return comp().getColumns();
    }

    /**
     * Sets the number of columns.
     *
     * @param cols the number of columns
     * @return this {@code DText} instance for method chaining
     */
    public DText cols(int cols) {
        comp().setColumns(cols);
        return this;
    }

    /**
     * Checks if this text component is editable.
     *
     * @return true if editable, false otherwise
     */
    public boolean editable() {
        return comp().isEditable();
    }

    /**
     * Sets whether this text component is editable.
     *
     * @param editable true to make editable, false to make read-only
     * @return this {@code DText} instance for method chaining
     */
    public DText editable(boolean editable) {
        comp().setEditable(editable);
        return this;
    }

    /**
     * Sets the text of this component.
     *
     * @param text the new text
     * @return this {@code DText} instance for method chaining
     */
    public DText text(String text) {
        comp().setText(text);
        return this;
    }

    /**
     * Appends the given text to the end of the document.
     *
     * @param text the text to append
     * @return this {@code DText} instance for method chaining
     */
    public DText append(String text) {
        comp().append(text);
        return this;
    }

    /**
     * Inserts the specified text at the specified position.
     *
     * @param text the text to insert
     * @param pos  the position to insert at
     * @return this {@code DText} instance for method chaining
     */
    public DText insert(String text, int pos) {
        comp().insert(text, pos);
        return this;
    }

    /**
     * Replaces text from the indicated start to end position with the new text specified.
     *
     * @param text  the text to use as the replacement
     * @param start the start position
     * @param end   the end position
     * @return this {@code DText} instance for method chaining
     */
    public DText replaceRange(String text, int start, int end) {
        comp().replaceRange(text, start, end);
        return this;
    }

    /**
     * Transfers the currently selected range in the associated text model
     * to the system clipboard, removing the contents from the model.
     *
     * @return this {@code DText} instance for method chaining
     */
    public DText cut() {
        comp().cut();
        return this;
    }

    /**
     * Transfers the currently selected range in the associated text model
     * to the system clipboard, leaving the contents in the text model.
     *
     * @return this {@code DText} instance for method chaining
     */
    public DText copy() {
        comp().copy();
        return this;
    }

    /**
     * Transfers the contents of the system clipboard into the associated text model.
     *
     * @return this {@code DText} instance for method chaining
     */
    public DText paste() {
        comp().paste();
        return this;
    }

    /**
     * Returns the line-wrapping policy of the text area.
     *
     * @return true if lines are wrapped, false otherwise
     */
    public boolean lineWrap() {
        return comp().getLineWrap();
    }

    /**
     * Sets the line-wrapping policy of the text area.
     *
     * @param lineWrap true to wrap lines, false otherwise
     * @return this {@code DText} instance for method chaining
     */
    public DText lineWrap(boolean lineWrap) {
        comp().setLineWrap(lineWrap);
        return this;
    }

    /**
     * Returns the style of wrapping used if the text area is wrapping lines.
     *
     * @return true if wrapping on word boundaries, false if on character boundaries
     */
    public boolean wrapStyleWord() {
        return comp().getWrapStyleWord();
    }

    /**
     * Sets the style of wrapping used if the text area is wrapping lines.
     *
     * @param wrapStyleWord true to wrap on word boundaries, false on character boundaries
     * @return this {@code DText} instance for method chaining
     */
    public DText wrapStyleWord(boolean wrapStyleWord) {
        comp().setWrapStyleWord(wrapStyleWord);
        return this;
    }

    /**
     * Returns the number of characters used to expand tabs.
     *
     * @return the number of characters representing a tab
     */
    public int tabSize() {
        return comp().getTabSize();
    }

    /**
     * Sets the number of characters to expand tabs to.
     *
     * @param size the number of characters representing a tab
     * @return this {@code DText} instance for method chaining
     */
    public DText tabSize(int size) {
        comp().setTabSize(size);
        return this;
    }

    /**
     * Selects the text between the specified start and end positions.
     *
     * @param selectionStart the start position of the text
     * @param selectionEnd   the end position of the text
     * @return this {@code DText} instance for method chaining
     */
    public DText select(int selectionStart, int selectionEnd) {
        comp().select(selectionStart, selectionEnd);
        return this;
    }

    /**
     * Selects all the text in the text component.
     *
     * @return this {@code DText} instance for method chaining
     */
    public DText selectAll() {
        comp().selectAll();
        return this;
    }

    /**
     * Returns the selected text's start position.
     *
     * @return the start position
     */
    public int selectionStart() {
        return comp().getSelectionStart();
    }

    /**
     * Sets the selection start to the specified position.
     *
     * @param selectionStart the start position
     * @return this {@code DText} instance for method chaining
     */
    public DText selectionStart(int selectionStart) {
        comp().setSelectionStart(selectionStart);
        return this;
    }

    /**
     * Returns the selected text's end position.
     *
     * @return the end position
     */
    public int selectionEnd() {
        return comp().getSelectionEnd();
    }

    /**
     * Sets the selection end to the specified position.
     *
     * @param selectionEnd the end position
     * @return this {@code DText} instance for method chaining
     */
    public DText selectionEnd(int selectionEnd) {
        comp().setSelectionEnd(selectionEnd);
        return this;
    }

    /**
     * Returns the selected text.
     *
     * @return the selected text
     */
    public String selectedText() {
        return comp().getSelectedText();
    }

    /**
     * Replaces the currently selected content with new content.
     *
     * @param content the replacement content
     * @return this {@code DText} instance for method chaining
     */
    public DText replaceSelection(String content) {
        comp().replaceSelection(content);
        return this;
    }

    /**
     * Returns the position of the text insertion caret.
     *
     * @return the caret position
     */
    public int caretPosition() {
        return comp().getCaretPosition();
    }

    /**
     * Sets the position of the text insertion caret.
     *
     * @param position the caret position
     * @return this {@code DText} instance for method chaining
     */
    public DText caretPosition(int position) {
        comp().setCaretPosition(position);
        return this;
    }

    /**
     * Moves the caret to a new position, leaving behind a mark defined by the last time setCaretPosition was called.
     *
     * @param position the new caret position
     * @return this {@code DText} instance for method chaining
     */
    public DText moveCaretPosition(int position) {
        comp().moveCaretPosition(position);
        return this;
    }

    /**
     * Returns the name of the component.
     *
     * @return the name
     */
    @Override
    public String name() {
        return comp().getName();
    }

    /**
     * Sets the name of the component.
     *
     * @param name the new name
     * @return this {@code DText} instance for method chaining
     */
    @Override
    public DText name(String name) {
        comp().setName(name);
        return this;
    }

    /**
     * Sets the tooltip text (hint) for this component.
     *
     * @param hint the tooltip text
     * @return this {@code DText} instance for method chaining
     */
    @Override
    public DText hint(String hint) {
        comp().setToolTipText(hint);
        return this;
    }

    /**
     * Returns the font used by this component.
     *
     * @return the font
     */
    public java.awt.Font font() {
        return comp().getFont();
    }

    /**
     * Sets the font for this component.
     *
     * @param font the new font
     * @return this {@code DText} instance for method chaining
     */
    public DText font(java.awt.Font font) {
        comp().setFont(font);
        return this;
    }

    /**
     * Returns the foreground color of this component.
     *
     * @return the foreground color
     */
    public java.awt.Color foreground() {
        return comp().getForeground();
    }

    /**
     * Sets the foreground color of this component.
     *
     * @param color the new foreground color
     * @return this {@code DText} instance for method chaining
     */
    public DText foreground(java.awt.Color color) {
        comp().setForeground(color);
        return this;
    }

    /**
     * Returns the background color of this component.
     *
     * @return the background color
     */
    public java.awt.Color background() {
        return comp().getBackground();
    }

    /**
     * Sets the background color of this component.
     *
     * @param color the new background color
     * @return this {@code DText} instance for method chaining
     */
    public DText background(java.awt.Color color) {
        comp().setBackground(color);
        return this;
    }

    /**
     * Returns the margin between the text component's border and its text.
     *
     * @return the margin
     */
    public java.awt.Insets margin() {
        return comp().getMargin();
    }

    /**
     * Sets the margin space between the text component's border and its text.
     *
     * @param margin the new margin
     * @return this {@code DText} instance for method chaining
     */
    public DText margin(java.awt.Insets margin) {
        comp().setMargin(margin);
        return this;
    }

    /**
     * Returns the color used to render the caret.
     *
     * @return the caret color
     */
    public java.awt.Color caretColor() {
        return comp().getCaretColor();
    }

    /**
     * Sets the color used to render the caret.
     *
     * @param color the new caret color
     * @return this {@code DText} instance for method chaining
     */
    public DText caretColor(java.awt.Color color) {
        comp().setCaretColor(color);
        return this;
    }

    /**
     * Returns the color used to render the selection.
     *
     * @return the selection color
     */
    public java.awt.Color selectionColor() {
        return comp().getSelectionColor();
    }

    /**
     * Sets the color used to render the selection.
     *
     * @param color the new selection color
     * @return this {@code DText} instance for method chaining
     */
    public DText selectionColor(java.awt.Color color) {
        comp().setSelectionColor(color);
        return this;
    }

    /**
     * Returns the color used to render the selected text.
     *
     * @return the selected text color
     */
    public java.awt.Color selectedTextColor() {
        return comp().getSelectedTextColor();
    }

    /**
     * Sets the color used to render the selected text.
     *
     * @param color the new selected text color
     * @return this {@code DText} instance for method chaining
     */
    public DText selectedTextColor(java.awt.Color color) {
        comp().setSelectedTextColor(color);
        return this;
    }

    /**
     * Returns the color used to render the disabled text.
     *
     * @return the disabled text color
     */
    public java.awt.Color disabledTextColor() {
        return comp().getDisabledTextColor();
    }

    /**
     * Sets the color used to render the disabled text.
     *
     * @param color the new disabled text color
     * @return this {@code DText} instance for method chaining
     */
    public DText disabledTextColor(java.awt.Color color) {
        comp().setDisabledTextColor(color);
        return this;
    }

}
