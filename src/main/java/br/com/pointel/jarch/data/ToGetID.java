package br.com.pointel.jarch.data;

public class ToGetID implements Data {

    public String name;
    public Valued filter;

    public ToGetID() {}

    public ToGetID(String name) {
        this.name = name;
    }

    public ToGetID(Valued filter) {
        this.filter = filter;
    }

    public ToGetID(String name, Valued filter) {
        this.name = name;
        this.filter = filter;
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

    public static ToGetID fromChars(String chars) {
        return Base.fromChars(chars, ToGetID.class);
    }

}
