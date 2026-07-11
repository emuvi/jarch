package com.vidlus.jarch.flow;

import com.vidlus.jarch.mage.WizLang;
import com.vidlus.jarch.mage.WizArray;

/**
 * Acts as the main entry point controller for a dual-mode (CLI/GUI) application.
 * Depending on the arguments passed during startup, this class automatically routes
 * execution to either the Command Line Interface application logic or the Graphical 
 * User Interface application logic.
 */
public class App {

    private final AppCLI appCLI;
    private final AppGUI appGUI;

    /**
     * Constructs an application that only supports a Command Line Interface (CLI).
     *
     * @param appCLI the CLI implementation
     */
    public App(AppCLI appCLI) {
        this.appCLI = appCLI;
        this.appGUI = null;
    }

    /**
     * Constructs an application that only supports a Graphical User Interface (GUI).
     *
     * @param appGUI the GUI implementation
     */
    public App(AppGUI appGUI) {
        this.appCLI = null;
        this.appGUI = appGUI;
    }

    /**
     * Constructs a hybrid application that supports both CLI and GUI modes.
     *
     * @param appCLI the CLI implementation
     * @param appGUI the GUI implementation
     */
    public App(AppCLI appCLI, AppGUI appGUI) {
        this.appCLI = appCLI;
        this.appGUI = appGUI;
    }

    /**
     * Retrieves the CLI component of this application.
     *
     * @return the AppCLI instance, or null if not defined
     */
    public AppCLI appCLI() {
        return appCLI;
    }

    /**
     * Retrieves the GUI component of this application.
     *
     * @return the AppGUI instance, or null if not defined
     */
    public AppGUI appGUI() {
        return appGUI;
    }

    /**
     * Starts the application by evaluating the command-line arguments.
     * If the args contain the "--gui" flag, it forces the application to launch in GUI mode.
     * Otherwise, it defaults to the CLI mode if available, falling back to GUI if CLI is missing.
     *
     * @param title the title of the application to be set globally
     * @param args  the command-line arguments passed from the OS
     * @throws RuntimeException if neither a CLI nor GUI component is defined
     */
    public void start(String title, String[] args) {
        WizLang.setTitle(title);
        if (WizArray.has("--gui", args)) {
            if (appGUI != null) {
                appGUI.start(title, args);
            } else {
                throw new RuntimeException("GUI mode requested but no GUI application is defined");
            }
        } else if (appCLI != null) {
            appCLI.start(title, args);
        } else if (appGUI != null) {
            appGUI.start(title, args);
        } else {
            throw new RuntimeException("No CLI or GUI defined for this application");
        }
    }

}
