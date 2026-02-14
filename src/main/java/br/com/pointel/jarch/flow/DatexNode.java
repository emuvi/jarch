package br.com.pointel.jarch.flow;

public class DatexNode {


    public static DatexNode[] of(DatexNode... nodes){
        return nodes;
    }

    public static DatexNode must(String name, DatexToken startsWith, DatexToken endsWith) {
        return new DatexNode(name, startsWith, endsWith, true);
    }

    public static DatexNode must(String name, DatexToken[] startsWith, DatexToken[] endsWith) {
        return new DatexNode(name, startsWith, endsWith, true);
    }

    public static DatexNode may(String name, DatexToken startsWith, DatexToken endsWith) {
        return new DatexNode(name, startsWith, endsWith, false);
    }

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

    public void clean() {
        this.value = null;
    }

}
