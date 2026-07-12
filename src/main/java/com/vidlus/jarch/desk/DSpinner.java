package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Font;
import java.util.Date;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

/**
 * A fluent API wrapper for JSpinner to easily create and configure spinner components.
 */
public class DSpinner extends JSpinner {

    /**
     * Constructs a complete spinner with pair of next/previous buttons and an editor for the SpinnerModel.
     */
    public DSpinner() {
        super();
    }

    /**
     * Constructs a spinner for the given model.
     * 
     * @param model the model to use
     */
    public DSpinner(SpinnerModel model) {
        super(model);
    }

    /**
     * Sets the model of the spinner.
     * 
     * @param model the SpinnerModel to be set
     * @return This DSpinner instance.
     */
    public DSpinner model(SpinnerModel model) {
        setModel(model);
        return this;
    }

    /**
     * Configures the spinner to use a SpinnerNumberModel with integer values.
     * 
     * @param value    the current value
     * @param minimum  the minimum value
     * @param maximum  the maximum value
     * @param stepSize the step size
     * @return This DSpinner instance.
     */
    public DSpinner numberModel(int value, int minimum, int maximum, int stepSize) {
        setModel(new SpinnerNumberModel(value, minimum, maximum, stepSize));
        return this;
    }

    /**
     * Configures the spinner to use a SpinnerNumberModel with double values.
     * 
     * @param value    the current value
     * @param minimum  the minimum value
     * @param maximum  the maximum value
     * @param stepSize the step size
     * @return This DSpinner instance.
     */
    public DSpinner numberModel(double value, double minimum, double maximum, double stepSize) {
        setModel(new SpinnerNumberModel(value, minimum, maximum, stepSize));
        return this;
    }

    /**
     * Configures the spinner to use a SpinnerListModel based on a List.
     * 
     * @param list the List to back the model
     * @return This DSpinner instance.
     */
    public DSpinner listModel(List<?> list) {
        setModel(new SpinnerListModel(list));
        return this;
    }

    /**
     * Configures the spinner to use a SpinnerListModel based on an array.
     * 
     * @param array the array to back the model
     * @return This DSpinner instance.
     */
    public DSpinner listModel(Object[] array) {
        setModel(new SpinnerListModel(array));
        return this;
    }

    /**
     * Configures the spinner to use a SpinnerDateModel.
     * 
     * @param value         the current (non null) Date
     * @param start         the first Date in the sequence or null
     * @param end           the last Date in the sequence or null
     * @param calendarField one of Calendar.ERA, Calendar.YEAR, Calendar.MONTH, etc.
     * @return This DSpinner instance.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public DSpinner dateModel(Date value, Comparable start, Comparable end, int calendarField) {
        setModel(new SpinnerDateModel(value, start, end, calendarField));
        return this;
    }

    /**
     * Sets the current value of the spinner.
     * 
     * @param value new value for the spinner
     * @return This DSpinner instance.
     */
    public DSpinner value(Object value) {
        setValue(value);
        return this;
    }

    /**
     * Changes the JComponent that displays the current value of the SpinnerModel.
     * 
     * @param editor the new editor
     * @return This DSpinner instance.
     */
    public DSpinner editor(JComponent editor) {
        setEditor(editor);
        return this;
    }

    /**
     * Adds a ChangeListener to the spinner's model.
     * 
     * @param listener the ChangeListener to add
     * @return This DSpinner instance.
     */
    public DSpinner onChange(ChangeListener listener) {
        addChangeListener(listener);
        return this;
    }

    /**
     * Sets the font of this component and cascades it to its editor.
     * 
     * @param font the desired Font for this component
     * @return This DSpinner instance.
     */
    public DSpinner font(Font font) {
        setFont(font);
        if (getEditor() instanceof DefaultEditor defaultEditor) {
            defaultEditor.getTextField().setFont(font);
        }
        return this;
    }

    /**
     * Sets the foreground color of this component.
     * 
     * @param fg the desired foreground Color
     * @return This DSpinner instance.
     */
    public DSpinner foreground(Color fg) {
        setForeground(fg);
        return this;
    }

    /**
     * Sets the background color of this component.
     * 
     * @param bg the desired background Color
     * @return This DSpinner instance.
     */
    public DSpinner background(Color bg) {
        setBackground(bg);
        return this;
    }
}
