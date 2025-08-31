package br.com.pointel.jarch.data;

public class Match implements Data {

    public Integer ord;
    public String inColumn;
    public String outColumn;

    public Match() {
    }

    public Match(String inColumn) {
        this.inColumn = inColumn;
    }

    public Match(Integer ord, String inColumn) {
        this.ord = ord;
        this.inColumn = inColumn;
    }

    public Match(String inColumn, String outColumn) {
        this.inColumn = inColumn;
        this.outColumn = outColumn;
    }

    public Match(Integer ord, String inColumn, String outColumn) {
        this.ord = ord;
        this.inColumn = inColumn;
        this.outColumn = outColumn;
    }

    @Override
    public boolean equals(Object that) {
        return this.deepEquals(that);
    }

    @Override
    public int hashCode() {
        return this.deepHash();
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static Match fromChars(String chars) {
        return Data.fromChars(chars, Match.class);
    }

}
