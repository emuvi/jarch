package com.vidlus.jarch.desk;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.function.Consumer;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;

/**
 * A specialized masked edit field for handling Timestamp (Date and Time) values
 * based on the system region date-time patterns.
 * Provides a fluent API for configuration and event handling.
 */
public class DMaskTimestamp extends DEditMask {

    /**
     * Constructs a new DMaskTimestamp using the system's short date and time patterns.
     * It dynamically generates the mask layout based on the regional settings.
     */
    public DMaskTimestamp() {
        super(createMask());
    }

    /**
     * Inspects the system's localized short date and time formats to deduce the
     * correct input mask format (e.g., converting "dd/MM/yyyy HH:mm" to "##/##/#### ##:##").
     *
     * @return the deduced mask string, or "##/##/#### ##:##" as a fallback
     */
    private static String createMask() {
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        if (df instanceof SimpleDateFormat) {
            String pattern = ((SimpleDateFormat) df).toPattern();
            // Convert localized pattern parts to strict character length templates
            pattern = pattern.replaceAll("d+", "dd");
            pattern = pattern.replaceAll("M+", "MM");
            pattern = pattern.replaceAll("y+", "yyyy");
            pattern = pattern.replaceAll("h+", "hh");
            pattern = pattern.replaceAll("H+", "HH");
            pattern = pattern.replaceAll("m+", "mm");
            pattern = pattern.replaceAll("s+", "ss");
            // Replace pattern letters with the '#' symbol expected by MaskFormatter
            pattern = pattern.replaceAll("[dMyhHms]", "#");
            // AM/PM markers become two uppercase letters ("UU" in MaskFormatter)
            pattern = pattern.replaceAll("a", "UU");
            return pattern;
        }
        return "##/##/#### ##:##";
    }

    // --- DEditMask Overrides ---

    /**
     * Sets a string mask using MaskFormatter.
     * @param mask the mask to apply
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp mask(String mask) {
        super.mask(mask);
        return this;
    }

    /**
     * Sets the placeholder character used when the value does not completely fill the mask.
     * @param placeholder the placeholder character
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp placeholder(char placeholder) {
        super.placeholder(placeholder);
        return this;
    }

    /**
     * Sets the formatter used by this component.
     * @param formatter the formatter to apply
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp formatter(JFormattedTextField.AbstractFormatter formatter) {
        super.formatter(formatter);
        return this;
    }

    /**
     * Sets the format used by this component's international formatter.
     * @param format the Format to apply
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp format(Format format) {
        super.format(format);
        return this;
    }

    /**
     * Sets the number of columns in this field.
     * @param cols the number of columns
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp cols(int cols) {
        super.cols(cols);
        return this;
    }

    /**
     * Sets whether this field is editable.
     * @param editable true if editable, false otherwise
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp editable(boolean editable) {
        super.editable(editable);
        return this;
    }

    /**
     * Sets the behavior when focus is lost.
     * @param behavior the focus lost behavior
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp focusLostBehavior(int behavior) {
        super.focusLostBehavior(behavior);
        return this;
    }

    /**
     * Sets the horizontal alignment of the text.
     * @param alignment the horizontal alignment
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp horizontalAlignment(int alignment) {
        super.horizontalAlignment(alignment);
        return this;
    }

    /**
     * Forces the current value to be committed to the underlying formatter.
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp commitEdit() {
        super.commitEdit();
        return this;
    }

    // --- DEdit Overrides ---

    /**
     * Sets the underlying JComponent.
     * @param comp the component
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp comp(JComponent comp) {
        super.comp(comp);
        return this;
    }

    /**
     * Sets the value of this field.
     * @param value the value to set
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp value(Object value) {
        super.value(value);
        return this;
    }

    /**
     * Clears the field value.
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp clear() {
        super.clear();
        return this;
    }

    /**
     * Sets whether the field is enabled.
     * @param enabled true if enabled, false otherwise
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp enabled(boolean enabled) {
        super.enabled(enabled);
        return this;
    }

    /**
     * Sets whether the field is focusable.
     * @param focusable true if focusable, false otherwise
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp focusable(boolean focusable) {
        super.focusable(focusable);
        return this;
    }

    /**
     * Requests focus for this component.
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp requestFocus() {
        super.requestFocus();
        return this;
    }

    /**
     * Requests focus in window for this component.
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp requestFocusInWindow() {
        super.requestFocusInWindow();
        return this;
    }

    /**
     * Sets the component name.
     * @param name the component name
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp name(String name) {
        super.name(name);
        return this;
    }

    /**
     * Sets the tooltip hint for this component.
     * @param hint the tooltip hint
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp hint(String hint) {
        super.hint(hint);
        return this;
    }

    /**
     * Adds an action listener.
     * @param consumer the action consumer
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp onAction(Consumer<ActionEvent> consumer) {
        super.onAction(consumer);
        return this;
    }

    /**
     * Adds a mouse clicked listener.
     * @param consumer the mouse event consumer
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp onMouseClicked(Consumer<MouseEvent> consumer) {
        super.onMouseClicked(consumer);
        return this;
    }

    /**
     * Adds a mouse pressed listener.
     * @param consumer the mouse event consumer
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp onMousePressed(Consumer<MouseEvent> consumer) {
        super.onMousePressed(consumer);
        return this;
    }

    /**
     * Adds a mouse released listener.
     * @param consumer the mouse event consumer
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp onMouseReleased(Consumer<MouseEvent> consumer) {
        super.onMouseReleased(consumer);
        return this;
    }

    /**
     * Adds a mouse entered listener.
     * @param consumer the mouse event consumer
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp onMouseEntered(Consumer<MouseEvent> consumer) {
        super.onMouseEntered(consumer);
        return this;
    }

    /**
     * Adds a mouse exited listener.
     * @param consumer the mouse event consumer
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp onMouseExited(Consumer<MouseEvent> consumer) {
        super.onMouseExited(consumer);
        return this;
    }

    /**
     * Adds a key typed listener.
     * @param consumer the key event consumer
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp onKeyTyped(Consumer<KeyEvent> consumer) {
        super.onKeyTyped(consumer);
        return this;
    }

    /**
     * Adds a key pressed listener.
     * @param consumer the key event consumer
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp onKeyPressed(Consumer<KeyEvent> consumer) {
        super.onKeyPressed(consumer);
        return this;
    }

    /**
     * Adds a key released listener.
     * @param consumer the key event consumer
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp onKeyReleased(Consumer<KeyEvent> consumer) {
        super.onKeyReleased(consumer);
        return this;
    }

    /**
     * Adds a focus gained listener.
     * @param consumer the focus event consumer
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp onFocusGained(Consumer<FocusEvent> consumer) {
        super.onFocusGained(consumer);
        return this;
    }

    /**
     * Adds a focus lost listener.
     * @param consumer the focus event consumer
     * @return This {@code DMaskTimestamp} instance for fluent chaining.
     */
    @Override
    public DMaskTimestamp onFocusLost(Consumer<FocusEvent> consumer) {
        super.onFocusLost(consumer);
        return this;
    }
}
