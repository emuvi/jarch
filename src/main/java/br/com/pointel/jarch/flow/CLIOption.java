package br.com.pointel.jarch.flow;

import java.util.Objects;

/**
 * CLIOption represents a command-line option with short and long forms,
 * description, and whether it requires a value.
 */
public class CLIOption {

    private final String name;
    private final String shortForm;
    private final String longForm;
    private final String description;
    private final boolean requiresValue;

    /**
     * Creates a new CLIOption.
     *
     * @param name the internal name of the option
     * @param shortForm the short form (e.g., "-v") or null if not applicable
     * @param longForm the long form (e.g., "--verbose") or null if not applicable
     * @param description a description of what the option does
     * @param requiresValue whether this option requires a value
     */
    public CLIOption(String name, String shortForm, String longForm, String description, boolean requiresValue) {
        this.name = Objects.requireNonNull(name, "Option name cannot be null");
        this.shortForm = shortForm;
        this.longForm = longForm;
        this.description = description;
        this.requiresValue = requiresValue;
    }

    /**
     * Creates a new CLIOption that does not require a value (flag).
     *
     * @param name the internal name of the option
     * @param shortForm the short form (e.g., "-v") or null if not applicable
     * @param longForm the long form (e.g., "--verbose") or null if not applicable
     * @param description a description of what the option does
     */
    public CLIOption(String name, String shortForm, String longForm, String description) {
        this(name, shortForm, longForm, description, false);
    }

    /**
     * Creates a new CLIOption with only a long form.
     *
     * @param name the internal name of the option
     * @param longForm the long form (e.g., "--verbose")
     * @param description a description of what the option does
     * @param requiresValue whether this option requires a value
     */
    public CLIOption(String name, String longForm, String description, boolean requiresValue) {
        this(name, null, longForm, description, requiresValue);
    }

    /**
     * Creates a new CLIOption with only a long form (flag).
     *
     * @param name the internal name of the option
     * @param longForm the long form (e.g., "--verbose")
     * @param description a description of what the option does
     */
    public CLIOption(String name, String longForm, String description) {
        this(name, null, longForm, description, false);
    }

    /**
     * Gets the internal name of the option.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the short form of the option.
     *
     * @return the short form or null if not set
     */
    public String getShortForm() {
        return shortForm;
    }

    /**
     * Gets the long form of the option.
     *
     * @return the long form or null if not set
     */
    public String getLongForm() {
        return longForm;
    }

    /**
     * Gets the description of the option.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Checks if this option requires a value.
     *
     * @return true if the option requires a value, false otherwise
     */
    public boolean requiresValue() {
        return requiresValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CLIOption that = (CLIOption) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "CLIOption{" +
                "name='" + name + '\'' +
                ", shortForm='" + shortForm + '\'' +
                ", longForm='" + longForm + '\'' +
                ", requiresValue=" + requiresValue +
                '}';
    }

}
