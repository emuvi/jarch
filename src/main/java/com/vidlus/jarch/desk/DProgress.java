package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BoundedRangeModel;
import javax.swing.JProgressBar;

/**
 * A fluent API wrapper for JProgressBar to easily create and configure progress bars.
 */
public class DProgress extends JProgressBar {

    /**
     * Creates a horizontal progress bar that displays a border but no progress string.
     * The initial and minimum values are 0, and the maximum is 100.
     */
    public DProgress() {
        super();
    }

    /**
     * Creates a progress bar with the specified orientation.
     * 
     * @param orient the desired orientation of the progress bar
     */
    public DProgress(int orient) {
        super(orient);
    }

    /**
     * Creates a horizontal progress bar with the specified minimum and maximum.
     * 
     * @param min the minimum value of the progress bar
     * @param max the maximum value of the progress bar
     */
    public DProgress(int min, int max) {
        super(min, max);
    }

    /**
     * Creates a progress bar using the specified orientation, minimum, and maximum.
     * 
     * @param orient the desired orientation of the progress bar
     * @param min the minimum value of the progress bar
     * @param max the maximum value of the progress bar
     */
    public DProgress(int orient, int min, int max) {
        super(orient, min, max);
    }

    /**
     * Creates a horizontal progress bar, the given model holding the progress bar's data.
     * 
     * @param newModel the data model for the progress bar
     */
    public DProgress(BoundedRangeModel newModel) {
        super(newModel);
    }

    /**
     * Sets the progress bar's current value.
     * 
     * @param n the new value
     * @return This DProgress instance.
     */
    public DProgress value(int n) {
        setValue(n);
        return this;
    }

    /**
     * Sets the progress bar's minimum value (stored in the progress bar's data model).
     * 
     * @param n the new minimum
     * @return This DProgress instance.
     */
    public DProgress min(int n) {
        setMinimum(n);
        return this;
    }

    /**
     * Sets the progress bar's maximum value (stored in the progress bar's data model).
     * 
     * @param n the new maximum
     * @return This DProgress instance.
     */
    public DProgress max(int n) {
        setMaximum(n);
        return this;
    }

    /**
     * Sets the value of the stringPainted property, which determines whether the progress bar should render a progress string.
     * 
     * @param b true if the progress bar should render a string
     * @return This DProgress instance.
     */
    public DProgress showText(boolean b) {
        setStringPainted(b);
        return this;
    }

    /**
     * Sets the value of the progress string.
     * 
     * @param s the value of the progress string
     * @return This DProgress instance.
     */
    public DProgress text(String s) {
        setString(s);
        return this;
    }

    /**
     * Sets the indeterminate property of the progress bar.
     * 
     * @param newValue true if the progress bar should change to indeterminate mode; false if it should revert to normal.
     * @return This DProgress instance.
     */
    public DProgress indeterminate(boolean newValue) {
        setIndeterminate(newValue);
        return this;
    }

    /**
     * Sets the progress bar's orientation to newOrientation, which must be JProgressBar.VERTICAL or JProgressBar.HORIZONTAL.
     * 
     * @param newOrientation the new orientation
     * @return This DProgress instance.
     */
    public DProgress orientation(int newOrientation) {
        setOrientation(newOrientation);
        return this;
    }

    /**
     * Sets the font of this component.
     * 
     * @param font the desired Font for this component
     * @return This DProgress instance.
     */
    public DProgress font(Font font) {
        setFont(font);
        return this;
    }

    /**
     * Sets the foreground color of this component.
     * 
     * @param fg the desired foreground Color
     * @return This DProgress instance.
     */
    public DProgress foreground(Color fg) {
        setForeground(fg);
        return this;
    }

    /**
     * Sets the background color of this component.
     * 
     * @param bg the desired background Color
     * @return This DProgress instance.
     */
    public DProgress background(Color bg) {
        setBackground(bg);
        return this;
    }
}
