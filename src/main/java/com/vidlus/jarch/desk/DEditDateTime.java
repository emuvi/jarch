package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * A custom component for selecting a java.time.LocalDateTime combining both DEditDate and DEditTime
 * side by side.
 */
public class DEditDateTime extends DEdit<LocalDateTime> {

    private final JPanel rootPanel;
    private final DEditDate dateEdit;
    private final DEditTime timeEdit;

    /**
     * Constructs a DEditTimestamp component, initializing both DEditDate and DEditTime
     * side by side.
     */
    public DEditDateTime() {
        super(new JPanel(new GridLayout(1, 2, 10, 0)));
        this.rootPanel = (JPanel) super.comp();
        
        this.dateEdit = new DEditDate();
        this.timeEdit = new DEditTime();
        
        this.rootPanel.add(this.dateEdit.comp());
        this.rootPanel.add(this.timeEdit.comp());
    }

    /**
     * Retrieves the currently selected date and time.
     * 
     * @return the selected date and time, or null if either date or time is cleared
     */
    @Override
    public LocalDateTime getValue() {
        java.time.LocalDate d = dateEdit.getValue();
        Date t = timeEdit.getValue();
        
        if (d == null || t == null) return null;
        
        java.time.LocalTime time = t.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        return LocalDateTime.of(d, time);
    }

    /**
     * Sets the currently selected date and time and updates both UI components.
     * 
     * @param value the date and time to select, or null to clear selection
     */
    @Override
    public void setValue(LocalDateTime value) {
        if (value == null) {
            this.dateEdit.setValue(null);
            this.timeEdit.setValue(null);
        } else {
            this.dateEdit.setValue(value.toLocalDate());
            this.timeEdit.setValue(Date.from(value.atZone(ZoneId.systemDefault()).toInstant()));
        }
    }

    // --- DEdit Overrides ---

    /**
     * Sets the underlying JComponent.
     * @param comp the component
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    @Override
    public DEditDateTime comp(JComponent comp) {
        super.comp(comp);
        return this;
    }

    /**
     * Sets the value of this field explicitly.
     * @param value the value to set
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    @Override
    public DEditDateTime value(LocalDateTime value) {
        super.value(value);
        return this;
    }

    /**
     * Clears the selected date effectively wiping the UI tracking.
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    @Override
    public DEditDateTime clear() {
        super.clear();
        return this;
    }

    /**
     * Sets whether the component is globally enabled.
     * @param enabled true if enabled, false otherwise
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    @Override
    public DEditDateTime enabled(boolean enabled) {
        super.enabled(enabled);
        this.dateEdit.enabled(enabled);
        this.timeEdit.enabled(enabled);
        return this;
    }

    /**
     * Sets whether the component is focusable.
     * @param focusable true if focusable, false otherwise
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    @Override
    public DEditDateTime focusable(boolean focusable) {
        super.focusable(focusable);
        return this;
    }

    /**
     * Requests focus for this component.
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    @Override
    public DEditDateTime requestFocus() {
        super.requestFocus();
        return this;
    }

    /**
     * Requests focus in window for this component.
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    @Override
    public DEditDateTime requestFocusInWindow() {
        super.requestFocusInWindow();
        return this;
    }

    /**
     * Sets the component name.
     * @param name the component name
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    @Override
    public DEditDateTime name(String name) {
        super.name(name);
        return this;
    }

    /**
     * Sets the tooltip hint for this component.
     * @param hint the tooltip hint
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    @Override
    public DEditDateTime hint(String hint) {
        super.hint(hint);
        return this;
    }

    /**
     * Adds an action listener to the root panel.
     * @param consumer the action consumer
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    @Override
    public DEditDateTime onAction(Consumer<ActionEvent> consumer) {
        super.onAction(consumer);
        return this;
    }

    /**
     * Adds a mouse clicked listener.
     * @param consumer the mouse event consumer
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    @Override
    public DEditDateTime onMouseClicked(Consumer<MouseEvent> consumer) {
        super.onMouseClicked(consumer);
        return this;
    }

    /**
     * Adds a mouse pressed listener.
     * @param consumer the mouse event consumer
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    @Override
    public DEditDateTime onMousePressed(Consumer<MouseEvent> consumer) {
        super.onMousePressed(consumer);
        return this;
    }

    /**
     * Adds a mouse released listener.
     * @param consumer the mouse event consumer
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    @Override
    public DEditDateTime onMouseReleased(Consumer<MouseEvent> consumer) {
        super.onMouseReleased(consumer);
        return this;
    }

    /**
     * Adds a mouse entered listener.
     * @param consumer the mouse event consumer
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    @Override
    public DEditDateTime onMouseEntered(Consumer<MouseEvent> consumer) {
        super.onMouseEntered(consumer);
        return this;
    }

    /**
     * Adds a mouse exited listener.
     * @param consumer the mouse event consumer
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    @Override
    public DEditDateTime onMouseExited(Consumer<MouseEvent> consumer) {
        super.onMouseExited(consumer);
        return this;
    }

    /**
     * Adds a key typed listener.
     * @param consumer the key event consumer
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    @Override
    public DEditDateTime onKeyTyped(Consumer<KeyEvent> consumer) {
        super.onKeyTyped(consumer);
        return this;
    }

    /**
     * Adds a key pressed listener.
     * @param consumer the key event consumer
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    @Override
    public DEditDateTime onKeyPressed(Consumer<KeyEvent> consumer) {
        super.onKeyPressed(consumer);
        return this;
    }

    /**
     * Adds a key released listener.
     * @param consumer the key event consumer
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    @Override
    public DEditDateTime onKeyReleased(Consumer<KeyEvent> consumer) {
        super.onKeyReleased(consumer);
        return this;
    }

    /**
     * Adds a focus gained listener.
     * @param consumer the focus event consumer
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    @Override
    public DEditDateTime onFocusGained(Consumer<FocusEvent> consumer) {
        super.onFocusGained(consumer);
        return this;
    }

    /**
     * Adds a focus lost listener.
     * @param consumer the focus event consumer
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    @Override
    public DEditDateTime onFocusLost(Consumer<FocusEvent> consumer) {
        super.onFocusLost(consumer);
        return this;
    }

    // --- Specific DEditTimestamp Features ---

    /**
     * Adds a listener that is notified whenever the user explicitly selects a date or time.
     * @param consumer the consumer to accept the newly selected date
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    public DEditDateTime onTimestampSelected(Consumer<LocalDateTime> consumer) {
        this.dateEdit.onDateSelected(d -> consumer.accept(getValue()));
        this.timeEdit.onTimeSelected(t -> consumer.accept(getValue()));
        return this;
    }

    /**
     * Sets the font for the calendar components.
     * @param font the font to apply
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    public DEditDateTime font(Font font) {
        this.dateEdit.font(font);
        this.timeEdit.font(font);
        return this;
    }

    /**
     * Sets the background color for the calendar panels.
     * @param bg the background color
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    public DEditDateTime background(Color bg) {
        this.dateEdit.background(bg);
        this.timeEdit.background(bg);
        return this;
    }

    /**
     * Sets the foreground color for the calendar components.
     * @param fg the foreground color
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    public DEditDateTime foreground(Color fg) {
        this.dateEdit.foreground(fg);
        this.timeEdit.foreground(fg);
        return this;
    }

    /**
     * Sets the minimum year allowed in the year spinner.
     * @param minYear the minimum year
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    public DEditDateTime minYear(int minYear) {
        this.dateEdit.minYear(minYear);
        return this;
    }

    /**
     * Sets the maximum year allowed in the year spinner.
     * @param maxYear the maximum year
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    public DEditDateTime maxYear(int maxYear) {
        this.dateEdit.maxYear(maxYear);
        return this;
    }

    /**
     * Sets the background color specifically for the inner drawn clock face circle.
     * @param color the clock face color
     * @return This {@code DEditTimestamp} instance for fluent chaining.
     */
    public DEditDateTime clockColor(Color color) {
        this.timeEdit.clockColor(color);
        return this;
    }
}
