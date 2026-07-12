package com.vidlus.jarch.desk;

import java.io.File;
import javax.swing.JFileChooser;

/**
 * A UI component for editing and selecting a directory.
 * Provides a text field for the path and a button that opens a directory chooser dialog.
 */
public class DChangeDir extends DEditChange<File> {

    /**
     * Constructs a new DChangeDir component.
     */
    public DChangeDir() {
        super("*");
    }

    /**
     * Retrieves the selected directory as a File object based on the text field content.
     * 
     * @return the selected directory File, or null if the text is empty
     */
    @Override
    public File getValue() {
        var text = getField().getText();
        return text.isEmpty() ? null : new File(text);
    }

    /**
     * Sets the directory value, updating the text field with its absolute path.
     * 
     * @param value the directory File to set
     */
    @Override
    public void setValue(File value) {
        getField().setText(value == null ? "" : value.getAbsolutePath());
    }

    /**
     * Handles the action button press event.
     * Opens a directory chooser dialog (DFile configured for directories) to select a folder.
     */
    @Override
    protected void onActionPressed() {
        if (!editable()) return;
        DFile fileDialog = new DFile();
        fileDialog.selectionMode(JFileChooser.DIRECTORIES_ONLY);

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
     * Fluent setter for the directory value.
     * 
     * @param dir the directory File to set
     * @return this DChangeDir instance
     */
    public DChangeDir dir(File dir) {
        setValue(dir);
        return this;
    }
}
