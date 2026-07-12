package com.vidlus.jarch.desk;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.KeyStroke;

/**
 * A fluent API wrapper for {@link javax.swing.Action} to easily create and configure actions.
 */
public class DAction extends AbstractAction {

    private ActionListener listener;

    /**
     * Constructs a default {@code DAction}.
     */
    public DAction() {
        super();
    }

    /**
     * Constructs a {@code DAction} with the specified name.
     * 
     * @param name the name ({@code Action.NAME}) for the action
     */
    public DAction(String name) {
        super(name);
    }

    /**
     * Constructs a {@code DAction} with the specified name and icon.
     * 
     * @param name the name ({@code Action.NAME}) for the action
     * @param icon the icon ({@code Action.SMALL_ICON}) for the action
     */
    public DAction(String name, Icon icon) {
        super(name, icon);
    }

    /**
     * Sets the name ({@code Action.NAME}) for the action.
     * 
     * @param name the name
     * @return this {@code DAction} instance for method chaining
     */
    public DAction name(String name) {
        putValue(NAME, name);
        return this;
    }

    /**
     * Sets the icon ({@code Action.SMALL_ICON}) for the action.
     * 
     * @param icon the icon
     * @return this {@code DAction} instance for method chaining
     */
    public DAction icon(Icon icon) {
        putValue(SMALL_ICON, icon);
        return this;
    }

    /**
     * Sets the short description/tooltip ({@code Action.SHORT_DESCRIPTION}) for the action.
     * 
     * @param tooltip the tooltip text
     * @return this {@code DAction} instance for method chaining
     */
    public DAction tooltip(String tooltip) {
        putValue(SHORT_DESCRIPTION, tooltip);
        return this;
    }

    /**
     * Sets the long description ({@code Action.LONG_DESCRIPTION}) for the action.
     * 
     * @param description the long description text
     * @return this {@code DAction} instance for method chaining
     */
    public DAction description(String description) {
        putValue(LONG_DESCRIPTION, description);
        return this;
    }

    /**
     * Sets the action command key ({@code Action.ACTION_COMMAND_KEY}) for the action.
     * 
     * @param command the action command string
     * @return this {@code DAction} instance for method chaining
     */
    public DAction command(String command) {
        putValue(ACTION_COMMAND_KEY, command);
        return this;
    }

    /**
     * Sets the mnemonic key ({@code Action.MNEMONIC_KEY}) for the action.
     * 
     * @param mnemonic the mnemonic key code
     * @return this {@code DAction} instance for method chaining
     */
    public DAction mnemonic(int mnemonic) {
        putValue(MNEMONIC_KEY, mnemonic);
        return this;
    }

    /**
     * Sets the accelerator key stroke ({@code Action.ACCELERATOR_KEY}) for the action.
     * 
     * @param accelerator the accelerator key stroke
     * @return this {@code DAction} instance for method chaining
     */
    public DAction accelerator(KeyStroke accelerator) {
        putValue(ACCELERATOR_KEY, accelerator);
        return this;
    }

    /**
     * Sets the selected state ({@code Action.SELECTED_KEY}) for the action.
     * 
     * @param selected the selected state
     * @return this {@code DAction} instance for method chaining
     */
    public DAction selected(boolean selected) {
        putValue(SELECTED_KEY, selected);
        return this;
    }

    /**
     * Sets the enabled state of the action.
     * 
     * @param enabled the enabled state
     * @return this {@code DAction} instance for method chaining
     */
    public DAction enabled(boolean enabled) {
        setEnabled(enabled);
        return this;
    }

    /**
     * Sets the {@link ActionListener} that will be notified when this action is performed.
     * 
     * @param listener the action listener
     * @return this {@code DAction} instance for method chaining
     */
    public DAction onAction(ActionListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * Sets a {@link Runnable} to be executed when this action is performed.
     * 
     * @param action the runnable action
     * @return this {@code DAction} instance for method chaining
     */
    public DAction onAction(Runnable action) {
        this.listener = e -> action.run();
        return this;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (listener != null) {
            listener.actionPerformed(e);
        }
    }
}
