package com.vidlus.jarch.desk;

/**
 * Interface representing a graphical object that can be mathematically
 * transformed
 * and rendered on a DCanvas by a DAnimator.
 */
public interface DExcited {

    /**
     * Translates (moves) this graphical object by the specified delta x and y.
     * 
     * @param dx the distance to move along the X axis
     * @param dy the distance to move along the Y axis
     * @return This object for fluent chaining
     */
    DExcited translate(double dx, double dy);

    /**
     * Scales this graphical object by the specified multipliers.
     * 
     * @param sx the scale multiplier for the X axis
     * @param sy the scale multiplier for the Y axis
     * @return This object for fluent chaining
     */
    DExcited scale(double sx, double sy);

    /**
     * Rotates this graphical object around its origin (0, 0).
     * 
     * @param angleDegrees the rotation angle in degrees
     * @return This object for fluent chaining
     */
    DExcited rotate(double angleDegrees);

    /**
     * Rotates this graphical object around a specific anchor point.
     * 
     * @param angleDegrees the rotation angle in degrees
     * @param anchorX      the X coordinate of the center of rotation
     * @param anchorY      the Y coordinate of the center of rotation
     * @return This object for fluent chaining
     */
    DExcited rotate(double angleDegrees, double anchorX, double anchorY);

    /**
     * Shears (skews) this graphical object by the specified multipliers.
     * 
     * @param shx the shear multiplier for the X axis
     * @param shy the shear multiplier for the Y axis
     * @return This object for fluent chaining
     */
    DExcited shear(double shx, double shy);

    /**
     * Gets the current fill color of this object.
     * 
     * @return the fill color, or null if it has no fill
     */
    java.awt.Color getFill();

    /**
     * Sets the fill color of this object.
     * 
     * @param c the new fill color
     * @return This object for fluent chaining
     */
    DExcited setFill(java.awt.Color c);

    /**
     * Gets the current stroke (border) color of this object.
     * 
     * @return the stroke color, or null if it has no stroke
     */
    java.awt.Color getStroke();

    /**
     * Sets the stroke (border) color of this object.
     * 
     * @param c the new stroke color
     * @return This object for fluent chaining
     */
    DExcited setStroke(java.awt.Color c);

    /**
     * Gets the current stroke width in pixels.
     * 
     * @return the stroke width
     */
    float getStrokeWidth();

    /**
     * Sets the stroke width in pixels.
     * 
     * @param width the new stroke width (must be &gt;= 0)
     * @return This object for fluent chaining
     */
    DExcited setStrokeWidth(float width);

    /**
     * Retrieves the compiled java.awt.Shape representation of this object's geometry.
     * 
     * @return the shape representation
     */
    java.awt.Shape getShape();

    /**
     * Checks if this object is currently visible.
     * 
     * @return true if visible, false otherwise
     */
    boolean isVisible();

    /**
     * Sets the visibility state of this object.
     * 
     * @param visible true to show, false to hide
     * @return This object for fluent chaining
     */
    DExcited setVisible(boolean visible);

    /**
     * Gets the current global opacity alpha of this object.
     * 
     * @return the alpha value between 0.0 (transparent) and 1.0 (opaque)
     */
    float getAlpha();

    /**
     * Sets the global opacity alpha of this object.
     * 
     * @param alpha the new alpha value (0.0 to 1.0)
     * @return This object for fluent chaining
     */
    DExcited setAlpha(float alpha);

    /**
     * Returns the high-precision bounding box of this object's geometry.
     * 
     * @return the bounding rectangle
     */
    java.awt.geom.Rectangle2D getBounds2D();

    /**
     * Tests if the specified coordinates are inside the boundary of this object.
     * 
     * @param x the X coordinate to test
     * @param y the Y coordinate to test
     * @return true if the coordinates are inside the geometry, false otherwise
     */
    boolean contains(double x, double y);
}
