package br.com.pointel.jarch.data;

import java.util.List;

public class Index implements Data {

    public String name;
    public TableHead tableHead;
    public List<Field> fieldList;

    public Index() {
    }

    public Index(String name) {
        this.name = name;
    }

    public Index(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    public Index(List<Field> fieldList) {
        this.fieldList = fieldList;
    }

    public Index(String name, TableHead tableHead) {
        this.name = name;
        this.tableHead = tableHead;
    }

    public Index(TableHead tableHead, List<Field> fieldList) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
    }

    public Index(String name, TableHead tableHead, List<Field> fieldList) {
        this.name = name;
        this.tableHead = tableHead;
        this.fieldList = fieldList;
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

    public static Index fromChars(String chars) {
        return Base.fromChars(chars, Index.class);
    }

}
