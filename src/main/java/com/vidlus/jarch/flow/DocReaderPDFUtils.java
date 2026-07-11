package com.vidlus.jarch.flow;

import java.io.File;
import org.apache.commons.io.FilenameUtils;

import com.vidlus.jarch.mage.WizArray;

/**
 * Utility class providing file extension matching algorithms to identify 
 * Portable Document Format (PDF) files.
 */
public class DocReaderPDFUtils {

    public static String[] PDF_EXTENSIONS = new String[]{ "pdf", "fdf", "xfdf", "pdx", "ppdf" };

    /**
     * @param file the file to check
     * @return true if the file is recognized as a PDF format
     */
    public static boolean isPDFFile(File file) {
        return isPDFFile(file.getName());
    }

    /**
     * @param fileName the name of the file to check
     * @return true if the file name has a recognized PDF extension
     */
    public static boolean isPDFFile(String fileName) {
        return WizArray.has(FilenameUtils.getExtension(fileName).toLowerCase(), PDF_EXTENSIONS);
    }
    
}
