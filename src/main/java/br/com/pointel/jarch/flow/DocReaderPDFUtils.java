package br.com.pointel.jarch.flow;

import java.io.File;
import org.apache.commons.io.FilenameUtils;
import br.com.pointel.jarch.mage.WizArray;

public class DocReaderPDFUtils {

    public static String[] PDF_EXTENSIONS = new String[]{ "pdf", "fdf", "xfdf", "pdx", "ppdf" };

    public static boolean isPDFFile(File file) {
        return isPDFFile(file.getName());
    }

    public static boolean isPDFFile(String fileName) {
        return WizArray.has(FilenameUtils.getExtension(fileName).toLowerCase(), PDF_EXTENSIONS);
    }
    
}
