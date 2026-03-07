package br.com.pointel.jarch.mage;

import java.io.File;

import javax.swing.filechooser.FileSystemView;

/**
 * Utility class for file system operations.
 */
public class WizFiles {

    /**
     * Retrieves the file system roots along with their system display names.
     *
     * @return An array of RootName records containing the root file and its display name.
     */
    public static RootName[] getRootsNamed() {
        FileSystemView fileSystemView = FileSystemView.getFileSystemView();
        File[] roots = File.listRoots();
        var result = new RootName[roots.length];
        for (int i = 0; i < roots.length; i++) {
            result[i] = new RootName(roots[i], fileSystemView.getSystemDisplayName(roots[i]));
        }
        return result;
    }

    /**
     * Recursively moves all files from a source directory to a destination directory.
     * If a file with the same name exists in the destination, it is renamed with an incremental counter.
     *
     * @param from The source directory.
     * @param to The destination directory.
     * @throws Exception If an error occurs during the move operation.
     */
    public static void moveAll(File from, File to) throws Exception {
        if (from == null || !from.exists() || !from.isDirectory()) {
            return;
        }
        if (to == null) {
            return;
        }
        if (!to.exists()) {
            to.mkdirs();
        }
        var files = from.listFiles();
        if (files != null) {
            for (var file : files) {
                if (file.isDirectory()) {
                    moveAll(file, to);
                } else {
                    if (file.getParentFile().getCanonicalPath().equals(to.getCanonicalPath())) {
                        continue;
                    }
                    var target = new File(to, file.getName());
                    if (target.exists()) {
                        target = getUnique(to, file.getName());
                    }
                    java.nio.file.Files.move(file.toPath(), target.toPath());
                }
            }
        }
    }

    /**
     * Generates a unique file handle in the specified folder based on the given name.
     * If the file already exists, it appends a counter to the base name (e.g., "name (1).ext").
     *
     * @param folder The folder where the file should be unique.
     * @param name The original file name.
     * @return A File object representing a unique path in the folder.
     */
    public static File getUnique(File folder, String name) {
        var base = WizFile.getBaseName(name);
        var ext = WizFile.getExtension(name);
        var count = 1;
        var result = new File(folder, base + " (" + count + ")" + ext);
        while (result.exists()) {
            count++;
            result = new File(folder, base + " (" + count + ")" + ext);
        }
        return result;
    }

    /**
     * A record to hold a file system root and its display name.
     *
     * @param root The root directory.
     * @param name The display name of the root.
     */
    public static record RootName(File root, String name){}

}
