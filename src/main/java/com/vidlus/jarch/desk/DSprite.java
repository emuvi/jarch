package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;

/**
 * A sprite that wraps an Image and implements DAnimatable, allowing it to be
 * animated by DAnimator and rendered on DCanvas.
 */
public class DSprite implements DExcited {

    private final Image image;
    private final AffineTransform transform = new AffineTransform();
    private Color fillColor = null; // Can be used as a tint or alpha overlay
    private Color strokeColor = null; // Can be used as a border
    private float strokeWidth = 1.0f;

    private boolean visible = true;
    private float alpha = 1.0f;

    private final int width;
    private final int height;

    /**
     * Constructs a new DSprite wrapping the provided image.
     * 
     * @param image the image to wrap
     */
    public DSprite(Image image) {
        this.image = image;
        if (image != null) {
            this.width = image.getWidth(null);
            this.height = image.getHeight(null);
        } else {
            this.width = 0;
            this.height = 0;
        }
    }

    /**
     * Constructs a new DSprite by loading an image from the given file path.
     * 
     * @param path the path to the image file
     */
    public DSprite(String path) {
        this(new ImageIcon(path).getImage());
    }

    /**
     * Creates a deep copy of this DSprite, including its current transform, colors, and visibility.
     * 
     * @return a new DSprite with identical properties
     */
    public DSprite copy() {
        DSprite clone = new DSprite(this.image);
        clone.transform.setTransform(this.transform);
        clone.setFill(this.fillColor);
        clone.setStroke(this.strokeColor);
        clone.setStrokeWidth(this.strokeWidth);
        clone.setAlpha(this.alpha);
        clone.setVisible(this.visible);
        return clone;
    }

    /**
     * Gets the base unscaled width of the wrapped image.
     * 
     * @return the base width in pixels
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the base unscaled height of the wrapped image.
     * 
     * @return the base height in pixels
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the current width of the sprite after all transformations (scaling, rotation) have been applied.
     * 
     * @return the transformed bounding width
     */
    public double getScaledWidth() {
        return getBounds2D().getWidth();
    }

    /**
     * Gets the current height of the sprite after all transformations (scaling, rotation) have been applied.
     * 
     * @return the transformed bounding height
     */
    public double getScaledHeight() {
        return getBounds2D().getHeight();
    }

    /**
     * Gets the underlying java.awt.Image.
     * 
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Gets the underlying AffineTransform applied to this sprite.
     * 
     * @return the transform
     */
    public AffineTransform getTransform() {
        return transform;
    }

    /**
     * Resets the sprite's transform to the identity matrix.
     * This effectively removes all scaling, rotation, and translation, placing it at 0,0.
     * 
     * @return This DSprite instance
     */
    public DSprite resetTransform() {
        this.transform.setToIdentity();
        return this;
    }

    /**
     * Overrides the entire transform of this sprite with a custom AffineTransform.
     * 
     * @param at the new transform to apply completely
     * @return This DSprite instance
     */
    public DSprite setTransform(AffineTransform at) {
        if (at != null) {
            this.transform.setTransform(at);
        }
        return this;
    }

    /**
     * Sets the exact absolute X and Y coordinate position of the sprite.
     * 
     * @param x the target absolute X coordinate
     * @param y the target absolute Y coordinate
     * @return This DSprite instance
     */
    public DSprite setPosition(double x, double y) {
        double curX = transform.getTranslateX();
        double curY = transform.getTranslateY();
        return translate(x - curX, y - curY);
    }

    /**
     * Centers the sprite exactly at the given X, Y coordinates, accounting for its base dimensions.
     * 
     * @param x the absolute X coordinate to center on
     * @param y the absolute Y coordinate to center on
     * @return This DSprite instance
     */
    public DSprite centerAt(double x, double y) {
        double targetX = x - (width / 2.0);
        double targetY = y - (height / 2.0);
        return setPosition(targetX, targetY);
    }

    /**
     * Translates (moves) this sprite by the specified delta x and y.
     * 
     * @param dx the distance to move along the X axis
     * @param dy the distance to move along the Y axis
     * @return This DSprite instance
     */
    @Override
    public DSprite translate(double dx, double dy) {
        transform.translate(dx, dy);
        return this;
    }

    /**
     * Scales this sprite by the specified X and Y multipliers.
     * 
     * @param sx the X-axis scale factor
     * @param sy the Y-axis scale factor
     * @return This DSprite instance
     */
    @Override
    public DSprite scale(double sx, double sy) {
        transform.scale(sx, sy);
        return this;
    }

    /**
     * Rotates this sprite around its current origin.
     * 
     * @param angleDegrees the rotation angle in degrees
     * @return This DSprite instance
     */
    @Override
    public DSprite rotate(double angleDegrees) {
        transform.rotate(Math.toRadians(angleDegrees));
        return this;
    }

    /**
     * Rotates this sprite around a specific anchor point.
     * 
     * @param angleDegrees the rotation angle in degrees
     * @param anchorX      the X coordinate of the rotation anchor
     * @param anchorY      the Y coordinate of the rotation anchor
     * @return This DSprite instance
     */
    @Override
    public DSprite rotate(double angleDegrees, double anchorX, double anchorY) {
        transform.rotate(Math.toRadians(angleDegrees), anchorX, anchorY);
        return this;
    }

    /**
     * Flips the sprite horizontally across its own center axis.
     * 
     * @return This DSprite instance
     */
    public DSprite flipHorizontal() {
        transform.translate(width / 2.0, 0);
        transform.scale(-1, 1);
        transform.translate(-width / 2.0, 0);
        return this;
    }

    /**
     * Flips the sprite vertically across its own center axis.
     * 
     * @return This DSprite instance
     */
    public DSprite flipVertical() {
        transform.translate(0, height / 2.0);
        transform.scale(1, -1);
        transform.translate(0, -height / 2.0);
        return this;
    }

    /**
     * Automatically calculates and applies a scale so the sprite exactly matches the target width and height.
     * 
     * @param targetWidth  the target width in pixels
     * @param targetHeight the target height in pixels
     * @return This DSprite instance
     */
    public DSprite scaleTo(double targetWidth, double targetHeight) {
        if (width > 0 && height > 0) {
            double curW = getBounds2D().getWidth();
            double curH = getBounds2D().getHeight();
            if (curW > 0 && curH > 0) {
                scale(targetWidth / curW, targetHeight / curH);
            }
        }
        return this;
    }

    /**
     * Gets the current fill (tint) color of this sprite.
     * 
     * @return the fill color, or null if no tint is applied
     */
    @Override
    public Color getFill() {
        return fillColor;
    }

    /**
     * Sets the fill (tint) color of this sprite.
     * This color acts as an overlay over the image area.
     * 
     * @param c the new fill color
     * @return This DSprite instance
     */
    @Override
    public DSprite setFill(Color c) {
        this.fillColor = c;
        return this;
    }

    /**
     * Gets the current stroke (border) color of this sprite.
     * 
     * @return the stroke color, or null if no border is applied
     */
    @Override
    public Color getStroke() {
        return strokeColor;
    }

    /**
     * Sets the stroke (border) color of this sprite.
     * 
     * @param c the new stroke color
     * @return This DSprite instance
     */
    @Override
    public DSprite setStroke(Color c) {
        this.strokeColor = c;
        return this;
    }

    /**
     * Gets the current stroke width (border thickness) in pixels.
     * 
     * @return the stroke width
     */
    @Override
    public float getStrokeWidth() {
        return strokeWidth;
    }

    /**
     * Sets the stroke width (border thickness) in pixels.
     * 
     * @param width the new stroke width
     * @return This DSprite instance
     */
    @Override
    public DSprite setStrokeWidth(float width) {
        this.strokeWidth = Math.max(0.0f, width);
        return this;
    }

    /**
     * Returns the bounding shape of this sprite, representing its transformed rectangle.
     * 
     * @return the transformed bounding shape
     */
    @Override
    public Shape getShape() {
        Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, width, height);
        return transform.createTransformedShape(rect);
    }

    /**
     * Shears (skews) this sprite by the specified multipliers.
     * 
     * @param shx the shear multiplier for the X axis
     * @param shy the shear multiplier for the Y axis
     * @return This DSprite instance
     */
    @Override
    public DSprite shear(double shx, double shy) {
        transform.shear(shx, shy);
        return this;
    }

    /**
     * Checks if this sprite is currently visible.
     * 
     * @return true if visible, false otherwise
     */
    @Override
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the visibility state of this sprite.
     * 
     * @param visible true to show, false to hide
     * @return This DSprite instance
     */
    @Override
    public DSprite setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    /**
     * Gets the current global opacity alpha of this sprite.
     * 
     * @return the alpha value between 0.0 (transparent) and 1.0 (opaque)
     */
    @Override
    public float getAlpha() {
        return alpha;
    }

    /**
     * Sets the global opacity alpha of this sprite.
     * 
     * @param alpha the new alpha value (0.0 to 1.0)
     * @return This DSprite instance
     */
    @Override
    public DSprite setAlpha(float alpha) {
        this.alpha = Math.max(0.0f, Math.min(1.0f, alpha));
        return this;
    }

    /**
     * Returns the bounding box of this sprite's current transformed geometry.
     * 
     * @return the bounding rectangle
     */
    @Override
    public Rectangle2D getBounds2D() {
        return getShape().getBounds2D();
    }

    /**
     * Tests if the specified absolute coordinates are inside the boundary of this sprite.
     * 
     * @param x the X coordinate to test
     * @param y the Y coordinate to test
     * @return true if the coordinates are inside, false otherwise
     */
    @Override
    public boolean contains(double x, double y) {
        return getShape().contains(x, y);
    }
}
