package com.vidlus.jarch.desk;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * A modal dialog tailored for selecting a Date and Time using DEditTimestamp.
 * It provides "OK" and "Cancel" buttons to confirm or discard the selection.
 */
public class DDialogTimestamp extends DDialog {

    /** The internal timestamp editor component. */
    private final DEditTimestamp editor;
    
    /** The OK button used to confirm selection. */
    private final JButton okBtn;
    
    /** The Cancel button used to discard selection. */
    private final JButton cancelBtn;
    
    /** The resulting timestamp to return upon confirmation. */
    private Date result = null;

    /**
     * Constructs a modal timestamp selection dialog with no specific owner frame.
     */
    public DDialogTimestamp() {
        this(null);
    }

    /**
     * Constructs a modal timestamp selection dialog with the specified owner frame.
     * 
     * @param owner the Frame from which the dialog is displayed
     */
    public DDialogTimestamp(Frame owner) {
        super(owner, "Select Date and Time", true); // explicitly set as a modal dialog
        
        editor = new DEditTimestamp();
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(editor.comp(), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");
        
        okBtn.addActionListener(e -> {
            result = editor.getValue();
            visible(false);
            dispose();
        });
        
        cancelBtn.addActionListener(e -> {
            result = null;
            visible(false);
            dispose();
        });
        
        buttonPanel.add(okBtn);
        buttonPanel.add(cancelBtn);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Use super to not trigger the overridden method before object is ready
        super.body(mainPanel);
        super.packDialog();
        super.center();
    }
    
    /**
     * Retrieves the underlying DEditTimestamp component for fluent configuration (e.g., setting font, colors).
     * 
     * @return the internal DEditTimestamp instance
     */
    public DEditTimestamp editor() {
        return editor;
    }
    
    /**
     * Sets the initial timestamp selection of the dialog.
     * 
     * @param date the timestamp to select initially
     * @return this DDialogTimestamp instance for fluent chaining
     */
    public DDialogTimestamp value(Date date) {
        editor.setValue(date);
        return this;
    }

    /**
     * Sets the text for the OK button.
     * @param text the new text
     * @return this DDialogTimestamp instance for fluent chaining
     */
    public DDialogTimestamp okText(String text) {
        okBtn.setText(text);
        return this;
    }

    /**
     * Sets the text for the Cancel button.
     * @param text the new text
     * @return this DDialogTimestamp instance for fluent chaining
     */
    public DDialogTimestamp cancelText(String text) {
        cancelBtn.setText(text);
        return this;
    }
    
    /**
     * Displays the dialog and blocks execution until the user selects OK or Cancel.
     * 
     * @return the selected date if OK was pressed, or null if Cancel was pressed.
     */
    public Date showDialog() {
        result = null;
        visible(true); // Since it's modal, this blocks until visible(false) is called
        return result;
    }

    // --- DDialog Fluent Overrides ---

    /**
     * Sets the primary content pane body of the dialog.
     * @param body the container to set as the body
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp body(java.awt.Container body) { super.body(body); return this; }
    
    /**
     * Sets the title of the dialog.
     * @param title the title string
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp title(String title) { super.title(title); return this; }
    
    /**
     * Sets the dimension (size) of the dialog.
     * @param width the width in pixels
     * @param height the height in pixels
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp dimension(int width, int height) { super.dimension(width, height); return this; }
    
    /**
     * Shows or hides the dialog.
     * @param visible true to show, false to hide
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp visible(boolean visible) { super.visible(visible); return this; }
    
    /**
     * Specifies whether this dialog should be resizable by the user.
     * @param resizable true to allow resizing, false to prevent it
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp resizable(boolean resizable) { super.resizable(resizable); return this; }
    
    /**
     * Specifies whether this dialog should be undecorated.
     * @param undecorated true to remove decorations, false to retain them
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp undecorated(boolean undecorated) { super.undecorated(undecorated); return this; }
    
    /**
     * Specifies whether this dialog should remain always on top of other windows.
     * @param alwaysOnTop true to keep the dialog on top, false otherwise
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp alwaysOnTop(boolean alwaysOnTop) { super.alwaysOnTop(alwaysOnTop); return this; }
    
    /**
     * Specifies whether this dialog should be modal.
     * @param modal true to enforce modality, false otherwise
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp modal(boolean modal) { super.modal(modal); return this; }
    
    /**
     * Sets the specific modality type of the dialog.
     * @param type the ModalityType to apply
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp modalityType(java.awt.Dialog.ModalityType type) { super.modalityType(type); return this; }
    
    /**
     * Sets the operation that will happen by default when the user initiates a close.
     * @param operation the close operation constant
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp defaultCloseOperation(int operation) { super.defaultCloseOperation(operation); return this; }
    
    /**
     * Causes the dialog to be sized to fit the preferred size of its subcomponents.
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp packDialog() { super.packDialog(); return this; }
    
    /**
     * Moves the dialog to the specified coordinates.
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp location(int x, int y) { super.location(x, y); return this; }
    
    /**
     * Moves the dialog to the specified point.
     * @param p the Point specifying the new location
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp location(java.awt.Point p) { super.location(p); return this; }
    
    /**
     * Positions the dialog relative to a specified component.
     * @param c the Component in relation to which the dialog's location is determined
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp locationRelativeTo(java.awt.Component c) { super.locationRelativeTo(c); return this; }
    
    /**
     * Centers the dialog on the screen.
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp center() { super.center(); return this; }
    
    /**
     * Moves and resizes the dialog to fit within the specified bounding box.
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param width the target width
     * @param height the target height
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp bounds(int x, int y, int width, int height) { super.bounds(x, y, width, height); return this; }
    
    /**
     * Moves and resizes the dialog to match the specified rectangle bounds.
     * @param r the Rectangle specifying the bounds
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp bounds(java.awt.Rectangle r) { super.bounds(r); return this; }
    
    /**
     * Resizes the dialog to the specified width and height.
     * @param width the target width
     * @param height the target height
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp size(int width, int height) { super.size(width, height); return this; }
    
    /**
     * Resizes the dialog to the specified dimension.
     * @param d the target Dimension
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp size(java.awt.Dimension d) { super.size(d); return this; }
    
    /**
     * Sets the minimum size of the dialog.
     * @param minimumSize the minimum allowed Dimension
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp minimumSize(java.awt.Dimension minimumSize) { super.minimumSize(minimumSize); return this; }
    
    /**
     * Sets the maximum size of the dialog.
     * @param maximumSize the maximum allowed Dimension
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp maximumSize(java.awt.Dimension maximumSize) { super.maximumSize(maximumSize); return this; }
    
    /**
     * Sets the preferred size of the dialog.
     * @param preferredSize the preferred Dimension
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp preferredSize(java.awt.Dimension preferredSize) { super.preferredSize(preferredSize); return this; }
    
    /**
     * Sets the background color of the dialog.
     * @param bg the target Color
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp background(java.awt.Color bg) { super.background(bg); return this; }
    
    /**
     * Sets the opacity of the dialog.
     * @param opacity the opacity float value (between 0.0 and 1.0)
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp opacity(float opacity) { super.opacity(opacity); return this; }
    
    /**
     * Sets the shape of the dialog window.
     * @param shape the custom Shape
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp shape(java.awt.Shape shape) { super.shape(shape); return this; }
    
    /**
     * Sets a specific icon image for the dialog.
     * @param image the Image to display as the window icon
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp iconImage(java.awt.Image image) { super.iconImage(image); return this; }
    
    /**
     * Sets the sequence of icon images for the dialog to allow the OS to pick the best fit.
     * @param icons a List of Image icons
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp iconImages(java.util.List<? extends java.awt.Image> icons) { super.iconImages(icons); return this; }
    
    /**
     * Sets the internal name of the dialog component.
     * @param name the internal string name
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp name(String name) { super.name(name); return this; }
    
    /**
     * Instantly makes the dialog fully transparent by stripping decorations and setting an alpha-zero background.
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp transparent() { super.transparent(); return this; }
    
    /**
     * Binds a window listener to the dialog to listen for window lifecycle events.
     * @param listener the WindowListener to attach
     * @return this DDialogTimestamp instance for fluent chaining
     */
    @Override public DDialogTimestamp windowListener(java.awt.event.WindowListener listener) { super.windowListener(listener); return this; }
}
