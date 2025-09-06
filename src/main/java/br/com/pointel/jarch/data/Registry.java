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

    public boolean hasBase() {
        return this.base != null;
    }

    public boolean hasTableHead() {
        return this.tableHead != null;
    }

    public Registry withBase(String base) {
        this.base = base;
        return this;
    }

    public Registry withNoBase() {
        this.base = null;
        return this;
    }

    public Registry withTableHead(TableHead tableHead) {
        this.tableHead = tableHead;
        return this;
    }

    public Registry withNoTableHead() {
        this.tableHead = null;
        return this;
    }

    public Registry uponBase(String base) {
        var clone = this.clone();
        clone.base = base;
        return clone;
    }

    public Registry uponNoBase() {
        var clone = this.clone();
        clone.base = null;
        return clone;
    }

    public Registry uponTableHead(TableHead tableHead) {
        var clone = this.clone();
        clone.tableHead = tableHead;
        return clone;
    }

    public Registry uponNoTableHead() {
        var clone = this.clone();
        clone.tableHead = null;
        return clone;
    }

    @Override
    public Registry clone() {
        return (Registry) this.deepClone();
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
