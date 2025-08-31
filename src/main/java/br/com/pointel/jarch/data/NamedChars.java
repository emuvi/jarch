package br.com.pointel.jarch.data;

public class NamedChars implements Data {

    public String name;
    public String chars;

    public NamedChars() {}

    public NamedChars(String name) {
        this.name = name;
    }

    public NamedChars(String name, String chars) {
        this.name = name;
        this.chars = chars;
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

    public static NamedChars fromChars(String chars) {
        return Data.fromChars(chars, NamedChars.class);
    }

}
