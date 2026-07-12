package com.vidlus.jarch.desk;

import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * A UI component for editing and selecting an image.
 * Provides a text field detailing the image dimensions and a button that opens an image file chooser.
 */
public class DChangeImage extends DEditChange<Image> {

    private Image currentImage;
    private File sourceFile;

    /**
     * Constructs a new DChangeImage component.
     * The internal text field is set to non-editable to prevent manual input.
     */
    public DChangeImage() {
        super("*");
        getField().setEditable(false);
    }

    /**
     * Retrieves the currently loaded image.
     * 
     * @return the Image, or null if none is selected
     */
    @Override
    public Image getValue() {
        return currentImage;
    }

    /**
     * Sets the image value. Updates the text field to show the source file name and image dimensions.
     * 
     * @param value the Image to set
     */
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

    /**
     * Sets whether this component is editable.
     * The internal text field remains non-editable to enforce selection via the dialog.
     * 
     * @param editable true to enable the action button, false to disable
     * @return this DChangeImage instance
     */
    @Override
    public DChangeImage editable(boolean editable) {
        super.editable(editable);
        getField().setEditable(false);
        return this;
    }

    /**
     * Handles the action button press event.
     * Opens a file chooser filtered for images. Upon selection, reads the image and updates the value.
     */
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

    /**
     * Fluent setter for the image value.
     * 
     * @param image the Image to set
     * @return this DChangeImage instance
     */
    public DChangeImage image(Image image) {
        setValue(image);
        return this;
    }
}
