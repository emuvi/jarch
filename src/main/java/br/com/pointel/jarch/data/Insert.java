package br.com.pointel.jarch.data;

import java.util.List;

public class Insert implements Data {

    public TableHead tableHead;
    public List<Valued> valuedList;
    public ToGetID toGetID;

    public Insert() {
    }

    public Insert(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    public Insert(TableHead tableHead, List<Valued> valuedList) {
        this.tableHead = tableHead;
        this.valuedList = valuedList;
    }

    public Insert(TableHead tableHead, List<Valued> valuedList, ToGetID toGetID) {
        this.tableHead = tableHead;
        this.valuedList = valuedList;
        this.toGetID = toGetID;
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

    public static Insert fromChars(String chars) {
        return Data.fromChars(chars, Insert.class);
    }

}
