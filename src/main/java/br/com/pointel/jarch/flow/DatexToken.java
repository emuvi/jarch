package br.com.pointel.jarch.flow;

public class DatexToken {

    
    public static DatexToken[] of(DatexToken... tokens){
        return tokens;
    }

    public static DatexToken textBegin() {
        return new DatexToken(Kind.TextBegin, null);
    }

    public static DatexToken literal(String code) {
        return new DatexToken(Kind.Literal, code);
    }

    public static DatexToken regex(String code) {
        return new DatexToken(Kind.Regex, code);
    }

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

    public enum Kind {
        TextBegin, Literal, Regex, TextEnd
    }

}
