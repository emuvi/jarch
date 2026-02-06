package br.com.pointel.jarch.flow;

import javax.management.RuntimeErrorException;

import br.com.pointel.jarch.mage.WizApp;
import br.com.pointel.jarch.mage.WizArray;

public class App {

    private final AppCLI appCLI;
    private final AppGUI appGUI;

    public App(AppCLI appCLI) {
        this.appCLI = appCLI;
        this.appGUI = null;
    }

    public App(AppGUI appGUI) {
        this.appCLI = null;
        this.appGUI = appGUI;
    }

    public App(AppCLI appCLI, AppGUI appGUI) {
        this.appCLI = appCLI;
        this.appGUI = appGUI;
    }

    public AppCLI appCLI() {
        return appCLI;
    }

    public AppGUI appGUI() {
        return appGUI;
    }

    public void start(String title, String[] args) {
        WizApp.setTitle(title);
        if (WizArray.has("--gui", args)) {
            appGUI.start(title, args);
        } else if (appCLI != null) {
            appCLI.start(title, args);
        } else if (appGUI != null) {
            appGUI.start(title, args);
        } else {
            throw new RuntimeException("No CLI or GUI defined for this application");
        }
    }

}
