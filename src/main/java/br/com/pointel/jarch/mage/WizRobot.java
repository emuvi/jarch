package br.com.pointel.jarch.mage;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;

public class WizRobot {
    
    public static Robot start() throws Exception {
        return start(300);
    }
    
    public static Robot start(int autoDelay) throws Exception {
        var robot = new Robot();
        robot.setAutoDelay(autoDelay);
        return robot;
    }
    
    public static void stroke(String keys) throws Exception {
        stroke(start(), keys);
    }
    
    public static void stroke(Robot robot, String keys) throws Exception {
        var keyStroke = KeyStroke.getKeyStroke(keys);
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
    
}
