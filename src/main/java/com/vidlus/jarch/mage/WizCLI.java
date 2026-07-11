package com.vidlus.jarch.mage;

import java.io.Console;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WizCLI {

    // =========================================================================
    // CONSTANTS & SETUP
    // =========================================================================
    
    private static final Logger LOG = LoggerFactory.getLogger(WizCLI.class);
    private static final Scanner SCANNER = new Scanner(System.in);

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String ANSI_UNDERLINE = "\u001B[4m";
    public static final String ANSI_REVERSED = "\u001B[7m";

    private static final String[] SPINNER_FRAMES = {"|", "/", "-", "\\"};
    private static int spinnerIndex = 0;

    // =========================================================================
    // OUTPUT & MESSAGING
    // =========================================================================

    /**
     * Logs an informational message using SLF4J.
     *
     * @param message the message to log
     */
    public static void showInfo(String message) {
        LOG.info(message);
    }

    /**
     * Logs an error with its stack trace using SLF4J.
     *
     * @param error the Throwable to log
     */
    public static void showError(Throwable error) {
        showError(error, null);
    }

    /**
     * Logs an error with a Throwable and optional detail message using SLF4J.
     *
     * @param error the Throwable to log
     * @param detail optional additional detail message
     */
    public static void showError(Throwable error, String detail) {
        String message = error.getMessage() + (detail != null ? " " + detail : "");
        LOG.error(message, error);
    }

    /**
     * Prints a message to standard output in the specified ANSI color.
     *
     * @param message the message to print
     * @param color the ANSI color code to apply
     */
    public static void printColored(String message, String color) {
        System.out.println(color + message + ANSI_RESET);
    }

    /**
     * Prints a success message with green color and checkmark icon.
     *
     * @param message the success message to print
     */
    public static void printSuccess(String message) {
        System.out.println(ANSI_GREEN + "✔ SUCCESS: " + ANSI_RESET + message);
    }

    /**
     * Prints a warning message with yellow color and warning icon.
     *
     * @param message the warning message to print
     */
    public static void printWarning(String message) {
        System.out.println(ANSI_YELLOW + "⚠ WARNING: " + ANSI_RESET + message);
    }

    /**
     * Prints an error message with red color and error icon.
     *
     * @param message the error message to print
     */
    public static void printError(String message) {
        System.out.println(ANSI_RED + "✖ ERROR: " + ANSI_RESET + message);
    }

    // =========================================================================
    // ADVANCED UI & PRESENTATION
    // =========================================================================

    /**
     * Prints a formatted header section with title.
     *
     * @param title the title to display in the header
     */
    public static void printHeader(String title) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("  " + title);
        System.out.println("========================================");
        System.out.println();
    }

    /**
     * Prints a message inside a box border.
     *
     * @param message the message to display in the box
     */
    public static void printBox(String message) {
        int length = message.length();
        StringBuilder border = new StringBuilder("+");
        for (int i = 0; i < length + 2; i++) {
            border.append("-");
        }
        border.append("+");
        System.out.println(border);
        System.out.println("| " + message + " |");
        System.out.println(border);
    }

    /**
     * Prints a progress bar showing current progress out of total.
     *
     * @param current the current progress value
     * @param total the total value to reach
     */
    public static void printProgressBar(int current, int total) {
        if (total <= 0) return;
        int percent = (int) (((double) current / total) * 100);
        int barLength = 50;
        int completed = (int) (((double) current / total) * barLength);
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < barLength; i++) {
            if (i < completed) {
                bar.append("=");
            } else if (i == completed) {
                bar.append(">");
            } else {
                bar.append(" ");
            }
        }
        bar.append("] ").append(percent).append("%");
        System.out.print("\r" + bar.toString());
        if (current >= total) {
            System.out.println();
        }
    }

    /**
     * Prints a formatted table with headers and rows.
     *
     * @param headers the column headers
     * @param rows the data rows, where each row is a list of string values
     */
    public static void printTable(List<String> headers, List<List<String>> rows) {
        if (headers == null || headers.isEmpty()) return;
        
        int[] columnWidths = new int[headers.size()];
        for (int i = 0; i < headers.size(); i++) {
            columnWidths[i] = headers.get(i).length();
        }
        if (rows != null) {
            for (List<String> row : rows) {
                for (int i = 0; i < Math.min(row.size(), headers.size()); i++) {
                    if (row.get(i) != null && row.get(i).length() > columnWidths[i]) {
                        columnWidths[i] = row.get(i).length();
                    }
                }
            }
        }
        
        StringBuilder formatBuilder = new StringBuilder();
        for (int width : columnWidths) {
            formatBuilder.append("| %-").append(width).append("s ");
        }
        formatBuilder.append("|%n");
        String format = formatBuilder.toString();
        
        int totalWidth = 1;
        for (int width : columnWidths) {
            totalWidth += width + 3;
        }
        String separator = "";
        for (int i = 0; i < totalWidth; i++) separator += "-";
        
        System.out.println(separator);
        System.out.printf(format, headers.toArray());
        System.out.println(separator);
        if (rows != null) {
            for (List<String> row : rows) {
                String[] rowData = new String[headers.size()];
                for (int i = 0; i < headers.size(); i++) {
                    rowData[i] = (i < row.size() && row.get(i) != null) ? row.get(i) : "";
                }
                System.out.printf(format, (Object[]) rowData);
            }
        }
        System.out.println(separator);
    }

    /**
     * Prints an animated spinner step with a message.
     * Spinner frames cycle through |, /, -, \\ characters.
     *
     * @param message the message to display with the spinner
     */
    public static void printSpinnerStep(String message) {
        System.out.print("\r" + SPINNER_FRAMES[spinnerIndex] + " " + message);
        spinnerIndex = (spinnerIndex + 1) % SPINNER_FRAMES.length;
    }
    
    /**
     * Clears the current line in the console.
     * Moves cursor to beginning and clears to end of line.
     */
    public static void clearCurrentLine() {
        System.out.print("\r\033[K"); 
    }

    /**
     * Clears the entire screen.
     * Uses platform-specific methods (cls on Windows, ANSI escape on Unix/Linux).
     */
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Ignore
        }
    }

    /**
     * Prints a message character by character with a delay (typing effect).
     *
     * @param message the message to print with typing effect
     * @param millisPerChar the delay in milliseconds between each character
     */
    public static void printTypingEffect(String message, long millisPerChar) {
        for (char c : message.toCharArray()) {
            System.out.print(c);
            System.out.flush();
            sleep(millisPerChar);
        }
        System.out.println();
    }

    /**
     * Pauses execution for the specified number of milliseconds.
     * Preserves interrupt status if interrupted.
     *
     * @param millis the number of milliseconds to sleep
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // =========================================================================
    // USER INPUT & PROMPTS
    // =========================================================================

    /**
     * Prompts the user for a yes/no confirmation.
     * Accepts 'Y' or 'y' as yes, any other input as no.
     *
     * @param message the confirmation message to display
     * @return true if user enters Y or y; false otherwise
     */
    public static boolean showConfirm(String message) {
        System.out.println(message + " (Y/N)");
        var result = SCANNER.nextLine();
        return result.equalsIgnoreCase("Y");
    }

    /**
     * Prompts the user for a yes/no confirmation with a default answer.
     * Empty input returns the default value.
     *
     * @param message the confirmation message to display
     * @param defaultYes true for yes as default, false for no as default
     * @return true for yes, false for no
     */
    public static boolean showConfirm(String message, boolean defaultYes) {
        String options = defaultYes ? "[Y/n]" : "[y/N]";
        System.out.println(message + " " + options);
        var result = SCANNER.nextLine().trim();
        if (result.isEmpty()) return defaultYes;
        return result.equalsIgnoreCase("Y");
    }

    /**
     * Prompts the user to enter a string input.
     *
     * @param message the prompt message to display
     * @return the user's input
     */
    public static String showInput(String message) {
        System.out.println(message);
        return SCANNER.nextLine();
    }

    /**
     * Prompts the user to enter a string input with a default value.
     * Empty input returns the default value.
     *
     * @param question the prompt question to display
     * @param value the default value to use if no input is provided
     * @return the user's input or the default value
     */
    public static String showInput(String question, String value) {
        System.out.println(question + " (" + value + ")");
        var result = SCANNER.nextLine();
        return result.isEmpty() ? value : result;
    }

    /**
     * Prompts the user to enter an integer value.
     * Repeats prompt until valid integer is entered.
     *
     * @param message the prompt message to display
     * @return the parsed integer value
     */
    public static int showInputInt(String message) {
        while (true) {
            System.out.println(message);
            String result = SCANNER.nextLine();
            try {
                return Integer.parseInt(result);
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer. Please try again.");
            }
        }
    }

    /**
     * Prompts the user to enter a double value.
     * Repeats prompt until valid number is entered.
     *
     * @param message the prompt message to display
     * @return the parsed double value
     */
    public static double showInputDouble(String message) {
        while (true) {
            System.out.println(message);
            String result = SCANNER.nextLine();
            try {
                return Double.parseDouble(result);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    /**
     * Prompts the user to enter a password securely.
     * Uses System.console() if available, otherwise falls back to regular input.
     * Characters are not echoed to the screen.
     *
     * @param message the prompt message to display
     * @return the user's password input
     */
    public static String showPasswordInput(String message) {
        Console console = System.console();
        if (console == null) {
            return showInput(message);
        }
        char[] passwordChars = console.readPassword(message + " ");
        return passwordChars != null ? new String(passwordChars) : null;
    }

    /**
     * Displays a menu with multiple options and prompts user to select one.
     * Repeats until valid selection (1 to number of options) is made.
     *
     * @param title the title of the menu
     * @param options the list of menu option strings
     * @return the 1-based index of the selected option
     */
    public static int showMenu(String title, List<String> options) {
        System.out.println("=== " + title + " ===");
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        while (true) {
            String input = showInput("Select an option (1-" + options.size() + "):");
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= options.size()) {
                    return choice;
                }
            } catch (NumberFormatException e) {
                // Ignore and prompt again
            }
            System.out.println("Invalid option. Please try again.");
        }
    }

    // =========================================================================
    // CLI ARGUMENT PARSING (CORE)
    // =========================================================================

    /**
     * Checks if any of the given flag names exist in the argument array.
     * Supports both flag-only format (-f) and key=value format (-f=value).
     *
     * @param args the command line arguments
     * @param flags the flag names to search for (without leading dashes)
     * @return true if any flag is found; false otherwise
     */
    public static boolean hasArg(String[] args, String... flags) {
        if (args == null || flags == null) return false;
        for (String arg : args) {
            for (String flag : flags) {
                if (arg.equals(flag)) return true;
                if (arg.startsWith(flag + "=")) return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the argument value for any of the given keys.
     * Supports both formats: -key value or -key=value
     *
     * @param args the command line arguments
     * @param keys the key names to search for
     * @return the argument value, or null if not found
     */
    public static String getArg(String[] args, String... keys) {
        if (args == null || keys == null) return null;
        for (int i = 0; i < args.length; i++) {
            for (String key : keys) {
                if (args[i].equals(key) && i + 1 < args.length) {
                    return args[i + 1];
                } else if (args[i].startsWith(key + "=")) {
                    return args[i].substring(key.length() + 1);
                }
            }
        }
        return null;
    }

    /**
     * Retrieves the argument value for any of the given keys, with a default.
     * Returns default if key not found.
     *
     * @param args the command line arguments
     * @param defaultValue the default value to return if key not found
     * @param keys the key names to search for
     * @return the argument value or the default value
     */
    public static String getArg(String[] args, String defaultValue, String... keys) {
        String value = getArg(args, keys);
        return value != null ? value : defaultValue;
    }

    /**
     * Retrieves all values for the given keys.
     * Collects multiple occurrences of the same key.
     *
     * @param args the command line arguments
     * @param keys the key names to search for
     * @return a list of all values found for the keys
     */
    public static List<String> getArgList(String[] args, String... keys) {
        List<String> results = new ArrayList<>();
        if (args == null || keys == null) return results;
        for (int i = 0; i < args.length; i++) {
            for (String key : keys) {
                if (args[i].equals(key) && i + 1 < args.length) {
                    results.add(args[i + 1]);
                } else if (args[i].startsWith(key + "=")) {
                    results.add(args[i].substring(key.length() + 1));
                }
            }
        }
        return results;
    }

    /**
     * Retrieves argument value as a CSV list (comma-separated values).
     * Splits the argument value on commas.
     *
     * @param args the command line arguments
     * @param keys the key names to search for
     * @return a list of values from CSV string, or empty list if not found or blank
     */
    public static List<String> getArgCsvList(String[] args, String... keys) {
        String value = getArg(args, keys);
        if (value == null || value.isBlank()) return new ArrayList<>();
        return Arrays.asList(value.split(","));
    }

    /**
     * Retrieves all positional arguments (non-flag arguments).
     * Arguments after '--' are always treated as positional.
     *
     * @param args the command line arguments
     * @return a list of positional arguments
     */
    public static List<String> getPositionalArgs(String[] args) {
        List<String> positionals = new ArrayList<>();
        if (args == null) return positionals;
        boolean onlyPositionals = false;
        for (int i = 0; i < args.length; i++) {
            if (onlyPositionals) {
                positionals.add(args[i]);
                continue;
            }
            if ("--".equals(args[i])) {
                onlyPositionals = true;
                continue;
            }
            if (args[i].startsWith("-")) {
                if (!args[i].contains("=") && i + 1 < args.length && !args[i + 1].startsWith("-")) {
                    i++; 
                }
            } else {
                positionals.add(args[i]);
            }
        }
        return positionals;
    }

    /**
     * Converts all command line arguments to a map.
     * Keys are flag names, values are the argument values.
     * Flags without values get 'true' as value.
     *
     * @param args the command line arguments
     * @return a LinkedHashMap of key-value pairs preserving insertion order
     */
    public static Map<String, String> getArgsMap(String[] args) {
        Map<String, String> map = new LinkedHashMap<>();
        if (args == null) return map;
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                if (args[i].contains("=")) {
                    int eqIndex = args[i].indexOf('=');
                    map.put(args[i].substring(0, eqIndex), args[i].substring(eqIndex + 1));
                } else if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                    map.put(args[i], args[i + 1]);
                    i++; 
                } else {
                    map.put(args[i], "true");
                }
            }
        }
        return map;
    }

    // =========================================================================
    // CLI ARGUMENT PARSING (TYPED)
    // =========================================================================

    /**
     * Retrieves a boolean argument value.
     * Returns true if flag exists with no value, or if value is 'true'.
     *
     * @param args the command line arguments
     * @param keys the key names to search for
     * @return Boolean.TRUE or Boolean.FALSE, or null if not found
     */
    public static Boolean getArgBoolean(String[] args, String... keys) {
        if (hasArg(args, keys)) {
            String value = getArg(args, keys);
            if (value == null || value.isEmpty()) {
                return true; 
            }
            return Boolean.parseBoolean(value);
        }
        return null;
    }
    
    /**
     * Retrieves a boolean argument with a default value.
     *
     * @param args the command line arguments
     * @param defaultValue the default value if key not found
     * @param keys the key names to search for
     * @return the boolean value or the default
     */
    public static boolean getArgBoolean(String[] args, boolean defaultValue, String... keys) {
        Boolean value = getArgBoolean(args, keys);
        return value != null ? value : defaultValue;
    }

    /**
     * Retrieves a Byte argument value.
     *
     * @param args the command line arguments
     * @param keys the key names to search for
     * @return the parsed Byte value, or null if not found or invalid
     */
    public static Byte getArgByte(String[] args, String... keys) {
        String value = getArg(args, keys);
        if (value == null) return null;
        try {
            return Byte.parseByte(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Retrieves a byte argument with a default value.
     *
     * @param args the command line arguments
     * @param defaultValue the default value if key not found or invalid
     * @param keys the key names to search for
     * @return the parsed byte value or the default
     */
    public static byte getArgByte(String[] args, byte defaultValue, String... keys) {
        Byte value = getArgByte(args, keys);
        return value != null ? value : defaultValue;
    }

    /**
     * Retrieves a Short argument value.
     *
     * @param args the command line arguments
     * @param keys the key names to search for
     * @return the parsed Short value, or null if not found or invalid
     */
    public static Short getArgShort(String[] args, String... keys) {
        String value = getArg(args, keys);
        if (value == null) return null;
        try {
            return Short.parseShort(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Retrieves a short argument with a default value.
     *
     * @param args the command line arguments
     * @param defaultValue the default value if key not found or invalid
     * @param keys the key names to search for
     * @return the parsed short value or the default
     */
    public static short getArgShort(String[] args, short defaultValue, String... keys) {
        Short value = getArgShort(args, keys);
        return value != null ? value : defaultValue;
    }

    /**
     * Retrieves an Integer argument value.
     *
     * @param args the command line arguments
     * @param keys the key names to search for
     * @return the parsed Integer value, or null if not found or invalid
     */
    public static Integer getArgInt(String[] args, String... keys) {
        String value = getArg(args, keys);
        if (value == null) return null;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Retrieves an int argument with a default value.
     *
     * @param args the command line arguments
     * @param defaultValue the default value if key not found or invalid
     * @param keys the key names to search for
     * @return the parsed int value or the default
     */
    public static int getArgInt(String[] args, int defaultValue, String... keys) {
        Integer value = getArgInt(args, keys);
        return value != null ? value : defaultValue;
    }

    /**
     * Retrieves a Long argument value.
     *
     * @param args the command line arguments
     * @param keys the key names to search for
     * @return the parsed Long value, or null if not found or invalid
     */
    public static Long getArgLong(String[] args, String... keys) {
        String value = getArg(args, keys);
        if (value == null) return null;
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Retrieves a long argument with a default value.
     *
     * @param args the command line arguments
     * @param defaultValue the default value if key not found or invalid
     * @param keys the key names to search for
     * @return the parsed long value or the default
     */
    public static long getArgLong(String[] args, long defaultValue, String... keys) {
        Long value = getArgLong(args, keys);
        return value != null ? value : defaultValue;
    }

    /**
     * Retrieves a Float argument value.
     *
     * @param args the command line arguments
     * @param keys the key names to search for
     * @return the parsed Float value, or null if not found or invalid
     */
    public static Float getArgFloat(String[] args, String... keys) {
        String value = getArg(args, keys);
        if (value == null) return null;
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Retrieves a float argument with a default value.
     *
     * @param args the command line arguments
     * @param defaultValue the default value if key not found or invalid
     * @param keys the key names to search for
     * @return the parsed float value or the default
     */
    public static float getArgFloat(String[] args, float defaultValue, String... keys) {
        Float value = getArgFloat(args, keys);
        return value != null ? value : defaultValue;
    }

    /**
     * Retrieves a Double argument value.
     *
     * @param args the command line arguments
     * @param keys the key names to search for
     * @return the parsed Double value, or null if not found or invalid
     */
    public static Double getArgDouble(String[] args, String... keys) {
        String value = getArg(args, keys);
        if (value == null) return null;
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Retrieves a double argument with a default value.
     *
     * @param args the command line arguments
     * @param defaultValue the default value if key not found or invalid
     * @param keys the key names to search for
     * @return the parsed double value or the default
     */
    public static double getArgDouble(String[] args, double defaultValue, String... keys) {
        Double value = getArgDouble(args, keys);
        return value != null ? value : defaultValue;
    }

    /**
     * Retrieves a Character argument value (first character of the value).
     *
     * @param args the command line arguments
     * @param keys the key names to search for
     * @return the Character value, or null if not found or empty
     */
    public static Character getArgCharacter(String[] args, String... keys) {
        String value = getArg(args, keys);
        if (value == null || value.isEmpty()) return null;
        return value.charAt(0);
    }

    /**
     * Retrieves a char argument with a default value.
     *
     * @param args the command line arguments
     * @param defaultValue the default value if key not found or empty
     * @param keys the key names to search for
     * @return the parsed char value or the default
     */
    public static char getArgCharacter(String[] args, char defaultValue, String... keys) {
        Character value = getArgCharacter(args, keys);
        return value != null ? value : defaultValue;
    }

    /**
     * Retrieves a BigDecimal argument value.
     *
     * @param args the command line arguments
     * @param keys the key names to search for
     * @return the parsed BigDecimal value, or null if not found or invalid
     */
    public static BigDecimal getArgBigDecimal(String[] args, String... keys) {
        String value = getArg(args, keys);
        if (value == null) return null;
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Retrieves a BigInteger argument value.
     *
     * @param args the command line arguments
     * @param keys the key names to search for
     * @return the parsed BigInteger value, or null if not found or invalid
     */
    public static BigInteger getArgBigInteger(String[] args, String... keys) {
        String value = getArg(args, keys);
        if (value == null) return null;
        try {
            return new BigInteger(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Retrieves a File argument value.
     *
     * @param args the command line arguments
     * @param keys the key names to search for
     * @return the File object for the argument path, or null if not found
     */
    public static File getArgFile(String[] args, String... keys) {
        String value = getArg(args, keys);
        if (value == null) return null;
        return new File(value);
    }

    /**
     * Retrieves a Path argument value.
     *
     * @param args the command line arguments
     * @param keys the key names to search for
     * @return the Path object for the argument path, or null if not found or invalid
     */
    public static Path getArgPath(String[] args, String... keys) {
        String value = getArg(args, keys);
        if (value == null) return null;
        try {
            return Paths.get(value);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Retrieves a LocalDate argument value.
     * Parses using ISO-8601 format (yyyy-MM-dd).
     *
     * @param args the command line arguments
     * @param keys the key names to search for
     * @return the parsed LocalDate value, or null if not found or invalid format
     */
    public static LocalDate getArgLocalDate(String[] args, String... keys) {
        String value = getArg(args, keys);
        if (value == null) return null;
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Retrieves a LocalDateTime argument value.
     * Parses using ISO-8601 format (yyyy-MM-ddTHH:mm:ss).
     *
     * @param args the command line arguments
     * @param keys the key names to search for
     * @return the parsed LocalDateTime value, or null if not found or invalid format
     */
    public static LocalDateTime getArgLocalDateTime(String[] args, String... keys) {
        String value = getArg(args, keys);
        if (value == null) return null;
        try {
            return LocalDateTime.parse(value);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

}
