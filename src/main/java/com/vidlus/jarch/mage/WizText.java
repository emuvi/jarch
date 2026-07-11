package com.vidlus.jarch.mage;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * A utility class for fast, UTF-8 standard text operations on files.
 * Automates parent directory creation and streamlines reading/writing
 * both full files and line-by-line arrays securely.
 */
public class WizText {

    private WizText() {
    }
    
    // =========================================================================
    // READING TEXT
    // =========================================================================

    /**
     * Reads the entire contents of a file into a UTF-8 String.
     *
     * @param file the file to read
     * @return the string contents, or null if the file does not exist or is null
     * @throws Exception if an I/O error occurs
     */
    public static String read(File file) throws Exception {
        if (file == null || !file.exists()) return null;
        return Files.readString(file.toPath(), StandardCharsets.UTF_8);
    }

    /**
     * Reads the entire contents of a file located at the specified string path.
     *
     * @param path the absolute or relative file path
     * @return the string contents, or null if the file does not exist
     * @throws Exception if an I/O error occurs
     */
    public static String read(String path) throws Exception {
        if (WizString.isEmpty(path)) return null;
        return read(new File(path));
    }

    /**
     * Reads all bytes from an InputStream and decodes them into a UTF-8 String.
     *
     * @param in the input stream to read from
     * @return the decoded string, or null if the stream is null
     * @throws Exception if an I/O error occurs
     */
    public static String read(InputStream in) throws Exception {
        if (in == null) return null;
        return new String(in.readAllBytes(), StandardCharsets.UTF_8);
    }

    /**
     * Reads all lines from a file into a List of Strings.
     *
     * @param file the file to read
     * @return a List containing all lines, or null if the file does not exist
     * @throws Exception if an I/O error occurs
     */
    public static List<String> readLines(File file) throws Exception {
        if (file == null || !file.exists()) return null;
        return Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
    }

    /**
     * Reads all lines from a file located at the specified string path.
     *
     * @param path the absolute or relative file path
     * @return a List containing all lines, or null if the file does not exist
     * @throws Exception if an I/O error occurs
     */
    public static List<String> readLines(String path) throws Exception {
        if (WizString.isEmpty(path)) return null;
        return readLines(new File(path));
    }

    // =========================================================================
    // WRITING TEXT
    // =========================================================================

    /**
     * Writes a block of text to a file using UTF-8 encoding.
     * Automatically creates any missing parent directories.
     * If the file already exists, its contents are completely overwritten.
     *
     * @param file  the target file
     * @param chars the text to write (null is converted to an empty string)
     * @throws Exception if an I/O error occurs
     */
    public static void write(File file, String chars) throws Exception {
        if (file == null) return;
        if (chars == null) chars = "";
        ensureParentDirectory(file);
        Files.writeString(file.toPath(), chars, StandardCharsets.UTF_8, 
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Writes a block of text to a file located at the specified string path.
     * Automatically creates any missing parent directories.
     * If the file already exists, its contents are completely overwritten.
     *
     * @param path  the target file path
     * @param chars the text to write
     * @throws Exception if an I/O error occurs
     */
    public static void write(String path, String chars) throws Exception {
        if (WizString.isEmpty(path)) return;
        write(new File(path), chars);
    }

    /**
     * Writes a List of strings to a file, line by line.
     * Automatically creates any missing parent directories.
     * If the file already exists, its contents are completely overwritten.
     *
     * @param file  the target file
     * @param lines the List of strings to write
     * @throws Exception if an I/O error occurs
     */
    public static void writeLines(File file, List<String> lines) throws Exception {
        if (file == null || lines == null) return;
        ensureParentDirectory(file);
        Files.write(file.toPath(), lines, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Writes a List of strings to a file at the specified path, line by line.
     * Automatically creates any missing parent directories.
     * If the file already exists, its contents are completely overwritten.
     *
     * @param path  the target file path
     * @param lines the List of strings to write
     * @throws Exception if an I/O error occurs
     */
    public static void writeLines(String path, List<String> lines) throws Exception {
        if (WizString.isEmpty(path)) return;
        writeLines(new File(path), lines);
    }

    // =========================================================================
    // APPENDING TEXT
    // =========================================================================

    /**
     * Appends a block of text to the end of a file.
     * Automatically creates the file and any missing parent directories if they do not exist.
     *
     * @param file  the target file
     * @param chars the text to append
     * @throws Exception if an I/O error occurs
     */
    public static void append(File file, String chars) throws Exception {
        if (file == null) return;
        if (chars == null) chars = "";
        ensureParentDirectory(file);
        Files.writeString(file.toPath(), chars, StandardCharsets.UTF_8, 
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    /**
     * Appends a block of text to the end of a file at the specified path.
     * Automatically creates the file and any missing parent directories if they do not exist.
     *
     * @param path  the target file path
     * @param chars the text to append
     * @throws Exception if an I/O error occurs
     */
    public static void append(String path, String chars) throws Exception {
        if (WizString.isEmpty(path)) return;
        append(new File(path), chars);
    }

    /**
     * Appends a List of strings to the end of a file, line by line.
     * Automatically creates the file and any missing parent directories if they do not exist.
     *
     * @param file  the target file
     * @param lines the List of strings to append
     * @throws Exception if an I/O error occurs
     */
    public static void appendLines(File file, List<String> lines) throws Exception {
        if (file == null || lines == null) return;
        ensureParentDirectory(file);
        Files.write(file.toPath(), lines, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    /**
     * Appends a List of strings to the end of a file at the specified path, line by line.
     * Automatically creates the file and any missing parent directories if they do not exist.
     *
     * @param path  the target file path
     * @param lines the List of strings to append
     * @throws Exception if an I/O error occurs
     */
    public static void appendLines(String path, List<String> lines) throws Exception {
        if (WizString.isEmpty(path)) return;
        appendLines(new File(path), lines);
    }

    // =========================================================================
    // INTERNAL UTILITIES
    // =========================================================================

    /**
     * Ensures that the directory containing the specified file actually exists on disk.
     * Safely ignores the operation if the file is at the root level.
     *
     * @param file the file requiring a parent directory
     */
    private static void ensureParentDirectory(File file) {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }
}