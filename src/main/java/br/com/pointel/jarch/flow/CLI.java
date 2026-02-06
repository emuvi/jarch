package br.com.pointel.jarch.flow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * CLI (Command Line Interface) parser for parsing command-line arguments.
 * Works in association with CLIOption and CLIApp classes to provide
 * a complete command-line argument parsing framework.
 */
public class CLI {

    private final CLIApp app;
    private final List<CLIOption> options;
    private final Map<String, String> parsedValues;
    private final List<String> remainingArgs;

    /**
     * Creates a new CLI parser for the given CLIApp.
     *
     * @param app the CLIApp to associate with this CLI parser
     */
    public CLI(CLIApp app) {
        this.app = app;
        this.options = new ArrayList<>();
        this.parsedValues = new HashMap<>();
        this.remainingArgs = new ArrayList<>();
    }

    /**
     * Adds a CLIOption to the parser.
     *
     * @param option the CLIOption to add
     * @return this CLI instance for method chaining
     */
    public CLI addOption(CLIOption option) {
        if (option != null) {
            this.options.add(option);
        }
        return this;
    }

    /**
     * Adds multiple CLIOptions to the parser.
     *
     * @param options the CLIOptions to add
     * @return this CLI instance for method chaining
     */
    public CLI addOptions(CLIOption... options) {
        if (options != null) {
            for (CLIOption option : options) {
                addOption(option);
            }
        }
        return this;
    }

    /**
     * Parses the provided command-line arguments.
     *
     * @param args the command-line arguments to parse
     * @return this CLI instance for method chaining
     */
    public CLI parse(String[] args) {
        if (args == null) {
            return this;
        }

        this.parsedValues.clear();
        this.remainingArgs.clear();

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            boolean matched = false;

            for (CLIOption option : options) {
                if (matchesOption(arg, option)) {
                    matched = true;

                    if (option.requiresValue()) {
                        if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                            String value = args[++i];
                            parsedValues.put(option.getName(), value);
                        } else {
                            parsedValues.put(option.getName(), "");
                        }
                    } else {
                        parsedValues.put(option.getName(), "true");
                    }
                    break;
                }
            }

            if (!matched) {
                remainingArgs.add(arg);
            }
        }

        return this;
    }

    /**
     * Checks if the given argument matches a CLI option.
     *
     * @param arg the argument to check
     * @param option the CLIOption to match against
     * @return true if the argument matches the option, false otherwise
     */
    private boolean matchesOption(String arg, CLIOption option) {
        if (arg == null || option == null) {
            return false;
        }

        String shortForm = option.getShortForm();
        String longForm = option.getLongForm();

        if (shortForm != null && arg.equals(shortForm)) {
            return true;
        }

        if (longForm != null && arg.equals(longForm)) {
            return true;
        }

        return false;
    }

    /**
     * Gets the value of a parsed option by its name.
     *
     * @param optionName the name of the option
     * @return the value of the option, or null if not provided
     */
    public String getValue(String optionName) {
        return parsedValues.get(optionName);
    }

    /**
     * Gets the value of a parsed option by its name, with a default value.
     *
     * @param optionName the name of the option
     * @param defaultValue the default value if the option is not provided
     * @return the value of the option, or defaultValue if not provided
     */
    public String getValue(String optionName, String defaultValue) {
        return parsedValues.getOrDefault(optionName, defaultValue);
    }

    /**
     * Checks if an option was provided in the parsed arguments.
     *
     * @param optionName the name of the option
     * @return true if the option was provided, false otherwise
     */
    public boolean hasOption(String optionName) {
        return parsedValues.containsKey(optionName);
    }

    /**
     * Gets all remaining arguments that were not matched to any option.
     *
     * @return a list of remaining arguments
     */
    public List<String> getRemaining() {
        return new ArrayList<>(remainingArgs);
    }

    /**
     * Gets the CLIApp associated with this parser.
     *
     * @return the CLIApp
     */
    public CLIApp getApp() {
        return app;
    }

    /**
     * Gets all parsed option values.
     *
     * @return a map of option names to their values
     */
    public Map<String, String> getParsedValues() {
        return new HashMap<>(parsedValues);
    }

    /**
     * Gets the number of parsed options.
     *
     * @return the number of parsed options
     */
    public int getParsedCount() {
        return parsedValues.size();
    }

    /**
     * Clears all parsed values and remaining arguments.
     *
     * @return this CLI instance for method chaining
     */
    public CLI reset() {
        this.parsedValues.clear();
        this.remainingArgs.clear();
        return this;
    }

    @Override
    public String toString() {
        return "CLI{" +
                "options=" + options.size() +
                ", parsed=" + parsedValues.size() +
                ", remaining=" + remainingArgs.size() +
                '}';
    }

}
