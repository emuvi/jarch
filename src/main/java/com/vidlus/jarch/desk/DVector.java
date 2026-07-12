package com.vidlus.jarch.desk;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/**
 * A fluent builder for creating complex vector graphics, paths, and compound
 * shapes.
 * DVectors can be drawn or filled on a DCanvas efficiently.
 */
public class DVector implements DExcited {

    private final Path2D.Double path;

    private java.awt.Color fillColor = null;
    private java.awt.Color strokeColor = null;
    private float strokeWidth = 1.0f;

    private boolean visible = true;
    private float alpha = 1.0f;

    /**
     * Creates a new, empty DVector.
     */
    public DVector() {
        this.path = new Path2D.Double();
    }

    /**
     * Creates a new DVector initialized with an existing Shape.
     * 
     * @param shape the initial shape
     */
    public DVector(Shape shape) {
        this.path = new Path2D.Double(shape);
    }

    /**
     * Creates a deep copy of this DVector.
     * 
     * @return a new DVector with identical path and style properties
     */
    public DVector copy() {
        DVector v = new DVector(this.path);
        v.setFill(this.fillColor);
        v.setStroke(this.strokeColor);
        v.setStrokeWidth(this.strokeWidth);
        v.setAlpha(this.alpha);
        v.setVisible(this.visible);
        return v;
    }

    /**
     * Gets the current fill color of this vector.
     * 
     * @return the fill color, or null if it has no fill
     */
    @Override
    public java.awt.Color getFill() {
        return fillColor;
    }

    /**
     * Sets the fill color of this vector.
     * 
     * @param c the new fill color
     * @return This DVector instance
     */
    @Override
    public DVector setFill(java.awt.Color c) {
        this.fillColor = c;
        return this;
    }

    /**
     * Gets the current stroke (border) color of this vector.
     * 
     * @return the stroke color, or null if it has no stroke
     */
    @Override
    public java.awt.Color getStroke() {
        return strokeColor;
    }

    /**
     * Sets the stroke (border) color of this vector.
     * 
     * @param c the new stroke color
     * @return This DVector instance
     */
    @Override
    public DVector setStroke(java.awt.Color c) {
        this.strokeColor = c;
        return this;
    }

    /**
     * Gets the current stroke width in pixels.
     * 
     * @return the stroke width
     */
    @Override
    public float getStrokeWidth() {
        return strokeWidth;
    }

    /**
     * Sets the stroke width in pixels.
     * 
     * @param width the new stroke width (must be &gt;= 0)
     * @return This DVector instance
     */
    @Override
    public DVector setStrokeWidth(float width) {
        this.strokeWidth = Math.max(0.0f, width);
        return this;
    }

    // ==========================================
    // --- Core Path Commands
    // ==========================================

    /**
     * Clears the current path, removing all points and primitives.
     * 
     * @return This DVector instance
     */
    public DVector reset() {
        path.reset();
        return this;
    }

    /**
     * Checks if this vector path is currently empty (contains no geometry).
     * 
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return path.getCurrentPoint() == null;
    }

    /**
     * Gets the most recently added point on the path.
     * 
     * @return the current point, or null if the path is empty
     */
    public java.awt.geom.Point2D getCurrentPoint() {
        return path.getCurrentPoint();
    }

    /**
     * Moves the current drawing point to (x, y) without drawing a line.
     * 
     * @param x the x coordinate to move to
     * @param y the y coordinate to move to
     * @return This DVector instance
     */
    public DVector moveTo(double x, double y) {
        path.moveTo(x, y);
        return this;
    }

    /**
     * Draws a straight line from the current point to (x, y).
     * 
     * @param x the x coordinate to draw to
     * @param y the y coordinate to draw to
     * @return This DVector instance
     */
    public DVector lineTo(double x, double y) {
        path.lineTo(x, y);
        return this;
    }

    /**
     * Draws a quadratic bezier curve from the current point to (x, y) using (cx,
     * cy) as the control point.
     * 
     * @param cx the x coordinate of the bezier control point
     * @param cy the y coordinate of the bezier control point
     * @param x  the x coordinate of the final destination
     * @param y  the y coordinate of the final destination
     * @return This DVector instance
     */
    public DVector quadTo(double cx, double cy, double x, double y) {
        path.quadTo(cx, cy, x, y);
        return this;
    }

    /**
     * Draws a cubic bezier curve from the current point to (x, y) using two control
     * points.
     * 
     * @param cx1 the x coordinate of the first control point
     * @param cy1 the y coordinate of the first control point
     * @param cx2 the x coordinate of the second control point
     * @param cy2 the y coordinate of the second control point
     * @param x   the x coordinate of the final destination
     * @param y   the y coordinate of the final destination
     * @return This DVector instance
     */
    public DVector curveTo(double cx1, double cy1, double cx2, double cy2, double x, double y) {
        path.curveTo(cx1, cy1, cx2, cy2, x, y);
        return this;
    }

    /**
     * Closes the current path by drawing a straight line back to the last moveTo
     * coordinates.
     * 
     * @return This DVector instance
     */
    public DVector closePath() {
        path.closePath();
        return this;
    }

    // ==========================================
    // --- Primitive Additions
    // ==========================================

    /**
     * Adds a single straight line segment between two explicit points.
     * 
     * @param x1 the starting x coordinate
     * @param y1 the starting y coordinate
     * @param x2 the ending x coordinate
     * @param y2 the ending y coordinate
     * @return This DVector instance
     */
    public DVector addLine(double x1, double y1, double x2, double y2) {
        path.append(new java.awt.geom.Line2D.Double(x1, y1, x2, y2), false);
        return this;
    }

    /**
     * Adds a circle to the vector path based on a center point and radius.
     * 
     * @param cx     the x coordinate of the center
     * @param cy     the y coordinate of the center
     * @param radius the radius of the circle
     * @return This DVector instance
     */
    public DVector addCircle(double cx, double cy, double radius) {
        path.append(new Ellipse2D.Double(cx - radius, cy - radius, radius * 2, radius * 2), false);
        return this;
    }

    /**
     * Adds a rectangle to the vector path.
     * 
     * @param x      the x coordinate of the rectangle's top-left corner
     * @param y      the y coordinate of the rectangle's top-left corner
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @return This DVector instance
     */
    public DVector addRect(double x, double y, double width, double height) {
        path.append(new Rectangle2D.Double(x, y, width, height), false);
        return this;
    }

    /**
     * Adds an ellipse (or circle) to the vector path.
     * 
     * @param x      the x coordinate of the bounding rectangle's top-left corner
     * @param y      the y coordinate of the bounding rectangle's top-left corner
     * @param width  the width of the bounding rectangle
     * @param height the height of the bounding rectangle
     * @return This DVector instance
     */
    public DVector addEllipse(double x, double y, double width, double height) {
        path.append(new Ellipse2D.Double(x, y, width, height), false);
        return this;
    }

    /**
     * Adds a rounded rectangle to the vector path.
     * 
     * @param x         the x coordinate of the rectangle's top-left corner
     * @param y         the y coordinate of the rectangle's top-left corner
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     * @param arcWidth  the width of the corner rounding arc
     * @param arcHeight the height of the corner rounding arc
     * @return This DVector instance
     */
    public DVector addRoundRect(double x, double y, double width, double height, double arcWidth, double arcHeight) {
        path.append(new RoundRectangle2D.Double(x, y, width, height, arcWidth, arcHeight), false);
        return this;
    }

    /**
     * Adds an arc (a partial ellipse) to the vector path.
     * 
     * @param x          the x coordinate of the bounding rectangle
     * @param y          the y coordinate of the bounding rectangle
     * @param width      the width of the bounding rectangle
     * @param height     the height of the bounding rectangle
     * @param startAngle the starting angle of the arc in degrees
     * @param arcExtent  the angular extent of the arc in degrees
     * @param type       the type of arc (e.g., Arc2D.OPEN, Arc2D.CHORD, or
     *                   Arc2D.PIE)
     * @return This DVector instance
     */
    public DVector addArc(double x, double y, double width, double height, double startAngle, double arcExtent,
            int type) {
        path.append(new Arc2D.Double(x, y, width, height, startAngle, arcExtent, type), false);
        return this;
    }

    /**
     * Appends another DVector's path to this one.
     * 
     * @param other the other DVector to append
     * @return This DVector instance
     */
    public DVector append(DVector other) {
        if (other != null) {
            path.append(other.getShape(), false);
        }
        return this;
    }

    /**
     * Converts a text string into raw vector geometry and appends it to this path.
     * Once converted, the text becomes a scalable, animatable path outline.
     * 
     * @param text the string of text to convert
     * @param font the font to use for generating the text outline
     * @param x    the starting X coordinate (baseline)
     * @param y    the starting Y coordinate (baseline)
     * @return This DVector instance
     */
    public DVector addText(String text, java.awt.Font font, double x, double y) {
        if (text != null && !text.isEmpty() && font != null) {
            java.awt.font.FontRenderContext frc = new java.awt.font.FontRenderContext(null, true, true);
            java.awt.font.GlyphVector gv = font.createGlyphVector(frc, text);
            java.awt.Shape glyphShape = gv.getOutline((float) x, (float) y);
            path.append(glyphShape, false);
        }
        return this;
    }

    /**
     * Automatically calculates bezier control points to draw a perfectly smooth
     * spline
     * passing through all the provided coordinates.
     * 
     * @param xPoints an array of X coordinates
     * @param yPoints an array of Y coordinates
     * @return This DVector instance
     */
    public DVector addSmoothSpline(double[] xPoints, double[] yPoints) {
        if (xPoints == null || yPoints == null || xPoints.length < 2 || xPoints.length != yPoints.length) {
            return this;
        }

        path.moveTo(xPoints[0], yPoints[0]);

        if (xPoints.length == 2) {
            path.lineTo(xPoints[1], yPoints[1]);
            return this;
        }

        double tension = 0.2; // 0 to 1, higher is tighter, lower is looser

        for (int i = 0; i < xPoints.length - 1; i++) {
            double p0x = (i == 0) ? xPoints[0] : xPoints[i - 1];
            double p0y = (i == 0) ? yPoints[0] : yPoints[i - 1];

            double p1x = xPoints[i];
            double p1y = yPoints[i];

            double p2x = xPoints[i + 1];
            double p2y = yPoints[i + 1];

            double p3x = (i + 2 < xPoints.length) ? xPoints[i + 2] : p2x;
            double p3y = (i + 2 < yPoints.length) ? yPoints[i + 2] : p2y;

            double cp1x = p1x + (p2x - p0x) * tension;
            double cp1y = p1y + (p2y - p0y) * tension;

            double cp2x = p2x - (p3x - p1x) * tension;
            double cp2y = p2y - (p3y - p1y) * tension;

            path.curveTo(cp1x, cp1y, cp2x, cp2y, p2x, p2y);
        }

        return this;
    }

    // ==========================================
    // --- Constructive Solid Geometry (CSG)
    // ==========================================

    /**
     * Mathematically merges (unions) another DVector into this one, creating a
     * single solid path.
     * 
     * @param other the DVector to merge
     * @return This DVector instance
     */
    public DVector union(DVector other) {
        if (other != null) {
            Area a1 = new Area(this.path);
            Area a2 = new Area(other.getShape());
            a1.add(a2);
            this.path.reset();
            this.path.append(a1, false);
        }
        return this;
    }

    /**
     * Subtracts (cuts out) another DVector's shape from this one, like a cookie
     * cutter.
     * 
     * @param other the DVector to subtract
     * @return This DVector instance
     */
    public DVector subtract(DVector other) {
        if (other != null) {
            Area a1 = new Area(this.path);
            Area a2 = new Area(other.getShape());
            a1.subtract(a2);
            this.path.reset();
            this.path.append(a1, false);
        }
        return this;
    }

    /**
     * Intersects another DVector with this one, leaving only the overlapping
     * regions of both paths.
     * 
     * @param other the DVector to intersect
     * @return This DVector instance
     */
    public DVector intersect(DVector other) {
        if (other != null) {
            Area a1 = new Area(this.path);
            Area a2 = new Area(other.getShape());
            a1.intersect(a2);
            this.path.reset();
            this.path.append(a1, false);
        }
        return this;
    }

    /**
     * Performs an exclusive-or (XOR) operation with another DVector,
     * leaving only the non-overlapping regions of both paths.
     * 
     * @param other the DVector to XOR
     * @return This DVector instance
     */
    public DVector exclusiveOr(DVector other) {
        if (other != null) {
            Area a1 = new Area(this.path);
            Area a2 = new Area(other.getShape());
            a1.exclusiveOr(a2);
            this.path.reset();
            this.path.append(a1, false);
        }
        return this;
    }

    // ==========================================
    // --- Transformations
    // ==========================================

    /**
     * Applies an arbitrary AffineTransform directly to the vector's path.
     * 
     * @param at the transform to apply
     * @return This DVector instance
     */
    public DVector transform(AffineTransform at) {
        if (at != null) {
            path.transform(at);
        }
        return this;
    }

    /**
     * Flips (mirrors) the vector path horizontally across a specific X coordinate.
     * 
     * @param centerX the X coordinate to flip across
     * @return This DVector instance
     */
    public DVector flipHorizontal(double centerX) {
        path.transform(AffineTransform.getTranslateInstance(centerX, 0));
        path.transform(AffineTransform.getScaleInstance(-1, 1));
        path.transform(AffineTransform.getTranslateInstance(-centerX, 0));
        return this;
    }

    /**
     * Flips (mirrors) the vector path vertically across a specific Y coordinate.
     * 
     * @param centerY the Y coordinate to flip across
     * @return This DVector instance
     */
    public DVector flipVertical(double centerY) {
        path.transform(AffineTransform.getTranslateInstance(0, centerY));
        path.transform(AffineTransform.getScaleInstance(1, -1));
        path.transform(AffineTransform.getTranslateInstance(0, -centerY));
        return this;
    }

    /**
     * Translates (moves) the entire vector path by dx, dy.
     * 
     * @return This DVector instance
     */
    @Override
    public DVector translate(double dx, double dy) {
        path.transform(AffineTransform.getTranslateInstance(dx, dy));
        return this;
    }

    /**
     * Scales the vector path by sx and sy.
     * 
     * @return This DVector instance
     */
    @Override
    public DVector scale(double sx, double sy) {
        path.transform(AffineTransform.getScaleInstance(sx, sy));
        return this;
    }

    /**
     * Rotates the vector path around the origin (0, 0).
     * 
     * @param angleDegrees the rotation angle in degrees
     * @return This DVector instance
     */
    @Override
    public DVector rotate(double angleDegrees) {
        path.transform(AffineTransform.getRotateInstance(Math.toRadians(angleDegrees)));
        return this;
    }

    /**
     * Rotates the vector path around a specific anchor point.
     * 
     * @param angleDegrees the rotation angle in degrees
     * @return This DVector instance
     */
    @Override
    public DVector rotate(double angleDegrees, double anchorX, double anchorY) {
        path.transform(AffineTransform.getRotateInstance(Math.toRadians(angleDegrees), anchorX, anchorY));
        return this;
    }

    // ==========================================
    // --- Output
    // ==========================================

    /**
     * Returns the compiled java.awt.Shape representation of this vector.
     * 
     * @return the shape representation of the path
     */
    @Override
    public Shape getShape() {
        return path;
    }

    /**
     * Shears (skews) this vector path by the specified multipliers.
     * 
     * @param shx the shear multiplier for the X axis
     * @param shy the shear multiplier for the Y axis
     * @return This DVector instance
     */
    @Override
    public DVector shear(double shx, double shy) {
        path.transform(AffineTransform.getShearInstance(shx, shy));
        return this;
    }

    /**
     * Checks if this vector is currently visible.
     * 
     * @return true if visible, false otherwise
     */
    @Override
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the visibility state of this vector.
     * 
     * @param visible true to show, false to hide
     * @return This DVector instance
     */
    @Override
    public DVector setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    /**
     * Gets the current global opacity alpha of this vector.
     * 
     * @return the alpha value between 0.0 (transparent) and 1.0 (opaque)
     */
    @Override
    public float getAlpha() {
        return alpha;
    }

    /**
     * Sets the global opacity alpha of this vector.
     * 
     * @param alpha the new alpha value (0.0 to 1.0)
     * @return This DVector instance
     */
    @Override
    public DVector setAlpha(float alpha) {
        this.alpha = Math.max(0.0f, Math.min(1.0f, alpha));
        return this;
    }

    /**
     * Returns the high-precision bounding box of this vector's path geometry.
     * 
     * @return the bounding rectangle
     */
    @Override
    public Rectangle2D getBounds2D() {
        return path.getBounds2D();
    }

    /**
     * Tests if the specified coordinates are inside the boundary of this vector path.
     * 
     * @param x the X coordinate to test
     * @param y the Y coordinate to test
     * @return true if the coordinates are inside the geometry, false otherwise
     */
    @Override
    public boolean contains(double x, double y) {
        return path.contains(x, y);
    }

    // ==========================================
    // --- Predefined Vector Generators
    // ==========================================

    /**
     * Generates a plus-shaped cross centered at 0,0.
     * 
     * @param width     the total width of the horizontal bar
     * @param height    the total height of the vertical bar
     * @param thickness the thickness of the bars
     * @return a new DVector representing the cross
     */
    public static DVector createCross(double width, double height, double thickness) {
        DVector v = new DVector();
        double w2 = width / 2.0;
        double h2 = height / 2.0;
        double t2 = thickness / 2.0;
        
        v.moveTo(-w2, -t2);
        v.lineTo(-t2, -t2);
        v.lineTo(-t2, -h2);
        v.lineTo(t2, -h2);
        v.lineTo(t2, -t2);
        v.lineTo(w2, -t2);
        v.lineTo(w2, t2);
        v.lineTo(t2, t2);
        v.lineTo(t2, h2);
        v.lineTo(-t2, h2);
        v.lineTo(-t2, t2);
        v.lineTo(-w2, t2);
        
        return v.closePath();
    }

    /**
     * Generates a crescent moon shape.
     * 
     * @param radius      the radius of the outer circle
     * @param innerOffset the horizontal offset of the inner circle (defines the thickness of the moon)
     * @return a new DVector representing the crescent
     */
    public static DVector createCrescent(double radius, double innerOffset) {
        DVector outer = new DVector();
        outer.addCircle(0, 0, radius);
        
        DVector inner = new DVector();
        inner.addCircle(innerOffset, 0, radius);
        
        return outer.subtract(inner);
    }

    /**
     * Generates a donut (ring) shape.
     * 
     * @param outerRadius the radius of the outer circle
     * @param innerRadius the radius of the inner hole
     * @return a new DVector representing the donut
     */
    public static DVector createDonut(double outerRadius, double innerRadius) {
        DVector outer = new DVector();
        outer.addCircle(0, 0, outerRadius);
        
        DVector inner = new DVector();
        inner.addCircle(0, 0, innerRadius);
        
        return outer.subtract(inner);
    }

    /**
     * Generates a regular polygon (e.g., triangle, hexagon) centered at 0,0.
     * 
     * @param sides  the number of sides (must be >= 3)
     * @param radius the distance from the center to each vertex
     * @return a new DVector representing the polygon
     */
    public static DVector createPolygon(int sides, double radius) {
        DVector v = new DVector();
        if (sides < 3)
            return v;
        double angleStep = Math.PI * 2 / sides;
        v.moveTo(radius * Math.cos(0), radius * Math.sin(0));
        for (int i = 1; i < sides; i++) {
            v.lineTo(radius * Math.cos(i * angleStep), radius * Math.sin(i * angleStep));
        }
        return v.closePath();
    }

    /**
     * Generates a multi-pointed star centered at 0,0.
     * 
     * @param points      the number of points on the star
     * @param innerRadius the distance from the center to the inner vertices
     * @param outerRadius the distance from the center to the outer tips
     * @return a new DVector representing the star
     */
    public static DVector createStar(int points, double innerRadius, double outerRadius) {
        DVector v = new DVector();
        double angleStep = Math.PI / points;
        double startAngle = -Math.PI / 2; // Point pointing straight up
        v.moveTo(outerRadius * Math.cos(startAngle), outerRadius * Math.sin(startAngle));
        for (int i = 1; i < points * 2; i++) {
            double r = (i % 2 == 0) ? outerRadius : innerRadius;
            double a = startAngle + i * angleStep;
            v.lineTo(r * Math.cos(a), r * Math.sin(a));
        }
        return v.closePath();
    }

    /**
     * Generates a classic heart shape, fitting roughly within a bounding box of
     * size (width, height).
     * The top-center of the cleft sits near y=0, with the point at the bottom.
     * 
     * @param width  the overall width of the heart
     * @param height the overall height of the heart
     * @return a new DVector representing the heart
     */
    public static DVector createHeart(double width, double height) {
        DVector v = new DVector();
        v.moveTo(0, height / 4.0);
        v.curveTo(0, 0, -width / 2.0, 0, -width / 2.0, height / 4.0);
        v.curveTo(-width / 2.0, height / 2.0, 0, height * 0.75, 0, height);
        v.curveTo(0, height * 0.75, width / 2.0, height / 2.0, width / 2.0, height / 4.0);
        v.curveTo(width / 2.0, 0, 0, 0, 0, height / 4.0);
        return v.closePath();
    }

    /**
     * Generates an arrow pointing to the right (from 0,0 to length,0).
     * 
     * @param length     the total length of the arrow from base to tip
     * @param headWidth  the total width of the arrowhead
     * @param headLength the length of the arrowhead from the tip to its back
     * @return a new DVector representing the arrow
     */
    public static DVector createArrow(double length, double headWidth, double headLength) {
        DVector v = new DVector();
        double stemWidth = headWidth * 0.4;
        v.moveTo(0, -stemWidth / 2);
        v.lineTo(length - headLength, -stemWidth / 2);
        v.lineTo(length - headLength, -headWidth / 2);
        v.lineTo(length, 0);
        v.lineTo(length - headLength, headWidth / 2);
        v.lineTo(length - headLength, stemWidth / 2);
        v.lineTo(0, stemWidth / 2);
        return v.closePath();
    }

    /**
     * Generates a mechanical gear shape centered at 0,0.
     * 
     * @param teeth       the number of teeth on the gear
     * @param innerRadius the radius of the inner valleys between teeth
     * @param outerRadius the radius of the outer tips of the teeth
     * @return a new DVector representing the gear
     */
    public static DVector createGear(int teeth, double innerRadius, double outerRadius) {
        DVector v = new DVector();
        if (teeth < 3)
            return v;

        int steps = teeth * 2;
        double angleStep = Math.PI * 2 / steps;

        v.moveTo(outerRadius * Math.cos(0), outerRadius * Math.sin(0));

        for (int i = 1; i < steps; i++) {
            double radius = (i % 2 == 0) ? outerRadius : innerRadius;
            v.lineTo(radius * Math.cos(i * angleStep), radius * Math.sin(i * angleStep));
        }

        return v.closePath();
    }

    /**
     * Generates a comic-book style speech bubble.
     * 
     * @param width      the width of the bubble
     * @param height     the height of the bubble
     * @param tailLength the length of the speech tail extending downwards
     * @return a new DVector representing the speech bubble
     */
    public static DVector createSpeechBubble(double width, double height, double tailLength) {
        DVector v = new DVector();
        double arc = height * 0.4;

        // Start with a rounded rectangle
        v.addRoundRect(0, 0, width, height, arc, arc);

        // Create the tail pointing down from the bottom-left area
        DVector tail = new DVector();
        tail.moveTo(width * 0.2, height - (arc / 2)); // slight overlap to ensure solid union
        tail.lineTo(width * 0.2, height + tailLength);
        tail.lineTo(width * 0.4, height - (arc / 2));
        tail.closePath();

        // Mathematically merge the tail perfectly into the bubble
        v.union(tail);

        return v;
    }
}
