package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.border.Border;

/**
 * A custom component for displaying a java.time.LocalDateTime combining both DDate and DTime
 * side by side.
 */
public class DDateTime extends DPane {

    private final DDate dateView;
    private final DTime timeView;

    /**
     * Constructs a DTimestamp component, initializing both DDate and DTime
     * side by side.
     */
    public DDateTime() {
        super(new GridLayout(1, 2, 10, 0));
        
        this.dateView = new DDate();
        this.timeView = new DTime();
        
        add(this.dateView);
        add(this.timeView);
    }

    /**
     * Sets the date and time to display in this component.
     * 
     * @param datetime the date and time to show
     * @return this DTimestamp instance for fluent chaining
     */
    public DDateTime value(LocalDateTime datetime) {
        if (datetime == null) {
            this.dateView.value((java.time.LocalDate) null);
            this.timeView.value(null);
        } else {
            this.dateView.value(datetime.toLocalDate());
            this.timeView.value(Date.from(datetime.atZone(ZoneId.systemDefault()).toInstant()));
        }
        return this;
    }

    /**
     * Retrieves the currently displayed date and time.
     * 
     * @return the displayed date and time
     */
    public LocalDateTime value() {
        java.time.LocalDate d = dateView.value();
        Date t = timeView.value();
        if (d == null || t == null) return null;
        return LocalDateTime.of(d, t.toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
    }

    /**
     * Sets the highlight color for the active day and time.
     * 
     * @param color the highlight color
     * @return this DTimestamp instance for fluent chaining
     */
    public DDateTime highlightColor(Color color) {
        this.dateView.highlightColor(color);
        this.timeView.highlightColor(color);
        return this;
    }

    /**
     * Sets the font for both date and time components.
     * 
     * @param font the font to apply
     * @return this DTimestamp instance for fluent chaining
     */
    public DDateTime font(Font font) {
        super.setFont(font);
        this.dateView.font(font);
        this.timeView.font(font);
        return this;
    }

    /**
     * Sets the background color for both components.
     * 
     * @param bg the background color
     * @return this DTimestamp instance for fluent chaining
     */
    public DDateTime background(Color bg) {
        super.setBackground(bg);
        this.dateView.background(bg);
        this.timeView.background(bg);
        return this;
    }

    /**
     * Sets the foreground color for both components.
     * 
     * @param fg the foreground color
     * @return this DTimestamp instance for fluent chaining
     */
    public DDateTime foreground(Color fg) {
        super.setForeground(fg);
        this.dateView.foreground(fg);
        this.timeView.foreground(fg);
        return this;
    }

    /**
     * Globally enables or disables the component.
     * 
     * @param enabled true if enabled, false otherwise
     * @return this DTimestamp instance for fluent chaining
     */
    public DDateTime enabled(boolean enabled) {
        setEnabled(enabled);
        this.dateView.enabled(enabled);
        this.timeView.enabled(enabled);
        return this;
    }

    // --- Fluent Overrides for DPane ---

    /**
     * Sets the layout manager for this component.
     * @param layout the layout manager to use
     * @return this DTimestamp instance for fluent chaining
     */
    @Override
    public DDateTime layout(LayoutManager layout) {
        super.layout(layout);
        return this;
    }

    /**
     * Sets the border of this component.
     * @param border the border to set
     * @return this DTimestamp instance for fluent chaining
     */
    @Override
    public DDateTime border(Border border) {
        super.border(border);
        return this;
    }

    /**
     * Sets an empty border with the specified padding on all sides.
     * @param size the padding size
     * @return this DTimestamp instance for fluent chaining
     */
    @Override
    public DDateTime borderEmpty(int size) {
        super.borderEmpty(size);
        return this;
    }

    /**
     * Sets an empty border with specified padding for each side.
     * @param top the top padding
     * @param left the left padding
     * @param bottom the bottom padding
     * @param right the right padding
     * @return this DTimestamp instance for fluent chaining
     */
    @Override
    public DDateTime borderEmpty(int top, int left, int bottom, int right) {
        super.borderEmpty(top, left, bottom, right);
        return this;
    }

    /**
     * Sets a line border with the specified color.
     * @param color the line color
     * @return this DTimestamp instance for fluent chaining
     */
    @Override
    public DDateTime borderLine(Color color) {
        super.borderLine(color);
        return this;
    }

    /**
     * Sets a line border with the specified color and thickness.
     * @param color the line color
     * @param thickness the line thickness
     * @return this DTimestamp instance for fluent chaining
     */
    @Override
    public DDateTime borderLine(Color color, int thickness) {
        super.borderLine(color, thickness);
        return this;
    }

    /**
     * Sets a line border with the specified color, thickness, and rounded corners.
     * @param color the line color
     * @param thickness the line thickness
     * @param rounded true if corners should be rounded
     * @return this DTimestamp instance for fluent chaining
     */
    @Override
    public DDateTime borderLine(Color color, int thickness, boolean rounded) {
        super.borderLine(color, thickness, rounded);
        return this;
    }

    /**
     * Sets a default etched border.
     * @return this DTimestamp instance for fluent chaining
     */
    @Override
    public DDateTime borderEtched() {
        super.borderEtched();
        return this;
    }

    /**
     * Sets an etched border with the specified type (RAISED or LOWERED).
     * @param type the border type
     * @return this DTimestamp instance for fluent chaining
     */
    @Override
    public DDateTime borderEtched(int type) {
        super.borderEtched(type);
        return this;
    }

    /**
     * Sets an etched border with specific highlight and shadow colors.
     * @param highlight the highlight color
     * @param shadow the shadow color
     * @return this DTimestamp instance for fluent chaining
     */
    @Override
    public DDateTime borderEtched(Color highlight, Color shadow) {
        super.borderEtched(highlight, shadow);
        return this;
    }

    /**
     * Sets an etched border with specific type, highlight, and shadow colors.
     * @param type the border type
     * @param highlight the highlight color
     * @param shadow the shadow color
     * @return this DTimestamp instance for fluent chaining
     */
    @Override
    public DDateTime borderEtched(int type, Color highlight, Color shadow) {
        super.borderEtched(type, highlight, shadow);
        return this;
    }

    /**
     * Sets a raised bevel border.
     * @return this DTimestamp instance for fluent chaining
     */
    @Override
    public DDateTime borderBevelRaised() {
        super.borderBevelRaised();
        return this;
    }

    /**
     * Sets a lowered bevel border.
     * @return this DTimestamp instance for fluent chaining
     */
    @Override
    public DDateTime borderBevelLowered() {
        super.borderBevelLowered();
        return this;
    }

    /**
     * Sets a bevel border of the specified type (RAISED or LOWERED).
     * @param type the border type
     * @return this DTimestamp instance for fluent chaining
     */
    @Override
    public DDateTime borderBevel(int type) {
        super.borderBevel(type);
        return this;
    }

    /**
     * Sets a bevel border of the specified type with highlight and shadow colors.
     * @param type the border type
     * @param highlight the highlight color
     * @param shadow the shadow color
     * @return this DTimestamp instance for fluent chaining
     */
    @Override
    public DDateTime borderBevel(int type, Color highlight, Color shadow) {
        super.borderBevel(type, highlight, shadow);
        return this;
    }

    /**
     * Sets a bevel border of the specified type with detailed highlight and shadow colors.
     * @param type the border type
     * @param highlightOuter the outer highlight color
     * @param highlightInner the inner highlight color
     * @param shadowOuter the outer shadow color
     * @param shadowInner the inner shadow color
     * @return this DTimestamp instance for fluent chaining
     */
    @Override
    public DDateTime borderBevel(int type, Color highlightOuter, Color highlightInner, Color shadowOuter,
            Color shadowInner) {
        super.borderBevel(type, highlightOuter, highlightInner, shadowOuter, shadowInner);
        return this;
    }

    /**
     * Sets a soft bevel border of the specified type (RAISED or LOWERED).
     * @param type the border type
     * @return this DTimestamp instance for fluent chaining
     */
    @Override
    public DDateTime borderSoftBevel(int type) {
        super.borderSoftBevel(type);
        return this;
    }

    /**
     * Sets a soft bevel border of the specified type with highlight and shadow colors.
     * @param type the border type
     * @param highlight the highlight color
     * @param shadow the shadow color
     * @return this DTimestamp instance for fluent chaining
     */
    @Override
    public DDateTime borderSoftBevel(int type, Color highlight, Color shadow) {
        super.borderSoftBevel(type, highlight, shadow);
        return this;
    }

    /**
     * Sets a soft bevel border of the specified type with detailed highlight and shadow colors.
     * @param type the border type
     * @param highlightOuter the outer highlight color
     * @param highlightInner the inner highlight color
     * @param shadowOuter the outer shadow color
     * @param shadowInner the inner shadow color
     * @return this DTimestamp instance for fluent chaining
     */
    @Override
    public DDateTime borderSoftBevel(int type, Color highlightOuter, Color highlightInner, Color shadowOuter,
            Color shadowInner) {
        super.borderSoftBevel(type, highlightOuter, highlightInner, shadowOuter, shadowInner);
        return this;
    }

    /**
     * Sets a titled border with the specified title string.
     * @param title the title text
     * @return this DTimestamp instance for fluent chaining
     */
    @Override
    public DDateTime borderTitled(String title) {
        super.borderTitled(title);
        return this;
    }
}
