package com.vidlus.jarch.mage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.filechooser.FileSystemView;

/**
 * Utility class for bulk file system operations (recursive moves, copying, zipping, etc.).
 */
public class WizFiles {

    private WizFiles() {}

    /**
     * Retrieves the file system roots along with their system display names.
     *
     * @return An array of RootName records containing the root file and its display name.
     */
    public static RootName[] getRootsNamed() {
        FileSystemView fileSystemView = FileSystemView.getFileSystemView();
        File[] roots = File.listRoots();
        if (roots == null) return new RootName[0];
        
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
            throw new IllegalArgumentException("Source must be an existing directory.");
        }
        if (to == null) {
            throw new IllegalArgumentException("Destination cannot be null.");
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
                    Files.move(file.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }

    /**
     * Recursively copies a directory and its contents to a destination directory.
     *
     * @param source The source directory or file.
     * @param dest The destination directory or file.
     * @throws IOException If an I/O error occurs.
     */
    public static void copyAll(File source, File dest) throws IOException {
        if (source == null || dest == null || !source.exists()) return;
        
        if (source.isDirectory()) {
            if (!dest.exists()) {
                dest.mkdirs();
            }
            String[] children = source.list();
            if (children != null) {
                for (String child : children) {
                    copyAll(new File(source, child), new File(dest, child));
                }
            }
        } else {
            Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
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
     * Recursively deletes a file or directory.
     *
     * @param file The file or directory to delete.
     * @return true if the file or directory was successfully deleted, false otherwise.
     */
    public static boolean delete(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        if (file.isDirectory()) {
            var files = file.listFiles();
            if (files != null) {
                for (var child : files) {
                    delete(child);
                }
            }
        }
        return file.delete();
    }
    
    /**
     * Deletes multiple files or directories recursively.
     *
     * @param files The files to delete.
     */
    public static void deleteAll(File... files) {
        if (files != null) {
            for (File file : files) {
                delete(file);
            }
        }
    }

    /**
     * Recursively lists all files within a directory (excluding directories themselves).
     *
     * @param folder The directory to search.
     * @return A list of all files.
     */
    public static List<File> listAll(File folder) {
        List<File> result = new ArrayList<>();
        if (folder != null && folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        result.addAll(listAll(file));
                    } else {
                        result.add(file);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Zips a file or directory into a target zip file.
     *
     * @param source The file or directory to compress.
     * @param zipFile The output zip file.
     * @throws IOException If an I/O error occurs.
     */
    public static void zip(File source, File zipFile) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            zipFile(source, source.getName(), zos);
        }
    }

    /**
     * Recursively adds a file or directory to a zip output stream.
     * Skips hidden files and creates directory entries as needed.
     *
     * @param fileToZip the file or directory to add to the zip
     * @param fileName the name to use in the zip entry
     * @param zos the ZipOutputStream to write to
     * @throws IOException if an I/O error occurs
     */
    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zos) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zos.putNextEntry(new ZipEntry(fileName));
                zos.closeEntry();
            } else {
                zos.putNextEntry(new ZipEntry(fileName + "/"));
                zos.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            if (children != null) {
                for (File childFile : children) {
                    zipFile(childFile, fileName + "/" + childFile.getName(), zos);
                }
            }
            return;
        }
        try (FileInputStream fis = new FileInputStream(fileToZip)) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zos.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }
        }
    }

    /**
     * Unzips a zip file into a target directory.
     *
     * @param zipFile The zip file to extract.
     * @param destDir The target directory.
     * @throws IOException If an I/O error occurs.
     */
    public static void unzip(File zipFile, File destDir) throws IOException {
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                File newFile = newFile(destDir, zipEntry);
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        }
    }

    private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());
        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }
        return destFile;
    }

    /**
     * A record to hold a file system root and its display name.
     *
     * @param root The root directory.
     * @param name The display name of the root.
     */
    public static record RootName(File root, String name){}

}
