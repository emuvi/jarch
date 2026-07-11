package com.vidlus.jarch.flow;

import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * A high-level abstraction for handling CSV files safely. 
 * Automatically manages the underlying {@link CSVRead} or {@link CSVWrite} instances 
 * depending on the requested file mode. Enforces UTF-8 encoding.
 */
public class CSVFile implements Closeable {
    private final File file;
    private final Mode mode;
    private final CSVRead reader;
    private final CSVWrite writer;

    /**
     * Opens or creates a CSV file in the specified mode.
     *
     * @param file the target CSV file
     * @param mode the mode to open the file in (READ, WRITE, or APPEND)
     * @throws Exception if the file cannot be found or created
     */
    public CSVFile(File file, Mode mode) throws Exception {
        this.file = file;
        this.mode = mode;
        if (mode == Mode.READ) {
            this.reader = new CSVRead(new FileReader(file, StandardCharsets.UTF_8));
            this.writer = null;
        } else {
            this.reader = null;
            this.writer = new CSVWrite(new FileWriter(file, StandardCharsets.UTF_8,
                            mode == Mode.APPEND));
        }
    }

    /**
     * Retrieves the file being operated on.
     *
     * @return the CSV file
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Retrieves the access mode this file was opened with.
     *
     * @return the access Mode
     */
    public Mode getMode() {
        return this.mode;
    }

    /**
     * Reads the next logical line from the CSV file.
     * Only valid if the file was opened in READ mode.
     *
     * @return an array of strings representing the columns, or null if EOF is reached
     * @throws Exception if an I/O error occurs or the mode is incorrect
     */
    public String[] readLine() throws Exception {
        return this.reader.readLine();
    }

    /**
     * Writes a row of columns to the CSV file.
     * Only valid if the file was opened in WRITE or APPEND mode.
     *
     * @param columns varargs representing the column values
     */
    public void writeLine(String... columns) {
        this.writer.writeLine(columns);
    }

    /**
     * Reads all remaining lines from the CSV file.
     * Only valid if the file was opened in READ mode.
     *
     * @return a list of string arrays representing the rows
     * @throws Exception if an I/O error occurs or the mode is incorrect
     */
    public List<String[]> readAllLines() throws Exception {
        return this.reader.readAllLines();
    }

    /**
     * Skips a specified number of lines from the CSV file.
     * Only valid if the file was opened in READ mode.
     *
     * @param count the number of lines to skip
     * @throws Exception if an I/O error occurs or the mode is incorrect
     */
    public void skipLines(int count) throws Exception {
        this.reader.skipLines(count);
    }

    /**
     * Writes multiple rows to the CSV file.
     * Only valid if the file was opened in WRITE or APPEND mode.
     *
     * @param lines an iterable collection of string arrays to write
     */
    public void writeLines(Iterable<String[]> lines) {
        this.writer.writeLines(lines);
    }

    /**
     * Writes multiple rows to the CSV file.
     * Only valid if the file was opened in WRITE or APPEND mode.
     *
     * @param lines an array of string arrays to write
     */
    public void writeLines(String[][] lines) {
        this.writer.writeLines(lines);
    }

    /**
     * Flushes the underlying writers.
     */
    public void flush() {
        if (this.writer != null) {
            this.writer.flush();
        }
    }

    /**
     * Closes the underlying file readers or writers to free up system resources.
     *
     * @throws IOException if closing the stream fails
     */
    @Override
    public void close() throws IOException {
        if (this.reader != null) {
            this.reader.close();
        }
        if (this.writer != null) {
            this.writer.close();
        }
    }

    /**
     * Defines the operational mode for the CSV file.
     */
    public static enum Mode {
        READ, WRITE, APPEND
    }
}
