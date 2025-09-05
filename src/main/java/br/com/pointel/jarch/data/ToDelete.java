package br.com.pointel.jarch.data;

public class ToDelete implements Data {

    public String base;
    public Delete delete;

    public ToDelete() {
    }

    public ToDelete(String base) {
        this.base = base;
    }

    public ToDelete(Delete delete) {
        this.delete = delete;
    }

    public ToDelete(String base, Delete delete) {
        this.base = base;
        this.delete = delete;
    }

    public Registry getRegistry() {
        return new Registry(base, delete.tableHead);
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

    public static ToDelete fromChars(String chars) {
        return Base.fromChars(chars, ToDelete.class);
    }

}
