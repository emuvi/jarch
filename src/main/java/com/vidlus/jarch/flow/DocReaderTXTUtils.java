package com.vidlus.jarch.flow;

import java.io.File;
import org.apache.commons.io.FilenameUtils;

import com.vidlus.jarch.mage.WizArray;

/**
 * Utility class providing file extension matching algorithms to identify 
 * plain text and source code files.
 */
public class DocReaderTXTUtils {

    public static String[] TXT_EXTENSIONS = new String[]{ "txt", "md", "htm", "html", "php", "phtml", "java", "py", "log" };

    /**
     * @param file the file to check
     * @return true if the file is recognized as a plaintext format
     */
    public static boolean isTXTFile(File file) {
        return isTXTFile(file.getName());
    }

    /**
     * @param fileName the name of the file to check
     * @return true if the file name has a recognized plaintext extension
     */
    public static boolean isTXTFile(String fileName) {
        return WizArray.has(FilenameUtils.getExtension(fileName).toLowerCase(), TXT_EXTENSIONS);
    }
    
}
