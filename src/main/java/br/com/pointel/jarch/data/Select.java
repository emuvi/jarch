package br.com.pointel.jarch.data;

import java.sql.ResultSet;
import java.util.List;
import br.com.pointel.jarch.mage.WizBased;

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

    public boolean hasTableHead() {
        return this.tableHead != null;
    }

    public boolean hasFieldList() {
        return this.fieldList != null && !this.fieldList.isEmpty();
    }

    public boolean hasJoinList() {
        return this.joinList != null && !this.joinList.isEmpty();
    }

    public boolean hasFilterList() {
        return this.filterList != null && !this.filterList.isEmpty();
    }

    public boolean hasOrderList() {
        return this.orderList != null && !this.orderList.isEmpty();
    }

    public boolean hasOffset() {
        return this.offset != null;
    }

    public boolean hasLimit() {
        return this.limit != null;
    }

    public Select withTableHead(TableHead tableHead) {
        this.tableHead = tableHead;
        return this;
    }

    public Select withNoTableHead() {
        this.tableHead = null;
        return this;
    }

    public Select withFieldList(List<Typed> fieldList) {
        this.fieldList = fieldList;
        return this;
    }

    public Select withFieldList(Typed... fieldArgs) {
        this.fieldList = List.of(fieldArgs);
        return this;
    }

    public Select withNoFieldList() {
        this.fieldList = null;
        return this;
    }

    public Select withJoinList(List<Join> joinList) {
        this.joinList = joinList;
        return this;
    }

    public Select withJoinList(Join... joinArgs) {
        this.joinList = List.of(joinArgs);
        return this;
    }

    public Select withNoJoinList() {
        this.joinList = null;
        return this;
    }

    public Select withFilterList(List<Filter> filterList) {
        this.filterList = filterList;
        return this;
    }

    public Select withFilterList(Filter... filterArgs) {
        this.filterList = List.of(filterArgs);
        return this;
    }

    public Select withNoFilterList() {
        this.filterList = null;
        return this;
    }

    public Select withOrderList(List<Order> orderList) {
        this.orderList = orderList;
        return this;
    }

    public Select withOrderList(Order... orderArgs) {
        this.orderList = List.of(orderArgs);
        return this;
    }

    public Select withNoOrderList() {
        this.orderList = null;
        return this;
    }

    public Select withOffset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public Select withNoOffset() {
        this.offset = null;
        return this;
    }

    public Select withLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public Select withNoLimit() {
        this.limit = null;
        return this;
    }

    public Select uponTableHead(TableHead tableHead) {
        var clone = this.clone();
        clone.tableHead = tableHead;
        return clone;
    }

    public Select uponNoTableHead() {
        var clone = this.clone();
        clone.tableHead = null;
        return clone;
    }

    public Select uponFieldList(List<Typed> fieldList) {
        var clone = this.clone();
        clone.fieldList = fieldList;
        return clone;
    }

    public Select uponFieldList(Typed... fieldArgs) {
        var clone = this.clone();
        clone.fieldList = List.of(fieldArgs);
        return clone;
    }

    public Select uponNoFieldList() {
        var clone = this.clone();
        clone.fieldList = null;
        return clone;
    }

    public Select uponJoinList(List<Join> joinList) {
        var clone = this.clone();
        clone.joinList = joinList;
        return clone;
    }

    public Select uponJoinList(Join... joinArgs) {
        var clone = this.clone();
        clone.joinList = List.of(joinArgs);
        return clone;
    }

    public Select uponNoJoinList() {
        var clone = this.clone();
        clone.joinList = null;
        return clone;
    }

    public Select uponFilterList(List<Filter> filterList) {
        var clone = this.clone();
        clone.filterList = filterList;
        return clone;
    }

    public Select uponFilterList(Filter... filterArgs) {
        var clone = this.clone();
        clone.filterList = List.of(filterArgs);
        return clone;
    }

    public Select uponNoFilterList() {
        var clone = this.clone();
        clone.filterList = null;
        return clone;
    }

    public Select uponOrderList(List<Order> orderList) {
        var clone = this.clone();
        clone.orderList = orderList;
        return clone;
    }

    public Select uponOrderList(Order... orderArgs) {
        var clone = this.clone();
        clone.orderList = List.of(orderArgs);
        return clone;
    }

    public Select uponNoOrderList() {
        var clone = this.clone();
        clone.orderList = null;
        return clone;
    }

    public Select uponOffset(Integer offset) {
        var clone = this.clone();
        clone.offset = offset;
        return clone;
    }

    public Select uponNoOffset() {
        var clone = this.clone();
        clone.offset = null;
        return clone;
    }

    public Select uponLimit(Integer limit) {
        var clone = this.clone();
        clone.limit = limit;
        return clone;
    }

    public Select uponNoLimit() {
        var clone = this.clone();
        clone.limit = null;
        return clone;
    }

    @Override
    public Select clone() {
        return (Select) this.deepClone();
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

    public Select filterWithValues(Object... values) {
        if (values == null || values.length == 0) {
            return this;
        }
        if (this.filterList == null || this.filterList.size() < values.length) {
            throw new IllegalArgumentException("Filter list is null or has less elements than values");
        }
        for (int i = 0; i < values.length; i++) {
            this.filterList.get(i).valued.value = values[i];
        }
        return this;
    } 

    public Select filterUponValues(Object... values) {
        var clone = this.clone();
        clone.filterWithValues(values);
        return clone;
    }

    public <T> T mapResult(ResultSet resultSet, Class<T> clazz) throws Exception {
        return WizBased.mapResult(resultSet, fieldList, clazz);
    }

    public <T> List<T> mapResults(ResultSet resultSet, Class<T> clazz) throws Exception {
        return WizBased.mapResults(resultSet, fieldList, clazz);
    }

}
