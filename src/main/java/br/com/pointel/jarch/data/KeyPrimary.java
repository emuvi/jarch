package br.com.pointel.jarch.data;

import java.util.List;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof KeyPrimary)) {
            return false;
        }
        KeyPrimary keyPrimary = (KeyPrimary) o;
        return Objects.equals(name, keyPrimary.name) && Objects.equals(columnList, keyPrimary.columnList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, columnList);
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static KeyPrimary fromChars(String chars) {
        return Data.fromChars(chars, KeyPrimary.class);
    }

}
