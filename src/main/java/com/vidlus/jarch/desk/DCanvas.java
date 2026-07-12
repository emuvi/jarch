package com.vidlus.jarch.desk;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * A specialized DPane designed for custom drawing and graphics operations.
 * It automatically enables high-quality anti-aliasing and allows drawing via
 * fluent callbacks.
 */
public class DCanvas extends DPane {

    /**
     * Functional interface for custom canvas drawing.
     */
    public interface DCanvasPainter {
        /**
         * Called when the canvas needs to be repainted.
         * 
         * @param g2d    the Graphics2D context (with anti-aliasing already configured
         *               if enabled)
         * @param width  the width of the canvas
         * @param height the height of the canvas
         */
        void paint(Graphics2D g2d, int width, int height);
    }

    private final List<DCanvasPainter> painters = new ArrayList<>();
    private final List<DExcited> trackedActors = new ArrayList<>();
    private final java.util.Map<DExcited, DCanvasPainter> actorPainters = new java.util.HashMap<>();
    private boolean highQuality = true;

    // State stacks for pushState / popState
    private final transient java.util.Deque<java.awt.geom.AffineTransform> transformStack = new java.util.ArrayDeque<>();
    private final transient java.util.Deque<java.awt.Composite> compositeStack = new java.util.ArrayDeque<>();
    private final transient java.util.Deque<java.awt.Color> colorStack = new java.util.ArrayDeque<>();
    private final transient java.util.Deque<java.awt.Stroke> strokeStack = new java.util.ArrayDeque<>();
    private final transient java.util.Deque<java.awt.Font> fontStack = new java.util.ArrayDeque<>();
    private final transient java.util.Deque<java.awt.Shape> clipStack = new java.util.LinkedList<>(); // LinkedList
                                                                                                      // allows nulls
                                                                                                      // for clip

    public DCanvas() {
        super();
        setOpaque(false); // Transparent by default to allow easy overlaying, unless background is
                          // explicitly set
    }

    /**
     * Adds a custom painter callback that will be executed whenever the canvas
     * repaints.
     * Multiple painters can be added; they will draw in the order they were added.
     * 
     * @param painter the painter callback
     * @return This DCanvas instance.
     */
    public DCanvas onPaint(DCanvasPainter painter) {
        if (painter != null) {
            painters.add(painter);
            repaint();
        }
        return this;
    }

    /**
     * Enables or disables high-quality rendering (anti-aliasing) for this canvas.
     * Enabled by default.
     * 
     * @param highQuality true to enable anti-aliasing
     * @return This DCanvas instance.
     */
    public DCanvas highQuality(boolean highQuality) {
        this.highQuality = highQuality;
        repaint();
        return this;
    }

    /**
     * Clears all registered painters from this canvas.
     * 
     * @return This DCanvas instance.
     */
    public DCanvas clearPainters() {
        painters.clear();
        trackedActors.clear();
        actorPainters.clear();
        repaint();
        return this;
    }

    /**
     * Checks all currently drawn actors to see if any intersect with the specified
     * point.
     * Returns the top-most actor (the one drawn last) that contains the point.
     * 
     * @param x the X coordinate to test
     * @param y the Y coordinate to test
     * @return the DAnimatable at that point, or null if none was clicked
     */
    public DExcited getActorAt(double x, double y) {
        for (int i = trackedActors.size() - 1; i >= 0; i--) {
            DExcited a = trackedActors.get(i);
            if (a != null && a.isVisible() && a.contains(x, y)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Removes a specific actor from the canvas.
     * 
     * @param actor the DAnimatable to remove
     * @return This DCanvas instance.
     */
    public DCanvas remove(DExcited actor) {
        if (trackedActors.remove(actor)) {
            DCanvasPainter p = actorPainters.remove(actor);
            if (p != null) {
                painters.remove(p);
            }
            repaint();
        }
        return this;
    }

    /**
     * Brings the specified actor to the front of all other drawn elements.
     * 
     * @param actor the DAnimatable to bring to the front
     * @return This DCanvas instance for fluent chaining
     */
    public DCanvas bringToFront(DExcited actor) {
        if (trackedActors.remove(actor)) {
            trackedActors.add(actor);
            DCanvasPainter p = actorPainters.get(actor);
            if (p != null) {
                painters.remove(p);
                painters.add(p);
            }
            repaint();
        }
        return this;
    }

    /**
     * Sends the specified actor to the back, behind all other drawn elements.
     * 
     * @param actor the DAnimatable to send to the back
     * @return This DCanvas instance for fluent chaining
     */
    public DCanvas sendToBack(DExcited actor) {
        if (trackedActors.remove(actor)) {
            trackedActors.add(0, actor);
            DCanvasPainter p = actorPainters.get(actor);
            if (p != null) {
                painters.remove(p);
                painters.add(0, p);
            }
            repaint();
        }
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Draw the background if opaque
        super.paintComponent(g);

        if (painters.isEmpty()) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g.create();

        if (highQuality) {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        }

        int w = getWidth();
        int h = getHeight();

        // Clear state stacks before paint cycle
        transformStack.clear();
        compositeStack.clear();
        colorStack.clear();
        strokeStack.clear();
        fontStack.clear();
        clipStack.clear();

        for (DCanvasPainter painter : painters) {
            painter.paint(g2d, w, h);
        }

        g2d.dispose();
    }

    // ==========================================
    // --- Core Rendering & Lifecycle
    // ==========================================

    /**
     * Clears all drawing instructions from the canvas. Alias for clearPainters().
     * 
     * @return This DCanvas instance.
     */
    public DCanvas clear() {
        return clearPainters();
    }

    // ==========================================
    // --- State Management & Transformations
    // ==========================================

    /**
     * Pushes the current Graphics2D state (Transform, Composite, Color, Stroke,
     * Font) onto a stack.
     * Use popState() to restore it later. Crucial for isolated drawing routines.
     * 
     * @return This DCanvas instance.
     */
    public DCanvas pushState() {
        return onPaint((g2d, w, h) -> {
            transformStack.push(g2d.getTransform());
            compositeStack.push(g2d.getComposite());
            colorStack.push(g2d.getColor());
            strokeStack.push(g2d.getStroke());
            fontStack.push(g2d.getFont());
            clipStack.push(g2d.getClip());
        });
    }

    /**
     * Pops the last saved Graphics2D state from the stack and restores it.
     * 
     * @return This DCanvas instance.
     */
    public DCanvas popState() {
        return onPaint((g2d, w, h) -> {
            if (!transformStack.isEmpty())
                g2d.setTransform(transformStack.pop());
            if (!compositeStack.isEmpty())
                g2d.setComposite(compositeStack.pop());
            if (!colorStack.isEmpty())
                g2d.setColor(colorStack.pop());
            if (!strokeStack.isEmpty())
                g2d.setStroke(strokeStack.pop());
            if (!fontStack.isEmpty())
                g2d.setFont(fontStack.pop());
            if (!clipStack.isEmpty())
                g2d.setClip(clipStack.pop());
        });
    }

    /**
     * Sets the opacity alpha value for all subsequent drawing operations.
     * 
     * @param alpha the opacity from 0.0f (transparent) to 1.0f (opaque)
     * @return This DCanvas instance.
     */
    public DCanvas alpha(float alpha) {
        return onPaint((g2d, w, h) -> {
            g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, alpha));
        });
    }

    /**
     * Defines blend modes for compositing overlapping graphics.
     */
    public enum BlendMode {
        NORMAL(java.awt.AlphaComposite.SRC_OVER),
        ADD(java.awt.AlphaComposite.SRC_OVER), // Standard Java2D doesn't have native ADD without custom Composite,
                                               // fallback to SRC_OVER
        CLEAR(java.awt.AlphaComposite.CLEAR),
        SRC_IN(java.awt.AlphaComposite.SRC_IN),
        SRC_OUT(java.awt.AlphaComposite.SRC_OUT),
        DST_IN(java.awt.AlphaComposite.DST_IN),
        DST_OUT(java.awt.AlphaComposite.DST_OUT),
        XOR(java.awt.AlphaComposite.XOR);

        final int rule;

        BlendMode(int rule) {
            this.rule = rule;
        }
    }

    /**
     * Sets the composite blend mode for all subsequent drawing operations.
     * 
     * @param mode  the blend mode to apply
     * @param alpha the opacity from 0.0f to 1.0f
     * @return This DCanvas instance.
     */
    public DCanvas setBlendMode(BlendMode mode, float alpha) {
        return onPaint((g2d, w, h) -> {
            g2d.setComposite(java.awt.AlphaComposite.getInstance(mode.rule, alpha));
        });
    }

    /**
     * Translates the origin of the graphics context to the point (tx, ty) for all
     * subsequent drawing.
     * 
     * @param tx the x distance to translate
     * @param ty the y distance to translate
     * @return This DCanvas instance.
     */
    public DCanvas translate(double tx, double ty) {
        return onPaint((g2d, w, h) -> g2d.translate(tx, ty));
    }

    /**
     * Rotates the graphics context by the specified angle for all subsequent
     * drawing.
     * 
     * @param theta the angle of rotation in radians
     * @return This DCanvas instance.
     */
    public DCanvas rotate(double theta) {
        return onPaint((g2d, w, h) -> g2d.rotate(theta));
    }

    /**
     * Rotates the graphics context by the specified angle around a specific point
     * for all subsequent drawing.
     * 
     * @param theta the angle of rotation in radians
     * @param x     the x coordinate of the origin of the rotation
     * @param y     the y coordinate of the origin of the rotation
     * @return This DCanvas instance.
     */
    public DCanvas rotate(double theta, double x, double y) {
        return onPaint((g2d, w, h) -> g2d.rotate(theta, x, y));
    }

    /**
     * Scales the graphics context for all subsequent drawing.
     * 
     * @param sx the amount by which X coordinates are multiplied
     * @param sy the amount by which Y coordinates are multiplied
     * @return This DCanvas instance.
     */
    public DCanvas scale(double sx, double sy) {
        return onPaint((g2d, w, h) -> g2d.scale(sx, sy));
    }

    /**
     * Intersects the current clip with the specified shape. All subsequent drawing
     * will be constrained to the inside of this shape.
     * 
     * @param clipShape the shape to clip to
     * @return This DCanvas instance.
     */
    public DCanvas clip(java.awt.Shape clipShape) {
        return onPaint((g2d, w, h) -> g2d.clip(clipShape));
    }

    /**
     * Sets the clipping region to the specified rectangle.
     * All subsequent drawing will be constrained to the inside of this rectangle.
     * 
     * @param x      the x coordinate of the clipping rectangle
     * @param y      the y coordinate of the clipping rectangle
     * @param width  the width of the clipping rectangle
     * @param height the height of the clipping rectangle
     * @return This DCanvas instance.
     */
    public DCanvas setClipRect(int x, int y, int width, int height) {
        return onPaint((g2d, w, h) -> g2d.setClip(x, y, width, height));
    }

    /**
     * Sets the clipping region to an arbitrary vector shape.
     * All subsequent drawing will be constrained to the inside of this shape.
     * 
     * @param shape the shape to clip to
     * @return This DCanvas instance.
     */
    public DCanvas setClipShape(java.awt.Shape shape) {
        return onPaint((g2d, w, h) -> g2d.setClip(shape));
    }

    /**
     * Clears any active clipping region, allowing drawing across the entire canvas.
     * 
     * @return This DCanvas instance.
     */
    public DCanvas clearClip() {
        return onPaint((g2d, w, h) -> g2d.setClip(null));
    }

    // ==========================================
    // --- Basic Primitive Drawing
    // ==========================================

    /**
     * Fills the entire canvas background with a specific color.
     * 
     * @param color the background color
     * @return This DCanvas instance.
     */
    public DCanvas fillBackground(java.awt.Color color) {
        return onPaint((g2d, w, h) -> {
            if (color != null) {
                g2d.setColor(color);
                g2d.fillRect(0, 0, w, h);
            }
        });
    }

    /**
     * Instantly clears the entire bounds of the canvas with a solid color.
     * 
     * @param color the solid background color to apply
     * @return This DCanvas instance.
     */
    public DCanvas clear(java.awt.Color color) {
        return fillBackground(color);
    }

    /**
     * Adds a painter that draws the outline of a DVector using the specified color
     * and thickness.
     * 
     * @param vector    the DVector to outline
     * @param color     the color of the stroke
     * @param thickness the thickness of the stroke
     * @return This DCanvas instance.
     */
    public DCanvas drawVector(DVector vector, java.awt.Color color, float thickness) {
        if (vector != null) {
            onPaint((g2d, w, h) -> {
                g2d.setColor(color);
                g2d.setStroke(new java.awt.BasicStroke(thickness));
                g2d.draw(vector.getShape());
            });
        }
        return this;
    }

    /**
     * Adds a painter that fills a DVector with the specified color.
     * 
     * @param vector the DVector to fill
     * @param color  the fill color
     * @return This DCanvas instance.
     */
    public DCanvas fillVector(DVector vector, java.awt.Color color) {
        if (vector != null) {
            onPaint((g2d, w, h) -> {
                g2d.setColor(color);
                g2d.fill(vector.getShape());
            });
        }
        return this;
    }

    /**
     * Adds a painter that draws a line between two points with the specified color
     * and thickness.
     */
    public DCanvas drawLine(int x1, int y1, int x2, int y2, java.awt.Color color, float thickness) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new java.awt.BasicStroke(thickness));
            g2d.drawLine(x1, y1, x2, y2);
        });
    }

    /**
     * Adds a painter that draws a DAnimatable actor (DVector or DSprite) on the
     * canvas.
     * Automatically reads the actor's internal properties and flawlessly renders
     * it!
     * 
     * @param actor the DAnimatable to draw
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas draw(DExcited actor) {
        if (actor != null && !trackedActors.contains(actor)) {
            trackedActors.add(actor);
            DCanvasPainter painter = (g2d, w, h) -> {
                if (!actor.isVisible()) {
                    return;
                }

                java.awt.Composite oldComposite = null;
                float alpha = actor.getAlpha();
                if (alpha < 1.0f) {
                    oldComposite = g2d.getComposite();
                    g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, alpha));
                }

                if (actor instanceof DSprite) {
                    DSprite sprite = (DSprite) actor;
                    java.awt.Image img = sprite.getImage();
                    if (img != null) {
                        java.awt.geom.AffineTransform old = g2d.getTransform();
                        g2d.transform(sprite.getTransform());
                        g2d.drawImage(img, 0, 0, null);
                        g2d.setTransform(old);
                    }
                }

                if (actor.getShape() != null) {
                    java.awt.Color fill = actor.getFill();
                    if (fill != null && fill.getAlpha() > 0) {
                        g2d.setColor(fill);
                        g2d.fill(actor.getShape());
                    }

                    java.awt.Color stroke = actor.getStroke();
                    if (stroke != null && stroke.getAlpha() > 0) {
                        java.awt.Stroke oldStroke = g2d.getStroke();
                        g2d.setColor(stroke);
                        g2d.setStroke(new java.awt.BasicStroke(actor.getStrokeWidth()));
                        g2d.draw(actor.getShape());
                        g2d.setStroke(oldStroke);
                    }
                }

                if (oldComposite != null) {
                    g2d.setComposite(oldComposite);
                }
            };
            actorPainters.put(actor, painter);
            onPaint(painter);
        }
        return this;
    }

    /**
     * Adds a painter that sets the Graphics2D clipping region to the geometry of
     * the provided actor.
     * Any graphics drawn AFTER this will only be visible inside the actor's bounds.
     * Because actors can be animated via DAnimator, this effectively creates an
     * animated clipping mask!
     * 
     * @param actor the DAnimatable to use as a clipping mask
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas setClip(DExcited actor) {
        return onPaint((g2d, w, h) -> {
            if (actor == null || actor.getShape() == null) {
                g2d.setClip(null);
            } else {
                g2d.setClip(actor.getShape());
            }
        });
    }

    /**
     * Draws a rectangular outline with the specified color and thickness.
     * 
     * @param x         the X coordinate of the rectangle's top-left corner
     * @param y         the Y coordinate of the rectangle's top-left corner
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     * @param color     the color of the outline
     * @param thickness the thickness of the outline stroke
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawRect(int x, int y, int width, int height, java.awt.Color color, float thickness) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new java.awt.BasicStroke(thickness));
            g2d.drawRect(x, y, width, height);
        });
    }

    /**
     * Fills a rectangular area with the specified color.
     * 
     * @param x      the X coordinate of the rectangle's top-left corner
     * @param y      the Y coordinate of the rectangle's top-left corner
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @param color  the fill color
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas fillRect(int x, int y, int width, int height, java.awt.Color color) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.fillRect(x, y, width, height);
        });
    }

    /**
     * Draws a rounded rectangular outline.
     * 
     * @param x         the X coordinate of the rectangle's top-left corner
     * @param y         the Y coordinate of the rectangle's top-left corner
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     * @param arcWidth  the horizontal diameter of the arc at the four corners
     * @param arcHeight the vertical diameter of the arc at the four corners
     * @param color     the color of the outline
     * @param thickness the thickness of the outline stroke
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight, java.awt.Color color,
            float thickness) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new java.awt.BasicStroke(thickness));
            g2d.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
        });
    }

    /**
     * Fills a rounded rectangular area with the specified color.
     * 
     * @param x         the X coordinate of the rectangle's top-left corner
     * @param y         the Y coordinate of the rectangle's top-left corner
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     * @param arcWidth  the horizontal diameter of the arc at the four corners
     * @param arcHeight the vertical diameter of the arc at the four corners
     * @param color     the fill color
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight,
            java.awt.Color color) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
        });
    }

    /**
     * Draws an oval outline perfectly bounded by the specified rectangle.
     * 
     * @param x         the X coordinate of the bounding rectangle's top-left corner
     * @param y         the Y coordinate of the bounding rectangle's top-left corner
     * @param width     the width of the bounding rectangle
     * @param height    the height of the bounding rectangle
     * @param color     the color of the outline
     * @param thickness the thickness of the outline stroke
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawOval(int x, int y, int width, int height, java.awt.Color color, float thickness) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new java.awt.BasicStroke(thickness));
            g2d.drawOval(x, y, width, height);
        });
    }

    /**
     * Fills an oval area perfectly bounded by the specified rectangle.
     * 
     * @param x      the X coordinate of the bounding rectangle's top-left corner
     * @param y      the Y coordinate of the bounding rectangle's top-left corner
     * @param width  the width of the bounding rectangle
     * @param height the height of the bounding rectangle
     * @param color  the fill color
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas fillOval(int x, int y, int width, int height, java.awt.Color color) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.fillOval(x, y, width, height);
        });
    }

    /**
     * Draws the outline of a circular or elliptical arc.
     * 
     * @param x          the X coordinate of the bounding rectangle's top-left corner
     * @param y          the Y coordinate of the bounding rectangle's top-left corner
     * @param width      the width of the bounding rectangle
     * @param height     the height of the bounding rectangle
     * @param startAngle the starting angle of the arc in degrees
     * @param arcAngle   the angular extent of the arc in degrees
     * @param color      the color of the outline
     * @param thickness  the thickness of the outline stroke
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawArc(int x, int y, int width, int height, int startAngle, int arcAngle, java.awt.Color color,
            float thickness) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new java.awt.BasicStroke(thickness));
            g2d.drawArc(x, y, width, height, startAngle, arcAngle);
        });
    }

    /**
     * Fills a circular or elliptical arc (pie slice) with the specified color.
     * 
     * @param x          the X coordinate of the bounding rectangle's top-left corner
     * @param y          the Y coordinate of the bounding rectangle's top-left corner
     * @param width      the width of the bounding rectangle
     * @param height     the height of the bounding rectangle
     * @param startAngle the starting angle of the arc in degrees
     * @param arcAngle   the angular extent of the arc in degrees
     * @param color      the fill color
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas fillArc(int x, int y, int width, int height, int startAngle, int arcAngle, java.awt.Color color) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.fillArc(x, y, width, height, startAngle, arcAngle);
        });
    }

    /**
     * Draws the outline of a polygon defined by arrays of X and Y coordinates.
     * 
     * @param xPoints   an array of X coordinates
     * @param yPoints   an array of Y coordinates
     * @param color     the color of the outline
     * @param thickness the thickness of the outline stroke
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawPolygon(int[] xPoints, int[] yPoints, java.awt.Color color, float thickness) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new java.awt.BasicStroke(thickness));
            g2d.drawPolygon(xPoints, yPoints, Math.min(xPoints.length, yPoints.length));
        });
    }

    /**
     * Fills a polygon defined by arrays of X and Y coordinates with the specified color.
     * 
     * @param xPoints an array of X coordinates
     * @param yPoints an array of Y coordinates
     * @param color   the fill color
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas fillPolygon(int[] xPoints, int[] yPoints, java.awt.Color color) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.fillPolygon(xPoints, yPoints, Math.min(xPoints.length, yPoints.length));
        });
    }

    /**
     * Draws a polyline (an open sequence of connected line segments) defined by arrays of X and Y coordinates.
     * 
     * @param xPoints   an array of X coordinates
     * @param yPoints   an array of Y coordinates
     * @param color     the color of the line
     * @param thickness the thickness of the stroke
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawPolyline(int[] xPoints, int[] yPoints, java.awt.Color color, float thickness) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new java.awt.BasicStroke(thickness));
            g2d.drawPolyline(xPoints, yPoints, Math.min(xPoints.length, yPoints.length));
        });
    }

    /**
     * Draws a Quadratic Bezier curve using a single control point.
     * 
     * @param x1    start x
     * @param y1    start y
     * @param ctrlX control point x
     * @param ctrlY control point y
     * @param x2    end x
     * @param y2    end y
     */
    public DCanvas drawQuadCurve(int x1, int y1, int ctrlX, int ctrlY, int x2, int y2, java.awt.Color color,
            float thickness) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new java.awt.BasicStroke(thickness));
            g2d.draw(new java.awt.geom.QuadCurve2D.Float(x1, y1, ctrlX, ctrlY, x2, y2));
        });
    }

    /**
     * Fills a Quadratic Bezier curve shape.
     * 
     * @param x1    start x
     * @param y1    start y
     * @param ctrlX control point x
     * @param ctrlY control point y
     * @param x2    end x
     * @param y2    end y
     * @param color the fill color
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas fillQuadCurve(int x1, int y1, int ctrlX, int ctrlY, int x2, int y2, java.awt.Color color) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.fill(new java.awt.geom.QuadCurve2D.Float(x1, y1, ctrlX, ctrlY, x2, y2));
        });
    }

    /**
     * Draws a Cubic Bezier curve using two control points.
     * 
     * @param x1     start x
     * @param y1     start y
     * @param ctrlX1 first control point x
     * @param ctrlY1 first control point y
     * @param ctrlX2 second control point x
     * @param ctrlY2 second control point y
     * @param x2     end x
     * @param y2     end y
     */
    public DCanvas drawCubicCurve(int x1, int y1, int ctrlX1, int ctrlY1, int ctrlX2, int ctrlY2, int x2, int y2,
            java.awt.Color color, float thickness) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new java.awt.BasicStroke(thickness));
            g2d.draw(new java.awt.geom.CubicCurve2D.Float(x1, y1, ctrlX1, ctrlY1, ctrlX2, ctrlY2, x2, y2));
        });
    }

    /**
     * Fills a Cubic Bezier curve shape.
     * 
     * @param x1     start x
     * @param y1     start y
     * @param ctrlX1 first control point x
     * @param ctrlY1 first control point y
     * @param ctrlX2 second control point x
     * @param ctrlY2 second control point y
     * @param x2     end x
     * @param y2     end y
     * @param color  the fill color
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas fillCubicCurve(int x1, int y1, int ctrlX1, int ctrlY1, int ctrlX2, int ctrlY2, int x2, int y2,
            java.awt.Color color) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.fill(new java.awt.geom.CubicCurve2D.Float(x1, y1, ctrlX1, ctrlY1, ctrlX2, ctrlY2, x2, y2));
        });
    }

    /**
     * Draws the outline of a native java.awt.Polygon.
     * 
     * @param polygon   the polygon to draw
     * @param color     the color of the outline
     * @param thickness the thickness of the stroke
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawPolygon(java.awt.Polygon polygon, java.awt.Color color, float thickness) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new java.awt.BasicStroke(thickness));
            g2d.drawPolygon(polygon);
        });
    }

    /**
     * Fills a native java.awt.Polygon with the specified color.
     * 
     * @param polygon the polygon to fill
     * @param color   the fill color
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas fillPolygon(java.awt.Polygon polygon, java.awt.Color color) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.fillPolygon(polygon);
        });
    }

    /**
     * Internal helper to calculate a regular polygon (e.g. triangle, pentagon,
     * hexagon).
     */
    private java.awt.Polygon createRegularPolygon(int cx, int cy, int radius, int sides) {
        java.awt.Polygon p = new java.awt.Polygon();
        for (int i = 0; i < sides; i++) {
            double angle = 2.0 * Math.PI * i / sides - Math.PI / 2.0; // Start pointing straight up
            p.addPoint((int) (cx + radius * Math.cos(angle)), (int) (cy + radius * Math.sin(angle)));
        }
        return p;
    }

    /**
     * Draws the outline of a regular polygon (e.g., triangle, hexagon, octagon).
     * 
     * @param cx        the X coordinate of the center point
     * @param cy        the Y coordinate of the center point
     * @param radius    the radius from the center to the vertices
     * @param sides     the number of sides (e.g., 3 for triangle, 6 for hexagon)
     * @param color     the color of the outline
     * @param thickness the thickness of the stroke
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawRegularPolygon(int cx, int cy, int radius, int sides, java.awt.Color color, float thickness) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new java.awt.BasicStroke(thickness));
            g2d.drawPolygon(createRegularPolygon(cx, cy, radius, sides));
        });
    }

    /**
     * Fills a regular polygon (e.g., triangle, hexagon, octagon) with the specified color.
     * 
     * @param cx     the X coordinate of the center point
     * @param cy     the Y coordinate of the center point
     * @param radius the radius from the center to the vertices
     * @param sides  the number of sides (e.g., 3 for triangle, 6 for hexagon)
     * @param color  the fill color
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas fillRegularPolygon(int cx, int cy, int radius, int sides, java.awt.Color color) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.fillPolygon(createRegularPolygon(cx, cy, radius, sides));
        });
    }

    /**
     * Internal helper to calculate a star polygon.
     */
    private java.awt.Polygon createStar(int cx, int cy, int outerRadius, int innerRadius, int points) {
        java.awt.Polygon p = new java.awt.Polygon();
        for (int i = 0; i < points * 2; i++) {
            double angle = Math.PI * i / points - Math.PI / 2.0; // Start pointing straight up
            int radius = (i % 2 == 0) ? outerRadius : innerRadius;
            p.addPoint((int) (cx + radius * Math.cos(angle)), (int) (cy + radius * Math.sin(angle)));
        }
        return p;
    }

    /**
     * Draws the outline of a Star polygon with alternating inner and outer radii.
     * 
     * @param cx          the X coordinate of the center point
     * @param cy          the Y coordinate of the center point
     * @param outerRadius the outer radius (distance to the star points)
     * @param innerRadius the inner radius (distance to the star valleys)
     * @param points      the number of points on the star
     * @param color       the color of the outline
     * @param thickness   the thickness of the stroke
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawStar(int cx, int cy, int outerRadius, int innerRadius, int points, java.awt.Color color,
            float thickness) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new java.awt.BasicStroke(thickness));
            g2d.drawPolygon(createStar(cx, cy, outerRadius, innerRadius, points));
        });
    }

    /**
     * Fills a Star polygon with alternating inner and outer radii.
     * 
     * @param cx          the X coordinate of the center point
     * @param cy          the Y coordinate of the center point
     * @param outerRadius the outer radius (distance to the star points)
     * @param innerRadius the inner radius (distance to the star valleys)
     * @param points      the number of points on the star
     * @param color       the fill color
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas fillStar(int cx, int cy, int outerRadius, int innerRadius, int points, java.awt.Color color) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.fillPolygon(createStar(cx, cy, outerRadius, innerRadius, points));
        });
    }

    // ==========================================
    // --- Advanced Shapes & Gradients
    // ==========================================

    /**
     * Draws the outline of an arbitrary java.awt.Shape (e.g., Path2D, Area).
     * 
     * @param shape     the shape to draw
     * @param color     the color of the outline
     * @param thickness the thickness of the stroke
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawShape(java.awt.Shape shape, java.awt.Color color, float thickness) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new java.awt.BasicStroke(thickness));
            g2d.draw(shape);
        });
    }

    /**
     * Fills an arbitrary java.awt.Shape (e.g., Path2D, Area) with the specified color.
     * 
     * @param shape the shape to fill
     * @param color the fill color
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas fillShape(java.awt.Shape shape, java.awt.Color color) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.fill(shape);
        });
    }

    /**
     * Fills a rectangle with a linear gradient transitioning between two colors.
     * 
     * @param x          the X coordinate of the rectangle
     * @param y          the Y coordinate of the rectangle
     * @param width      the width of the rectangle
     * @param height     the height of the rectangle
     * @param startColor the starting color of the gradient
     * @param endColor   the ending color of the gradient
     * @param horizontal true for left-to-right, false for top-to-bottom
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas fillLinearGradient(int x, int y, int width, int height, java.awt.Color startColor,
            java.awt.Color endColor, boolean horizontal) {
        return onPaint((g2d, w, h) -> {
            java.awt.GradientPaint gp = new java.awt.GradientPaint(
                    x, y, startColor,
                    horizontal ? x + width : x, horizontal ? y : y + height, endColor);
            g2d.setPaint(gp);
            g2d.fillRect(x, y, width, height);
        });
    }

    /**
     * Fills a rectangle with a multi-stop linear gradient.
     * 
     * @param colors    Array of colors for the gradient stops
     * @param fractions Array of floats (0.0f to 1.0f) defining where each color is
     *                  placed
     */
    public DCanvas fillLinearGradient(int x, int y, int width, int height, java.awt.Color[] colors, float[] fractions,
            boolean horizontal) {
        return onPaint((g2d, w, h) -> {
            java.awt.LinearGradientPaint lgp = new java.awt.LinearGradientPaint(
                    x, y,
                    horizontal ? x + width : x, horizontal ? y : y + height,
                    fractions, colors);
            g2d.setPaint(lgp);
            g2d.fillRect(x, y, width, height);
        });
    }

    /**
     * Fills a rectangle with a diagonal linear gradient transitioning between two colors.
     * 
     * @param x                    the X coordinate of the rectangle
     * @param y                    the Y coordinate of the rectangle
     * @param width                the width of the rectangle
     * @param height               the height of the rectangle
     * @param startColor           the starting color of the gradient
     * @param endColor             the ending color of the gradient
     * @param topLeftToBottomRight true for top-left to bottom-right, false for bottom-left to top-right
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas fillLinearGradientDiagonal(int x, int y, int width, int height, java.awt.Color startColor,
            java.awt.Color endColor, boolean topLeftToBottomRight) {
        return onPaint((g2d, w, h) -> {
            java.awt.GradientPaint gp;
            if (topLeftToBottomRight) {
                gp = new java.awt.GradientPaint(x, y, startColor, x + width, y + height, endColor);
            } else {
                gp = new java.awt.GradientPaint(x, y + height, startColor, x + width, y, endColor);
            }
            g2d.setPaint(gp);
            g2d.fillRect(x, y, width, height);
        });
    }

    /**
     * Fills an oval with a radial gradient transitioning from the center to the edge.
     * 
     * @param cx          the X coordinate of the center
     * @param cy          the Y coordinate of the center
     * @param radius      the radius of the gradient and oval
     * @param centerColor the color at the center of the gradient
     * @param edgeColor   the color at the outer edge
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas fillRadialGradient(int cx, int cy, int radius, java.awt.Color centerColor,
            java.awt.Color edgeColor) {
        return onPaint((g2d, w, h) -> {
            float[] dist = { 0.0f, 1.0f };
            java.awt.Color[] colors = { centerColor, edgeColor };
            java.awt.RadialGradientPaint rgp = new java.awt.RadialGradientPaint(
                    new java.awt.geom.Point2D.Float(cx, cy), radius, dist, colors);
            g2d.setPaint(rgp);
            g2d.fillOval(cx - radius, cy - radius, radius * 2, radius * 2);
        });
    }

    /**
     * Fills an oval with a multi-stop radial gradient.
     * 
     * @param colors    Array of colors for the gradient stops
     * @param fractions Array of floats (0.0f to 1.0f) defining where each color is
     *                  placed
     */
    public DCanvas fillRadialGradient(int cx, int cy, int radius, java.awt.Color[] colors, float[] fractions) {
        return onPaint((g2d, w, h) -> {
            java.awt.RadialGradientPaint rgp = new java.awt.RadialGradientPaint(
                    new java.awt.geom.Point2D.Float(cx, cy), radius, fractions, colors);
            g2d.setPaint(rgp);
            g2d.fillOval(cx - radius, cy - radius, radius * 2, radius * 2);
        });
    }

    /**
     * Fills an arbitrary shape with a linear gradient transitioning between two colors along a defined line.
     * 
     * @param shape      the shape to fill
     * @param startColor the starting color of the gradient
     * @param endColor   the ending color of the gradient
     * @param startX     the X coordinate of the gradient's start point
     * @param startY     the Y coordinate of the gradient's start point
     * @param endX       the X coordinate of the gradient's end point
     * @param endY       the Y coordinate of the gradient's end point
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas fillShapeLinearGradient(java.awt.Shape shape, java.awt.Color startColor, java.awt.Color endColor,
            int startX, int startY, int endX, int endY) {
        return onPaint((g2d, w, h) -> {
            java.awt.GradientPaint gp = new java.awt.GradientPaint(startX, startY, startColor, endX, endY, endColor);
            g2d.setPaint(gp);
            g2d.fill(shape);
        });
    }

    /**
     * Fills an arbitrary shape with a radial gradient transitioning from a center point to an outer radius.
     * 
     * @param shape       the shape to fill
     * @param centerColor the color at the center of the gradient
     * @param edgeColor   the color at the outer edge
     * @param cx          the X coordinate of the gradient's center
     * @param cy          the Y coordinate of the gradient's center
     * @param radius      the radius of the radial gradient
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas fillShapeRadialGradient(java.awt.Shape shape, java.awt.Color centerColor, java.awt.Color edgeColor,
            int cx, int cy, int radius) {
        return onPaint((g2d, w, h) -> {
            float[] dist = { 0.0f, 1.0f };
            java.awt.Color[] colors = { centerColor, edgeColor };
            java.awt.RadialGradientPaint rgp = new java.awt.RadialGradientPaint(
                    new java.awt.geom.Point2D.Float(cx, cy), radius, dist, colors);
            g2d.setPaint(rgp);
            g2d.fill(shape);
        });
    }

    // ==========================================
    // --- Text Drawing & Typography
    // ==========================================

    /**
     * Draws a single-line string of text at the specified coordinates.
     * The coordinates determine the bottom-left baseline of the first character.
     * 
     * @param text  the string to draw
     * @param x     the X coordinate of the baseline
     * @param y     the Y coordinate of the baseline
     * @param color the text color
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawString(String text, int x, int y, java.awt.Color color) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.drawString(text, x, y);
        });
    }

    /**
     * Draws a single-line string of text right-aligned to the specified coordinates.
     * 
     * @param text  the string to draw
     * @param x     the X coordinate of the right edge of the text
     * @param y     the Y coordinate of the baseline
     * @param color the text color
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawStringRight(String text, int x, int y, java.awt.Color color) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            int width = g2d.getFontMetrics().stringWidth(text);
            g2d.drawString(text, x - width, y);
        });
    }

    /**
     * Draws a single-line string of text right-aligned using a specific font.
     * 
     * @param text  the string to draw
     * @param x     the X coordinate of the right edge of the text
     * @param y     the Y coordinate of the baseline
     * @param color the text color
     * @param font  the font to use
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawStringRight(String text, int x, int y, java.awt.Color color, java.awt.Font font) {
        return onPaint((g2d, w, h) -> {
            if (font != null)
                g2d.setFont(font);
            g2d.setColor(color);
            int width = g2d.getFontMetrics().stringWidth(text);
            g2d.drawString(text, x - width, y);
        });
    }

    /**
     * Draws multi-line text (separated by '\n').
     * 
     * @param text  the multi-line string to draw
     * @param x     the X coordinate of the left edge
     * @param y     the Y coordinate of the top line's baseline
     * @param color the text color
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawStringMultiLine(String text, int x, int y, java.awt.Color color) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            int lineHeight = g2d.getFontMetrics().getHeight();
            String[] lines = text.split("\n");
            for (int i = 0; i < lines.length; i++) {
                g2d.drawString(lines[i], x, y + (i * lineHeight));
            }
        });
    }

    /**
     * Draws multi-line text (separated by '\n') using a specific font.
     * 
     * @param text  the multi-line string to draw
     * @param x     the X coordinate of the left edge
     * @param y     the Y coordinate of the top line's baseline
     * @param color the text color
     * @param font  the font to use
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawStringMultiLine(String text, int x, int y, java.awt.Color color, java.awt.Font font) {
        return onPaint((g2d, w, h) -> {
            if (font != null)
                g2d.setFont(font);
            g2d.setColor(color);
            int lineHeight = g2d.getFontMetrics().getHeight();
            String[] lines = text.split("\n");
            for (int i = 0; i < lines.length; i++) {
                g2d.drawString(lines[i], x, y + (i * lineHeight));
            }
        });
    }

    /**
     * Draws text with a built-in drop shadow offset by a specific delta.
     * 
     * @param text        the string to draw
     * @param x           the X coordinate of the baseline
     * @param y           the Y coordinate of the baseline
     * @param textColor   the color of the main text
     * @param shadowColor the color of the drop shadow
     * @param shadowDx    the X offset for the shadow
     * @param shadowDy    the Y offset for the shadow
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawTextWithShadow(String text, int x, int y, java.awt.Color textColor, java.awt.Color shadowColor,
            int shadowDx, int shadowDy) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(shadowColor);
            g2d.drawString(text, x + shadowDx, y + shadowDy);
            g2d.setColor(textColor);
            g2d.drawString(text, x, y);
        });
    }

    /**
     * Draws text with a built-in drop shadow offset by a specific delta using a specific font.
     * 
     * @param text        the string to draw
     * @param x           the X coordinate of the baseline
     * @param y           the Y coordinate of the baseline
     * @param textColor   the color of the main text
     * @param shadowColor the color of the drop shadow
     * @param shadowDx    the X offset for the shadow
     * @param shadowDy    the Y offset for the shadow
     * @param font        the font to use
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawTextWithShadow(String text, int x, int y, java.awt.Color textColor, java.awt.Color shadowColor,
            int shadowDx, int shadowDy, java.awt.Font font) {
        return onPaint((g2d, w, h) -> {
            if (font != null)
                g2d.setFont(font);
            g2d.setColor(shadowColor);
            g2d.drawString(text, x + shadowDx, y + shadowDy);
            g2d.setColor(textColor);
            g2d.drawString(text, x, y);
        });
    }

    /**
     * Draws the outline (stroke) of a text string instead of filling it.
     * 
     * @param text      the string to draw
     * @param x         the X coordinate of the baseline
     * @param y         the Y coordinate of the baseline
     * @param color     the color of the text outline
     * @param thickness the thickness of the stroke
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawTextOutline(String text, int x, int y, java.awt.Color color, float thickness) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new java.awt.BasicStroke(thickness));
            java.awt.font.FontRenderContext frc = g2d.getFontRenderContext();
            java.awt.font.TextLayout tl = new java.awt.font.TextLayout(text, g2d.getFont(), frc);
            java.awt.Shape shape = tl.getOutline(java.awt.geom.AffineTransform.getTranslateInstance(x, y));
            g2d.draw(shape);
        });
    }

    /**
     * Draws the outline (stroke) of a text string using a specific font.
     * 
     * @param text      the string to draw
     * @param x         the X coordinate of the baseline
     * @param y         the Y coordinate of the baseline
     * @param color     the color of the text outline
     * @param font      the font to use
     * @param thickness the thickness of the stroke
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawTextOutline(String text, int x, int y, java.awt.Color color, java.awt.Font font,
            float thickness) {
        return onPaint((g2d, w, h) -> {
            if (font != null)
                g2d.setFont(font);
            g2d.setColor(color);
            g2d.setStroke(new java.awt.BasicStroke(thickness));
            java.awt.font.FontRenderContext frc = g2d.getFontRenderContext();
            java.awt.font.TextLayout tl = new java.awt.font.TextLayout(text, g2d.getFont(), frc);
            java.awt.Shape shape = tl.getOutline(java.awt.geom.AffineTransform.getTranslateInstance(x, y));
            g2d.draw(shape);
        });
    }

    /**
     * Draws a single-line string of text using a specific font.
     * 
     * @param text  the string to draw
     * @param x     the X coordinate of the baseline
     * @param y     the Y coordinate of the baseline
     * @param color the text color
     * @param font  the font to use
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawString(String text, int x, int y, java.awt.Color color, java.awt.Font font) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            if (font != null) {
                g2d.setFont(font);
            }
            g2d.drawString(text, x, y);
        });
    }

    /**
     * Draws text perfectly centered horizontally and vertically at the specified coordinates.
     * 
     * @param text  the string to draw
     * @param x     the exact center X coordinate
     * @param y     the exact center Y coordinate
     * @param color the text color
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawStringCentered(String text, int x, int y, java.awt.Color color) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            java.awt.FontMetrics fm = g2d.getFontMetrics();
            int cx = x - fm.stringWidth(text) / 2;
            int cy = y + (fm.getAscent() - fm.getDescent()) / 2;
            g2d.drawString(text, cx, cy);
        });
    }

    /**
     * Draws text perfectly centered horizontally and vertically at the specified coordinates using a specific font.
     * 
     * @param text  the string to draw
     * @param x     the exact center X coordinate
     * @param y     the exact center Y coordinate
     * @param color the text color
     * @param font  the font to use
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawStringCentered(String text, int x, int y, java.awt.Color color, java.awt.Font font) {
        return onPaint((g2d, w, h) -> {
            if (font != null)
                g2d.setFont(font);
            g2d.setColor(color);
            java.awt.FontMetrics fm = g2d.getFontMetrics();
            int cx = x - fm.stringWidth(text) / 2;
            int cy = y + (fm.getAscent() - fm.getDescent()) / 2;
            g2d.drawString(text, cx, cy);
        });
    }

    // ==========================================
    // --- Image Drawing
    // ==========================================

    /**
     * Adds a painter that draws an image at the specified coordinates.
     * 
     * @param image the image to draw
     * @param x     the x coordinate
     * @param y     the y coordinate
     * @return This DCanvas instance.
     */
    public DCanvas drawImage(Image image, int x, int y) {
        return onPaint((g2d, w, h) -> {
            if (image != null)
                g2d.drawImage(image, x, y, this);
        });
    }

    /**
     * Adds a painter that draws an image scaled to the specified width and height.
     * 
     * @param image  the image to draw
     * @param x      the x coordinate
     * @param y      the y coordinate
     * @param width  the width to draw the image
     * @param height the height to draw the image
     * @return This DCanvas instance.
     */
    public DCanvas drawImage(Image image, int x, int y, int width, int height) {
        return onPaint((g2d, w, h) -> {
            if (image != null)
                g2d.drawImage(image, x, y, width, height, this);
        });
    }

    /**
     * Adds a painter that loads and draws an image from a file path at the
     * specified coordinates.
     * 
     * @param path the path to the image file
     * @param x    the x coordinate
     * @param y    the y coordinate
     * @return This DCanvas instance.
     */
    public DCanvas drawImage(String path, int x, int y) {
        return drawImage(new ImageIcon(path).getImage(), x, y);
    }

    /**
     * Adds a painter that loads and draws an image from a URL at the specified
     * coordinates.
     * 
     * @param url the URL to the image
     * @param x   the x coordinate
     * @param y   the y coordinate
     * @return This DCanvas instance.
     */
    public DCanvas drawImage(URL url, int x, int y) {
        return drawImage(new ImageIcon(url).getImage(), x, y);
    }

    /**
     * Draws an image with a specific opacity (alpha) level.
     * 
     * @param image the image to draw
     * @param x     the X coordinate
     * @param y     the Y coordinate
     * @param alpha the opacity level from 0.0f (transparent) to 1.0f (opaque)
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawImageAlpha(Image image, int x, int y, float alpha) {
        return onPaint((g2d, w, h) -> {
            java.awt.Composite old = g2d.getComposite();
            g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, alpha));
            if (image != null)
                g2d.drawImage(image, x, y, this);
            g2d.setComposite(old);
        });
    }

    // ==========================================
    // --- Utilities & Debugging
    // ==========================================

    /**
     * Draws a debug grid over the canvas with the specified spacing.
     * 
     * @param spacing the distance in pixels between grid lines
     * @param color   the color of the grid lines
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawGrid(int spacing, java.awt.Color color) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            for (int x = 0; x < w; x += spacing)
                g2d.drawLine(x, 0, x, h);
            for (int y = 0; y < h; y += spacing)
                g2d.drawLine(0, y, w, y);
        });
    }

    // ==========================================
    // --- High-Utility Composite Drawing
    // ==========================================

    /**
     * Draws a line with a dynamically calculated solid arrowhead at the target coordinate (x2, y2).
     * 
     * @param x1        the starting X coordinate
     * @param y1        the starting Y coordinate
     * @param x2        the target X coordinate (where the arrowhead points)
     * @param y2        the target Y coordinate
     * @param color     the color of the arrow
     * @param thickness the thickness of the arrow's shaft
     * @param headSize  the size of the arrowhead in pixels
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawArrow(int x1, int y1, int x2, int y2, java.awt.Color color, float thickness, int headSize) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new java.awt.BasicStroke(thickness));
            g2d.drawLine(x1, y1, x2, y2);
            double angle = Math.atan2(y2 - y1, x2 - x1);
            java.awt.Polygon head = new java.awt.Polygon();
            head.addPoint(x2, y2);
            head.addPoint((int) (x2 - headSize * Math.cos(angle - Math.PI / 6)),
                          (int) (y2 - headSize * Math.sin(angle - Math.PI / 6)));
            head.addPoint((int) (x2 - headSize * Math.cos(angle + Math.PI / 6)),
                          (int) (y2 - headSize * Math.sin(angle + Math.PI / 6)));
            g2d.fillPolygon(head);
        });
    }

    /**
     * Draws a dashed line between two points.
     * 
     * @param x1         the starting X coordinate
     * @param y1         the starting Y coordinate
     * @param x2         the ending X coordinate
     * @param y2         the ending Y coordinate
     * @param color      the color of the dashed line
     * @param thickness  the thickness of the line
     * @param dashLength the length of each dash in pixels
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawDashedLine(int x1, int y1, int x2, int y2, java.awt.Color color, float thickness, float dashLength) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new java.awt.BasicStroke(thickness, java.awt.BasicStroke.CAP_BUTT, java.awt.BasicStroke.JOIN_BEVEL, 0, new float[]{dashLength}, 0));
            g2d.drawLine(x1, y1, x2, y2);
        });
    }

    /**
     * Draws a dashed rectangular outline.
     * 
     * @param x          the X coordinate of the rectangle
     * @param y          the Y coordinate of the rectangle
     * @param width      the width of the rectangle
     * @param height     the height of the rectangle
     * @param color      the color of the dashed outline
     * @param thickness  the thickness of the stroke
     * @param dashLength the length of each dash in pixels
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawDashedRect(int x, int y, int width, int height, java.awt.Color color, float thickness, float dashLength) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new java.awt.BasicStroke(thickness, java.awt.BasicStroke.CAP_BUTT, java.awt.BasicStroke.JOIN_BEVEL, 0, new float[]{dashLength}, 0));
            g2d.drawRect(x, y, width, height);
        });
    }

    /**
     * Draws text overlaid perfectly on top of a solid background padding block for high readability.
     * 
     * @param text      the string to draw
     * @param x         the X coordinate of the text baseline
     * @param y         the Y coordinate of the text baseline
     * @param textColor the color of the text
     * @param bgColor   the solid color of the background block
     * @param padding   the padding in pixels around the text
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawStringWithBackground(String text, int x, int y, java.awt.Color textColor, java.awt.Color bgColor, int padding) {
        return onPaint((g2d, w, h) -> {
            java.awt.FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();
            int ascent = fm.getAscent();
            
            g2d.setColor(bgColor);
            g2d.fillRect(x - padding, y - ascent - padding, textWidth + (padding * 2), textHeight + (padding * 2));
            
            g2d.setColor(textColor);
            g2d.drawString(text, x, y);
        });
    }

    /**
     * Fills an arbitrary shape and automatically casts a drop shadow underneath it.
     * 
     * @param shape       the shape to fill
     * @param shapeColor  the color of the main shape
     * @param shadowColor the color of the shadow
     * @param shadowDx    the X offset for the shadow
     * @param shadowDy    the Y offset for the shadow
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas fillShapeWithShadow(java.awt.Shape shape, java.awt.Color shapeColor, java.awt.Color shadowColor, int shadowDx, int shadowDy) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(shadowColor);
            java.awt.geom.AffineTransform old = g2d.getTransform();
            g2d.translate(shadowDx, shadowDy);
            g2d.fill(shape);
            g2d.setTransform(old);
            g2d.setColor(shapeColor);
            g2d.fill(shape);
        });
    }

    /**
     * Draws a targeting crosshair mark perfectly centered at the given coordinates.
     * 
     * @param cx        the exact center X coordinate
     * @param cy        the exact center Y coordinate
     * @param size      the radius (arm length) of the crosshair from the center
     * @param color     the color of the crosshair
     * @param thickness the thickness of the lines
     * @return This DCanvas instance for fluent chaining.
     */
    public DCanvas drawCrosshair(int cx, int cy, int size, java.awt.Color color, float thickness) {
        return onPaint((g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new java.awt.BasicStroke(thickness));
            g2d.drawLine(cx - size, cy, cx + size, cy);
            g2d.drawLine(cx, cy - size, cx, cy + size);
        });
    }
    // ==========================================
    // --- Image Export Functions
    // ==========================================

    /**
     * Creates a BufferedImage containing exactly what is currently drawn on this
     * canvas.
     * Uses the current width and height of the component.
     * 
     * @return a new BufferedImage snapshot of the canvas
     */
    public BufferedImage toImage() {
        int w = getWidth();
        int h = getHeight();
        if (w <= 0 || h <= 0) {
            w = getPreferredSize().width > 0 ? getPreferredSize().width : 800;
            h = getPreferredSize().height > 0 ? getPreferredSize().height : 600;
        }
        return toImage(w, h);
    }

    /**
     * Creates a BufferedImage snapshot of the canvas at the specified dimensions.
     * 
     * @param width  the desired width of the image
     * @param height the desired height of the image
     * @return a new BufferedImage snapshot
     */
    public BufferedImage toImage(int width, int height) {
        BufferedImage img = new BufferedImage(Math.max(1, width), Math.max(1, height), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();

        if (highQuality) {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        }

        int oldW = getWidth();
        int oldH = getHeight();
        setSize(width, height);

        paint(g2d);

        setSize(oldW, oldH);
        g2d.dispose();

        return img;
    }

    /**
     * Saves the current canvas drawing to an image file.
     * The format is automatically determined by the file extension (e.g., "png",
     * "jpg").
     * 
     * @param filePath the absolute or relative path to save the image
     * @return This DCanvas instance.
     */
    public DCanvas saveImage(String filePath) {
        String format = "png";
        String lower = filePath.toLowerCase();
        if (lower.endsWith(".jpg") || lower.endsWith(".jpeg"))
            format = "jpg";
        else if (lower.endsWith(".gif"))
            format = "gif";
        else if (lower.endsWith(".bmp"))
            format = "bmp";
        return saveImage(new File(filePath), format);
    }

    /**
     * Saves the current canvas drawing to an image file using the specified format.
     * 
     * @param file   the file to save to
     * @param format the image format (e.g., "png", "jpg")
     * @return This DCanvas instance.
     */
    public DCanvas saveImage(File file, String format) {
        try {
            ImageIO.write(toImage(), format, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
}
