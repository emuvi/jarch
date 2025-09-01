package br.com.pointel.jarch.data;

import java.util.TreeMap;

public class DataMapTree<K, V> extends TreeMap<K, V> implements Data {

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
        return Data.fromChars(chars, DataMapTree.class);
    }

}
