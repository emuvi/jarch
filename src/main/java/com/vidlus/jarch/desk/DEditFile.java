package com.vidlus.jarch.desk;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * A fluent API wrapper for JFileChooser extending DEdit for inline file selection fields.
 */
public class DEditFile extends DEdit<File> {

    /**
     * Constructs a new DEditFile with a default JFileChooser.
     */
    public DEditFile() {
        super(new JFileChooser());
    }

    /**
     * Constructs a new DEditFile pointing to the specified directory path.
     * 
     * @param currentDirectoryPath the initial directory path
     */
    public DEditFile(String currentDirectoryPath) {
        super(new JFileChooser(currentDirectoryPath));
    }

    /**
     * Constructs a new DEditFile pointing to the specified directory.
     * 
     * @param currentDirectory the initial directory
     */
    public DEditFile(File currentDirectory) {
        super(new JFileChooser(currentDirectory));
    }

    /**
     * Returns the underlying JFileChooser component.
     * 
     * @return the JFileChooser component
     */
    @Override
    public JFileChooser comp() {
        return (JFileChooser) super.comp();
    }

    /**
     * Gets the currently selected file.
     * 
     * @return the selected file
     */
    @Override
    public File getValue() {
        return comp().getSelectedFile();
    }

    /**
     * Sets the currently selected file.
     * 
     * @param value the file to select
     */
    @Override
    public void setValue(File value) {
        comp().setSelectedFile(value);
    }

    /**
     * Returns the current directory.
     * 
     * @return the current directory
     */
    public File directory() {
        return comp().getCurrentDirectory();
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
     * Returns the current selection mode.
     * 
     * @return the selection mode
     */
    public int selectionMode() {
        return comp().getFileSelectionMode();
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
     * Returns true if multiple files can be selected.
     * 
     * @return true if multiple files can be selected
     */
    public boolean multiSelection() {
        return comp().isMultiSelectionEnabled();
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
     * Returns the currently selected file filter.
     * 
     * @return the current file filter
     */
    public FileFilter filter() {
        return comp().getFileFilter();
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
     * Returns whether the AcceptAll FileFilter is used.
     * 
     * @return true if the AcceptAll FileFilter is used
     */
    public boolean acceptAll() {
        return comp().isAcceptAllFileFilterUsed();
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
     * Returns the string that goes in the file chooser's title bar.
     * 
     * @return the string from the title bar
     */
    public String title() {
        return comp().getDialogTitle();
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
     * Returns whether hidden files are shown.
     * 
     * @return true if hidden files are not shown
     */
    public boolean hiddenFiles() {
        return comp().isFileHidingEnabled();
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
     * Returns whether the control buttons (Approve, Cancel) are shown in the dialog.
     * 
     * @return true if control buttons are shown
     */
    public boolean controlButtonsAreShown() {
        return comp().getControlButtonsAreShown();
    }

    /**
     * Sets whether the control buttons (Approve, Cancel) are shown in the dialog.
     * 
     * @param b true to show control buttons
     * @return This DEditFile instance.
     */
    public DEditFile controlButtonsAreShown(boolean b) {
        comp().setControlButtonsAreShown(b);
        return this;
    }

    /**
     * Returns the type of this dialog.
     * 
     * @return the dialog type (e.g., JFileChooser.OPEN_DIALOG, JFileChooser.SAVE_DIALOG)
     */
    public int dialogType() {
        return comp().getDialogType();
    }

    /**
     * Sets the type of this dialog.
     * 
     * @param type the dialog type
     * @return This DEditFile instance.
     */
    public DEditFile dialogType(int type) {
        comp().setDialogType(type);
        return this;
    }

    /**
     * Returns whether drag and drop is enabled.
     * 
     * @return true if drag and drop is enabled
     */
    public boolean dragEnabled() {
        return comp().getDragEnabled();
    }

    /**
     * Sets whether drag and drop is enabled.
     * 
     * @param b true to enable drag and drop
     * @return This DEditFile instance.
     */
    public DEditFile dragEnabled(boolean b) {
        comp().setDragEnabled(b);
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
