package br.com.pointel.jarch.data;

import java.util.HashMap;

public class DataMapHash<K, V> extends HashMap<K, V> implements Data {

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

    public static DataMapHash fromChars(String chars) {
        return Data.fromChars(chars, DataMapHash.class);
    }

}
