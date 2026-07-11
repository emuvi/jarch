package com.vidlus.jarch.desk;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * A fluent API wrapper for JFileChooser extending DEdit for inline file selection fields.
 */
public class DEditFile extends DEdit<File> {

    public DEditFile() {
        super(new JFileChooser());
    }

    public DEditFile(String currentDirectoryPath) {
        super(new JFileChooser(currentDirectoryPath));
    }

    public DEditFile(File currentDirectory) {
        super(new JFileChooser(currentDirectory));
    }

    @Override
    public JFileChooser comp() {
        return (JFileChooser) super.comp();
    }

    @Override
    public File getValue() {
        return comp().getSelectedFile();
    }

    @Override
    public void setValue(File value) {
        comp().setSelectedFile(value);
    }

    /**
     * Sets the current directory.
     * 
     * @param dir the current directory to point to
     * @return This DEditFile instance.
     */
    public DEditFile directory(File dir) {
        comp().setCurrentDirectory(dir);
        return this;
    }

    /**
     * Sets the current directory using a path string.
     * 
     * @param path the path of the current directory to point to
     * @return This DEditFile instance.
     */
    public DEditFile directory(String path) {
        comp().setCurrentDirectory(new File(path));
        return this;
    }

    /**
     * Sets the JFileChooser to allow the user to just select files, just select directories, or select both files and directories.
     * 
     * @param mode the type of files to be displayed (FILES_ONLY, DIRECTORIES_ONLY, FILES_AND_DIRECTORIES)
     * @return This DEditFile instance.
     */
    public DEditFile selectionMode(int mode) {
        comp().setFileSelectionMode(mode);
        return this;
    }

    /**
     * Sets the file chooser to allow multiple file selections.
     * 
     * @param b true if multiple files may be selected
     * @return This DEditFile instance.
     */
    public DEditFile multiSelection(boolean b) {
        comp().setMultiSelectionEnabled(b);
        return this;
    }

    /**
     * Sets the current file filter.
     * 
     * @param filter the new current file filter
     * @return This DEditFile instance.
     */
    public DEditFile filter(FileFilter filter) {
        comp().setFileFilter(filter);
        return this;
    }

    /**
     * Adds a filter to the list of user choosable file filters.
     * 
     * @param filter the FileFilter to add
     * @return This DEditFile instance.
     */
    public DEditFile addFilter(FileFilter filter) {
        comp().addChoosableFileFilter(filter);
        return this;
    }

    /**
     * Determines whether the AcceptAll FileFilter is used as an available choice in the choosable filter list.
     * 
     * @param b true to include the AcceptAll filter
     * @return This DEditFile instance.
     */
    public DEditFile acceptAll(boolean b) {
        comp().setAcceptAllFileFilterUsed(b);
        return this;
    }

    /**
     * Sets the string that goes in the file chooser's title bar.
     * 
     * @param dialogTitle the new String for the title bar
     * @return This DEditFile instance.
     */
    public DEditFile title(String dialogTitle) {
        comp().setDialogTitle(dialogTitle);
        return this;
    }

    /**
     * Sets file hiding on or off.
     * 
     * @param b the boolean value that determines whether hidden files are shown
     * @return This DEditFile instance.
     */
    public DEditFile hiddenFiles(boolean b) {
        comp().setFileHidingEnabled(b);
        return this;
    }

    /**
     * Pops up an "Open File" file chooser dialog.
     * 
     * @param parent the parent component of the dialog
     * @return the return state of the file chooser on popdown: APPROVE_OPTION or CANCEL_OPTION
     */
    public int showOpen(Component parent) {
        return comp().showOpenDialog(parent);
    }

    /**
     * Pops up a "Save File" file chooser dialog.
     * 
     * @param parent the parent component of the dialog
     * @return the return state of the file chooser on popdown: APPROVE_OPTION or CANCEL_OPTION
     */
    public int showSave(Component parent) {
        return comp().showSaveDialog(parent);
    }

    /**
     * Pops a custom file chooser dialog with a custom approve button.
     * 
     * @param parent the parent component of the dialog
     * @param approveButtonText the text of the ApproveButton
     * @return the return state of the file chooser on popdown
     */
    public int showDialog(Component parent, String approveButtonText) {
        return comp().showDialog(parent, approveButtonText);
    }

    /**
     * Returns the selected file.
     * 
     * @return the selected file
     */
    public File file() {
        return comp().getSelectedFile();
    }

    /**
     * Returns a list of selected files if the file chooser is set to allow multiple selection.
     * 
     * @return an array of selected Files
     */
    public File[] files() {
        return comp().getSelectedFiles();
    }
}
