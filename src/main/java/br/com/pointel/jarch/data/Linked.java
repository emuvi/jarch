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

    public boolean hasName() {
        return this.name != null && !this.name.isEmpty();
    }

    public boolean hasUpon() {
        return this.upon != null && !this.upon.isEmpty();
    }

    public Linked withName(String name) {
        this.name = name;
        return this;
    }

    public Linked withNoName() {
        this.name = null;
        return this;
    }

    public Linked withUpon(String upon) {
        this.upon = upon;
        return this;
    }

    public Linked withNoUpon() {
        this.upon = null;
        return this;
    }

    public Linked uponName(String name) {
        var clone = this.clone();
        clone.name = name;
        return clone;
    }

    public Linked uponNoName() {
        var clone = this.clone();
        clone.name = null;
        return clone;
    }

    public Linked uponUpon(String upon) {
        var clone = this.clone();
        clone.upon = upon;
        return clone;
    }

    public Linked uponNoUpon() {
        var clone = this.clone();
        clone.upon = null;
        return clone;
    }

    @Override
    public Linked clone() {
        return (Linked) this.deepClone();
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
        return Base.fromChars(chars, Linked.class);
    }

}
