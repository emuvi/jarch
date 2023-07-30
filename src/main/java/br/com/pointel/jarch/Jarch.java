package br.com.pointel.jarch;

import org.apache.commons.lang3.ArrayUtils;

import br.com.pointel.jarch.cli.Cli;
import br.com.pointel.jarch.gui.Gui;

public class Jarch {

    public static void main(String[] args) throws Exception {
        var cli = args.length > 0 && !ArrayUtils.contains(args, "--gui");
        var gui = args.length == 0 || ArrayUtils.contains(args, "--gui");
        if (cli) {
            Cli.start(args);
        } else if (gui) {
            Gui.start(args);
        }
    }

}
