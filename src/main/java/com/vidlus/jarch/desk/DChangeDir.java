package com.vidlus.jarch.desk;

import java.io.File;
import javax.swing.JFileChooser;

public class DChangeDir extends DEditChange<File> {

    public DChangeDir() {
        super("*");
    }

    @Override
    public File getValue() {
        var text = getField().getText();
        return text.isEmpty() ? null : new File(text);
    }

    @Override
    public void setValue(File value) {
        getField().setText(value == null ? "" : value.getAbsolutePath());
    }

    @Override
    protected void onActionPressed() {
        if (!editable()) return;
        DFile fileDialog = new DFile();
        fileDialog.selectionMode(JFileChooser.DIRECTORIES_ONLY);

        File current = getValue();
        if (current != null && current.exists()) {
            if (current.isDirectory()) {
                fileDialog.directory(current);
            } else {
                fileDialog.directory(current.getParentFile());
            }
        }

        if (fileDialog.showOpen(comp()) == JFileChooser.APPROVE_OPTION) {
            setValue(fileDialog.file());
        }
    }
}
