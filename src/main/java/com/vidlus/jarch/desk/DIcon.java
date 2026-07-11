package com.vidlus.jarch.desk;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * A fluent API wrapper around ImageIcon to make loading and manipulating images easier.
 */
public class DIcon extends ImageIcon {

    public DIcon() {
        super();
    }

    public DIcon(String filename) {
        super(filename);
    }

    public DIcon(String filename, String description) {
        super(filename, description);
    }

    public DIcon(URL location) {
        super(location);
    }

    public DIcon(URL location, String description) {
        super(location, description);
    }

    public DIcon(Image image) {
        super(image);
    }

    public DIcon(Image image, String description) {
        super(image, description);
    }

    public DIcon(byte[] imageData) {
        super(imageData);
    }

    public DIcon(byte[] imageData, String description) {
        super(imageData, description);
    }

    /**
     * Resizes the icon to the specified width and height using smooth scaling.
     * 
     * @param width  the new width
     * @param height the new height
     * @return This DIcon instance.
     */
    public DIcon size(int width, int height) {
        if (getImage() != null && width > 0 && height > 0) {
            Image newImg = getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            setImage(newImg);
        }
        return this;
    }

    /**
     * Resizes the icon to the specified width, automatically adjusting the height to maintain the aspect ratio.
     * 
     * @param width the new width
     * @return This DIcon instance.
     */
    public DIcon width(int width) {
        if (getImage() != null && width > 0) {
            int currentWidth = getIconWidth();
            if (currentWidth == 0) return this;
            int height = (int) (getIconHeight() * ((double) width / currentWidth));
            return size(width, Math.max(1, height));
        }
        return this;
    }

    /**
     * Resizes the icon to the specified height, automatically adjusting the width to maintain the aspect ratio.
     * 
     * @param height the new height
     * @return This DIcon instance.
     */
    public DIcon height(int height) {
        if (getImage() != null && height > 0) {
            int currentHeight = getIconHeight();
            if (currentHeight == 0) return this;
            int width = (int) (getIconWidth() * ((double) height / currentHeight));
            return size(Math.max(1, width), height);
        }
        return this;
    }

    /**
     * Sets the description of the image. This can be useful for accessibility.
     * 
     * @param description a brief textual description of the image
     * @return This DIcon instance.
     */
    public DIcon desc(String description) {
        setDescription(description);
        return this;
    }
}
