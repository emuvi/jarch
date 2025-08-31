package br.com.pointel.jarch.data;

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

    public static ToSelect fromChars(String chars) {
        return Data.fromChars(chars, ToSelect.class);
    }

}
