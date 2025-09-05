package br.com.pointel.jarch.data;

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

    public static Registry fromChars(String chars) {
        return Base.fromChars(chars, Registry.class);
    }

}
