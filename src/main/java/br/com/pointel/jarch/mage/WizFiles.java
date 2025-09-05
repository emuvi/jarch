package br.com.pointel.jarch.mage;

import java.io.Closeable;
import java.io.File;
import javax.swing.filechooser.FileSystemView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WizFiles {

    private static final Logger log = LoggerFactory.getLogger(WizFiles.class);

    public static record RootName(File root, String name){}
    
    public static RootName[] getRootsNamed() {
        FileSystemView fileSystemView = FileSystemView.getFileSystemView();
        File[] roots = File.listRoots();
        var result = new RootName[roots.length];
        for (int i = 0; i < roots.length; i++) {
            result[i] = new RootName(roots[i], fileSystemView.getSystemDisplayName(roots[i]));
        }
        return result;
    }

    public static void closeAside(Closeable closeable) {
        new Thread(() -> {
            try {
                closeable.close();
            } catch (Exception ex) {
                log.error("Error closing resource", ex);
            }
        }).start();
    }
    
}
