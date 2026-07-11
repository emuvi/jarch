package com.vidlus.jarch.desk;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 * A fluent API wrapper for JOptionPane to easily construct and display alert boxes and prompts.
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

    public DAlert() {
        this.title = "Message";
    }

    public DAlert parent(Component parent) {
        this.parent = parent;
        return this;
    }

    public DAlert message(Object message) {
        this.message = message;
        return this;
    }

    /**
     * Sets the message using String.format.
     */
    public DAlert messageFormat(String format, Object... args) {
        this.message = String.format(format, args);
        return this;
    }

    public DAlert title(String title) {
        this.title = title;
        return this;
    }

    public DAlert info() {
        this.messageType = JOptionPane.INFORMATION_MESSAGE;
        return this;
    }

    public DAlert warning() {
        this.messageType = JOptionPane.WARNING_MESSAGE;
        return this;
    }

    public DAlert error() {
        this.messageType = JOptionPane.ERROR_MESSAGE;
        return this;
    }

    public DAlert question() {
        this.messageType = JOptionPane.QUESTION_MESSAGE;
        return this;
    }

    public DAlert plain() {
        this.messageType = JOptionPane.PLAIN_MESSAGE;
        return this;
    }

    public DAlert yesNo() {
        this.optionType = JOptionPane.YES_NO_OPTION;
        return this;
    }

    public DAlert yesNoCancel() {
        this.optionType = JOptionPane.YES_NO_CANCEL_OPTION;
        return this;
    }

    public DAlert okCancel() {
        this.optionType = JOptionPane.OK_CANCEL_OPTION;
        return this;
    }

    public DAlert icon(Icon icon) {
        this.icon = icon;
        return this;
    }

    /**
     * Sets the options to display (e.g., custom button labels or dropdown choices).
     */
    public DAlert options(Object... options) {
        this.options = options;
        return this;
    }

    public DAlert initialValue(Object initialValue) {
        this.initialValue = initialValue;
        return this;
    }

    /**
     * Brings up an information-message dialog.
     */
    public void show() {
        JOptionPane.showMessageDialog(parent, message, title, messageType, icon);
    }

    /**
     * Brings up a dialog where the number of choices is determined by the optionType.
     * 
     * @return an integer indicating the option selected by the user
     */
    public int confirm() {
        return JOptionPane.showConfirmDialog(parent, message, title, optionType, messageType, icon);
    }

    /**
     * Brings up a dialog with a specified array of options.
     * 
     * @return an integer indicating the option chosen by the user, or CLOSED_OPTION
     */
    public int showOptions() {
        return JOptionPane.showOptionDialog(parent, message, title, optionType, messageType, icon, options, initialValue);
    }
    
    /**
     * Shows a question-message dialog requesting input from the user.
     * 
     * @return user's input, or null meaning the user canceled the input
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
     * Brings up a confirm dialog and returns true if the user clicked Yes (or OK).
     */
    public boolean confirmYes() {
        return confirm() == JOptionPane.YES_OPTION || confirm() == JOptionPane.OK_OPTION;
    }

    /**
     * Brings up a confirm dialog and returns true if the user clicked No.
     */
    public boolean confirmNo() {
        return confirm() == JOptionPane.NO_OPTION;
    }

    /**
     * Brings up a confirm dialog and returns true if the user clicked Cancel.
     */
    public boolean confirmCancel() {
        return confirm() == JOptionPane.CANCEL_OPTION;
    }

    // --- JDialog Creation ---

    /**
     * Creates and returns the underlying JDialog without immediately showing it.
     * Useful if you need to position the dialog manually, add window listeners, or show it non-modally.
     * 
     * @return a configured JDialog instance
     */
    public javax.swing.JDialog createDialog() {
        JOptionPane pane = new JOptionPane(message, messageType, optionType, icon, options, initialValue);
        return pane.createDialog(parent, title);
    }

    // --- Internal Frame Support ---

    /**
     * Brings up an internal information-message dialog.
     */
    public void showInternal() {
        JOptionPane.showInternalMessageDialog(parent, message, title, messageType, icon);
    }

    /**
     * Brings up an internal confirm dialog.
     */
    public int confirmInternal() {
        return JOptionPane.showInternalConfirmDialog(parent, message, title, optionType, messageType, icon);
    }

    /**
     * Brings up an internal option dialog.
     */
    public int showOptionsInternal() {
        return JOptionPane.showInternalOptionDialog(parent, message, title, optionType, messageType, icon, options, initialValue);
    }

    /**
     * Brings up an internal input dialog.
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
