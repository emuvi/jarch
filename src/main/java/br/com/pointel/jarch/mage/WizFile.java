package br.com.pointel.jarch.mage;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Utility class for file and path manipulation, including file dialogs.
 */
public class WizFile {

    /**
     * Cleans a file path by fixing separators and resolving it to an absolute path.
     *
     * @param path The path to clean.
     * @return The cleaned absolute path.
     */
    public static String clean(String path) {
        return WizFile.getAbsolute(WizFile.fixSeparators(path));
    }

    /**
     * Resolves a path to its absolute form, handling relative prefixes like ".", "..", and "~".
     *
     * @param path The path to resolve.
     * @return The absolute path.
     */
    public static String getAbsolute(String path) {
        if (path == null || path.isEmpty()) {
            return path;
        }
        final var samePrefix = "." + File.separator;
        final var upperPrefix = ".." + File.separator;
        final var homePrefix = "~" + File.separator;
        if (path.startsWith(samePrefix) || path.startsWith(upperPrefix)) {
            var workingDir = new File(System.getProperty("user.dir"));
            while (path.startsWith(samePrefix) || path.startsWith(upperPrefix)) {
                if (path.startsWith(samePrefix)) {
                    path = path.substring(samePrefix.length());
                } else {
                    workingDir = workingDir.getParentFile();
                    path = path.substring(upperPrefix.length());
                }
            }
            return WizFile.sum(workingDir.getAbsolutePath(), path);
        }
        if (path.startsWith(homePrefix)) {
            var homeDir = new File(System.getProperty("user.home"));
            return WizFile.sum(homeDir.getAbsolutePath(), path);
        }
        return path;
    }

    /**
     * Fixes file separators in a path string to match the system's separator.
     *
     * @param path The path to fix.
     * @return The path with fixed separators.
     */
    public static String fixSeparators(String path) {
        if (WizString.isEmpty(path)) {
            return path;
        }
        if (path.contains("\\") && "/".equals(File.separator)) {
            path = path.replaceAll("\\", "/");
        } else if (path.contains("/") && "\\".equals(File.separator)) {
            path = path.replaceAll("/", "\\");
        }
        return path;
    }

    /**
     * Joins a parent path and a child path, handling separators.
     *
     * @param path The parent path.
     * @param child The child path.
     * @return The joined path.
     */
    public static String sum(String path, String child) {
        if (!WizString.isNotEmpty(path) || !WizString.isNotEmpty(child)) {
            return WizString.getFirstNonEmpty(path, child);
        }
        if (path.endsWith(File.separator) && child.startsWith(File.separator)) {
            return path + child.substring(File.separator.length());
        } else if (path.endsWith(File.separator) || child.startsWith(File.separator)) {
            return path + child;
        } else {
            return path + File.separator + child;
        }
    }

    /**
     * Joins a parent path and multiple child path segments.
     *
     * @param path The parent path.
     * @param children The child path segments.
     * @return The joined path.
     */
    public static String sum(String path, String... children) {
        var result = path;
        if (children != null) {
            for (String filho : children) {
                result = WizFile.sum(result, filho);
            }
        }
        return result;
    }

    /**
     * Joins a parent File and multiple child path segments.
     *
     * @param path The parent File.
     * @param children The child path segments.
     * @return The resulting File.
     */
    public static File sum(File path, String... children) {
        var result = path;
        if (result != null && children != null) {
            for (String child : children) {
                result = new File(result, child);
            }
        }
        return result;
    }

    /**
     * Finds a parent directory of a file that matches a specific name.
     *
     * @param path The starting file.
     * @param withName The name of the parent directory to find.
     * @return The matching parent File, or null if not found.
     */
    public static File getParent(File path, String withName) {
        File result = null;
        if (path != null) {
            var actual = path.getParentFile();
            while (!withName.equals(actual.getName())) {
                actual = actual.getParentFile();
                if (actual == null) {
                    break;
                }
            }
            result = actual;
        }
        return result;
    }

    /**
     * Gets the parent path string of a given path.
     *
     * @param path The path string.
     * @return The parent path string, or the original path if no separator is found.
     */
    public static String getParent(String path) {
        if (path.contains(File.separator)) {
            return path.substring(0, path.lastIndexOf(File.separator));
        }
        return path;
    }

    /**
     * Finds a root directory (parent) with a specific name starting from a given path.
     *
     * @param withName The name of the root to find.
     * @param fromPath The starting path.
     * @return The matching parent File, or null if not found.
     */
    public static File getRoot(String withName, File fromPath) {
        if (fromPath == null) {
            return null;
        }
        var result = fromPath.getParentFile();
        while (result != null && !Objects.equals(withName, result.getName())) {
            result = result.getParentFile();
        }
        return result;
    }

    /**
     * Extracts the file name from a path string.
     *
     * @param path The path string.
     * @return The file name.
     */
    public static String getName(String path) {
        if (path == null) {
            return null;
        }
        final var sep = path.lastIndexOf(File.separator);
        if (sep == -1) {
            return path;
        }
        return path.substring(sep + 1);
    }

    /**
     * Extracts the base name (file name without extension) from a path string.
     *
     * @param path The path string.
     * @return The base name.
     */
    public static String getBaseName(String path) {
        if (path == null) {
            return null;
        }
        path = WizFile.getName(path);
        final var dot = path.lastIndexOf(".");
        if (dot > -1) {
            return path.substring(0, dot);
        }
        return path;
    }

    /**
     * Extracts the extension from a path string.
     *
     * @param path The path string.
     * @return The extension (including the dot), or an empty string if none.
     */
    public static String getExtension(String path) {
        if (path == null) {
            return null;
        }
        final var dot = path.lastIndexOf(".");
        if (dot > -1) {
            return path.substring(dot);
        }
        return "";
    }

    /**
     * Changes the extension of a path string.
     *
     * @param path The path string.
     * @param newExtension The new extension (with or without dot).
     * @return The path with the new extension.
     */
    public static String changeExtension(String path, String newExtension) {
        if (path == null) {
            return null;
        }
        if (newExtension == null) {
            newExtension = "";
        }
        if (!newExtension.isEmpty() && !newExtension.startsWith(".")) {
            newExtension = "." + newExtension;
        }
        final var dot = path.lastIndexOf(".");
        if (dot > -1) {
            return path.substring(0, dot) + newExtension;
        }
        return path + newExtension;
    }

    /**
     * Appends characters to the base name of a path (before the extension).
     *
     * @param path The path string.
     * @param chars The characters to append.
     * @return The modified path.
     */
    public static String addOnBaseName(String path, String chars) {
        if (path == null) {
            return chars;
        }
        if (WizString.isEmpty(chars)) {
            return path;
        }
        var dotIndex = path.lastIndexOf(".");
        if (dotIndex > -1) {
            return path.substring(0, dotIndex) + chars + path.substring(dotIndex);
        }
        return path + chars;
    }

    /**
     * Appends characters to the base name of a File (before the extension).
     *
     * @param file The File.
     * @param chars The characters to append.
     * @return The modified File.
     */
    public static File addOnBaseName(File file, String chars) {
        return new File(WizFile.addOnBaseName(file.getAbsolutePath(), chars));
    }

    /**
     * Returns a File object that does not point to an existing file.
     * If the given path exists, it appends a counter to the base name until a unique path is found.
     *
     * @param path The desired File path.
     * @return A unique File path.
     */
    public static File notOverride(File path) {
        if ((path == null) || !path.exists()) {
            return path;
        }
        File result = null;
        var attempt = 2;
        do {
            result = new File(WizFile.addOnBaseName(path.getAbsolutePath(), " (" + attempt + ")"));
            attempt++;
        } while (result.exists());
        return result;
    }

    /**
     * Creates a JFileChooser with a specific description and extensions filter.
     *
     * @param description The description of the file type.
     * @param extensions The allowed extensions.
     * @return The configured JFileChooser.
     */
    public static JFileChooser chooser(String description, String... extensions) {
        var chooser = new JFileChooser();
        chooser.setDialogTitle("Select");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(extensions == null);
        FileFilter filter = new FileNameExtensionFilter(description, extensions);
        chooser.setFileFilter(filter);
        return chooser;
    }

    /**
     * Opens a dialog to select a file or directory.
     *
     * @return The selected File, or null if canceled.
     */
    public static File open() {
        return WizFile.open(null);
    }

    /**
     * Opens a dialog to select a file or directory, starting from a selected file.
     *
     * @param selected The initially selected file.
     * @return The selected File, or null if canceled.
     */
    public static File open(File selected) {
        return WizFile.open(selected, null);
    }

    /**
     * Opens a dialog to select a file or directory with a filter.
     *
     * @param description The filter description.
     * @param extensions The allowed extensions.
     * @return The selected File, or null if canceled.
     */
    public static File open(String description, String... extensions) {
        return WizFile.open(null, description, extensions);
    }

    /**
     * Opens a dialog to select a file or directory with a filter and initial selection.
     *
     * @param selected The initially selected file.
     * @param description The filter description.
     * @param extensions The allowed extensions.
     * @return The selected File, or null if canceled.
     */
    public static File open(File selected, String description, String... extensions) {
        File result = null;
        if (WizGUI.isStarted()) {
            var chooser = new JFileChooser();
            chooser.setDialogTitle("Select File or Directory");
            chooser.setMultiSelectionEnabled(false);
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            chooser.setAcceptAllFileFilterUsed(extensions == null);
            if (selected != null) {
                chooser.setSelectedFile(selected);
            }
            if (description != null & extensions != null) {
                FileFilter filter = new FileNameExtensionFilter(description, extensions);
                chooser.setFileFilter(filter);
            }
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                result = chooser.getSelectedFile();
            }
        } else {
            result = WizFile.selectFileTerminal(FileTerminalAction.OPEN,
                            FileTerminalNature.BOTH,
                            selected, description, extensions);
        }
        return result;
    }

    /**
     * Opens a dialog to select a file.
     *
     * @return The selected File, or null if canceled.
     */
    public static File openFile() {
        return WizFile.openFile(null);
    }

    /**
     * Opens a dialog to select a file, starting from a selected file.
     *
     * @param selected The initially selected file.
     * @return The selected File, or null if canceled.
     */
    public static File openFile(File selected) {
        return WizFile.openFile(selected, null);
    }

    /**
     * Opens a dialog to select a file with a filter.
     *
     * @param description The filter description.
     * @param extensions The allowed extensions.
     * @return The selected File, or null if canceled.
     */
    public static File openFile(String description, String... extensions) {
        return WizFile.openFile(null, description, extensions);
    }

    /**
     * Opens a dialog to select a file with a filter and initial selection.
     *
     * @param selected The initially selected file.
     * @param description The filter description.
     * @param extensions The allowed extensions.
     * @return The selected File, or null if canceled.
     */
    public static File openFile(File selected, String description, String... extensions) {
        File result = null;
        if (WizGUI.isStarted()) {
            var chooser = new JFileChooser();
            chooser.setDialogTitle("Select File");
            chooser.setMultiSelectionEnabled(false);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setAcceptAllFileFilterUsed(extensions == null);
            if (selected != null) {
                chooser.setSelectedFile(selected);
            }
            if (description != null & extensions != null) {
                FileFilter filter = new FileNameExtensionFilter(description, extensions);
                chooser.setFileFilter(filter);
            }
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                result = chooser.getSelectedFile();
            }
        } else {
            result = WizFile.selectFileTerminal(FileTerminalAction.OPEN,
                            FileTerminalNature.FILE,
                            selected, description, extensions);
        }
        return result;
    }

    /**
     * Opens a dialog to select a directory.
     *
     * @return The selected directory, or null if canceled.
     */
    public static File openDir() {
        return WizFile.openDir(null);
    }

    /**
     * Opens a dialog to select a directory, starting from a selected file.
     *
     * @param selected The initially selected file.
     * @return The selected directory, or null if canceled.
     */
    public static File openDir(File selected) {
        return WizFile.openDir(selected, null);
    }

    /**
     * Opens a dialog to select a directory with a filter.
     *
     * @param description The filter description.
     * @param extensions The allowed extensions.
     * @return The selected directory, or null if canceled.
     */
    public static File openDir(String description, String... extensions) {
        return WizFile.openDir(null, description, extensions);
    }

    /**
     * Opens a dialog to select a directory with a filter and initial selection.
     *
     * @param selected The initially selected file.
     * @param description The filter description.
     * @param extensions The allowed extensions.
     * @return The selected directory, or null if canceled.
     */
    public static File openDir(File selected, String description, String... extensions) {
        File result = null;
        if (WizGUI.isStarted()) {
            var chooser = new JFileChooser();
            chooser.setDialogTitle("Select Directory");
            chooser.setMultiSelectionEnabled(false);
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(extensions == null);
            if (selected != null) {
                chooser.setSelectedFile(selected);
            }
            if (description != null & extensions != null) {
                FileFilter filter = new FileNameExtensionFilter(description, extensions);
                chooser.setFileFilter(filter);
            }
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                result = chooser.getSelectedFile();
            }
        } else {
            result = WizFile.selectFileTerminal(FileTerminalAction.OPEN,
                            FileTerminalNature.DIRECTORY,
                            selected, description, extensions);
        }
        return result;
    }

    /**
     * Opens a dialog to select multiple files or directories.
     *
     * @return The selected Files, or null if canceled.
     */
    public static File[] openMany() {
        return WizFile.openMany(null);
    }

    /**
     * Opens a dialog to select multiple files or directories, starting from selected files.
     *
     * @param selected The initially selected files.
     * @return The selected Files, or null if canceled.
     */
    public static File[] openMany(File[] selected) {
        return WizFile.openMany(selected, null);
    }

    /**
     * Opens a dialog to select multiple files or directories with a filter.
     *
     * @param description The filter description.
     * @param extensions The allowed extensions.
     * @return The selected Files, or null if canceled.
     */
    public static File[] openMany(String description, String... extensions) {
        return WizFile.openMany(null, description, extensions);
    }

    /**
     * Opens a dialog to select multiple files or directories with a filter and initial selection.
     *
     * @param selected The initially selected files.
     * @param description The filter description.
     * @param extensions The allowed extensions.
     * @return The selected Files, or null if canceled.
     */
    public static File[] openMany(File[] selected, String description,
                    String... extensions) {
        File[] result = null;
        if (WizGUI.isStarted()) {
            var chooser = new JFileChooser();
            chooser.setDialogTitle("Select Many Files or Directories");
            chooser.setMultiSelectionEnabled(true);
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            chooser.setAcceptAllFileFilterUsed(extensions == null);
            if (selected != null) {
                chooser.setSelectedFiles(selected);
            }
            if (description != null & extensions != null) {
                FileFilter filter = new FileNameExtensionFilter(description, extensions);
                chooser.setFileFilter(filter);
            }
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                result = chooser.getSelectedFiles();
            }
        } else {
            result = WizFile.selectFileTerminalMany(true, FileTerminalAction.OPEN,
                            FileTerminalNature.BOTH, selected, description, extensions);
        }
        return result;
    }

    /**
     * Opens a dialog to select multiple files.
     *
     * @return The selected Files, or null if canceled.
     */
    public static File[] openFileMany() {
        return WizFile.openFileMany(null);
    }

    /**
     * Opens a dialog to select multiple files, starting from selected files.
     *
     * @param selected The initially selected files.
     * @return The selected Files, or null if canceled.
     */
    public static File[] openFileMany(File[] selected) {
        return WizFile.openFileMany(selected, null);
    }

    /**
     * Opens a dialog to select multiple files with a filter.
     *
     * @param description The filter description.
     * @param extensions The allowed extensions.
     * @return The selected Files, or null if canceled.
     */
    public static File[] openFileMany(String description, String... extensions) {
        return WizFile.openFileMany(null, description, extensions);
    }

    /**
     * Opens a dialog to select multiple files with a filter and initial selection.
     *
     * @param selected The initially selected files.
     * @param description The filter description.
     * @param extensions The allowed extensions.
     * @return The selected Files, or null if canceled.
     */
    public static File[] openFileMany(File[] selected, String description,
                    String... extensions) {
        File[] result = null;
        if (WizGUI.isStarted()) {
            var chooser = new JFileChooser();
            chooser.setDialogTitle("Select Many Files");
            chooser.setMultiSelectionEnabled(true);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setAcceptAllFileFilterUsed(extensions == null);
            if (selected != null) {
                chooser.setSelectedFiles(selected);
            }
            if (description != null & extensions != null) {
                FileFilter filter = new FileNameExtensionFilter(description, extensions);
                chooser.setFileFilter(filter);
            }
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                result = chooser.getSelectedFiles();
            }
        } else {
            result = WizFile.selectFileTerminalMany(true, FileTerminalAction.OPEN,
                            FileTerminalNature.FILE, selected, description, extensions);
        }
        return result;
    }

    /**
     * Opens a dialog to select multiple directories.
     *
     * @return The selected directories, or null if canceled.
     */
    public static File[] openDirMany() {
        return WizFile.openDirMany(null);
    }

    /**
     * Opens a dialog to select multiple directories, starting from selected files.
     *
     * @param selected The initially selected files.
     * @return The selected directories, or null if canceled.
     */
    public static File[] openDirMany(File[] selected) {
        return WizFile.openDirMany(selected, null);
    }

    /**
     * Opens a dialog to select multiple directories with a filter.
     *
     * @param description The filter description.
     * @param extensions The allowed extensions.
     * @return The selected directories, or null if canceled.
     */
    public static File[] openDirMany(String description, String... extensions) {
        return WizFile.openDirMany(null, description, extensions);
    }

    /**
     * Opens a dialog to select multiple directories with a filter and initial selection.
     *
     * @param selected The initially selected files.
     * @param description The filter description.
     * @param extensions The allowed extensions.
     * @return The selected directories, or null if canceled.
     */
    public static File[] openDirMany(File[] selected, String description,
                    String... extensions) {
        File[] result = null;
        if (WizGUI.isStarted()) {
            var chooser = new JFileChooser();
            chooser.setDialogTitle("Select Many Directories");
            chooser.setMultiSelectionEnabled(true);
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(extensions == null);
            if (selected != null) {
                chooser.setSelectedFiles(selected);
            }
            if (description != null & extensions != null) {
                FileFilter filter = new FileNameExtensionFilter(description, extensions);
                chooser.setFileFilter(filter);
            }
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                result = chooser.getSelectedFiles();
            }
        } else {
            result = WizFile.selectFileTerminalMany(true, FileTerminalAction.OPEN,
                            FileTerminalNature.DIRECTORY, selected, description,
                            extensions);
        }
        return result;
    }

    /**
     * Opens a dialog to save a file or directory.
     *
     * @return The selected File, or null if canceled.
     */
    public static File save() {
        return WizFile.save(null);
    }

    /**
     * Opens a dialog to save a file or directory, starting from a selected file.
     *
     * @param selected The initially selected file.
     * @return The selected File, or null if canceled.
     */
    public static File save(File selected) {
        return WizFile.save(selected, null);
    }

    /**
     * Opens a dialog to save a file or directory with a filter.
     *
     * @param description The filter description.
     * @param extensions The allowed extensions.
     * @return The selected File, or null if canceled.
     */
    public static File save(String description, String... extensions) {
        return WizFile.save(null, description, extensions);
    }

    /**
     * Opens a dialog to save a file or directory with a filter and initial selection.
     *
     * @param selected The initially selected file.
     * @param description The filter description.
     * @param extensions The allowed extensions.
     * @return The selected File, or null if canceled.
     */
    public static File save(File selected, String description, String... extensions) {
        File result = null;
        if (WizGUI.isStarted()) {
            var chooser = new JFileChooser();
            chooser.setDialogTitle("Save File or Directory");
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            chooser.setAcceptAllFileFilterUsed(extensions == null);
            if (selected != null) {
                chooser.setSelectedFile(selected);
            }
            if (description != null & extensions != null) {
                FileFilter filter = new FileNameExtensionFilter(description, extensions);
                chooser.setFileFilter(filter);
            }
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                result = chooser.getSelectedFile();
                var ext = WizFile.getExtension(result.getAbsolutePath());
                if (extensions != null) {
                    if (!WizArray.has(ext, extensions)) {
                        result = new File(result.getAbsolutePath() + "." + extensions[0]);
                    }
                }
            }
        } else {
            result = WizFile.selectFileTerminal(FileTerminalAction.SAVE,
                            FileTerminalNature.BOTH,
                            selected, description, extensions);
        }
        return result;
    }

    /**
     * Opens a dialog to save a file.
     *
     * @return The selected File, or null if canceled.
     */
    public static File saveFile() {
        return WizFile.saveFile(null);
    }

    /**
     * Opens a dialog to save a file, starting from a selected file.
     *
     * @param selected The initially selected file.
     * @return The selected File, or null if canceled.
     */
    public static File saveFile(File selected) {
        return WizFile.saveFile(selected, null);
    }

    /**
     * Opens a dialog to save a file with a filter.
     *
     * @param description The filter description.
     * @param extensions The allowed extensions.
     * @return The selected File, or null if canceled.
     */
    public static File saveFile(String description, String... extensions) {
        return WizFile.saveFile(null, description, extensions);
    }

    /**
     * Opens a dialog to save a file with a filter and initial selection.
     *
     * @param selected The initially selected file.
     * @param description The filter description.
     * @param extensions The allowed extensions.
     * @return The selected File, or null if canceled.
     */
    public static File saveFile(File selected, String description, String... extensions) {
        File result = null;
        if (WizGUI.isStarted()) {
            var chooser = new JFileChooser();
            chooser.setDialogTitle("Save File");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setAcceptAllFileFilterUsed(extensions == null);
            if (selected != null) {
                chooser.setSelectedFile(selected);
            }
            if (description != null & extensions != null) {
                FileFilter filter = new FileNameExtensionFilter(description, extensions);
                chooser.setFileFilter(filter);
            }
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                result = chooser.getSelectedFile();
                if (extensions != null) {
                    if (extensions.length > 0) {
                        var mustInclude = true;
                        if (result.getName().contains(".")) {
                            var ext = WizFile.getExtension(result.getName());
                            if (WizArray.has(ext, extensions)) {
                                mustInclude = false;
                            }
                        }
                        if (mustInclude) {
                            result = new File(result.getAbsolutePath() + "."
                                            + extensions[0]);
                        }
                    }
                }
            }
        } else {
            result = WizFile.selectFileTerminal(FileTerminalAction.SAVE,
                            FileTerminalNature.FILE,
                            selected, description, extensions);
        }
        return result;
    }

    /**
     * Opens a dialog to save a directory.
     *
     * @return The selected directory, or null if canceled.
     */
    public static File saveDir() {
        return WizFile.saveDir(null);
    }

    /**
     * Opens a dialog to save a directory, starting from a selected file.
     *
     * @param selected The initially selected file.
     * @return The selected directory, or null if canceled.
     */
    public static File saveDir(File selected) {
        return WizFile.saveDir(selected, null);
    }

    /**
     * Opens a dialog to save a directory with a filter.
     *
     * @param description The filter description.
     * @param extensions The allowed extensions.
     * @return The selected directory, or null if canceled.
     */
    public static File saveDir(String description, String... extensions) {
        return WizFile.saveDir(null, description, extensions);
    }

    /**
     * Opens a dialog to save a directory with a filter and initial selection.
     *
     * @param selected The initially selected file.
     * @param description The filter description.
     * @param extensions The allowed extensions.
     * @return The selected directory, or null if canceled.
     */
    public static File saveDir(File selected, String description, String... extensions) {
        File result = null;
        if (WizGUI.isStarted()) {
            var chooser = new JFileChooser();
            chooser.setDialogTitle("Save Directory");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(extensions == null);
            if (selected != null) {
                chooser.setSelectedFile(selected);
            }
            if (description != null & extensions != null) {
                FileFilter filter = new FileNameExtensionFilter(description, extensions);
                chooser.setFileFilter(filter);
            }
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                result = chooser.getSelectedFile();
                var ext = WizFile.getExtension(result.getAbsolutePath());
                if (extensions != null) {
                    if (!WizArray.has(ext, extensions)) {
                        result = new File(result.getAbsolutePath() + "." + extensions[0]);
                    }
                }
            }
        } else {
            result = WizFile.selectFileTerminal(FileTerminalAction.SAVE,
                            FileTerminalNature.DIRECTORY,
                            selected, description, extensions);
        }
        return result;
    }

    private static enum FileTerminalAction {
        OPEN, SAVE
    }

    private static enum FileTerminalNature {
        BOTH, DIRECTORY, FILE,
    }

    private static File selectFileTerminal(FileTerminalAction action,
                    FileTerminalNature nature, File selected, String description,
                    String... extensions) {
        return WizFile.selectFileTerminalMany(false, action, nature, new File[] {
                        selected},
                        description, extensions)[0];
    }

    private static File[] selectFileTerminalMany(boolean askForMany,
                    FileTerminalAction action, FileTerminalNature nature, File[] selected,
                    String description, String... extensions) {
        System.out.println("--- File Selection (" + action + " - " + nature + ") ---");
        if (description != null && !description.isEmpty()) {
            System.out.println("Description: " + description);
        }
        if (extensions != null && extensions.length > 0) {
            System.out.print("Extensions: ");
            for (String ext : extensions) {
                System.out.print(ext + " ");
            }
            System.out.println();
        }
        if (selected != null && selected.length > 0) {
            System.out.println("Current selection:");
            for (File f : selected) {
                System.out.println(" - " + f.getAbsolutePath());
            }
        }
        var resultList = new ArrayList<File>();
        while (true) {
            var prompt = "Enter path" + (askForMany ? " (one per line, empty to finish)" : "") + ": ";
            var input = WizCLI.showInput(prompt);
            if (input == null || input.trim().isEmpty()) {
                if (askForMany && !resultList.isEmpty()) {
                    break;
                }
                return null;
            }
            var file = new File(input);
            if (action == FileTerminalAction.OPEN) {
                if (!file.exists()) {
                    System.out.println("Error: File does not exist.");
                    continue;
                }
            }
            if (nature == FileTerminalNature.FILE && file.isDirectory()) {
                System.out.println("Error: Expected a file, got a directory.");
                continue;
            }
            if (nature == FileTerminalNature.DIRECTORY && file.isFile()) {
                System.out.println("Error: Expected a directory, got a file.");
                continue;
            }
            if (nature == FileTerminalNature.FILE && extensions != null && extensions.length > 0) {
                var extMatch = false;
                var name = file.getName();
                for (var ext : extensions) {
                    if (name.toLowerCase().endsWith("." + ext.toLowerCase())) {
                        extMatch = true;
                        break;
                    }
                }
                if (!extMatch) {
                    System.out.println("Error: File extension does not match allowed extensions.");
                    continue;
                }
            }
            resultList.add(file);
            if (!askForMany) {
                break;
            }
        }
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.toArray(new File[0]);
    }
}
