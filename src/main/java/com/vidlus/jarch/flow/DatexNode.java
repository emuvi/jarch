package com.vidlus.jarch.flow;

/**
 * Represents a distinct chunk of data to be extracted by a {@link Datex} parser.
 * A node is bounded by "startsWith" and "endsWith" tokens.
 */
public class DatexNode {

    /**
     * Helper to wrap multiple nodes into an array.
     */
    public static DatexNode[] of(DatexNode... nodes){
        return nodes;
    }

    /**
     * Creates a required Node. If this node is not found, parsing will fail.
     */
    public static DatexNode must(String name, DatexToken startsWith, DatexToken endsWith) {
        return new DatexNode(name, startsWith, endsWith, true);
    }

    /**
     * Creates a required Node using an array of possible boundary tokens.
     */
    public static DatexNode must(String name, DatexToken[] startsWith, DatexToken[] endsWith) {
        return new DatexNode(name, startsWith, endsWith, true);
    }

    /**
     * Creates an optional Node. If this node is not found, parsing skips it.
     */
    public static DatexNode may(String name, DatexToken startsWith, DatexToken endsWith) {
        return new DatexNode(name, startsWith, endsWith, false);
    }

    /**
     * Creates an optional Node using an array of possible boundary tokens.
     */
    public static DatexNode may(String name, DatexToken[] startsWith, DatexToken[] endsWith) {
        return new DatexNode(name, startsWith, endsWith, false);
    }
    
    private final String name;
    private final DatexToken[] startsWith;
    private final DatexToken[] endsWith;
    private final boolean required;

    private String value;

    public DatexNode(String name, DatexToken startsWith, DatexToken endsWith, boolean required) {
        this(name, DatexToken.of(startsWith), DatexToken.of(endsWith), required);        
    }

    public DatexNode(String name, DatexToken[] startsWith, DatexToken[] endsWith, boolean required) {
        this.name = name;
        this.startsWith = startsWith;
        this.endsWith = endsWith;
        this.required = required;
        this.value = null;
    }

    public String getName() {
        return name;
    }

    public DatexToken[] getStartsWith() {
        return startsWith;
    }

    public DatexToken[] getEndsWith() {
        return endsWith;
    }

    public boolean isRequired() {
        return required;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Erases the extracted value from this node, resetting it for a new parse.
     */
    public void clean() {
        this.value = null;
    }

    /**
     * @return true if a value has been successfully extracted into this node
     */
    public boolean isPresent() {
        return value != null && !value.isBlank();
    }

}
