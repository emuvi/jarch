package com.vidlus.jarch.flow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CLI (Command Line Interface) parser for parsing command-line arguments.
 * Works in association with CLIOption and CLIApp classes to provide
 * a complete command-line argument parsing framework.
 * This class handles parsing the raw string arguments array into typed values
 * and matching them against the registered options.
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
     * Loads all options registered in the associated CLIApp.
     *
     * @return this CLI instance for method chaining
     */
    public CLI loadAppOptions() {
        if (app != null) {
            addOptions(app.getOptions().toArray(new CLIOption[0]));
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
     * Gets the value of a parsed option as an integer.
     *
     * @param optionName the name of the option
     * @param defaultValue the default value if the option is not provided or not an integer
     * @return the integer value of the option
     */
    public int getInt(String optionName, int defaultValue) {
        String val = getValue(optionName);
        if (val == null || val.isEmpty()) return defaultValue;
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Gets the value of a parsed option as a long.
     *
     * @param optionName the name of the option
     * @param defaultValue the default value if the option is not provided or not a long
     * @return the long value of the option
     */
    public long getLong(String optionName, long defaultValue) {
        String val = getValue(optionName);
        if (val == null || val.isEmpty()) return defaultValue;
        try {
            return Long.parseLong(val);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Gets the value of a parsed option as a double.
     *
     * @param optionName the name of the option
     * @param defaultValue the default value if the option is not provided or not a double
     * @return the double value of the option
     */
    public double getDouble(String optionName, double defaultValue) {
        String val = getValue(optionName);
        if (val == null || val.isEmpty()) return defaultValue;
        try {
            return Double.parseDouble(val);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Gets the value of a parsed option as a float.
     *
     * @param optionName the name of the option
     * @param defaultValue the default value if the option is not provided or not a float
     * @return the float value of the option
     */
    public float getFloat(String optionName, float defaultValue) {
        String val = getValue(optionName);
        if (val == null || val.isEmpty()) return defaultValue;
        try {
            return Float.parseFloat(val);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Gets the value of a parsed option as a boolean.
     *
     * @param optionName the name of the option
     * @return true if the option is present and is true/empty, false otherwise
     */
    public boolean getBoolean(String optionName) {
        return getBoolean(optionName, false);
    }

    /**
     * Gets the value of a parsed option as a boolean.
     *
     * @param optionName the name of the option
     * @param defaultValue the default value if not provided
     * @return true if the option is present and is true/empty, false otherwise
     */
    public boolean getBoolean(String optionName, boolean defaultValue) {
        String val = getValue(optionName);
        if (val == null) return defaultValue;
        if (val.isEmpty()) return true;
        return Boolean.parseBoolean(val);
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
     * Checks if there are any remaining arguments.
     *
     * @return true if there are remaining arguments, false otherwise
     */
    public boolean hasRemaining() {
        return !remainingArgs.isEmpty();
    }

    /**
     * Gets a remaining argument at the specified index.
     *
     * @param index the index of the remaining argument
     * @return the argument at the index, or null if out of bounds
     */
    public String getRemaining(int index) {
        if (index >= 0 && index < remainingArgs.size()) {
            return remainingArgs.get(index);
        }
        return null;
    }

    /**
     * Checks if there are any parsed values.
     *
     * @return true if there are parsed values, false otherwise
     */
    public boolean hasParsedValues() {
        return !parsedValues.isEmpty();
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

    /**
     * Returns a string representation of this CLI parser.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return "CLI{" +
                "options=" + options.size() +
                ", parsed=" + parsedValues.size() +
                ", remaining=" + remainingArgs.size() +
                '}';
    }

}
