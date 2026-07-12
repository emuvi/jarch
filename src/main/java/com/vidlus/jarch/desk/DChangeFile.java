package com.vidlus.jarch.desk;

import java.io.File;
import javax.swing.JFileChooser;

/**
 * A UI component for editing and selecting a file.
 * Provides a text field for the path and a button that opens a file chooser dialog.
 */
public class DChangeFile extends DEditChange<File> {

    private javax.swing.filechooser.FileNameExtensionFilter filter;

    /**
     * Constructs a new DChangeFile component.
     */
    public DChangeFile() {
        super("*");
    }

    /**
     * Retrieves the selected file as a File object based on the text field content.
     * 
     * @return the selected File, or null if the text is empty
     */
    @Override
    public File getValue() {
        var text = getField().getText();
        return text.isEmpty() ? null : new File(text);
    }

    /**
     * Sets the file value, updating the text field with its absolute path.
     * 
     * @param value the File to set
     */
    @Override
    public void setValue(File value) {
        getField().setText(value == null ? "" : value.getAbsolutePath());
    }

    /**
     * Handles the action button press event.
     * Opens a file chooser dialog (DFile) configured with the set filters to select a file.
     */
    @Override
    protected void onActionPressed() {
        if (!editable()) return;
        DFile fileDialog = new DFile();
        if (filter != null) {
            fileDialog.filter(filter);
        }
        File current = getValue();
        if (current != null && current.exists()) {
            if (current.isDirectory()) {
                fileDialog.directory(current);
            } else {
                fileDialog.directory(current.getParentFile());
            }
        }

        if (fileDialog.showOpen(comp()) == JFileChooser.APPROVE_OPTION) {
            setValue(fileDialog.file());
        }
    }

    /**
     * Fluent setter for the file value.
     * 
     * @param file the File to set
     * @return this DChangeFile instance
     */
    public DChangeFile file(File file) {
        setValue(file);
        return this;
    }

    /**
     * Fluent setter to add a file extension filter to the file chooser dialog.
     * 
     * @param description the description of the filter (e.g., "Images")
     * @param extensions one or more file extensions (e.g., "png", "jpg")
     * @return this DChangeFile instance
     */
    public DChangeFile filter(String description, String... extensions) {
        this.filter = new javax.swing.filechooser.FileNameExtensionFilter(description, extensions);
        return this;
    }
}
