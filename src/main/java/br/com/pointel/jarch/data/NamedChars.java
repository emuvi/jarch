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

    public boolean hasName() {
        return this.name != null && !this.name.isEmpty();
    }

    public boolean hasChars() {
        return this.chars != null && !this.chars.isEmpty();
    }

    public NamedChars withName(String name) {
        this.name = name;
        return this;
    }

    public NamedChars withNoName() {
        this.name = null;
        return this;
    }

    public NamedChars withChars(String chars) {
        this.chars = chars;
        return this;
    }

    public NamedChars withNoChars() {
        this.chars = null;
        return this;
    }

    public NamedChars uponName(String name) {
        var clone = this.clone();
        clone.name = name;
        return clone;
    }

    public NamedChars uponNoName() {
        var clone = this.clone();
        clone.name = null;
        return clone;
    }

    public NamedChars uponChars(String chars) {
        var clone = this.clone();
        clone.chars = chars;
        return clone;
    }

    public NamedChars uponNoChars() {
        var clone = this.clone();
        clone.chars = null;
        return clone;
    }

    @Override
    public NamedChars clone() {
        return (NamedChars) this.deepClone();
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
        return Base.fromChars(chars, NamedChars.class);
    }

}
