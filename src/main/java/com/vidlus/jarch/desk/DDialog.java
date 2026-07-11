package com.vidlus.jarch.desk;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

/**
 * A fluent API wrapper for JDialog to easily create and configure custom dialogs.
 */
public class DDialog extends JDialog {

    public DDialog(Frame owner) {
        this(owner, "", false);
    }

    public DDialog(Frame owner, String title) {
        this(owner, title, false);
    }

    public DDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(owner);
    }

    /**
     * Sets the content pane body of the dialog.
     * 
     * @param body the container to set as the body
     * @return This DDialog instance.
     */
    public DDialog body(Container body) {
        setContentPane(body);
        pack();
        return this;
    }

    /**
     * Gets the title of the dialog.
     * 
     * @return the title
     */
    public String title() {
        return super.getTitle();
    }

    /**
     * Sets the title of the dialog.
     * 
     * @param title the new title
     * @return This DDialog instance.
     */
    public DDialog title(String title) {
        super.setTitle(title);
        return this;
    }

    /**
     * Gets the dimension of the dialog.
     * 
     * @return the dimension
     */
    public Dimension dimension() {
        return super.getSize();
    }

    /**
     * Sets the dimension of the dialog.
     * 
     * @param width the width
     * @param height the height
     * @return This DDialog instance.
     */
    public DDialog dimension(int width, int height) {
        super.setSize(width, height);
        return this;
    }

    /**
     * Gets whether the dialog is visible.
     * 
     * @return true if visible
     */
    public boolean visible() {
        return super.isVisible();
    }

    /**
     * Sets whether the dialog is visible.
     * 
     * @param visible true to show the dialog
     * @return This DDialog instance.
     */
    public DDialog visible(boolean visible) {
        super.setVisible(visible);
        return this;
    }

    /**
     * Gets whether the dialog is resizable.
     * 
     * @return true if resizable
     */
    public boolean resizable() {
        return super.isResizable();
    }

    /**
     * Sets whether the dialog is resizable.
     * 
     * @param resizable true if resizable
     * @return This DDialog instance.
     */
    public DDialog resizable(boolean resizable) {
        super.setResizable(resizable);
        return this;
    }

    /**
     * Gets whether the dialog is undecorated.
     * 
     * @return true if undecorated
     */
    public boolean undecorated() {
        return super.isUndecorated();
    }

    /**
     * Sets whether the dialog is undecorated.
     * 
     * @param undecorated true if undecorated
     * @return This DDialog instance.
     */
    public DDialog undecorated(boolean undecorated) {
        super.setUndecorated(undecorated);
        return this;
    }

    /**
     * Gets whether the dialog is always on top.
     * 
     * @return true if always on top
     */
    public boolean alwaysOnTop() {
        return super.isAlwaysOnTop();
    }

    /**
     * Sets whether the dialog is always on top.
     * 
     * @param alwaysOnTop true if always on top
     * @return This DDialog instance.
     */
    public DDialog alwaysOnTop(boolean alwaysOnTop) {
        super.setAlwaysOnTop(alwaysOnTop);
        return this;
    }
}
