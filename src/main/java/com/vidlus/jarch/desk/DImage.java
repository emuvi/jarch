package com.vidlus.jarch.desk;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 * A specialized Swing {@link JComponent} designed for dynamic and flexible image rendering.
 * It provides built-in support for multiple scaling strategies (FIT, FILL, STRETCH, NONE) 
 * to easily adapt image dimensions to the component's bounds. 
 * <p>
 * Additionally, it features advanced rendering capabilities, including:
 * <ul>
 *   <li>High-quality bilinear interpolation and anti-aliasing</li>
 *   <li>Dynamic opacity (alpha composite) adjustments</li>
 *   <li>Arbitrary rotations (in degrees)</li>
 *   <li>Horizontal and vertical mirroring (flipping)</li>
 *   <li>Rounded corners through shape clipping</li>
 * </ul>
 * Its fluent API design allows for clean and chained method calls when configuring properties.
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

    /** The underlying image instance to be rendered by this component. */
    private Image image;

    /** The scaling strategy determining how the image adapts to the component's dimensions. */
    private ScaleType scaleType = ScaleType.FIT;

    /** Determines whether high-quality rendering hints (anti-aliasing, interpolation) are applied. */
    private boolean highQuality = true;

    /** The opacity level of the image, ranging from 0.0f (fully transparent) to 1.0f (fully opaque). */
    private float alpha = 1.0f;

    /** The rotation angle of the image in degrees, rotating around its calculated center point. */
    private double rotation = 0.0;

    /** Indicates whether the image should be visually mirrored along the horizontal axis. */
    private boolean flipX = false;

    /** Indicates whether the image should be visually mirrored along the vertical axis. */
    private boolean flipY = false;

    /** The arc size (in pixels) used to clip the image and produce rounded corners. 0 means no rounding. */
    private int arc = 0;

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
     * Loads an image from the specified URL and repaints the component.
     * 
     * @param url the URL to the image
     * @return This DImage instance.
     */
    public DImage image(URL url) {
        return image(new ImageIcon(url).getImage());
    }

    /**
     * Loads an image from the specified File and repaints the component.
     * 
     * @param file the File pointing to the image
     * @return This DImage instance.
     */
    public DImage image(File file) {
        return image(new ImageIcon(file.getAbsolutePath()).getImage());
    }

    /**
     * Checks if this component currently has an image set.
     * 
     * @return true if an image is set, false otherwise
     */
    public boolean hasImage() {
        return image != null;
    }

    /**
     * Clears the current image and repaints the component.
     * 
     * @return This DImage instance.
     */
    public DImage clear() {
        this.image = null;
        repaint();
        return this;
    }

    /**
     * Sets the opacity/alpha of the image rendering.
     * 
     * @param alpha the opacity from 0.0f (fully transparent) to 1.0f (fully opaque)
     * @return This DImage instance.
     */
    public DImage alpha(float alpha) {
        this.alpha = Math.max(0.0f, Math.min(1.0f, alpha));
        repaint();
        return this;
    }

    /**
     * Sets the rotation angle in degrees.
     * 
     * @param degrees the rotation angle in degrees
     * @return This DImage instance.
     */
    public DImage rotation(double degrees) {
        this.rotation = degrees;
        repaint();
        return this;
    }

    /**
     * Sets whether the image should be flipped horizontally.
     * 
     * @param flipX true to flip horizontally
     * @return This DImage instance.
     */
    public DImage flipX(boolean flipX) {
        this.flipX = flipX;
        repaint();
        return this;
    }

    /**
     * Flips the image horizontally, toggling the current state.
     * 
     * @return This DImage instance.
     */
    public DImage flipX() {
        return flipX(!this.flipX);
    }

    /**
     * Sets whether the image should be flipped vertically.
     * 
     * @param flipY true to flip vertically
     * @return This DImage instance.
     */
    public DImage flipY(boolean flipY) {
        this.flipY = flipY;
        repaint();
        return this;
    }

    /**
     * Flips the image vertically, toggling the current state.
     * 
     * @return This DImage instance.
     */
    public DImage flipY() {
        return flipY(!this.flipY);
    }

    /**
     * Sets the arc size for rounded corners.
     * 
     * @param arc the arc size in pixels (0 for no rounding)
     * @return This DImage instance.
     */
    public DImage arc(int arc) {
        this.arc = Math.max(0, arc);
        repaint();
        return this;
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

    /**
     * Retrieves the current scale type.
     * 
     * @return the scale type
     */
    public ScaleType getScaleType() {
        return scaleType;
    }

    /**
     * Checks if high-quality rendering is enabled.
     * 
     * @return true if enabled
     */
    public boolean isHighQuality() {
        return highQuality;
    }

    /**
     * Retrieves the current alpha (opacity) level.
     * 
     * @return the alpha value (0.0f to 1.0f)
     */
    public float getAlpha() {
        return alpha;
    }

    /**
     * Retrieves the current rotation angle in degrees.
     * 
     * @return the rotation angle
     */
    public double getRotation() {
        return rotation;
    }

    /**
     * Checks if the image is flipped horizontally.
     * 
     * @return true if flipped horizontally
     */
    public boolean isFlipX() {
        return flipX;
    }

    /**
     * Checks if the image is flipped vertically.
     * 
     * @return true if flipped vertically
     */
    public boolean isFlipY() {
        return flipY;
    }

    /**
     * Retrieves the arc size for rounded corners.
     * 
     * @return the arc size in pixels
     */
    public int getArc() {
        return arc;
    }

    /**
     * Core rendering method that executes all active image transformations before drawing.
     * <p>
     * Order of operations:
     * 1. Calculate image scaling based on {@link ScaleType}.
     * 2. Apply high-quality rendering hints.
     * 3. Apply opacity/alpha composite.
     * 4. Apply geometric transformations (rotation, horizontal flip, vertical flip).
     * 5. Apply rounded corner clipping.
     * 6. Draw the image to the graphics context.
     * 
     * @param g the Graphics context in which to paint
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // 1. Ensure there is an image to draw
        if (image == null) return;

        int imgWidth = image.getWidth(this);
        int imgHeight = image.getHeight(this);
        
        // 2. Ensure image dimensions are valid before calculating scales
        if (imgWidth <= 0 || imgHeight <= 0) return;

        int compWidth = getWidth();
        int compHeight = getHeight();

        int drawX = 0, drawY = 0, drawWidth = compWidth, drawHeight = compHeight;

        // 3. Calculate the final drawing coordinates and dimensions based on the ScaleType
        switch (scaleType) {
            case NONE:
                // Draw original size centered in the component
                drawWidth = imgWidth;
                drawHeight = imgHeight;
                drawX = (compWidth - drawWidth) / 2;
                drawY = (compHeight - drawHeight) / 2;
                break;
            case STRETCH:
                // Stretch to completely fill the bounds without respecting aspect ratio
                drawX = 0;
                drawY = 0;
                drawWidth = compWidth;
                drawHeight = compHeight;
                break;
            case FIT:
            case FILL:
                // Calculate scale factors to maintain aspect ratio
                double scaleX = (double) compWidth / imgWidth;
                double scaleY = (double) compHeight / imgHeight;
                
                // FIT uses the minimum scale (shows whole image), FILL uses maximum (covers component entirely)
                double scale = (scaleType == ScaleType.FIT) ? Math.min(scaleX, scaleY) : Math.max(scaleX, scaleY);
                
                drawWidth = (int) (imgWidth * scale);
                drawHeight = (int) (imgHeight * scale);
                
                // Center the scaled image within the component
                drawX = (compWidth - drawWidth) / 2;
                drawY = (compHeight - drawHeight) / 2;
                break;
        }

        // Create a copy of the graphics context to prevent our transforms from affecting subsequent paints
        Graphics2D g2d = (Graphics2D) g.create();
        
        // 4. Apply high-quality rendering hints for better aesthetics
        if (highQuality) {
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        // 5. Apply alpha transparency if opacity is reduced
        if (alpha < 1.0f) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        }

        // Calculate the exact center of the drawing area for geometric transformations
        double cx = drawX + drawWidth / 2.0;
        double cy = drawY + drawHeight / 2.0;

        // 6. Apply rotation around the image's calculated center point
        if (rotation != 0.0) {
            g2d.rotate(Math.toRadians(rotation), cx, cy);
        }

        // 7. Apply horizontal mirroring using a negative scale factor on the X axis
        if (flipX) {
            g2d.translate(cx, 0);
            g2d.scale(-1, 1);
            g2d.translate(-cx, 0);
        }

        // 8. Apply vertical mirroring using a negative scale factor on the Y axis
        if (flipY) {
            g2d.translate(0, cy);
            g2d.scale(1, -1);
            g2d.translate(0, -cy);
        }

        // 9. Apply clipping area to create rounded corners natively
        if (arc > 0) {
            g2d.setClip(new RoundRectangle2D.Float(drawX, drawY, drawWidth, drawHeight, arc, arc));
        }

        // 10. Execute the draw operation with all parameters applied
        g2d.drawImage(image, drawX, drawY, drawWidth, drawHeight, this);
        g2d.dispose();
    }
}
