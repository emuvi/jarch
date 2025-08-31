package br.com.pointel.jarch.data;

public class ToInsert implements Data {

    public String base;
    public Insert insert;

    public ToInsert() {
    }

    public ToInsert(String base) {
        this.base = base;
    }

    public ToInsert(Insert insert) {
        this.insert = insert;
    }

    public ToInsert(String base, Insert insert) {
        this.base = base;
        this.insert = insert;
    }

    public Registry getRegistry() {
        return new Registry(base, insert.tableHead);
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

    public static ToInsert fromChars(String chars) {
        return Data.fromChars(chars, ToInsert.class);
    }

}
