package com.vidlus.jarch.desk;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * A wrapper for interaction events that occur on DAnimatable actors within a
 * DMovie.
 * Provides easy access to the event payload such as mouse coordinates or key
 * pressed.
 */
public class DEvent {

    private final DExcited target;
    private final DEventType type;
    private final InputEvent rawEvent;

    /**
     * Constructs a new DEvent.
     * 
     * @param target   the actor that received the event
     * @param type     the type of the event
     * @param rawEvent the underlying Swing InputEvent
     */
    public DEvent(DExcited target, DEventType type, InputEvent rawEvent) {
        this.target = target;
        this.type = type;
        this.rawEvent = rawEvent;
    }

    /**
     * The actor that was interacted with.
     * 
     * @return the target DExcited instance
     */
    public DExcited getTarget() {
        return target;
    }

    /**
     * The type of interaction that occurred.
     * 
     * @return the DEventType of this event
     */
    public DEventType getType() {
        return type;
    }

    /**
     * Gets the original Swing InputEvent (MouseEvent or KeyEvent).
     * 
     * @return the underlying awt InputEvent
     */
    public InputEvent getRawEvent() {
        return rawEvent;
    }

    /**
     * Returns the X coordinate of the mouse if this was a mouse event.
     * Returns -1 if this was not a mouse event.
     * 
     * @return the X coordinate, or -1 if not a mouse event
     */
    public int getMouseX() {
        if (rawEvent instanceof MouseEvent) {
            return ((MouseEvent) rawEvent).getX();
        }
        return -1;
    }

    /**
     * Returns the Y coordinate of the mouse if this was a mouse event.
     * Returns -1 if this was not a mouse event.
     * 
     * @return the Y coordinate, or -1 if not a mouse event
     */
    public int getMouseY() {
        if (rawEvent instanceof MouseEvent) {
            return ((MouseEvent) rawEvent).getY();
        }
        return -1;
    }

    /**
     * Returns the key code if this was a keyboard event.
     * Returns -1 if this was not a keyboard event.
     * 
     * @return the integer key code, or -1 if not a key event
     */
    public int getKeyCode() {
        if (rawEvent instanceof KeyEvent) {
            return ((KeyEvent) rawEvent).getKeyCode();
        }
        return -1;
    }

    /**
     * Returns the character typed if this was a keyboard event.
     * Returns '\0' if this was not a keyboard event.
     * 
     * @return the typed char, or '\0' if not a key event
     */
    public char getKeyChar() {
        if (rawEvent instanceof KeyEvent) {
            return ((KeyEvent) rawEvent).getKeyChar();
        }
        return '\0';
    }

    /**
     * Returns true if the Shift key was held down during the event.
     * 
     * @return true if shift was pressed
     */
    public boolean isShiftDown() {
        return rawEvent != null && rawEvent.isShiftDown();
    }

    /**
     * Returns true if the Ctrl (or Command on Mac) key was held down during the
     * event.
     * 
     * @return true if control was pressed
     */
    public boolean isControlDown() {
        return rawEvent != null && rawEvent.isControlDown();
    }

    /**
     * Returns true if the Alt key was held down during the event.
     * 
     * @return true if alt was pressed
     */
    public boolean isAltDown() {
        return rawEvent != null && rawEvent.isAltDown();
    }

    /**
     * Returns true if the Meta key (e.g., Command key on Mac) was held down.
     * 
     * @return true if meta was pressed
     */
    public boolean isMetaDown() {
        return rawEvent != null && rawEvent.isMetaDown();
    }

    /**
     * Checks if this event is a mouse event.
     * 
     * @return true if it is a mouse event
     */
    public boolean isMouseEvent() {
        return rawEvent instanceof MouseEvent;
    }

    /**
     * Checks if this event is a keyboard event.
     * 
     * @return true if it is a keyboard event
     */
    public boolean isKeyEvent() {
        return rawEvent instanceof KeyEvent;
    }

    /**
     * Consumes this event so that it will not be processed by other listeners.
     */
    public void consume() {
        if (rawEvent != null) {
            rawEvent.consume();
        }
    }

    /**
     * Checks whether this event has been consumed.
     * 
     * @return true if consumed
     */
    public boolean isConsumed() {
        return rawEvent != null && rawEvent.isConsumed();
    }

    /**
     * Returns true if the left mouse button was pressed/clicked.
     * 
     * @return true if it was a left click
     */
    public boolean isLeftClick() {
        if (rawEvent instanceof MouseEvent) {
            return javax.swing.SwingUtilities.isLeftMouseButton((MouseEvent) rawEvent);
        }
        return false;
    }

    /**
     * Returns true if the right mouse button was pressed/clicked.
     * 
     * @return true if it was a right click
     */
    public boolean isRightClick() {
        if (rawEvent instanceof MouseEvent) {
            return javax.swing.SwingUtilities.isRightMouseButton((MouseEvent) rawEvent);
        }
        return false;
    }

    /**
     * Returns true if the middle mouse button was pressed/clicked.
     * 
     * @return true if it was a middle click
     */
    public boolean isMiddleClick() {
        if (rawEvent instanceof MouseEvent) {
            return javax.swing.SwingUtilities.isMiddleMouseButton((MouseEvent) rawEvent);
        }
        return false;
    }

    /**
     * Returns the number of times the mouse was clicked.
     * Useful for detecting double clicks.
     * 
     * @return the click count, or 0 if not a mouse event
     */
    public int getClickCount() {
        if (rawEvent instanceof MouseEvent) {
            return ((MouseEvent) rawEvent).getClickCount();
        }
        return 0;
    }
}
