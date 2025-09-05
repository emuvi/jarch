package br.com.pointel.jarch.data;

import java.util.List;

public class Select implements Data {

    public TableHead tableHead;
    public List<Typed> fieldList;
    public List<Join> joinList;
    public List<Filter> filterList;
    public List<Order> orderList;
    public Integer offset;
    public Integer limit;

    public Select() {
    }

    public Select(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    public Select(TableHead tableHead, List<Typed> fieldList) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
    }

    public Select(TableHead tableHead, List<Typed> fieldList, List<Join> joinList) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
        this.joinList = joinList;
    }

    public Select(TableHead tableHead, List<Typed> fieldList, List<Join> joinList, List<Filter> filterList) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
        this.joinList = joinList;
        this.filterList = filterList;
    }

    public Select(TableHead tableHead, List<Typed> fieldList, List<Join> joinList, List<Filter> filterList, List<Order> orderList) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
        this.joinList = joinList;
        this.filterList = filterList;
        this.orderList = orderList;
    }

    public Select(TableHead tableHead, List<Typed> fieldList, List<Join> joinList, List<Filter> filterList, List<Order> orderList, Integer offset) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
        this.joinList = joinList;
        this.filterList = filterList;
        this.orderList = orderList;
        this.offset = offset;
    }

    public Select(TableHead tableHead, List<Typed> fieldList, List<Join> joinList, List<Filter> filterList, List<Order> orderList, Integer offset, Integer limit) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
        this.joinList = joinList;
        this.filterList = filterList;
        this.orderList = orderList;
        this.offset = offset;
        this.limit = limit;
    }

    public boolean hasJoins() {
        return this.joinList != null && !this.joinList.isEmpty();
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

    public static Select fromChars(String chars) {
        return Base.fromChars(chars, Select.class);
    }

}
