package com.vidlus.jarch.flow;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * A specialized document reader that extracts text, page counts, and images from PDFs.
 * It leverages the Apache PDFBox library to perform deep text extraction, including 
 * parsing and formatting embedded PDF annotations/comments.
 */
public class DocReaderPDF {
    
    /**
     * Checks if the file is a supported PDF format.
     *
     * @param file the file to check
     * @return true if the file is a PDF
     */
    public static boolean canRead(File file) {
        return DocReaderPDFUtils.isPDFFile(file);
    }
    
    private final File file;

    /**
     * Constructs a reader for a PDF document.
     *
     * @param file the file to read
     */
    public DocReaderPDF(File file) {
        this.file = file;
    }

    /**
     * Extracts the text from all pages in the document.
     * Pages are separated by "---" markers in the resulting string.
     *
     * @return the combined extracted text
     * @throws Exception if an error occurs reading the PDF
     */
    public String read() throws Exception {
        try (var doc = PDDocument.load(file)) {
            var count = doc.getNumberOfPages();
            var builder = new StringBuilder();
            for (int i = 0; i < count; i++) {
                if (i > 0) {
                    builder.append(System.lineSeparator());
                    builder.append("---");
                    builder.append(System.lineSeparator());
                    builder.append(System.lineSeparator());
                }
                builder.append(readPage(doc, i));
            }
            return builder.toString();
        }
    }

    /**
     * Obtains the total number of physical pages in the PDF.
     *
     * @return the total page count
     * @throws Exception if an error occurs reading the PDF metadata
     */
    public Integer countPages() throws Exception {
        try (var doc = PDDocument.load(file)) {
            return doc.getNumberOfPages();
        }
    }

    /**
     * Extracts the text content from a single specific page.
     *
     * @param pageNumber the zero-based index of the page
     * @return the extracted text of the page
     * @throws Exception if the page is out of bounds or reading fails
     */
    public String readPage(int pageNumber) throws Exception {
        try (var doc = PDDocument.load(file)) {
            return readPage(doc, pageNumber);
        }
    }

    /**
     * Core static logic for parsing text and annotations from a loaded PDF page.
     * Appends any found PDF annotations/comments to the bottom of the page text.
     *
     * @param doc        the loaded PDDocument object
     * @param pageNumber the zero-based index of the page
     * @return the text and annotations combined
     * @throws IOException if an error occurs extracting the data
     */
    public static String readPage(PDDocument doc, int pageNumber) throws IOException {
        int numPages = doc.getNumberOfPages();
        if (pageNumber < 0 || pageNumber >= numPages) {
            throw new IllegalArgumentException("Page number " + pageNumber + " is out of bounds. The PDF has " + numPages + " pages.");
        }
        var stripper = new PDFTextStripper();
        stripper.setStartPage(pageNumber + 1);
        stripper.setEndPage(pageNumber + 1);
        String text = stripper.getText(doc).trim();

        var page = doc.getPage(pageNumber);
        var annotations = page.getAnnotations();
        StringBuilder comments = new StringBuilder();
        for (var annotation : annotations) {
            var contents = annotation.getContents();
            if (contents != null && !contents.isBlank()) {
                if (comments.length() > 0) {
                    comments.append(System.lineSeparator());
                    comments.append("--:");
                    comments.append(System.lineSeparator());
                    comments.append(System.lineSeparator());
                }
                comments.append(contents.trim());
            }
        }

        if (comments.length() > 0) {
            text = text
                            + System.lineSeparator()
                            + "--\\" 
                            + System.lineSeparator()
                            + System.lineSeparator()
                            + comments.toString();
        }
        return text;
    }

    /**
     * Renders a specific page into a standard BufferedImage raster.
     *
     * @param pageNumber the zero-based index of the page
     * @param dpi        the resolution (Dots Per Inch) to render the image at
     * @return the rendered image
     * @throws Exception if an error occurs rendering the page
     */
    public BufferedImage getPageAsImage(int pageNumber, int dpi) throws Exception {
        try (var doc = PDDocument.load(file)) {
            if (pageNumber < 0 || pageNumber >= doc.getNumberOfPages()) {
                throw new IllegalArgumentException("Page number " + pageNumber + " is out of bounds. The PDF has " + doc.getNumberOfPages() + " pages.");
            }
            PDFRenderer pdfRenderer = new PDFRenderer(doc);
            BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(pageNumber, dpi);
            return bufferedImage;
        }
    }

}
