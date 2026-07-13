package com.vidlus.jarch.desk;

import java.awt.event.ActionEvent;
import java.util.function.Consumer;
import javax.swing.Box;

import com.vidlus.jarch.mage.WizGUI;

/**
 * A dialog frame specifically designed for editing text values.
 * It provides a text area within a scroll pane, and confirmation/cancellation buttons.
 */
public class DTextDesk extends DFrame {

    private final DText textValue = new DText();
    private final DScroll scrollText = new DScroll(textValue);

    private final DButton buttonConfirm = new DButton("Confirm")
            .onAction(this::confirm);
    private final DButton buttonCancel = new DButton("Cancel")
            .onAction(this::cancel);

    private final DPane actsBody = new DPaneRow().insets(2)
            .growHorizontal().put(Box.createHorizontalGlue())
            .growNone().put(buttonConfirm)
            .growNone().put(buttonCancel);

    private final DPane paneBody = new DPaneColumn().insets(2)
            .growBoth().put(scrollText)
            .growHorizontal().put(actsBody)
            .borderEmpty(7);

    private Consumer<String> onConfirm;

    /**
     * Constructs a new {@code DTextDesk} with the default title "Text Desk".
     */
    public DTextDesk() {
        this("Text Desk", null, null);
    }

    /**
     * Constructs a new {@code DTextDesk} with the specified title.
     *
     * @param title the dialog title
     */
    public DTextDesk(String title) {
        this(title, null, null);
    }

    /**
     * Constructs a new {@code DTextDesk} with the specified title and initial text.
     *
     * @param title       the dialog title
     * @param initialText the initial text to edit
     */
    public DTextDesk(String title, String initialText) {
        this(title, initialText, null);
    }

    /**
     * Constructs a new {@code DTextDesk} with the specified confirmation callback.
     *
     * @param onConfirm the callback to execute when the user confirms the text
     */
    public DTextDesk(Consumer<String> onConfirm) {
        this("Text Desk", null, onConfirm);
    }

    /**
     * Constructs a new {@code DTextDesk} with the specified title and confirmation callback.
     *
     * @param title     the dialog title
     * @param onConfirm the callback to execute when the user confirms the text
     */
    public DTextDesk(String title, Consumer<String> onConfirm) {
        this(title, null, onConfirm);
    }

    /**
     * Constructs a new {@code DTextDesk} with the specified title, initial text, and confirmation callback.
     *
     * @param title       the dialog title
     * @param initialText the initial text to edit
     * @param onConfirm   the callback to execute when the user confirms the text
     */
    public DTextDesk(String title, String initialText, Consumer<String> onConfirm) {
        super(title);
        this.onConfirm = onConfirm;
        initComponents(initialText);
    }
    
    /**
     * Initializes the components of the dialog.
     *
     * @param initialText the initial text to set in the text area
     */
    private void initComponents(String initialText) {
        body(paneBody);
        if (initialText != null) {
            textValue.value(initialText);
        }
    }

    /**
     * Adds an additional button to the action pane.
     *
     * @param button the button to add
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk putButton(DButton button) {
        actsBody.put(button);
        return this;
    }

    /**
     * Removes all buttons from the action pane.
     *
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk delButtons() {
        actsBody.removeAll();
        return this;
    }

    /**
     * Returns the current text value.
     *
     * @return the text value
     */
    public String text() {
        return textValue.value();
    }

    /**
     * Sets the text value.
     *
     * @param text the new text value
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk text(String text) {
        textValue.value(text);
        return this;
    }

    /**
     * Checks if the text component is editable.
     *
     * @return true if editable, false otherwise
     */
    public boolean editable() {
        return textValue.editable();
    }

    /**
     * Sets whether the text component is editable.
     *
     * @param editable true to make editable, false to make read-only
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk editable(boolean editable) {
        textValue.editable(editable);
        return this;
    }

    /**
     * Returns the text value of this component.
     *
     * @return the text value
     */
    public String value() {
        return textValue.value();
    }

    /**
     * Sets the text value of this component.
     *
     * @param value the new text value
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk value(String value) {
        textValue.value(value);
        return this;
    }

    /**
     * Returns the number of rows of the text component.
     *
     * @return the number of rows
     */
    public int rows() {
        return textValue.rows();
    }

    /**
     * Sets the number of rows of the text component.
     *
     * @param rows the number of rows
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk rows(int rows) {
        textValue.rows(rows);
        return this;
    }

    /**
     * Returns the number of columns of the text component.
     *
     * @return the number of columns
     */
    public int cols() {
        return textValue.cols();
    }

    /**
     * Sets the number of columns of the text component.
     *
     * @param cols the number of columns
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk cols(int cols) {
        textValue.cols(cols);
        return this;
    }

    /**
     * Appends the given text to the end of the document.
     *
     * @param text the text to append
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk append(String text) {
        textValue.append(text);
        return this;
    }

    /**
     * Inserts the specified text at the specified position.
     *
     * @param text the text to insert
     * @param pos  the position to insert at
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk insert(String text, int pos) {
        textValue.insert(text, pos);
        return this;
    }

    /**
     * Replaces text from the indicated start to end position with the new text specified.
     *
     * @param text  the text to use as the replacement
     * @param start the start position
     * @param end   the end position
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk replaceRange(String text, int start, int end) {
        textValue.replaceRange(text, start, end);
        return this;
    }

    /**
     * Transfers the currently selected range in the associated text model
     * to the system clipboard, removing the contents from the model.
     *
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk cut() {
        textValue.cut();
        return this;
    }

    /**
     * Transfers the currently selected range in the associated text model
     * to the system clipboard, leaving the contents in the text model.
     *
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk copy() {
        textValue.copy();
        return this;
    }

    /**
     * Transfers the contents of the system clipboard into the associated text model.
     *
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk paste() {
        textValue.paste();
        return this;
    }

    /**
     * Returns the line-wrapping policy of the text area.
     *
     * @return true if lines are wrapped, false otherwise
     */
    public boolean lineWrap() {
        return textValue.lineWrap();
    }

    /**
     * Sets the line-wrapping policy of the text area.
     *
     * @param lineWrap true to wrap lines, false otherwise
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk lineWrap(boolean lineWrap) {
        textValue.lineWrap(lineWrap);
        return this;
    }

    /**
     * Returns the style of wrapping used if the text area is wrapping lines.
     *
     * @return true if wrapping on word boundaries, false if on character boundaries
     */
    public boolean wrapStyleWord() {
        return textValue.wrapStyleWord();
    }

    /**
     * Sets the style of wrapping used if the text area is wrapping lines.
     *
     * @param wrapStyleWord true to wrap on word boundaries, false on character boundaries
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk wrapStyleWord(boolean wrapStyleWord) {
        textValue.wrapStyleWord(wrapStyleWord);
        return this;
    }

    /**
     * Returns the number of characters used to expand tabs.
     *
     * @return the number of characters representing a tab
     */
    public int tabSize() {
        return textValue.tabSize();
    }

    /**
     * Sets the number of characters to expand tabs to.
     *
     * @param size the number of characters representing a tab
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk tabSize(int size) {
        textValue.tabSize(size);
        return this;
    }

    /**
     * Selects the text between the specified start and end positions.
     *
     * @param selectionStart the start position of the text
     * @param selectionEnd   the end position of the text
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk select(int selectionStart, int selectionEnd) {
        textValue.select(selectionStart, selectionEnd);
        return this;
    }

    /**
     * Selects all the text in the text component.
     *
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk selectAll() {
        textValue.selectAll();
        return this;
    }

    /**
     * Returns the selected text's start position.
     *
     * @return the start position
     */
    public int selectionStart() {
        return textValue.selectionStart();
    }

    /**
     * Sets the selection start to the specified position.
     *
     * @param selectionStart the start position
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk selectionStart(int selectionStart) {
        textValue.selectionStart(selectionStart);
        return this;
    }

    /**
     * Returns the selected text's end position.
     *
     * @return the end position
     */
    public int selectionEnd() {
        return textValue.selectionEnd();
    }

    /**
     * Sets the selection end to the specified position.
     *
     * @param selectionEnd the end position
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk selectionEnd(int selectionEnd) {
        textValue.selectionEnd(selectionEnd);
        return this;
    }

    /**
     * Returns the selected text.
     *
     * @return the selected text
     */
    public String selectedText() {
        return textValue.selectedText();
    }

    /**
     * Replaces the currently selected content with new content.
     *
     * @param content the replacement content
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk replaceSelection(String content) {
        textValue.replaceSelection(content);
        return this;
    }

    /**
     * Returns the position of the text insertion caret.
     *
     * @return the caret position
     */
    public int caretPosition() {
        return textValue.caretPosition();
    }

    /**
     * Sets the position of the text insertion caret.
     *
     * @param position the caret position
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk caretPosition(int position) {
        textValue.caretPosition(position);
        return this;
    }

    /**
     * Moves the caret to a new position, leaving behind a mark defined by the last time setCaretPosition was called.
     *
     * @param position the new caret position
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk moveCaretPosition(int position) {
        textValue.moveCaretPosition(position);
        return this;
    }

    /**
     * Returns the font used by the text component.
     *
     * @return the font
     */
    public java.awt.Font font() {
        return textValue.font();
    }

    /**
     * Sets the font for the text component.
     *
     * @param font the new font
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk font(java.awt.Font font) {
        textValue.font(font);
        return this;
    }

    /**
     * Returns the foreground color of the text component.
     *
     * @return the foreground color
     */
    public java.awt.Color foreground() {
        return textValue.foreground();
    }

    /**
     * Sets the foreground color of the text component.
     *
     * @param color the new foreground color
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk foreground(java.awt.Color color) {
        textValue.foreground(color);
        return this;
    }

    /**
     * Sets the background color of the text component.
     *
     * @param color the new background color
     * @return this {@code DTextDesk} instance for method chaining
     */
    @Override
    public DTextDesk background(java.awt.Color color) {
        textValue.background(color);
        return this;
    }

    /**
     * Returns the margin between the text component's border and its text.
     *
     * @return the margin
     */
    public java.awt.Insets margin() {
        return textValue.margin();
    }

    /**
     * Sets the margin space between the text component's border and its text.
     *
     * @param margin the new margin
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk margin(java.awt.Insets margin) {
        textValue.margin(margin);
        return this;
    }

    /**
     * Returns the color used to render the caret.
     *
     * @return the caret color
     */
    public java.awt.Color caretColor() {
        return textValue.caretColor();
    }

    /**
     * Sets the color used to render the caret.
     *
     * @param color the new caret color
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk caretColor(java.awt.Color color) {
        textValue.caretColor(color);
        return this;
    }

    /**
     * Returns the color used to render the selection.
     *
     * @return the selection color
     */
    public java.awt.Color selectionColor() {
        return textValue.selectionColor();
    }

    /**
     * Sets the color used to render the selection.
     *
     * @param color the new selection color
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk selectionColor(java.awt.Color color) {
        textValue.selectionColor(color);
        return this;
    }

    /**
     * Returns the color used to render the selected text.
     *
     * @return the selected text color
     */
    public java.awt.Color selectedTextColor() {
        return textValue.selectedTextColor();
    }

    /**
     * Sets the color used to render the selected text.
     *
     * @param color the new selected text color
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk selectedTextColor(java.awt.Color color) {
        textValue.selectedTextColor(color);
        return this;
    }

    /**
     * Returns the color used to render the disabled text.
     *
     * @return the disabled text color
     */
    public java.awt.Color disabledTextColor() {
        return textValue.disabledTextColor();
    }

    /**
     * Sets the color used to render the disabled text.
     *
     * @param color the new disabled text color
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk disabledTextColor(java.awt.Color color) {
        textValue.disabledTextColor(color);
        return this;
    }

    /**
     * Sets the callback to be executed when the confirmation button is clicked.
     *
     * @param onConfirm the new callback action
     * @return this {@code DTextDesk} instance for method chaining
     */
    public DTextDesk onConfirm(Consumer<String> onConfirm) {
        this.onConfirm = onConfirm;
        return this;
    }

    /**
     * Handles the confirm action.
     *
     * @param e the action event
     */
    private void confirm(ActionEvent e) {
        var text = textValue.value();
        if (text == null) {
            return;
        }
        if (onConfirm != null) {
            onConfirm.accept(text);
            WizGUI.close(this);
        }
    }

    /**
     * Handles the cancel action.
     *
     * @param e the action event
     */
    private void cancel(ActionEvent e) {
        WizGUI.close(this);
    }

    /**
     * Makes the dialog visible, focuses the text area, and prepares for editing.
     *
     * @return this {@code DTextDesk} instance for method chaining
     */
    @Override
    public DTextDesk view() {
        setVisible(true);
        textValue.selectionStart(0);
        textValue.selectionEnd(0);
        textValue.requestFocusInWindow();
        textValue.requestFocus();
        return this;
    }

}
