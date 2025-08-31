package br.com.pointel.jarch.data;

public class Linked implements Data {

    public String name;
    public String upon;

    public Linked() {
    }

    public Linked(String name) {
        this.name = name;
    }

    public Linked(String name, String upon) {
        this.name = name;
        this.upon = upon;
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

    public static Linked fromChars(String chars) {
        return Data.fromChars(chars, Linked.class);
    }

}
