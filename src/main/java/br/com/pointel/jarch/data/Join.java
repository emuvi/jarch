package br.com.pointel.jarch.data;

import java.util.List;

public class Join implements Data {

    public TableHead tableHead;
    public String alias;
    public List<Filter> filterList;
    public JoinTies ties;

    public Join() {
    }

    public Join(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    public Join(TableHead tableHead, String alias) {
        this.tableHead = tableHead;
        this.alias = alias;
    }

    public Join(TableHead tableHead, JoinTies ties) {
        this.tableHead = tableHead;
        this.ties = ties;
    }

    public Join(TableHead tableHead, String alias, JoinTies ties) {
        this.tableHead = tableHead;
        this.alias = alias;
        this.ties = ties;
    }

    public Join(TableHead tableHead, List<Filter> filterList) {
        this.tableHead = tableHead;
        this.filterList = filterList;
    }

    public Join(TableHead tableHead, String alias, List<Filter> filterList) {
        this.tableHead = tableHead;
        this.alias = alias;
        this.filterList = filterList;
    }

    public Join(TableHead tableHead, List<Filter> filterList, JoinTies ties) {
        this.tableHead = tableHead;
        this.filterList = filterList;
        this.ties = ties;
    }

    public Join(TableHead tableHead, String alias, List<Filter> filterList, JoinTies ties) {
        this.tableHead = tableHead;
        this.alias = alias;
        this.filterList = filterList;
        this.ties = ties;
    }

    public boolean hasFilters() {
        return this.filterList != null && !this.filterList.isEmpty();
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

    public static Join fromChars(String chars) {
        return Base.fromChars(chars, Join.class);
    }

}
