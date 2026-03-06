package br.com.pointel.jarch.flow;

import java.awt.image.BufferedImage;
import java.io.File;

public class DocReader {

    public static boolean canRead(File file) {
        return DocReaderPDF.canRead(file)
                || DocReaderMSO.canRead(file)
                || DocReaderTXT.canRead(file);
    }

    private final File file;

    public DocReader(File file) {
        this.file = file;
    }

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
