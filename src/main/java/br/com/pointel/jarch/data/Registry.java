package br.com.pointel.jarch.data;

import java.util.Objects;

public class Registry implements Data {

    public String base;
    public TableHead tableHead;

    public Registry() {
    }

    public Registry(String base) {
        this.base = base;
    }

    public Registry(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    public Registry(String base, TableHead tableHead) {
        this.base = base;
        this.tableHead = tableHead;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Registry)) {
            return false;
        }
        Registry registry = (Registry) o;
        return Objects.equals(base, registry.base) && Objects.equals(tableHead, registry.tableHead);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, tableHead);
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static Registry fromChars(String chars) {
        return Data.fromChars(chars, Registry.class);
    }

}
