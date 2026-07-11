package com.vidlus.jarch.desk;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

/**
 * A specialized DPane designed for custom drawing and graphics operations.
 * It automatically enables high-quality anti-aliasing and allows drawing via fluent callbacks.
 */
public class DCanvas extends DPane {

    /**
     * Functional interface for custom canvas drawing.
     */
    public interface DCanvasPainter {
        /**
         * Called when the canvas needs to be repainted.
         * 
         * @param g2d    the Graphics2D context (with anti-aliasing already configured if enabled)
         * @param width  the width of the canvas
         * @param height the height of the canvas
         */
        void paint(Graphics2D g2d, int width, int height);
    }

    private final List<DCanvasPainter> painters = new ArrayList<>();
    private boolean highQuality = true;

    public DCanvas() {
        super();
        setOpaque(false); // Transparent by default to allow easy overlaying, unless background is explicitly set
    }

    /**
     * Adds a custom painter callback that will be executed whenever the canvas repaints.
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
        repaint();
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

        for (DCanvasPainter painter : painters) {
            painter.paint(g2d, w, h);
        }

        g2d.dispose();
    }
}
