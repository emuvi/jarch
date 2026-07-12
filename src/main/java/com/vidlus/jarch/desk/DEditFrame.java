package com.vidlus.jarch.desk;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.vidlus.jarch.mage.WizGUI;

/**
 * A specialized frame that hosts a {@link DEdit} component to allow users to modify a value.
 * <p>
 * This frame provides built-in "Confirm" and "Cancel" actions, and executes
 * the provided callbacks when these actions are triggered. It uses a fluent API
 * to easily configure its properties and callbacks.
 * 
 * @param <T> the type of the value being edited
 */
public class DEditFrame<T> extends DFrame implements DValue<T> {

    private final JPanel panelBody = new JPanel(new BorderLayout(2, 2));
    private final JPanel panelAction = new JPanel(new FlowLayout(FlowLayout.RIGHT, 2, 2));
    private final JButton buttonConfirm = new JButton("Confirm");
    private final JButton buttonCancel = new JButton("Cancel");

    private DEdit<T> edit;
    private Consumer<T> onConfirm;
    private Runnable onCancel;

    /**
     * Constructs a new {@code DEditFrame} with the specified title,
     * without an initial edit component or confirmation callback.
     * 
     * @param title the title of the frame
     */
    public DEditFrame(String title) {
        this(title, null, null);
    }

    /**
     * Constructs a new {@code DEditFrame} with the specified title and edit component.
     * 
     * @param title the title of the frame
     * @param edit the edit component to display
     */
    public DEditFrame(String title, DEdit<T> edit) {
        this(title, edit, null);
    }

    /**
     * Constructs a new {@code DEditFrame} with the specified title and confirmation callback.
     * 
     * @param title the title of the frame
     * @param onConfirm the callback to execute when the confirm button is pressed
     */
    public DEditFrame(String title, Consumer<T> onConfirm) {
        this(title, null, onConfirm);
    }

    /**
     * Constructs a new {@code DEditFrame} with the specified title, edit component, 
     * and confirmation callback.
     * 
     * @param title the title of the frame
     * @param edit the edit component to display
     * @param onConfirm the callback to execute when the confirm button is pressed
     */
    public DEditFrame(String title, DEdit<T> edit, Consumer<T> onConfirm) {
        super(title);
        this.edit = edit;
        this.onConfirm = onConfirm;
        buttonConfirm.addActionListener(this::actConfirm);
        buttonCancel.addActionListener(this::actCancel);
        if (edit != null) {
            panelBody.add(edit.comp(), BorderLayout.CENTER);
        }
        panelAction.add(buttonConfirm);
        panelAction.add(buttonCancel);
        panelBody.add(panelAction, BorderLayout.SOUTH);
        panelBody.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
        body(panelBody);
    }

    /**
     * Retrieves the current value from the edit component.
     * 
     * @return the current value, or null if there is no edit component
     */
    @Override
    public T getValue() {
        return edit.getValue();
    }

    /**
     * Sets the value of the edit component.
     * 
     * @param value the new value to set
     */
    @Override
    public void setValue(T value) {
        edit.setValue(value);
    }

    /**
     * Retrieves the current edit component.
     * 
     * @return the edit component being displayed
     */
    public DEdit<T> edit() {
        return edit;
    }

    /**
     * Sets the edit component to be displayed in the frame.
     * 
     * @param edit the new edit component
     * @return this frame instance for chaining
     */
    public DEditFrame<T> edit(DEdit<T> edit) {
        this.edit = edit;
        if (edit != null) {
            panelBody.add(edit.comp(), BorderLayout.CENTER);
        }
        return this;
    }

    /**
     * Retrieves the confirmation callback.
     * 
     * @return the confirmation callback, or null if none is set
     */
    public Consumer<T> onConfirm() {
        return onConfirm;
    }

    /**
     * Sets the callback to execute when the confirm button is pressed.
     * 
     * @param onConfirm the confirmation callback
     * @return this frame instance for chaining
     */
    public DEditFrame<T> onConfirm(Consumer<T> onConfirm) {
        this.onConfirm = onConfirm;
        return this;
    }

    /**
     * Retrieves the cancellation callback.
     * 
     * @return the cancellation callback, or null if none is set
     */
    public Runnable onCancel() {
        return onCancel;
    }

    /**
     * Sets the callback to execute when the cancel button is pressed.
     * 
     * @param onCancel the cancellation callback
     * @return this frame instance for chaining
     */
    public DEditFrame<T> onCancel(Runnable onCancel) {
        this.onCancel = onCancel;
        return this;
    }

    /**
     * Retrieves the current value fluently.
     * 
     * @return the current value from the edit component
     */
    public T value() {
        return edit.getValue();
    }

    /**
     * Sets the value of the edit component fluently.
     * 
     * @param value the new value to set
     * @return this frame instance for chaining
     */
    public DEditFrame<T> value(T value) {
        edit.setValue(value);
        return this;
    }

    /**
     * Retrieves the text of the confirm button.
     * 
     * @return the text displayed on the confirm button
     */
    public String confirmText() {
        return buttonConfirm.getText();
    }

    /**
     * Sets the text of the confirm button fluently.
     * 
     * @param text the new text for the confirm button
     * @return this frame instance for chaining
     */
    public DEditFrame<T> confirmText(String text) {
        buttonConfirm.setText(text);
        return this;
    }

    /**
     * Retrieves the text of the cancel button.
     * 
     * @return the text displayed on the cancel button
     */
    public String cancelText() {
        return buttonCancel.getText();
    }

    /**
     * Sets the text of the cancel button fluently.
     * 
     * @param text the new text for the cancel button
     * @return this frame instance for chaining
     */
    public DEditFrame<T> cancelText(String text) {
        buttonCancel.setText(text);
        return this;
    }

    /**
     * Handles the action event triggered by the confirm button.
     * Validates and executes the confirmation callback, then closes the frame.
     * 
     * @param event the action event
     */
    private void actConfirm(ActionEvent event) {
        if (onConfirm != null) {
            onConfirm.accept(edit.getValue());
        }
        WizGUI.close(this);
    }

    /**
     * Handles the action event triggered by the cancel button.
     * Executes the cancellation callback if set, then closes the frame.
     * 
     * @param event the action event
     */
    private void actCancel(ActionEvent event) {
        if (onCancel != null) {
            onCancel.run();
        }
        WizGUI.close(this);
    }

}
