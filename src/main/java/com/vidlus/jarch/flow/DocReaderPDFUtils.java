package com.vidlus.jarch.flow;

import java.io.File;
import org.apache.commons.io.FilenameUtils;

import com.vidlus.jarch.mage.WizArray;

public class DocReaderPDFUtils {

    public static String[] PDF_EXTENSIONS = new String[]{ "pdf", "fdf", "xfdf", "pdx", "ppdf" };

    public static boolean isPDFFile(File file) {
        return isPDFFile(file.getName());
    }

    public static boolean isPDFFile(String fileName) {
        return WizArray.has(FilenameUtils.getExtension(fileName).toLowerCase(), PDF_EXTENSIONS);
    }
    
}
