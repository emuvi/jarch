package br.com.pointel.jarch.data;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ToInsert)) {
            return false;
        }
        ToInsert toInsert = (ToInsert) o;
        return Objects.equals(base, toInsert.base) && Objects.equals(insert, toInsert.insert);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, insert);
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static ToInsert fromChars(String chars) {
        return Data.fromChars(chars, ToInsert.class);
    }

}
