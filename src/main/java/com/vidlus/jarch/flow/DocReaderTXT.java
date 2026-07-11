package com.vidlus.jarch.flow;

import java.io.File;
import java.nio.file.Files;

/**
 * A basic document reader that extracts raw text from plain text files
 * and other source code formats.
 */
public class DocReaderTXT {
    
    /**
     * Checks if the file is a recognized plaintext format.
     *
     * @param file the file to check
     * @return true if the file is a text file
     */
    public static boolean canRead(File file) {
        return DocReaderTXTUtils.isTXTFile(file);
    }
    
    private final File file;

    /**
     * Constructs a reader for a plaintext document.
     *
     * @param file the file to read
     */
    public DocReaderTXT(File file) {
        this.file = file;
    }

    /**
     * Extracts the complete text from the file utilizing standard system encodings.
     *
     * @return the raw string content of the file
     * @throws Exception if an I/O error occurs
     */
    public String read() throws Exception {
        return Files.readString(file.toPath());
    }

}
