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

    public boolean hasTableHead() {
        return this.tableHead != null;
    }

    public boolean hasValuedList() {
        return this.valuedList != null && !this.valuedList.isEmpty();
    }

    public boolean hasFilterList() {
        return this.filterList != null && !this.filterList.isEmpty();
    }

    public boolean hasLimit() {
        return this.limit != null;
    }

    public Update withTableHead(TableHead tableHead) {
        this.tableHead = tableHead;
        return this;
    }

    public Update withNoTableHead() {
        this.tableHead = null;
        return this;
    }

    public Update withValuedList(List<Valued> valuedList) {
        this.valuedList = valuedList;
        return this;
    }

    public Update withValuedList(Valued... valuedArgs) {
        this.valuedList = List.of(valuedArgs);
        return this;
    }

    public Update withNoValuedList() {
        this.valuedList = null;
        return this;
    }

    public Update withFilterList(List<Filter> filterList) {
        this.filterList = filterList;
        return this;
    }

    public Update withFilterList(Filter... filterArgs) {
        this.filterList = List.of(filterArgs);
        return this;
    }

    public Update withNoFilterList() {
        this.filterList = null;
        return this;
    }

    public Update withLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public Update withNoLimit() {
        this.limit = null;
        return this;
    }

    public Update uponTableHead(TableHead tableHead) {
        var clone = this.clone();
        clone.tableHead = tableHead;
        return clone;
    }

    public Update uponNoTableHead() {
        var clone = this.clone();
        clone.tableHead = null;
        return clone;
    }

    public Update uponValuedList(List<Valued> valuedList) {
        var clone = this.clone();
        clone.valuedList = valuedList;
        return clone;
    }

    public Update uponValuedList(Valued... valuedArgs) {
        var clone = this.clone();
        clone.valuedList = List.of(valuedArgs);
        return clone;
    }

    public Update uponNoValuedList() {
        var clone = this.clone();
        clone.valuedList = null;
        return clone;
    }

    public Update uponFilterList(List<Filter> filterList) {
        var clone = this.clone();
        clone.filterList = filterList;
        return clone;
    }

    public Update uponFilterList(Filter... filterArgs) {
        var clone = this.clone();
        clone.filterList = List.of(filterArgs);
        return clone;
    }

    public Update uponNoFilterList() {
        var clone = this.clone();
        clone.filterList = null;
        return clone;
    }

    public Update uponLimit(Integer limit) {
        var clone = this.clone();
        clone.limit = limit;
        return clone;
    }

    public Update uponNoLimit() {
        var clone = this.clone();
        clone.limit = null;
        return clone;
    }

    @Override
    public Update clone() {
        return (Update) this.deepClone();
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
        return Base.fromChars(chars, Update.class);
    }

    public Update valuedWithValues(Object... values) {
        if (values == null || values.length == 0) {
            return this;
        }
        if (this.valuedList == null || this.valuedList.size() < values.length) {
            throw new IllegalArgumentException("Valued list is null or has less elements than values");
        }
        for (int i = 0; i < values.length; i++) {
            this.valuedList.get(i).value = values[i];
        }
        return this;
    }

    public Update valuedUponValues(Object... values) {
        var clone = this.clone();
        clone.valuedWithValues(values);
        return clone;
    }

    public Update filterWithValues(Object... values) {
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

    public Update filterUponValues(Object... values) {
        var clone = this.clone();
        clone.filterWithValues(values);
        return clone;
    }

}
