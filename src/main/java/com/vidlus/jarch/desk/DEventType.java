package com.vidlus.jarch.desk;

/**
 * Defines the types of interaction events that can occur on a DAnimatable actor inside a DMovie.
 */
public enum DEventType {
    /**
     * Fired when the mouse is clicked (pressed and released) on the actor.
     */
    MOUSE_CLICK,
    /**
     * Fired when the mouse button is initially pressed down on the actor.
     */
    MOUSE_PRESS,
    /**
     * Fired when the mouse button is released over the actor.
     */
    MOUSE_RELEASE,
    /**
     * Fired when the mouse pointer enters the bounding area of the actor.
     */
    MOUSE_ENTER,
    /**
     * Fired when the mouse pointer leaves the bounding area of the actor.
     */
    MOUSE_EXIT,
    /**
     * Fired when the mouse pointer moves within the bounding area of the actor.
     */
    MOUSE_MOVE,
    /**
     * Fired when the mouse is dragged (moved while pressed) on the actor.
     */
    MOUSE_DRAG,
    /**
     * Fired when a keyboard key is pressed down while the actor has focus.
     */
    KEY_PRESS,
    /**
     * Fired when a keyboard key is released while the actor has focus.
     */
    KEY_RELEASE,
    /**
     * Fired when a keyboard key is typed (pressed and released) while the actor has focus.
     */
    KEY_TYPE
}
