package br.com.pointel.jarch.data;

import java.util.TreeMap;

public class DataMapTree<K, V> extends TreeMap<K, V> implements Data {
    
    @Override
    public DataMapTree<K, V> clone() {
        return (DataMapTree<K, V>) this.deepClone();
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

    public static DataMapTree fromChars(String chars) {
        return Base.fromChars(chars, DataMapTree.class);
    }

}
