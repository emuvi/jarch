package br.com.pointel.jarch.mage;

import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WizCLI {

    private static final Logger LOG = LoggerFactory.getLogger(WizCLI.class);
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void showInfo(String message) {
        LOG.info(message);
    }

    public static void showError(Throwable error) {
        showError(error, null);
    }

    public static void showError(Throwable error, String detail) {
        String message = error.getMessage() + (detail != null ? " " + detail : "");
        LOG.error(message, error);
    }

    public static boolean showConfirm(String message) {
        System.out.println(message + " (Y/N)");
        var result = SCANNER.nextLine();
        return result.equalsIgnoreCase("Y");
    }

    public static String showInput(String message) {
        System.out.println(message);
        return SCANNER.nextLine();
    }

    public static String showInput(String question, String value) {
        System.out.println(question + " (" + value + ")");
        var result = SCANNER.nextLine();
        return result.isEmpty() ? value : result;
    }

}
