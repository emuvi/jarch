package br.com.pointel.jarch.data;

import org.apache.commons.collections4.list.TreeList;

public class DataListTree<T> extends TreeList<T> implements Data {
    
    @Override
    public DataListTree<T> clone() {
        return (DataListTree<T>) this.deepClone();
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

    public static DataListTree fromChars(String chars) {
        return Base.fromChars(chars, DataListTree.class);
    }
    
}
