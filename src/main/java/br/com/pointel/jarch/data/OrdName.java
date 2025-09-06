package br.com.pointel.jarch.data;

public class OrdName implements Data {

    public Integer ord;
    public String name;

    public OrdName() {
    }

    public OrdName(String name) {
        this.name = name;
    }

    public OrdName(Integer ord, String name) {
        this.ord = ord;
        this.name = name;
    }

    public boolean hasOrd() {
        return this.ord != null;
    }

    public boolean hasName() {
        return this.name != null && !this.name.isEmpty();
    }

    public OrdName withOrd(Integer ord) {
        this.ord = ord;
        return this;
    }

    public OrdName withNoOrd() {
        this.ord = null;
        return this;
    }

    public OrdName withName(String name) {
        this.name = name;
        return this;
    }

    public OrdName withNoName() {
        this.name = null;
        return this;
    }

    public OrdName uponOrd(Integer ord) {
        var clone = this.clone();
        clone.ord = ord;
        return clone;
    }

    public OrdName uponNoOrd() {
        var clone = this.clone();
        clone.ord = null;
        return clone;
    }

    public OrdName uponName(String name) {
        var clone = this.clone();
        clone.name = name;
        return clone;
    }

    public OrdName uponNoName() {
        var clone = this.clone();
        clone.name = null;
        return clone;
    }

    @Override
    public OrdName clone() {
        return (OrdName) this.deepClone();
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

    public static OrdName fromChars(String chars) {
        return Base.fromChars(chars, OrdName.class);
    }

}
