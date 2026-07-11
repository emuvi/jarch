package com.vidlus.jarch.mage;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.KeyStroke;

/**
 * A utility wrapper for java.awt.Robot that simplifies OS-level automation.
 * Provides easy-to-use methods for simulating mouse clicks, keyboard typing, 
 * and capturing screen content programmatically.
 */
public class WizRobot {

    private WizRobot() {
    }
    
    /**
     * Initializes a new AWT Robot instance with a default auto-delay of 300 milliseconds.
     * The auto-delay is applied after every event (mouse move, key press, etc.) to ensure 
     * the operating system has time to process the inputs.
     *
     * @return a new Robot instance
     * @throws Exception if the platform configuration does not allow low-level input control
     */
    public static Robot start() throws Exception {
        return start(300);
    }
    
    /**
     * Initializes a new AWT Robot instance with a custom auto-delay.
     *
     * @param autoDelay the number of milliseconds to pause after generating an event
     * @return a new Robot instance
     * @throws Exception if the platform configuration does not allow low-level input control
     */
    public static Robot start(int autoDelay) throws Exception {
        var robot = new Robot();
        robot.setAutoDelay(autoDelay);
        return robot;
    }

    // =========================================================================
    // KEYBOARD (KEY STROKES)
    // =========================================================================
    
    /**
     * Simulates a complex keystroke combination on the system using a newly spawned Robot.
     * Parses standard KeyStroke descriptors (e.g., "ctrl C", "shift alt T").
     *
     * @param keys the string descriptor of the keystroke combination
     * @throws Exception if the key descriptor is invalid or Robot creation fails
     */
    public static void stroke(String keys) throws Exception {
        stroke(start(), keys);
    }
    
    /**
     * Simulates a complex keystroke combination on the system using a provided Robot instance.
     * Correctly handles depressing and releasing all specified modifier keys (Ctrl, Alt, Shift, Meta).
     *
     * @param robot the Robot instance to execute the keystroke
     * @param keys  the string descriptor of the keystroke combination
     * @throws Exception if the key descriptor is invalid
     */
    public static void stroke(Robot robot, String keys) throws Exception {
        var keyStroke = KeyStroke.getKeyStroke(keys);
        if (keyStroke == null) {
            throw new IllegalArgumentException("Invalid keystroke description: " + keys);
        }
        if ((keyStroke.getModifiers() & InputEvent.META_DOWN_MASK) != 0) {
            robot.keyPress(KeyEvent.VK_META);
        }
        if ((keyStroke.getModifiers() & InputEvent.CTRL_DOWN_MASK) != 0) {
            robot.keyPress(KeyEvent.VK_CONTROL);
        }
        if ((keyStroke.getModifiers() & InputEvent.ALT_DOWN_MASK) != 0) {
            robot.keyPress(KeyEvent.VK_ALT);
        }
        if ((keyStroke.getModifiers() & InputEvent.SHIFT_DOWN_MASK) != 0) {
            robot.keyPress(KeyEvent.VK_SHIFT);
        }
        
        robot.keyPress(keyStroke.getKeyCode());
        robot.keyRelease(keyStroke.getKeyCode());
        
        if ((keyStroke.getModifiers() & InputEvent.SHIFT_DOWN_MASK) != 0) {
            robot.keyRelease(KeyEvent.VK_SHIFT);
        }
        if ((keyStroke.getModifiers() & InputEvent.ALT_DOWN_MASK) != 0) {
            robot.keyRelease(KeyEvent.VK_ALT);
        }
        if ((keyStroke.getModifiers() & InputEvent.CTRL_DOWN_MASK) != 0) {
            robot.keyRelease(KeyEvent.VK_CONTROL);
        }
        if ((keyStroke.getModifiers() & InputEvent.META_DOWN_MASK) != 0) {
            robot.keyRelease(KeyEvent.VK_META);
        }
    }

    /**
     * Automatically types out a full string of text character by character using a newly spawned Robot.
     *
     * @param text the text to type
     * @throws Exception if Robot creation fails
     */
    public static void type(String text) throws Exception {
        type(start(), text);
    }

    /**
     * Automatically types out a full string of text character by character using the provided Robot.
     * Detects uppercase characters and properly injects SHIFT key presses around them.
     *
     * @param robot the Robot instance to execute the typing
     * @param text  the text to type
     */
    public static void type(Robot robot, String text) {
        if (WizString.isEmpty(text)) return;
        for (char c : text.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
            if (keyCode != KeyEvent.VK_UNDEFINED) {
                if (Character.isUpperCase(c)) {
                    robot.keyPress(KeyEvent.VK_SHIFT);
                }
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
                if (Character.isUpperCase(c)) {
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                }
            }
        }
    }

    // =========================================================================
    // MOUSE OPERATIONS
    // =========================================================================

    /**
     * Warps the mouse cursor to a specific (x, y) screen coordinate using a newly spawned Robot.
     *
     * @param x the target x coordinate
     * @param y the target y coordinate
     * @throws Exception if Robot creation fails
     */
    public static void mouseMove(int x, int y) throws Exception {
        mouseMove(start(), x, y);
    }

    /**
     * Warps the mouse cursor to a specific (x, y) screen coordinate using the provided Robot.
     *
     * @param robot the Robot instance to execute the movement
     * @param x     the target x coordinate
     * @param y     the target y coordinate
     */
    public static void mouseMove(Robot robot, int x, int y) {
        robot.mouseMove(x, y);
    }

    /**
     * Simulates a left-click at the current mouse position.
     *
     * @throws Exception if Robot creation fails
     */
    public static void mouseClickLeft() throws Exception {
        mouseClick(start(), InputEvent.BUTTON1_DOWN_MASK);
    }

    /**
     * Simulates a right-click at the current mouse position.
     *
     * @throws Exception if Robot creation fails
     */
    public static void mouseClickRight() throws Exception {
        mouseClick(start(), InputEvent.BUTTON3_DOWN_MASK);
    }

    /**
     * Simulates a middle-click (scroll wheel click) at the current mouse position.
     *
     * @throws Exception if Robot creation fails
     */
    public static void mouseClickMiddle() throws Exception {
        mouseClick(start(), InputEvent.BUTTON2_DOWN_MASK);
    }

    /**
     * Simulates a physical mouse button click using a custom InputEvent button mask.
     *
     * @param buttonMask the AWT InputEvent mask representing the button to click
     * @throws Exception if Robot creation fails
     */
    public static void mouseClick(int buttonMask) throws Exception {
        mouseClick(start(), buttonMask);
    }

    /**
     * Simulates a physical mouse button click using a custom InputEvent button mask and the provided Robot.
     *
     * @param robot      the Robot instance to execute the click
     * @param buttonMask the AWT InputEvent mask representing the button to click
     */
    public static void mouseClick(Robot robot, int buttonMask) {
        robot.mousePress(buttonMask);
        robot.mouseRelease(buttonMask);
    }

    /**
     * Simulates rotating the mouse scroll wheel.
     *
     * @param wheelAmount number of "notches" to scroll. Negative values scroll up/away, positive values scroll down/towards.
     * @throws Exception if Robot creation fails
     */
    public static void mouseScroll(int wheelAmount) throws Exception {
        mouseScroll(start(), wheelAmount);
    }

    /**
     * Simulates rotating the mouse scroll wheel using the provided Robot.
     *
     * @param robot       the Robot instance to execute the scroll
     * @param wheelAmount number of "notches" to scroll
     */
    public static void mouseScroll(Robot robot, int wheelAmount) {
        robot.mouseWheel(wheelAmount);
    }

    // =========================================================================
    // SCREEN & COLOR CAPTURE
    // =========================================================================

    /**
     * Probes the exact color of the pixel at the specified screen coordinates.
     *
     * @param x the x coordinate on the screen
     * @param y the y coordinate on the screen
     * @return the AWT Color object representing the pixel's RGB value
     * @throws Exception if Robot creation fails
     */
    public static Color getPixelColor(int x, int y) throws Exception {
        return getPixelColor(start(), x, y);
    }

    /**
     * Probes the exact color of the pixel at the specified screen coordinates using the provided Robot.
     *
     * @param robot the Robot instance to execute the probe
     * @param x     the x coordinate on the screen
     * @param y     the y coordinate on the screen
     * @return the AWT Color object representing the pixel's RGB value
     */
    public static Color getPixelColor(Robot robot, int x, int y) {
        return robot.getPixelColor(x, y);
    }

    /**
     * Takes a screenshot of the entire primary monitor.
     *
     * @return a BufferedImage containing the captured screen data
     * @throws Exception if Robot creation fails
     */
    public static BufferedImage captureScreen() throws Exception {
        return captureScreen(start());
    }

    /**
     * Takes a screenshot of the entire primary monitor using the provided Robot.
     *
     * @param robot the Robot instance to execute the capture
     * @return a BufferedImage containing the captured screen data
     */
    public static BufferedImage captureScreen(Robot robot) {
        var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        var screenRect = new Rectangle(screenSize);
        return robot.createScreenCapture(screenRect);
    }

    /**
     * Takes a localized screenshot of a specific bounding box on the screen.
     *
     * @param x      the starting x coordinate of the region
     * @param y      the starting y coordinate of the region
     * @param width  the width of the capture box
     * @param height the height of the capture box
     * @return a BufferedImage containing the captured region
     * @throws Exception if Robot creation fails
     */
    public static BufferedImage captureRegion(int x, int y, int width, int height) throws Exception {
        return captureRegion(start(), new Rectangle(x, y, width, height));
    }

    /**
     * Takes a localized screenshot of a specific bounding box on the screen using the provided Robot.
     *
     * @param robot  the Robot instance to execute the capture
     * @param region the exact rectangular boundaries of the capture box
     * @return a BufferedImage containing the captured region
     */
    public static BufferedImage captureRegion(Robot robot, Rectangle region) {
        return robot.createScreenCapture(region);
    }

    // =========================================================================
    // UTILITIES
    // =========================================================================

    /**
     * Halts execution for a specified number of milliseconds using a newly spawned Robot.
     *
     * @param ms the number of milliseconds to sleep
     * @throws Exception if Robot creation fails
     */
    public static void delay(int ms) throws Exception {
        delay(start(), ms);
    }

    /**
     * Halts execution for a specified number of milliseconds using the provided Robot.
     * This is useful to force pauses between chained automation events.
     *
     * @param robot the Robot instance executing the delay
     * @param ms    the number of milliseconds to sleep
     */
    public static void delay(Robot robot, int ms) {
        robot.delay(ms);
    }
}
