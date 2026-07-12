package com.vidlus.jarch.desk;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 * A specialized JComponent for rendering images dynamically.
 * Features built-in support for different scaling modes (fit, fill, stretch, none).
 */
public class DImage extends JComponent {

    /**
     * Defines how the image should be scaled to fit within the component's bounds.
     */
    public enum ScaleType {
        /** Display the image at its original resolution, centered. */
        NONE, 
        /** Scale the image to fit entirely within the component, maintaining aspect ratio. */
        FIT, 
        /** Scale the image to completely fill the component, maintaining aspect ratio (may crop). */
        FILL, 
        /** Stretch the image to exactly match the component's dimensions, ignoring aspect ratio. */
        STRETCH
    }

    private Image image;
    private ScaleType scaleType = ScaleType.FIT;
    private boolean highQuality = true;

    /**
     * Creates an empty DImage component with no initial image.
     */
    public DImage() {
    }

    /**
     * Creates a DImage component that displays the given awt Image.
     * 
     * @param image the Image to display
     */
    public DImage(Image image) {
        this.image = image;
    }

    /**
     * Creates a DImage component that loads and displays an image from the specified path.
     * 
     * @param path the file path to the image
     */
    public DImage(String path) {
        this(new ImageIcon(path).getImage());
    }

    /**
     * Creates a DImage component that loads and displays an image from the specified URL.
     * 
     * @param url the URL to the image
     */
    public DImage(URL url) {
        this(new ImageIcon(url).getImage());
    }

    /**
     * Creates a DImage component that loads and displays an image from the specified File.
     * 
     * @param file the File pointing to the image
     */
    public DImage(File file) {
        this(new ImageIcon(file.getAbsolutePath()).getImage());
    }

    /**
     * Enables or disables high-quality rendering (bilinear interpolation and anti-aliasing) when scaling the image.
     * Enabled by default.
     * 
     * @param highQuality true to enable high-quality rendering
     * @return This DImage instance.
     */
    public DImage highQuality(boolean highQuality) {
        this.highQuality = highQuality;
        repaint();
        return this;
    }

    /**
     * Sets the image to display and repaints the component.
     * 
     * @param image the image
     * @return This DImage instance.
     */
    public DImage image(Image image) {
        this.image = image;
        repaint();
        return this;
    }

    /**
     * Loads an image from the specified path and repaints the component.
     * 
     * @param path the image path
     * @return This DImage instance.
     */
    public DImage image(String path) {
        return image(new ImageIcon(path).getImage());
    }

    /**
     * Sets the scaling mode for the image.
     * 
     * @param type the scale type
     * @return This DImage instance.
     */
    public DImage scaleType(ScaleType type) {
        this.scaleType = type;
        repaint();
        return this;
    }

    /**
     * Scales the image so it fits entirely within the component, maintaining its aspect ratio.
     * Empty space will be left on the sides if the aspect ratios don't match.
     * 
     * @return This DImage instance.
     */
    public DImage fit() {
        return scaleType(ScaleType.FIT);
    }

    /**
     * Scales the image so it completely fills the component, maintaining its aspect ratio.
     * The image will be cropped if the aspect ratios don't match.
     * 
     * @return This DImage instance.
     */
    public DImage fill() {
        return scaleType(ScaleType.FILL);
    }

    /**
     * Stretches the image to exactly match the component's dimensions, ignoring aspect ratio.
     * 
     * @return This DImage instance.
     */
    public DImage stretch() {
        return scaleType(ScaleType.STRETCH);
    }

    /**
     * Displays the image at its original resolution, centered within the component.
     * 
     * @return This DImage instance.
     */
    public DImage none() {
        return scaleType(ScaleType.NONE);
    }

    /**
     * Retrieves the underlying image.
     * 
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null) return;

        int imgWidth = image.getWidth(this);
        int imgHeight = image.getHeight(this);
        if (imgWidth <= 0 || imgHeight <= 0) return;

        int compWidth = getWidth();
        int compHeight = getHeight();

        int drawX = 0, drawY = 0, drawWidth = compWidth, drawHeight = compHeight;

        switch (scaleType) {
            case NONE:
                drawWidth = imgWidth;
                drawHeight = imgHeight;
                drawX = (compWidth - drawWidth) / 2;
                drawY = (compHeight - drawHeight) / 2;
                break;
            case STRETCH:
                drawX = 0;
                drawY = 0;
                drawWidth = compWidth;
                drawHeight = compHeight;
                break;
            case FIT:
            case FILL:
                double scaleX = (double) compWidth / imgWidth;
                double scaleY = (double) compHeight / imgHeight;
                double scale = (scaleType == ScaleType.FIT) ? Math.min(scaleX, scaleY) : Math.max(scaleX, scaleY);
                drawWidth = (int) (imgWidth * scale);
                drawHeight = (int) (imgHeight * scale);
                drawX = (compWidth - drawWidth) / 2;
                drawY = (compHeight - drawHeight) / 2;
                break;
        }

        Graphics2D g2d = (Graphics2D) g.create();
        if (highQuality) {
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        
        g2d.drawImage(image, drawX, drawY, drawWidth, drawHeight, this);
        g2d.dispose();
    }
}
