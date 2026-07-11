package com.vidlus.jarch.flow;

import java.io.File;
import org.apache.commons.io.FilenameUtils;

import com.vidlus.jarch.mage.WizArray;

/**
 * Utility class providing file extension matching algorithms to identify 
 * Microsoft Office documents.
 */
public class DocReaderMSOUtils {

    public static String[] MSPOWERPOINT_EXTENSIONS = new String[]{ "ppt", "pptx" };
    public static String[] MSEXCEL_EXTENSIONS = new String[]{ "xls", "xlsx" };
    public static String[] MSWORD_EXTENSIONS = new String[]{ "doc", "docx" };

    /**
     * @param file the file to check
     * @return true if the file is a Microsoft Word document
     */
    public static boolean isMSWordFile(File file) {
        return isMSWordFile(file.getName());
    }

    /**
     * @param fileName the name of the file to check
     * @return true if the file is a Microsoft Word document
     */
    public static boolean isMSWordFile(String fileName) {
        return WizArray.has(FilenameUtils.getExtension(fileName).toLowerCase(), MSWORD_EXTENSIONS);
    }

    /**
     * @param file the file to check
     * @return true if the file is a Microsoft PowerPoint document
     */
    public static boolean isMSPowerPointFile(File file) {
        return isMSPowerPointFile(file.getName());
    }

    /**
     * @param fileName the name of the file to check
     * @return true if the file is a Microsoft PowerPoint document
     */
    public static boolean isMSPowerPointFile(String fileName) {
        return WizArray.has(FilenameUtils.getExtension(fileName).toLowerCase(), MSPOWERPOINT_EXTENSIONS);
    }

    /**
     * @param file the file to check
     * @return true if the file is a Microsoft Excel document
     */
    public static boolean isMSExcelFile(File file) {
        return isMSExcelFile(file.getName());
    }

    /**
     * @param fileName the name of the file to check
     * @return true if the file is a Microsoft Excel document
     */
    public static boolean isMSExcelFile(String fileName) {
        return WizArray.has(FilenameUtils.getExtension(fileName).toLowerCase(), MSEXCEL_EXTENSIONS);
    }
    
}
