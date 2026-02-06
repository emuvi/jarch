package br.com.pointel.jarch.flow;

public class AppCLI {

    private final CLI cli;

    public AppCLI(CLI cli) {
        this.cli = cli;
    }

    public void start(String title, String[] args) {
        this.cli.parse(args);
    }

}
