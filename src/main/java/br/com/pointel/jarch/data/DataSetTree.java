package br.com.pointel.jarch.data;

import java.util.TreeSet;

public class DataSetTree<T> extends TreeSet<T> implements Data {

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

    public static DataSetTree fromChars(String chars) {
        return Data.fromChars(chars, DataSetTree.class);
    }
    
}
