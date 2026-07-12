package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

/**
 * A fluent API wrapper for {@link JDialog}, designed to easily create, configure, 
 * and manage custom dialog windows with chainable method calls.
 */
public class DDialog extends JDialog {

    /**
     * Constructs a non-modal {@code DDialog} with the specified owner frame and an empty title.
     * 
     * @param owner the {@link Frame} from which the dialog is displayed
     */
    public DDialog(Frame owner) {
        this(owner, "", false);
    }

    /**
     * Constructs a non-modal {@code DDialog} with the specified owner frame and title.
     * 
     * @param owner the {@link Frame} from which the dialog is displayed
     * @param title the string to display in the dialog's title bar
     */
    public DDialog(Frame owner, String title) {
        this(owner, title, false);
    }

    /**
     * Constructs a {@code DDialog} with the specified owner frame, title, and modality.
     * By default, the dialog sets its close operation to {@link WindowConstants#DISPOSE_ON_CLOSE} 
     * and positions itself relative to its owner.
     * 
     * @param owner the {@link Frame} from which the dialog is displayed
     * @param title the string to display in the dialog's title bar
     * @param modal {@code true} for a modal dialog, {@code false} for one that allows others windows to be active at the same time
     */
    public DDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(owner);
    }

    /**
     * Sets the primary content pane body of the dialog and subsequently packs the window.
     * 
     * @param body the {@link Container} to set as the dialog's content pane
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog body(Container body) {
        setContentPane(body);
        pack();
        return this;
    }

    /**
     * Retrieves the current title of the dialog.
     * 
     * @return the dialog's title string
     */
    public String title() {
        return super.getTitle();
    }

    /**
     * Sets the title of the dialog.
     * 
     * @param title the new title string
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog title(String title) {
        super.setTitle(title);
        return this;
    }

    /**
     * Retrieves the current dimension (size) of the dialog.
     * 
     * @return the {@link Dimension} of the dialog
     */
    public Dimension dimension() {
        return super.getSize();
    }

    /**
     * Sets the dimension (size) of the dialog.
     * 
     * @param width the width in pixels
     * @param height the height in pixels
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog dimension(int width, int height) {
        super.setSize(width, height);
        return this;
    }

    /**
     * Checks whether the dialog is currently visible.
     * 
     * @return {@code true} if visible, {@code false} otherwise
     */
    public boolean visible() {
        return super.isVisible();
    }

    /**
     * Shows or hides the dialog.
     * 
     * @param visible {@code true} to show the dialog, {@code false} to hide it
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog visible(boolean visible) {
        super.setVisible(visible);
        return this;
    }

    /**
     * Checks whether the dialog can be resized by the user.
     * 
     * @return {@code true} if resizable, {@code false} otherwise
     */
    public boolean resizable() {
        return super.isResizable();
    }

    /**
     * Specifies whether this dialog should be resizable by the user.
     * 
     * @param resizable {@code true} to allow resizing, {@code false} to prevent it
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog resizable(boolean resizable) {
        super.setResizable(resizable);
        return this;
    }

    /**
     * Checks whether the dialog is undecorated (lacks a window border and title bar).
     * 
     * @return {@code true} if undecorated, {@code false} otherwise
     */
    public boolean undecorated() {
        return super.isUndecorated();
    }

    /**
     * Specifies whether this dialog should be undecorated. 
     * This must be called before the dialog is made visible.
     * 
     * @param undecorated {@code true} to remove decorations, {@code false} to retain them
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog undecorated(boolean undecorated) {
        super.setUndecorated(undecorated);
        return this;
    }

    /**
     * Checks whether the dialog is set to always be on top of other windows.
     * 
     * @return {@code true} if always on top, {@code false} otherwise
     */
    public boolean alwaysOnTop() {
        return super.isAlwaysOnTop();
    }

    /**
     * Specifies whether this dialog should remain always on top of other windows.
     * 
     * @param alwaysOnTop {@code true} to keep the dialog on top, {@code false} otherwise
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog alwaysOnTop(boolean alwaysOnTop) {
        super.setAlwaysOnTop(alwaysOnTop);
        return this;
    }

    /**
     * Checks whether the dialog is modal.
     * 
     * @return {@code true} if modal, {@code false} otherwise
     */
    public boolean modal() {
        return super.isModal();
    }

    /**
     * Specifies whether this dialog should be modal.
     * 
     * @param modal {@code true} to enforce modality, {@code false} otherwise
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog modal(boolean modal) {
        super.setModal(modal);
        return this;
    }

    /**
     * Retrieves the specific modality type of the dialog.
     * 
     * @return the {@link ModalityType} of the dialog
     */
    public ModalityType modalityType() {
        return super.getModalityType();
    }

    /**
     * Sets the specific modality type of the dialog.
     * 
     * @param type the {@link ModalityType} to apply
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog modalityType(ModalityType type) {
        super.setModalityType(type);
        return this;
    }

    /**
     * Sets the operation that will happen by default when the user initiates a "close" on this dialog.
     * 
     * @param operation the close operation constant (e.g., {@link WindowConstants#DISPOSE_ON_CLOSE})
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog defaultCloseOperation(int operation) {
        super.setDefaultCloseOperation(operation);
        return this;
    }

    /**
     * Causes the dialog to be sized to fit the preferred size and layouts of its subcomponents.
     * 
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog packDialog() {
        super.pack();
        return this;
    }

    /**
     * Moves the dialog to the specified coordinates.
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog location(int x, int y) {
        super.setLocation(x, y);
        return this;
    }

    /**
     * Moves the dialog to the specified point.
     * 
     * @param p the {@link Point} specifying the new location
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog location(Point p) {
        super.setLocation(p);
        return this;
    }

    /**
     * Positions the dialog relative to a specified component. 
     * Passing {@code null} will center the dialog on the screen.
     * 
     * @param c the {@link Component} in relation to which the dialog's location is determined
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog locationRelativeTo(Component c) {
        super.setLocationRelativeTo(c);
        return this;
    }

    /**
     * Centers the dialog on the screen. This is a convenience method for {@code locationRelativeTo(null)}.
     * 
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog center() {
        super.setLocationRelativeTo(null);
        return this;
    }

    /**
     * Moves and resizes the dialog to fit within the specified bounding box.
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param width the target width
     * @param height the target height
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog bounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        return this;
    }

    /**
     * Moves and resizes the dialog to match the specified rectangle bounds.
     * 
     * @param r the {@link Rectangle} specifying the bounds
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog bounds(Rectangle r) {
        super.setBounds(r);
        return this;
    }

    /**
     * Resizes the dialog to the specified width and height. 
     * This acts as an alias for {@link #dimension(int, int)}.
     * 
     * @param width the target width
     * @param height the target height
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog size(int width, int height) {
        super.setSize(width, height);
        return this;
    }

    /**
     * Resizes the dialog to the specified dimension.
     * 
     * @param d the target {@link Dimension}
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog size(Dimension d) {
        super.setSize(d);
        return this;
    }

    /**
     * Sets the minimum size of the dialog.
     * 
     * @param minimumSize the minimum allowed {@link Dimension}
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog minimumSize(Dimension minimumSize) {
        super.setMinimumSize(minimumSize);
        return this;
    }

    /**
     * Sets the maximum size of the dialog.
     * 
     * @param maximumSize the maximum allowed {@link Dimension}
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog maximumSize(Dimension maximumSize) {
        super.setMaximumSize(maximumSize);
        return this;
    }

    /**
     * Sets the preferred size of the dialog.
     * 
     * @param preferredSize the preferred {@link Dimension}
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog preferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
        return this;
    }

    /**
     * Sets the background color of the dialog.
     * 
     * @param bg the target {@link Color}
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog background(Color bg) {
        super.setBackground(bg);
        return this;
    }

    /**
     * Sets the opacity of the dialog. The window must be undecorated for this to take effect.
     * 
     * @param opacity the opacity float value (between 0.0 and 1.0)
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog opacity(float opacity) {
        super.setOpacity(opacity);
        return this;
    }

    /**
     * Sets the shape of the dialog window. The window must be undecorated for this to take effect.
     * 
     * @param shape the custom {@link Shape}
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog shape(Shape shape) {
        super.setShape(shape);
        return this;
    }

    /**
     * Sets a specific icon image for the dialog.
     * 
     * @param image the {@link Image} to display as the window icon
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog iconImage(Image image) {
        super.setIconImage(image);
        return this;
    }

    /**
     * Sets the sequence of icon images for the dialog to allow the OS to pick the best fit.
     * 
     * @param icons a {@link List} of {@link Image} icons
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog iconImages(List<? extends Image> icons) {
        super.setIconImages(icons);
        return this;
    }

    /**
     * Sets the internal name of the dialog component.
     * 
     * @param name the internal string name
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog name(String name) {
        super.setName(name);
        return this;
    }

    /**
     * Instantly makes the dialog fully transparent by stripping decorations and setting an alpha-zero background.
     * Note: This must be called before the dialog is made visible.
     * 
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog transparent() {
        super.setUndecorated(true);
        super.setBackground(new Color(0, 0, 0, 0));
        return this;
    }

    /**
     * Binds a window listener to the dialog to listen for window lifecycle events (e.g., closing, opened).
     * 
     * @param listener the {@link WindowListener} to attach
     * @return this {@code DDialog} instance for method chaining
     */
    public DDialog windowListener(WindowListener listener) {
        super.addWindowListener(listener);
        return this;
    }
}
