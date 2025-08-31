package br.com.pointel.jarch.data;

import java.util.List;

public class Update implements Data {

    public TableHead tableHead;
    public List<Valued> valuedList;
    public List<Filter> filterList;
    public Integer limit;

    public Update() {
    }

    public Update(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    public Update(TableHead tableHead, List<Valued> valuedList) {
        this.tableHead = tableHead;
        this.valuedList = valuedList;
    }

    public Update(TableHead tableHead, List<Valued> valuedList, List<Filter> filterList) {
        this.tableHead = tableHead;
        this.valuedList = valuedList;
        this.filterList = filterList;
    }

    public Update(TableHead tableHead, List<Valued> valuedList, List<Filter> filterList, Integer limit) {
        this.tableHead = tableHead;
        this.valuedList = valuedList;
        this.filterList = filterList;
        this.limit = limit;
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

    public static Update fromChars(String chars) {
        return Data.fromChars(chars, Update.class);
    }

}
