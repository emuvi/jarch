package com.vidlus.jarch.desk;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

/**
 * Represents a physical display (monitor) in the system.
 * Provides utilities to interact with the display, such as capturing screenshots
 * and retrieving display properties.
 */
public class SwingDisplay {

    /** The underlying AWT GraphicsDevice representing the physical screen. */
    private final GraphicsDevice device;

    /**
     * Creates a new SwingDisplay wrapping the specified GraphicsDevice.
     *
     * @param device the underlying GraphicsDevice
     */
    public SwingDisplay(GraphicsDevice device) {
        this.device = device;
    }

    /**
     * Gets the underlying GraphicsDevice.
     *
     * @return the GraphicsDevice
     */
    public GraphicsDevice getDevice() {
        return device;
    }

    /**
     * Gets the bounds of this display.
     *
     * @return a Rectangle representing the bounds
     */
    public Rectangle getBounds() {
        return device.getDefaultConfiguration().getBounds();
    }

    /**
     * Gets the width of this display.
     *
     * @return the width in pixels
     */
    public int getWidth() {
        return getBounds().width;
    }

    /**
     * Gets the height of this display.
     *
     * @return the height in pixels
     */
    public int getHeight() {
        return getBounds().height;
    }

    /**
     * Gets the X coordinate of this display in the virtual screen space.
     *
     * @return the X coordinate
     */
    public int getX() {
        return getBounds().x;
    }

    /**
     * Gets the Y coordinate of this display in the virtual screen space.
     *
     * @return the Y coordinate
     */
    public int getY() {
        return getBounds().y;
    }

    /**
     * Captures the entire display.
     *
     * @return a BufferedImage containing the screenshot
     * @throws Exception if an AWTException occurs during capture
     */
    public BufferedImage capture() throws Exception {
        return new Robot().createScreenCapture(getBounds());
    }

    /**
     * Captures a specific region of this display.
     * The region should be relative to the display's bounds.
     *
     * @param region the region to capture, relative to this display
     * @return a BufferedImage containing the screenshot of the region
     * @throws Exception if an AWTException occurs during capture
     */
    public BufferedImage capture(Rectangle region) throws Exception {
        Rectangle captureRect = new Rectangle(
            getBounds().x + region.x,
            getBounds().y + region.y,
            region.width,
            region.height
        );
        return new Robot().createScreenCapture(captureRect);
    }

    /**
     * Checks if this display is the default system display.
     *
     * @return true if this is the default display, false otherwise
     */
    public boolean isDefaultDisplay() {
        return device.equals(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
    }

    /**
     * Gets the refresh rate of this display.
     *
     * @return the refresh rate in Hz
     */
    public int getRefreshRate() {
        return device.getDisplayMode().getRefreshRate();
    }

    /**
     * Gets the bit depth (color resolution) of this display.
     *
     * @return the bit depth
     */
    public int getBitDepth() {
        return device.getDisplayMode().getBitDepth();
    }

    /**
     * Returns a string representation of this display, typically its ID.
     *
     * @return the display ID string
     */
    @Override
    public String toString() {
        return device.getIDstring();
    }

    /**
     * Retrieves all available displays connected to the system.
     *
     * @return an array of SwingDisplay objects
     */
    public static SwingDisplay[] getDisplays() {
        GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        SwingDisplay[] displays = new SwingDisplay[devices.length];
        for (int i = 0; i < devices.length; i++) {
            displays[i] = new SwingDisplay(devices[i]);
        }
        return displays;
    }

    /**
     * Retrieves the default system display.
     *
     * @return the default SwingDisplay
     */
    public static SwingDisplay getDefaultDisplay() {
        return new SwingDisplay(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
    }
}
