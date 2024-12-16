package br.com.pointel.jarch.mage;

import java.io.File;
import javax.swing.filechooser.FileSystemView;

public class WizFiles {
    
    public static record RootNamed(File root, String name){};
    
    public static RootNamed[] getRootsNamed() {
        FileSystemView fileSystemView = FileSystemView.getFileSystemView();
        File[] roots = File.listRoots();
        var result = new RootNamed[roots.length];
        for (int i = 0; i < roots.length; i++) {
            result[i] = new RootNamed(roots[i], fileSystemView.getSystemDisplayName(roots[i]));
        }
        return result;
    };
    
}
