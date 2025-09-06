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

    public boolean hasTableHead() {
        return this.tableHead != null;
    }

    public boolean hasFilterList() {
        return this.filterList != null && !this.filterList.isEmpty();
    }

    public Delete withTableHead(TableHead tableHead) {
        this.tableHead = tableHead;
        return this;
    }

    public Delete withNoTableHead() {
        this.tableHead = null;
        return this;
    }

    public Delete withFilterList(List<Filter> filterList) {
        this.filterList = filterList;
        return this;
    }

    public Delete withFilterList(Filter... filters) {
        this.filterList = List.of(filters);
        return this;
    }

    public Delete withNoFilterList() {
        this.filterList = null;
        return this;
    }

    public Delete uponTableHead(TableHead tableHead) {
        var clone = this.clone();
        clone.tableHead = tableHead;
        return clone;
    }

    public Delete uponNoTableHead() {
        var clone = this.clone();
        clone.tableHead = null;
        return clone;
    }

    public Delete uponFilterList(List<Filter> filterList) {
        var clone = this.clone();
        clone.filterList = filterList;
        return clone;
    }

    public Delete uponFilterList(Filter... filters) {
        var clone = this.clone();
        clone.filterList = List.of(filters);
        return clone;
    }

    public Delete uponNoFilterList() {
        var clone = this.clone();
        clone.filterList = null;
        return clone;
    }

    @Override
    public Delete clone() {
        return (Delete) this.deepClone();
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
