package br.com.pointel.jarch.data;

import java.util.LinkedList;

public class DataListLinked<T> extends LinkedList<T> implements Data {

    @Override
    public DataListLinked<T> clone() {
        return (DataListLinked<T>) this.deepClone();
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

    public static DataListLinked fromChars(String chars) {
        return Base.fromChars(chars, DataListLinked.class);
    }
    
}
