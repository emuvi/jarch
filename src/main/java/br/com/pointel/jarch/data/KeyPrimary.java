package br.com.pointel.jarch.data;

import java.util.List;

public class KeyPrimary implements Data {

    public String name;
    public List<OrdName> columnList;

    public KeyPrimary() {
    }

    public KeyPrimary(String name) {
        this.name = name;
    }

    public KeyPrimary(List<OrdName> columnList) {
        this.columnList = columnList;
    }

    public KeyPrimary(String name, List<OrdName> columnList) {
        this.name = name;
        this.columnList = columnList;
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

    public static KeyPrimary fromChars(String chars) {
        return Base.fromChars(chars, KeyPrimary.class);
    }

}
