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

    // --- Image Export Functions ---

    /**
     * Creates a BufferedImage containing exactly what is currently drawn on this canvas.
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
     * The format is automatically determined by the file extension (e.g., "png", "jpg").
     * 
     * @param filePath the absolute or relative path to save the image
     * @return This DCanvas instance.
     */
    public DCanvas saveImage(String filePath) {
        String format = "png";
        String lower = filePath.toLowerCase();
        if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) format = "jpg";
        else if (lower.endsWith(".gif")) format = "gif";
        else if (lower.endsWith(".bmp")) format = "bmp";
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

    // --- Image Drawing Helpers ---

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
            if (image != null) g2d.drawImage(image, x, y, this);
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
            if (image != null) g2d.drawImage(image, x, y, width, height, this);
        });
    }

    /**
     * Adds a painter that loads and draws an image from a file path at the specified coordinates.
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
     * Adds a painter that loads and draws an image from a URL at the specified coordinates.
     * 
     * @param url the URL to the image
     * @param x   the x coordinate
     * @param y   the y coordinate
     * @return This DCanvas instance.
     */
    public DCanvas drawImage(URL url, int x, int y) {
        return drawImage(new ImageIcon(url).getImage(), x, y);
    }
}
