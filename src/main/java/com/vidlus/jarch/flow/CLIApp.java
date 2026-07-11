package com.vidlus.jarch.flow;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.vidlus.jarch.mage.WizCLI;

/**
 * CLIApp represents a command-line application with options and commands.
 * It provides the framework for managing CLI options and their execution.
 * It encapsulates the metadata about the application such as name, version,
 * and description, along with the supported commands and options.
 */
public class CLIApp {

    private final String name;
    private final String version;
    private final String description;
    private final List<CLIOption> options;
    private final List<String> commands;

    /**
     * Creates a new CLIApp.
     *
     * @param name the name of the application
     * @param version the version of the application
     * @param description a description of the application
     */
    public CLIApp(String name, String version, String description) {
        this.name = Objects.requireNonNull(name, "App name cannot be null");
        this.version = version;
        this.description = description;
        this.options = new ArrayList<>();
        this.commands = new ArrayList<>();
    }

    /**
     * Creates a new CLIApp with default version and description.
     *
     * @param name the name of the application
     */
    public CLIApp(String name) {
        this(name, "1.0.0", "");
    }

    /**
     * Adds an option to the application.
     *
     * @param option the CLIOption to add
     * @return this CLIApp instance for method chaining
     */
    public CLIApp addOption(CLIOption option) {
        if (option != null && !options.contains(option)) {
            this.options.add(option);
        }
        return this;
    }

    /**
     * Adds multiple options to the application.
     *
     * @param options the CLIOptions to add
     * @return this CLIApp instance for method chaining
     */
    public CLIApp addOptions(CLIOption... options) {
        if (options != null) {
            for (CLIOption option : options) {
                addOption(option);
            }
        }
        return this;
    }

    /**
     * Adds a command to the application.
     *
     * @param command the command to add
     * @return this CLIApp instance for method chaining
     */
    public CLIApp addCommand(String command) {
        if (command != null && !command.isEmpty() && !commands.contains(command)) {
            this.commands.add(command);
        }
        return this;
    }

    /**
     * Adds multiple commands to the application.
     *
     * @param commands the commands to add
     * @return this CLIApp instance for method chaining
     */
    public CLIApp addCommands(String... commands) {
        if (commands != null) {
            for (String command : commands) {
                addCommand(command);
            }
        }
        return this;
    }

    /**
     * Gets the name of the application.
     *
     * @return the application name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the version of the application.
     *
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Gets the description of the application.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets all options registered with this application.
     *
     * @return a list of CLIOptions
     */
    public List<CLIOption> getOptions() {
        return new ArrayList<>(options);
    }

    /**
     * Gets all commands registered with this application.
     *
     * @return a list of commands
     */
    public List<String> getCommands() {
        return new ArrayList<>(commands);
    }

    /**
     * Checks if an option with the given name exists.
     *
     * @param name the internal name of the option
     * @return true if the option exists, false otherwise
     */
    public boolean hasOption(String name) {
        if (name == null) return false;
        for (CLIOption option : options) {
            if (name.equals(option.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets an option by its internal name.
     *
     * @param name the internal name of the option
     * @return the CLIOption, or null if not found
     */
    public CLIOption getOption(String name) {
        if (name == null) return null;
        for (CLIOption option : options) {
            if (name.equals(option.getName())) {
                return option;
            }
        }
        return null;
    }

    /**
     * Checks if a command exists.
     *
     * @param command the command to check
     * @return true if the command exists, false otherwise
     */
    public boolean hasCommand(String command) {
        return commands.contains(command);
    }

    /**
     * Removes an option by its internal name.
     *
     * @param name the internal name of the option
     * @return this CLIApp instance for method chaining
     */
    public CLIApp removeOption(String name) {
        if (name != null) {
            options.removeIf(option -> name.equals(option.getName()));
        }
        return this;
    }

    /**
     * Removes a command.
     *
     * @param command the command to remove
     * @return this CLIApp instance for method chaining
     */
    public CLIApp removeCommand(String command) {
        if (command != null) {
            commands.remove(command);
        }
        return this;
    }

    /**
     * Clears all options.
     *
     * @return this CLIApp instance for method chaining
     */
    public CLIApp clearOptions() {
        options.clear();
        return this;
    }

    /**
     * Clears all commands.
     *
     * @return this CLIApp instance for method chaining
     */
    public CLIApp clearCommands() {
        commands.clear();
        return this;
    }

    /**
     * Prints the help message for this application.
     */
    public void showHelp() {
        StringBuilder help = new StringBuilder();
        help.append("\n=== ").append(name).append(" v").append(version).append(" ===\n");
        if (description != null && !description.isEmpty()) {
            help.append(description).append("\n\n");
        }
        
        if (!options.isEmpty()) {
            help.append("Options:\n");
            for (CLIOption option : options) {
                help.append("  ");
                if (option.getShortForm() != null) {
                    help.append(option.getShortForm()).append(", ");
                }
                if (option.getLongForm() != null) {
                    help.append(option.getLongForm());
                }
                if (option.requiresValue()) {
                    help.append(" <value>");
                }
                if (option.getDescription() != null) {
                    help.append("\n    ").append(option.getDescription());
                }
                help.append("\n");
            }
        }
        
        if (!commands.isEmpty()) {
            help.append("\nCommands:\n");
            for (String command : commands) {
                help.append("  ").append(command).append("\n");
            }
        }
        
        WizCLI.showInfo(help.toString());
    }

    /**
     * Gets the number of options.
     *
     * @return the number of options
     */
    public int getOptionCount() {
        return options.size();
    }

    /**
     * Gets the number of commands.
     *
     * @return the number of commands
     */
    public int getCommandCount() {
        return commands.size();
    }

    /**
     * Returns a string representation of this CLIApp.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return "CLIApp{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", options=" + options.size() +
                ", commands=" + commands.size() +
                '}';
    }

}
