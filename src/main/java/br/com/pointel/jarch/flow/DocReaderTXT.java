package br.com.pointel.jarch.flow;

import java.io.File;
import java.nio.file.Files;

public class DocReaderTXT {
    
    public static boolean canRead(File file) {
        return DocReaderTXTUtils.isTXTFile(file);
    }
    
    private final File file;

    public DocReaderTXT(File file) {
        this.file = file;
    }

    public String read() throws Exception {
        return Files.readString(file.toPath());
    }

}
