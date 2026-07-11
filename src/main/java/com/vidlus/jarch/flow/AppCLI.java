package com.vidlus.jarch.flow;

/**
 * Acts as the Command Line Interface (CLI) component launcher for an application.
 * Wraps a {@link CLI} parser to process incoming OS arguments before application start.
 */
public class AppCLI {

    private final CLI cli;

    /**
     * Constructs the CLI launcher component.
     *
     * @param cli the fully configured CLI argument parser
     */
    public AppCLI(CLI cli) {
        this.cli = cli;
    }

    /**
     * Commences the CLI lifecycle by parsing the provided arguments.
     *
     * @param title the title of the application
     * @param args  the string arguments passed from the OS
     */
    public void start(String title, String[] args) {
        this.cli.parse(args);
    }

}
