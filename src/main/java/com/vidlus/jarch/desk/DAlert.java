package com.vidlus.jarch.desk;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 * A fluent API wrapper for {@link JOptionPane}, designed to simplify the construction 
 * and display of alert boxes, prompts, and confirmation dialogs.
 * <p>
 * It provides a chainable interface to configure message types, options, icons, 
 * and behaviors before ultimately displaying the dialog via one of the display methods.
 */
public class DAlert {
    
    private Component parent;
    private Object message;
    private String title;
    private int messageType = JOptionPane.INFORMATION_MESSAGE;
    private int optionType = JOptionPane.DEFAULT_OPTION;
    private Icon icon;
    private Object[] options;
    private Object initialValue;

    /**
     * Constructs a new {@code DAlert} instance with a default title of "Message".
     */
    public DAlert() {
        this.title = "Message";
    }

    /**
     * Creates and returns a new {@code DAlert} instance.
     * This static factory method is provided for convenience in fluent chaining.
     * 
     * @return a new {@code DAlert} instance
     */
    public static DAlert build() {
        return new DAlert();
    }

    /**
     * Sets the parent component for the dialog. 
     * The dialog will typically be centered relative to this parent component.
     * 
     * @param parent the parent {@link Component} to which the dialog is attached
     * @return this {@code DAlert} instance for method chaining
     */
    public DAlert parent(Component parent) {
        this.parent = parent;
        return this;
    }

    /**
     * Sets the main message to display within the dialog.
     * 
     * @param message the primary message object (usually a {@link String} or a custom {@link Component})
     * @return this {@code DAlert} instance for method chaining
     */
    public DAlert message(Object message) {
        this.message = message;
        return this;
    }

    /**
     * Sets the main message to display within the dialog using a formatted string.
     * 
     * @param format a format string as described in {@link String#format(String, Object...)}
     * @param args arguments referenced by the format specifiers in the format string
     * @return this {@code DAlert} instance for method chaining
     */
    public DAlert messageFormat(String format, Object... args) {
        this.message = String.format(format, args);
        return this;
    }

    /**
     * Sets the title of the dialog window.
     * 
     * @param title the dialog title string
     * @return this {@code DAlert} instance for method chaining
     */
    public DAlert title(String title) {
        this.title = title;
        return this;
    }

    /**
     * Configures the dialog to display as an information message type.
     * Uses {@link JOptionPane#INFORMATION_MESSAGE}.
     * 
     * @return this {@code DAlert} instance for method chaining
     */
    public DAlert info() {
        this.messageType = JOptionPane.INFORMATION_MESSAGE;
        return this;
    }

    /**
     * Configures the dialog to display as a warning message type.
     * Uses {@link JOptionPane#WARNING_MESSAGE}.
     * 
     * @return this {@code DAlert} instance for method chaining
     */
    public DAlert warning() {
        this.messageType = JOptionPane.WARNING_MESSAGE;
        return this;
    }

    /**
     * Configures the dialog to display as an error message type.
     * Uses {@link JOptionPane#ERROR_MESSAGE}.
     * 
     * @return this {@code DAlert} instance for method chaining
     */
    public DAlert error() {
        this.messageType = JOptionPane.ERROR_MESSAGE;
        return this;
    }

    /**
     * Configures the dialog to display as a question message type.
     * Uses {@link JOptionPane#QUESTION_MESSAGE}.
     * 
     * @return this {@code DAlert} instance for method chaining
     */
    public DAlert question() {
        this.messageType = JOptionPane.QUESTION_MESSAGE;
        return this;
    }

    /**
     * Configures the dialog to display as a plain message type, which typically means no default icon is shown.
     * Uses {@link JOptionPane#PLAIN_MESSAGE}.
     * 
     * @return this {@code DAlert} instance for method chaining
     */
    public DAlert plain() {
        this.messageType = JOptionPane.PLAIN_MESSAGE;
        return this;
    }

    /**
     * Configures the dialog to display standard 'Yes' and 'No' buttons.
     * Uses {@link JOptionPane#YES_NO_OPTION}.
     * 
     * @return this {@code DAlert} instance for method chaining
     */
    public DAlert yesNo() {
        this.optionType = JOptionPane.YES_NO_OPTION;
        return this;
    }

    /**
     * Configures the dialog to display standard 'Yes', 'No', and 'Cancel' buttons.
     * Uses {@link JOptionPane#YES_NO_CANCEL_OPTION}.
     * 
     * @return this {@code DAlert} instance for method chaining
     */
    public DAlert yesNoCancel() {
        this.optionType = JOptionPane.YES_NO_CANCEL_OPTION;
        return this;
    }

    /**
     * Configures the dialog to display standard 'OK' and 'Cancel' buttons.
     * Uses {@link JOptionPane#OK_CANCEL_OPTION}.
     * 
     * @return this {@code DAlert} instance for method chaining
     */
    public DAlert okCancel() {
        this.optionType = JOptionPane.OK_CANCEL_OPTION;
        return this;
    }

    /**
     * Sets a custom icon to display alongside the message in the dialog.
     * 
     * @param icon the custom {@link Icon} to display
     * @return this {@code DAlert} instance for method chaining
     */
    public DAlert icon(Icon icon) {
        this.icon = icon;
        return this;
    }

    /**
     * Sets custom options to display in the dialog (e.g., custom button labels or dropdown choices).
     * 
     * @param options an array of objects representing the options to display
     * @return this {@code DAlert} instance for method chaining
     */
    public DAlert options(Object... options) {
        this.options = options;
        return this;
    }

    /**
     * Sets the initially selected value. This is primarily used for input dialogs 
     * or when displaying a dropdown list of options.
     * 
     * @param initialValue the default selection value
     * @return this {@code DAlert} instance for method chaining
     */
    public DAlert initialValue(Object initialValue) {
        this.initialValue = initialValue;
        return this;
    }

    /**
     * Displays a standard message dialog using the configured settings.
     * The dialog will block until the user dismisses it.
     */
    public void show() {
        JOptionPane.showMessageDialog(parent, message, title, messageType, icon);
    }

    /**
     * Displays a confirmation dialog using the configured settings.
     * 
     * @return an integer indicating the option selected by the user (e.g., {@link JOptionPane#YES_OPTION})
     */
    public int confirm() {
        return JOptionPane.showConfirmDialog(parent, message, title, optionType, messageType, icon);
    }

    /**
     * Displays an option dialog with the specified array of custom options.
     * 
     * @return an integer indicating the index of the option chosen by the user, or {@link JOptionPane#CLOSED_OPTION}
     */
    public int showOptions() {
        return JOptionPane.showOptionDialog(parent, message, title, optionType, messageType, icon, options, initialValue);
    }
    
    /**
     * Displays a prompt dialog requesting input from the user.
     * 
     * @return the string input provided by the user, or {@code null} if the input was canceled
     */
    public String input() {
        Object result;
        if (options != null) {
            result = JOptionPane.showInputDialog(parent, message, title, messageType, icon, options, initialValue);
        } else {
            result = JOptionPane.showInputDialog(parent, message, title, messageType);
        }
        return result != null ? result.toString() : null;
    }

    // --- Boolean Confirm Helpers ---

    /**
     * Displays a confirmation dialog and checks if the user confirmed the action.
     * 
     * @return {@code true} if the user selected 'Yes' or 'OK', {@code false} otherwise
     */
    public boolean confirmYes() {
        return confirm() == JOptionPane.YES_OPTION || confirm() == JOptionPane.OK_OPTION;
    }

    /**
     * Displays a confirmation dialog and checks if the user rejected the action.
     * 
     * @return {@code true} if the user selected 'No', {@code false} otherwise
     */
    public boolean confirmNo() {
        return confirm() == JOptionPane.NO_OPTION;
    }

    /**
     * Displays a confirmation dialog and checks if the user canceled the action.
     * 
     * @return {@code true} if the user selected 'Cancel' or closed the dialog, {@code false} otherwise
     */
    public boolean confirmCancel() {
        return confirm() == JOptionPane.CANCEL_OPTION;
    }

    // --- Additional Configuration Helpers ---

    /**
     * Directly sets the underlying {@link JOptionPane} message type.
     * 
     * @param messageType the message type integer constant (e.g., {@link JOptionPane#INFORMATION_MESSAGE})
     * @return this {@code DAlert} instance for method chaining
     */
    public DAlert messageType(int messageType) {
        this.messageType = messageType;
        return this;
    }

    /**
     * Directly sets the underlying {@link JOptionPane} option type.
     * 
     * @param optionType the option type integer constant (e.g., {@link JOptionPane#YES_NO_OPTION})
     * @return this {@code DAlert} instance for method chaining
     */
    public DAlert optionType(int optionType) {
        this.optionType = optionType;
        return this;
    }

    /**
     * Resets the dialog's option type to the default configuration.
     * Uses {@link JOptionPane#DEFAULT_OPTION}.
     * 
     * @return this {@code DAlert} instance for method chaining
     */
    public DAlert defaultOption() {
        this.optionType = JOptionPane.DEFAULT_OPTION;
        return this;
    }

    /**
     * Constructs and returns the underlying {@link JOptionPane} instance based on current settings.
     * This is useful if you wish to embed the pane in a custom layout or frame instead of a standard dialog.
     * 
     * @return a configured {@link JOptionPane} instance
     */
    public JOptionPane createPane() {
        return new JOptionPane(message, messageType, optionType, icon, options, initialValue);
    }

    // --- JDialog Creation ---

    /**
     * Constructs and returns the underlying {@link javax.swing.JDialog} without immediately showing it.
     * This is highly useful if you need to position the dialog manually, add window listeners, 
     * or show it non-modally before it appears on the screen.
     * 
     * @return a configured {@link javax.swing.JDialog} wrapping the pane
     */
    public javax.swing.JDialog createDialog() {
        JOptionPane pane = new JOptionPane(message, messageType, optionType, icon, options, initialValue);
        return pane.createDialog(parent, title);
    }

    // --- Internal Frame Support ---

    /**
     * Displays an internal information-message dialog within a desktop pane.
     */
    public void showInternal() {
        JOptionPane.showInternalMessageDialog(parent, message, title, messageType, icon);
    }

    /**
     * Displays an internal confirmation dialog within a desktop pane.
     * 
     * @return an integer indicating the option selected by the user
     */
    public int confirmInternal() {
        return JOptionPane.showInternalConfirmDialog(parent, message, title, optionType, messageType, icon);
    }

    /**
     * Displays an internal option dialog within a desktop pane.
     * 
     * @return an integer indicating the option chosen by the user
     */
    public int showOptionsInternal() {
        return JOptionPane.showInternalOptionDialog(parent, message, title, optionType, messageType, icon, options, initialValue);
    }

    /**
     * Displays an internal input dialog within a desktop pane.
     * 
     * @return the user's input string, or {@code null} if canceled
     */
    public String inputInternal() {
        Object result;
        if (options != null) {
            result = JOptionPane.showInternalInputDialog(parent, message, title, messageType, icon, options, initialValue);
        } else {
            result = JOptionPane.showInternalInputDialog(parent, message, title, messageType);
        }
        return result != null ? result.toString() : null;
    }
}
