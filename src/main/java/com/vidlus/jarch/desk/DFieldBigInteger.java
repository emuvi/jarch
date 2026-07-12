package com.vidlus.jarch.desk;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import javax.swing.JComponent;

import java.math.BigInteger;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NavigationFilter;
import java.awt.Font;
import java.awt.Color;
import javax.swing.text.Caret;
import javax.swing.text.Highlighter;
import javax.swing.DropMode;
import javax.swing.border.Border;
import javax.swing.text.Keymap;
import javax.swing.text.Document;
import java.awt.Insets;

/**
 * A specialized edit field for handling BigInteger values.
 * Provides a fluent API for configuration and event handling.
 */
public class DFieldBigInteger extends DEditField<BigInteger> {

    /**
     * Constructs a new DFieldBigInteger with a default initial value.
     */
    public DFieldBigInteger() {
        this(BigInteger.ZERO);
    }

    /**
     * Constructs a new DFieldBigInteger with the specified initial value.
     * @param value the initial value
     */
    public DFieldBigInteger(BigInteger value) {
        super(value == null ? "" : value.toString());
        ((AbstractDocument) comp().getDocument()).setDocumentFilter(new BigIntegerFilter());
    }

    /**
     * Retrieves the current value from the text field.
     * @return the parsed value, or null if invalid or empty
     */
    @Override
    public BigInteger getValue() {
        var text = comp().getText();
        if (text == null || text.isEmpty() || "-".equals(text)) {
            return null;
        }
        try {
            return new BigInteger(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Sets the value of the text field.
     * @param value the value to set
     */
    @Override
    public void setValue(BigInteger value) {
        comp().setText(value == null ? "" : value.toString());
    }

    /**
     * A DocumentFilter that restricts input to valid formats for this field.
     */
    private class BigIntegerFilter extends DocumentFilter {
        /**
         * Overrides the insertString method to apply validation before insertion.
         */
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            var text = fb.getDocument().getText(0, fb.getDocument().getLength());
            var newText = text.substring(0, offset) + string + text.substring(offset);
            if (isValidBigInteger(newText)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        /**
         * Overrides the replace method to apply validation before replacement.
         */
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            var currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            var newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
            if (isValidBigInteger(newText)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        /**
         * Overrides the remove method to apply validation before removal.
         */
        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            var currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            var newText = currentText.substring(0, offset) + currentText.substring(offset + length);
            if (isValidBigInteger(newText)) {
                super.remove(fb, offset, length);
            }
        }

        /**
         * Validates the text to ensure it matches the required format.
         * @param text the text to validate
         * @return true if valid, false otherwise
         */
        private boolean isValidBigInteger(String text) {
            if (text.isEmpty() || "-".equals(text)) {
                return true;
            }
            try {
                new BigInteger(text);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    // --- Fluent API Overrides ---
    /**
     * Sets the value of this field.
     * @param value the value parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger value(java.math.BigInteger value) {
        super.value(value);
        return this;
    }

    /**
     * Sets the underlying JComponent.
     * @param comp the comp parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger comp(JComponent comp) {
        super.comp(comp);
        return this;
    }

    /**
     * Clears the field value.
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger clear() {
        super.clear();
        return this;
    }

    /**
     * Sets whether the field is enabled.
     * @param enabled the enabled parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger enabled(boolean enabled) {
        super.enabled(enabled);
        return this;
    }

    /**
     * Sets whether the field is focusable.
     * @param focusable the focusable parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger focusable(boolean focusable) {
        super.focusable(focusable);
        return this;
    }

    /**
     * Requests focus for this component.
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger requestFocus() {
        super.requestFocus();
        return this;
    }

    /**
     * Requests focus in window for this component.
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger requestFocusInWindow() {
        super.requestFocusInWindow();
        return this;
    }

    /**
     * Sets the component name.
     * @param name the name parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger name(String name) {
        super.name(name);
        return this;
    }

    /**
     * Sets the tooltip hint for this component.
     * @param hint the hint parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger hint(String hint) {
        super.hint(hint);
        return this;
    }

    /**
     * Adds an action listener.
     * @param consumer the consumer parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger onAction(Consumer<ActionEvent> consumer) {
        super.onAction(consumer);
        return this;
    }

    /**
     * Adds a mouse clicked listener.
     * @param consumer the consumer parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger onMouseClicked(Consumer<MouseEvent> consumer) {
        super.onMouseClicked(consumer);
        return this;
    }

    /**
     * Adds a mouse pressed listener.
     * @param consumer the consumer parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger onMousePressed(Consumer<MouseEvent> consumer) {
        super.onMousePressed(consumer);
        return this;
    }

    /**
     * Adds a mouse released listener.
     * @param consumer the consumer parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger onMouseReleased(Consumer<MouseEvent> consumer) {
        super.onMouseReleased(consumer);
        return this;
    }

    /**
     * Adds a mouse entered listener.
     * @param consumer the consumer parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger onMouseEntered(Consumer<MouseEvent> consumer) {
        super.onMouseEntered(consumer);
        return this;
    }

    /**
     * Adds a mouse exited listener.
     * @param consumer the consumer parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger onMouseExited(Consumer<MouseEvent> consumer) {
        super.onMouseExited(consumer);
        return this;
    }

    /**
     * Adds a key typed listener.
     * @param consumer the consumer parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger onKeyTyped(Consumer<KeyEvent> consumer) {
        super.onKeyTyped(consumer);
        return this;
    }

    /**
     * Adds a key pressed listener.
     * @param consumer the consumer parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger onKeyPressed(Consumer<KeyEvent> consumer) {
        super.onKeyPressed(consumer);
        return this;
    }

    /**
     * Adds a key released listener.
     * @param consumer the consumer parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger onKeyReleased(Consumer<KeyEvent> consumer) {
        super.onKeyReleased(consumer);
        return this;
    }

    /**
     * Adds a focus gained listener.
     * @param consumer the consumer parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger onFocusGained(Consumer<FocusEvent> consumer) {
        super.onFocusGained(consumer);
        return this;
    }

    /**
     * Adds a focus lost listener.
     * @param consumer the consumer parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger onFocusLost(Consumer<FocusEvent> consumer) {
        super.onFocusLost(consumer);
        return this;
    }

    /**
     * Sets the number of columns.
     * @param cols the cols parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger cols(int cols) {
        super.cols(cols);
        return this;
    }

    /**
     * Sets whether the field is editable.
     * @param editable the editable parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger editable(boolean editable) {
        super.editable(editable);
        return this;
    }

    /**
     * Performs a cut operation.
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger cut() {
        super.cut();
        return this;
    }

    /**
     * Performs a copy operation.
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger copy() {
        super.copy();
        return this;
    }

    /**
     * Performs a paste operation.
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger paste() {
        super.paste();
        return this;
    }

    /**
     * Selects text between the specified positions.
     * @param selectionStart the selectionStart parameter
     * @param selectionEnd the selectionEnd parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger select(int selectionStart, int selectionEnd) {
        super.select(selectionStart, selectionEnd);
        return this;
    }

    /**
     * Selects all text.
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger selectAll() {
        super.selectAll();
        return this;
    }

    /**
     * Sets the selection start position.
     * @param selectionStart the selectionStart parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger selectionStart(int selectionStart) {
        super.selectionStart(selectionStart);
        return this;
    }

    /**
     * Sets the selection end position.
     * @param selectionEnd the selectionEnd parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger selectionEnd(int selectionEnd) {
        super.selectionEnd(selectionEnd);
        return this;
    }

    /**
     * Replaces the currently selected text.
     * @param content the content parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger replaceSelection(String content) {
        super.replaceSelection(content);
        return this;
    }

    /**
     * Sets the caret position.
     * @param position the position parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger caretPosition(int position) {
        super.caretPosition(position);
        return this;
    }

    /**
     * Moves the caret to a new position.
     * @param position the position parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger moveCaretPosition(int position) {
        super.moveCaretPosition(position);
        return this;
    }

    /**
     * Sets the horizontal alignment.
     * @param alignment the alignment parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger horizontalAlignment(int alignment) {
        super.horizontalAlignment(alignment);
        return this;
    }

    /**
     * Aligns the text to the left.
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger horizontalAlignmentLeft() {
        super.horizontalAlignmentLeft();
        return this;
    }

    /**
     * Centers the text horizontally.
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger horizontalAlignmentCenter() {
        super.horizontalAlignmentCenter();
        return this;
    }

    /**
     * Aligns the text to the right.
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger horizontalAlignmentRight() {
        super.horizontalAlignmentRight();
        return this;
    }

    /**
     * Aligns the text to the leading edge.
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger horizontalAlignmentLeading() {
        super.horizontalAlignmentLeading();
        return this;
    }

    /**
     * Aligns the text to the trailing edge.
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger horizontalAlignmentTrailing() {
        super.horizontalAlignmentTrailing();
        return this;
    }

    /**
     * Sets the text content directly.
     * @param text the text parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger text(String text) {
        super.text(text);
        return this;
    }

    /**
     * Sets the font of the component.
     * @param font the font parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger font(Font font) {
        super.font(font);
        return this;
    }

    /**
     * Sets the foreground color.
     * @param color the color parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger foreground(Color color) {
        super.foreground(color);
        return this;
    }

    /**
     * Sets the background color.
     * @param color the color parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger background(Color color) {
        super.background(color);
        return this;
    }

    /**
     * Sets the caret color.
     * @param c the c parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger caretColor(Color c) {
        super.caretColor(c);
        return this;
    }

    /**
     * Sets the text color when disabled.
     * @param c the c parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger disabledTextColor(Color c) {
        super.disabledTextColor(c);
        return this;
    }

    /**
     * Sets the selected text color.
     * @param c the c parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger selectedTextColor(Color c) {
        super.selectedTextColor(c);
        return this;
    }

    /**
     * Sets the selection background color.
     * @param c the c parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger selectionColor(Color c) {
        super.selectionColor(c);
        return this;
    }

    /**
     * Sets the margin around the text.
     * @param m the m parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger margin(Insets m) {
        super.margin(m);
        return this;
    }

    /**
     * Sets whether drag is enabled.
     * @param b the b parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger dragEnabled(boolean b) {
        super.dragEnabled(b);
        return this;
    }

    /**
     * Sets the drop mode.
     * @param m the m parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger dropMode(DropMode m) {
        super.dropMode(m);
        return this;
    }

    /**
     * Sets the document model.
     * @param doc the doc parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger document(Document doc) {
        super.document(doc);
        return this;
    }

    /**
     * Sets the highlighter.
     * @param h the h parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger highlighter(Highlighter h) {
        super.highlighter(h);
        return this;
    }

    /**
     * Sets the keymap.
     * @param map the map parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger keymap(Keymap map) {
        super.keymap(map);
        return this;
    }

    /**
     * Sets the caret instance.
     * @param c the c parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger caret(Caret c) {
        super.caret(c);
        return this;
    }

    /**
     * Sets the navigation filter.
     * @param filter the filter parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger navigationFilter(NavigationFilter filter) {
        super.navigationFilter(filter);
        return this;
    }

    /**
     * Sets the scroll offset.
     * @param scrollOffset the scrollOffset parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger scrollOffset(int scrollOffset) {
        super.scrollOffset(scrollOffset);
        return this;
    }

    /**
     * Sets the action command string.
     * @param command the command parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger actionCommand(String command) {
        super.actionCommand(command);
        return this;
    }

    /**
     * Sets the focus accelerator key.
     * @param aKey the aKey parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger focusAccelerator(char aKey) {
        super.focusAccelerator(aKey);
        return this;
    }

    /**
     * Sets whether the component is opaque.
     * @param isOpaque the isOpaque parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger opaque(boolean isOpaque) {
        super.opaque(isOpaque);
        return this;
    }

    /**
     * Sets the component border.
     * @param border the border parameter
     * @return This {@code DFieldBigInteger} instance for fluent chaining.
     */
    @Override
    public DFieldBigInteger border(Border border) {
        super.border(border);
        return this;
    }

}
