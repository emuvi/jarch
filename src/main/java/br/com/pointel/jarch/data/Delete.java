package br.com.pointel.jarch.data;

import java.util.List;

public class Delete implements Data {

    public TableHead tableHead;
    public List<Filter> filterList;

    public Delete() {
    }

    public Delete(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    public Delete(TableHead tableHead, List<Filter> filterList) {
        this.tableHead = tableHead;
        this.filterList = filterList;
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

    public static Delete fromChars(String chars) {
        return Base.fromChars(chars, Delete.class);
    }

}
