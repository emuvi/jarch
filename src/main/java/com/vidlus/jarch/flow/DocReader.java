package com.vidlus.jarch.flow;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * A generalized Facade class for reading text from various document formats.
 * It automatically delegates extraction logic to the appropriate specialized reader
 * (PDF, Microsoft Office, or Plain Text) based on the file extension.
 */
public class DocReader {

    /**
     * Checks if any of the underlying specialized readers can parse this file.
     *
     * @param file the file to check
     * @return true if the file format is supported
     */
    public static boolean canRead(File file) {
        return DocReaderPDF.canRead(file)
                || DocReaderMSO.canRead(file)
                || DocReaderTXT.canRead(file);
    }

    private final File file;

    /**
     * Constructs a unified document reader for the given file.
     *
     * @param file the document to read
     */
    public DocReader(File file) {
        this.file = file;
    }

    /**
     * Extracts the raw text content from the document.
     *
     * @return the extracted text, or an empty string if the format is not supported
     * @throws Exception if an error occurs during extraction
     */
    public String read() throws Exception {
        if (DocReaderPDF.canRead(file)) {
            return new DocReaderPDF(file).read();
        } else if (DocReaderMSO.canRead(file)) {
            return new DocReaderMSO(file).read();
        } else if (DocReaderTXT.canRead(file)) {
            return new DocReaderTXT(file).read();
        } else {
            return "";
        }
    }

    /**
     * Counts the total number of pages in the document.
     * Note: Currently, only PDF files support physical page counting.
     *
     * @return the number of pages, or 0 if unsupported
     * @throws Exception if an error occurs reading the document metadata
     */
    public Integer countPages() throws Exception {
        if (DocReaderPDF.canRead(file)) {
            return new DocReaderPDF(file).countPages();
        } else if (DocReaderMSO.canRead(file)) {
            return 0;
        } else if (DocReaderTXT.canRead(file)) {
            return 0;
        } else {
            return 0;
        }
    }

    /**
     * Renders a specific page of the document into a high-quality image.
     * Note: Currently, only PDF files support image rendering.
     *
     * @param pageNumber the zero-based index of the page to render
     * @param dpi        the dots-per-inch resolution for the rendered image
     * @return a BufferedImage of the page, or null if the format is unsupported
     * @throws Exception if an error occurs during rendering
     */
    public BufferedImage getPageAsImage(int pageNumber, int dpi) throws Exception {
        if (DocReaderPDF.canRead(file)) {
            return new DocReaderPDF(file).getPageAsImage(pageNumber, dpi);
        } else if (DocReaderMSO.canRead(file)) {
            return null;
        } else if (DocReaderTXT.canRead(file)) {
            return null;
        } else {
            return null;
        }
    }

}
