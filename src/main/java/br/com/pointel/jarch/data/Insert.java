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

    public boolean hasTableHead() {
        return this.tableHead != null;
    }

    public boolean hasValuedList() {
        return this.valuedList != null && !this.valuedList.isEmpty();
    }

    public boolean hasToGetID() {
        return this.toGetID != null;
    }

    public Insert withTableHead(TableHead tableHead) {
        this.tableHead = tableHead;
        return this;
    }

    public Insert withNoTableHead() {
        this.tableHead = null;
        return this;
    }

    public Insert withValuedList(List<Valued> valuedList) {
        this.valuedList = valuedList;
        return this;
    }

    public Insert withValuedList(Valued... valuedArgs) {
        this.valuedList = List.of(valuedArgs);
        return this;
    }

    public Insert withNoValuedList() {
        this.valuedList = null;
        return this;
    }

    public Insert withToGetID(ToGetID toGetID) {
        this.toGetID = toGetID;
        return this;
    }

    public Insert withNoToGetID() {
        this.toGetID = null;
        return this;
    }

    public Insert uponTableHead(TableHead tableHead) {
        var clone = this.clone();
        clone.tableHead = tableHead;
        return clone;
    }

    public Insert uponNoTableHead() {
        var clone = this.clone();
        clone.tableHead = null;
        return clone;
    }

    public Insert uponValuedList(List<Valued> valuedList) {
        var clone = this.clone();
        clone.valuedList = valuedList;
        return clone;
    }

    public Insert uponValuedList(Valued... valuedArgs) {
        var clone = this.clone();
        clone.valuedList = List.of(valuedArgs);
        return clone;
    }

    public Insert uponNoValuedList() {
        var clone = this.clone();
        clone.valuedList = null;
        return clone;
    }

    public Insert uponToGetID(ToGetID toGetID) {
        var clone = this.clone();
        clone.toGetID = toGetID;
        return clone;
    }

    public Insert uponNoToGetID() {
        var clone = this.clone();
        clone.toGetID = null;
        return clone;
    }

    @Override
    public Insert clone() {
        return (Insert) this.deepClone();
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
        return Base.fromChars(chars, Insert.class);
    }

    public Insert valuedWithValues(Object... values) {
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

    public Insert valuedUponValues(Object... values) {
        var clone = this.clone();
        clone.valuedWithValues(values);
        return clone;
    }

}
