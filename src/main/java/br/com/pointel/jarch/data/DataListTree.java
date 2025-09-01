package br.com.pointel.jarch.data;

import org.apache.commons.collections4.list.TreeList;

public class DataListTree<T> extends TreeList<T> implements Data {

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

    public static DataListTree fromChars(String chars) {
        return Data.fromChars(chars, DataListTree.class);
    }
    
}
