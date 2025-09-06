package br.com.pointel.jarch.data;

public class Valued implements Data {

    public String name;
    public Nature type;
    public Object value;

    public Valued() {}

    public Valued(String name) {
        this.name = name;
    }

    public Valued(String name, Nature type) {
        this.name = name;
        this.type = type;
    }

    public Valued(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public Valued(String name, Nature type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public boolean hasName() {
        return this.name != null && !this.name.isEmpty();
    }

    public boolean hasType() {
        return this.type != null;
    }

    public boolean hasValue() {
        return this.value != null;
    }

    public Valued withName(String name) {
        this.name = name;
        return this;
    }

    public Valued withNoName() {
        this.name = null;
        return this;
    }

    public Valued withType(Nature type) {
        this.type = type;
        return this;
    }

    public Valued withNoType() {
        this.type = null;
        return this;
    }

    public Valued withValue(Object value) {
        this.value = value;
        return this;
    }

    public Valued withNoValue() {
        this.value = null;
        return this;
    }

    public Valued uponName(String name) {
        var clone = this.clone();
        clone.name = name;
        return clone;
    }

    public Valued uponNoName() {
        var clone = this.clone();
        clone.name = null;
        return clone;
    }

    public Valued uponType(Nature type) {
        var clone = this.clone();
        clone.type = type;
        return clone;
    }

    public Valued uponNoType() {
        var clone = this.clone();
        clone.type = null;
        return clone;
    }

    public Valued uponValue(Object value) {
        var clone = this.clone();
        clone.value = value;
        return clone;
    }

    public Valued uponNoValue() {
        var clone = this.clone();
        clone.value = null;
        return clone;
    }

    @Override
    public Valued clone() {
        return (Valued) this.deepClone();
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

    public static Valued fromChars(String chars) {
        return Base.fromChars(chars, Valued.class);
    }

}
