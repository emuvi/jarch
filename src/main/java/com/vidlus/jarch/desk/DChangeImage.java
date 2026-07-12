package com.vidlus.jarch.desk;

import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DChangeImage extends DEditChange<Image> {

    private Image currentImage;
    private File sourceFile;

    public DChangeImage() {
        super("*");
        getField().setEditable(false);
    }

    @Override
    public Image getValue() {
        return currentImage;
    }

    @Override
    public void setValue(Image value) {
        this.currentImage = value;
        if (value != null && sourceFile != null) {
            getField().setText(sourceFile.getName() + " (" + value.getWidth(null) + "x" + value.getHeight(null) + ")");
        } else {
            getField()
                    .setText(value != null ? "Image (" + value.getWidth(null) + "x" + value.getHeight(null) + ")" : "");
        }
    }

    @Override
    public DChangeImage editable(boolean editable) {
        super.editable(editable);
        getField().setEditable(false);
        return this;
    }

    @Override
    protected void onActionPressed() {
        if (!editable()) return;
        DFile fileDialog = new DFile();
        fileDialog.filter(new FileNameExtensionFilter("Images (png, jpg, gif)", "png", "jpg", "jpeg", "gif"));

        if (sourceFile != null && sourceFile.exists()) {
            fileDialog.directory(sourceFile.getParentFile());
        }

        if (fileDialog.showOpen(comp()) == JFileChooser.APPROVE_OPTION) {
            File selected = fileDialog.file();
            try {
                Image img = ImageIO.read(selected);
                if (img != null) {
                    this.sourceFile = selected;
                    setValue(img);
                } else {
                    new DAlert().parent(comp()).title("Error").message("Could not read image file.").error().show();
                }
            } catch (Exception e) {
                new DAlert().parent(comp()).title("Error").message("Could not read image file.").error().show();
            }
        }
    }
}
