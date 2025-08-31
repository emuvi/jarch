package br.com.pointel.jarch.data;

import java.util.Objects;

public class ToSelect implements Data {

    public String base;
    public Select select;

    public ToSelect() {
    }

    public ToSelect(String base) {
        this.base = base;
    }

    public ToSelect(Select select) {
        this.select = select;
    }

    public ToSelect(String base, Select select) {
        this.base = base;
        this.select = select;
    }

    public Registry getRegistry() {
        return new Registry(base, select.tableHead);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ToSelect)) {
            return false;
        }
        ToSelect toSelect = (ToSelect) o;
        return Objects.equals(base, toSelect.base) && Objects.equals(select, toSelect.select);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, select);
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static ToSelect fromChars(String chars) {
        return Data.fromChars(chars, ToSelect.class);
    }

}
