package com.vidlus.jarch.flow;

import com.vidlus.jarch.desk.DFrame;
import com.vidlus.jarch.mage.WizGUI;

/**
 * Acts as the Graphical User Interface (GUI) component launcher for an application.
 * Safely initializes a Swing/AWT environment using {@link WizGUI} and spawns the initial frame.
 */
public class AppGUI {

    private final Class<? extends DFrame> frameClass;

    /**
     * Constructs the GUI launcher component mapping to a specific frame class.
     *
     * @param frameClass the Class definition of the primary window frame to instantiate upon startup
     */
    public AppGUI(Class<? extends DFrame> frameClass) {
        this.frameClass = frameClass;
    }

    /**
     * Commences the GUI lifecycle. Schedules the initialization of the primary frame
     * on the AWT Event Dispatch Thread and gracefully traps instantiation errors.
     *
     * @param title the title of the application
     * @param args  the string arguments passed from the OS
     */
    public void start(String title, String[] args) {
        WizGUI.start(title, () -> {
            try {
                var frame = frameClass.getConstructor().newInstance();
                frame.setVisible(true);
            } catch (Exception e) {
                WizGUI.showError(e);
            }
        });
    }

}
