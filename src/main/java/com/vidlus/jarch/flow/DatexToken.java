package com.vidlus.jarch.flow;

/**
 * Represents a boundary condition used by {@link DatexNode} to figure out
 * where data extraction begins and ends.
 */
public class DatexToken {
    
    /**
     * Helper to wrap multiple tokens into an array.
     */
    public static DatexToken[] of(DatexToken... tokens){
        return tokens;
    }

    /**
     * Creates a token matching the absolute beginning of a text block.
     */
    public static DatexToken textBegin() {
        return new DatexToken(Kind.TextBegin, null);
    }

    /**
     * Creates a token matching a literal exact string.
     */
    public static DatexToken literal(String code) {
        return new DatexToken(Kind.Literal, code);
    }

    /**
     * Creates a token matching a Regular Expression pattern.
     */
    public static DatexToken regex(String code) {
        return new DatexToken(Kind.Regex, code);
    }

    /**
     * Creates a token matching the absolute end of a text block.
     */
    public static DatexToken textEnd() {
        return new DatexToken(Kind.TextEnd, null);
    }


    private final Kind kind;
    private final String code;

    public DatexToken(Kind kind, String code) {
        this.kind = kind;
        this.code = code;
    }

    public Kind getKind() {
        return kind;
    }

    public String getCode() {
        return code;
    }

    /**
     * The evaluation strategies available for matching a token.
     */
    public enum Kind {
        TextBegin, Literal, Regex, TextEnd
    }

}
