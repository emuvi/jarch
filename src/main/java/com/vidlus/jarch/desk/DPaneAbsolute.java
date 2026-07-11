package com.vidlus.jarch.desk;

import java.awt.Component;

/**
 * A fluent wrapper for absolute positioning (null layout).
 * Ideal for UIs where you need exact pixel-perfect control over every component's location and size.
 */
public class DPaneAbsolute extends DPane {

    public DPaneAbsolute() {
        super.layout(null);
    }

    /**
     * Puts a component at the specified exact X and Y coordinates.
     * If the component does not yet have a size, it will automatically be sized to its preferred dimensions.
     * 
     * @param comp the component to add
     * @param x    the exact X coordinate in pixels
     * @param y    the exact Y coordinate in pixels
     * @return This DPaneAbsolute instance.
     */
    public DPaneAbsolute putAt(Component comp, int x, int y) {
        comp.setLocation(x, y);
        // In absolute layouts, components without bounds are invisible.
        // We automatically give it its preferred size if no size was set.
        if (comp.getWidth() == 0 || comp.getHeight() == 0) {
            comp.setSize(comp.getPreferredSize());
        }
        add(comp);
        return this;
    }

    public DPaneAbsolute putAt(DEdit<?> edit, int x, int y) {
        return putAt(edit.comp(), x, y);
    }

    /**
     * Puts a component at the specified X and Y coordinates and forcefully assigns it an exact width and height.
     * 
     * @param comp   the component to add
     * @param x      the exact X coordinate in pixels
     * @param y      the exact Y coordinate in pixels
     * @param width  the exact width in pixels
     * @param height the exact height in pixels
     * @return This DPaneAbsolute instance.
     */
    public DPaneAbsolute putBounds(Component comp, int x, int y, int width, int height) {
        comp.setBounds(x, y, width, height);
        add(comp);
        return this;
    }

    public DPaneAbsolute putBounds(DEdit<?> edit, int x, int y, int width, int height) {
        return putBounds(edit.comp(), x, y, width, height);
    }

    /**
     * Adds a component and sets its exact width and height. 
     * Its X and Y coordinates will remain wherever they were previously set (usually 0, 0).
     * 
     * @param comp   the component to add
     * @param width  the exact width in pixels
     * @param height the exact height in pixels
     * @return This DPaneAbsolute instance.
     */
    public DPaneAbsolute putSize(Component comp, int width, int height) {
        comp.setSize(width, height);
        add(comp);
        return this;
    }

    public DPaneAbsolute putSize(DEdit<?> edit, int width, int height) {
        return putSize(edit.comp(), width, height);
    }
}
