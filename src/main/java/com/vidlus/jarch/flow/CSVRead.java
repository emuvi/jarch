package com.vidlus.jarch.flow;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;

import com.vidlus.jarch.mage.WizString;

/**
 * A low-level CSV reader that parses character streams into string arrays.
 * Correctly handles CSV nuances such as quoted fields, escaped quotes, and embedded commas.
 */
public class CSVRead implements Closeable {

    private final BufferedReader reader;

    /**
     * Wraps a generic character reader for CSV parsing.
     *
     * @param reader the underlying character stream reader
     */
    public CSVRead(Reader reader) {
        this.reader = new BufferedReader(reader);
    }

    /**
     * Reads the next line from the character stream and parses it into columns.
     * Honors CSV quotation rules allowing for commas and newlines inside fields.
     *
     * @return an array of parsed column strings, or null if the end of the stream has been reached
     * @throws Exception if an I/O error occurs during reading
     */
    public String[] readLine() throws Exception {
        final var line = this.reader.readLine();
        if (line == null) {
            return null;
        }
        var result = new LinkedList<String>();
        var openQuotes = false;
        var builder = new StringBuilder();
        for (var i = 0; i < line.length(); i++) {
            var actual = line.charAt(i);
            var next = i < line.length() - 1 ? line.charAt(i + 1) : ' ';
            if (openQuotes) {
                if (actual == '"') {
                    if (next == '"') {
                        builder.append('"');
                        i++; // Skip the escaped quote
                    } else {
                        openQuotes = false;
                    }
                } else {
                    builder.append(actual);
                }
            } else if (actual == '"') {
                openQuotes = true;
            } else if (actual == ',' || actual == ';') {
                result.add(WizString.remakeBreaks(builder.toString()));
                builder = new StringBuilder();
            } else {
                builder.append(actual);
            }
        }
        result.add(WizString.remakeBreaks(builder.toString()));
        return result.toArray(new String[result.size()]);
    }

    /**
     * Closes the underlying BufferedReader.
     *
     * @throws IOException if closing the stream fails
     */
    @Override
    public void close() throws IOException {
        this.reader.close();
    }
}
