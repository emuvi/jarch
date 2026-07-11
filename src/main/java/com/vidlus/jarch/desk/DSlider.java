package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BoundedRangeModel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

/**
 * A fluent API wrapper for JSlider to easily create and configure slider components.
 */
public class DSlider extends JSlider {

    /**
     * Creates a horizontal slider with the range 0 to 100 and an initial value of 50.
     */
    public DSlider() {
        super();
    }

    /**
     * Creates a slider using the specified orientation with the range 0 to 100 and an initial value of 50.
     * 
     * @param orientation the orientation of the slider (JSlider.HORIZONTAL or JSlider.VERTICAL)
     */
    public DSlider(int orientation) {
        super(orientation);
    }

    /**
     * Creates a horizontal slider using the specified min and max with an initial value equal to the average of the min plus max.
     * 
     * @param min the minimum value of the slider
     * @param max the maximum value of the slider
     */
    public DSlider(int min, int max) {
        super(min, max);
    }

    /**
     * Creates a horizontal slider using the specified min, max and value.
     * 
     * @param min the minimum value of the slider
     * @param max the maximum value of the slider
     * @param value the initial value of the slider
     */
    public DSlider(int min, int max, int value) {
        super(min, max, value);
    }

    /**
     * Creates a slider with the specified orientation and the specified minimum, maximum, and initial values.
     * 
     * @param orientation the orientation of the slider
     * @param min the minimum value of the slider
     * @param max the maximum value of the slider
     * @param value the initial value of the slider
     */
    public DSlider(int orientation, int min, int max, int value) {
        super(orientation, min, max, value);
    }

    /**
     * Creates a horizontal slider using the specified BoundedRangeModel.
     * 
     * @param brm a BoundedRangeModel for the slider
     */
    public DSlider(BoundedRangeModel brm) {
        super(brm);
    }

    /**
     * Sets the slider's current value.
     * 
     * @param n the new value
     * @return This DSlider instance.
     */
    public DSlider value(int n) {
        setValue(n);
        return this;
    }

    /**
     * Sets the slider's minimum value.
     * 
     * @param min the minimum value
     * @return This DSlider instance.
     */
    public DSlider minimum(int min) {
        setMinimum(min);
        return this;
    }

    /**
     * Sets the slider's maximum value.
     * 
     * @param max the maximum value
     * @return This DSlider instance.
     */
    public DSlider maximum(int max) {
        setMaximum(max);
        return this;
    }

    /**
     * Sets the slider's orientation to either JSlider.HORIZONTAL or JSlider.VERTICAL.
     * 
     * @param orientation the new orientation
     * @return This DSlider instance.
     */
    public DSlider orientation(int orientation) {
        setOrientation(orientation);
        return this;
    }

    /**
     * Determines whether tick marks are painted on the slider.
     * 
     * @param b whether or not tick marks should be painted
     * @return This DSlider instance.
     */
    public DSlider paintTicks(boolean b) {
        setPaintTicks(b);
        return this;
    }

    /**
     * Determines whether the track is painted on the slider.
     * 
     * @param b whether or not to paint the slider track
     * @return This DSlider instance.
     */
    public DSlider paintTrack(boolean b) {
        setPaintTrack(b);
        return this;
    }

    /**
     * Determines whether labels are painted on the slider.
     * 
     * @param b whether or not to paint labels
     * @return This DSlider instance.
     */
    public DSlider paintLabels(boolean b) {
        setPaintLabels(b);
        return this;
    }

    /**
     * Sets the major tick spacing. The number that is passed in represents the distance, measured in values, between each major tick mark.
     * 
     * @param n new value for the majorTickSpacing property
     * @return This DSlider instance.
     */
    public DSlider majorTickSpacing(int n) {
        setMajorTickSpacing(n);
        return this;
    }

    /**
     * Sets the minor tick spacing. The number that is passed in represents the distance, measured in values, between each minor tick mark.
     * 
     * @param n new value for the minorTickSpacing property
     * @return This DSlider instance.
     */
    public DSlider minorTickSpacing(int n) {
        setMinorTickSpacing(n);
        return this;
    }

    /**
     * Specifying true makes the knob (and the data value it represents) resolve to the closest tick mark next to where the user positioned the knob.
     * 
     * @param b true to snap the knob to the nearest tick mark
     * @return This DSlider instance.
     */
    public DSlider snapToTicks(boolean b) {
        setSnapToTicks(b);
        return this;
    }

    /**
     * Specify true to reverse the value-range shown for the slider and false to put the value range in the normal order.
     * 
     * @param b true to reverse the slider values from their normal order
     * @return This DSlider instance.
     */
    public DSlider inverted(boolean b) {
        setInverted(b);
        return this;
    }

    /**
     * Adds a ChangeListener to the slider.
     * 
     * @param listener the ChangeListener to add
     * @return This DSlider instance.
     */
    public DSlider onChange(ChangeListener listener) {
        addChangeListener(listener);
        return this;
    }

    /**
     * Sets the font of this component.
     * 
     * @param font the desired Font for this component
     * @return This DSlider instance.
     */
    public DSlider font(Font font) {
        setFont(font);
        return this;
    }

    /**
     * Sets the foreground color of this component.
     * 
     * @param fg the desired foreground Color
     * @return This DSlider instance.
     */
    public DSlider foreground(Color fg) {
        setForeground(fg);
        return this;
    }

    /**
     * Sets the background color of this component.
     * 
     * @param bg the desired background Color
     * @return This DSlider instance.
     */
    public DSlider background(Color bg) {
        setBackground(bg);
        return this;
    }
}
