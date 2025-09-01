package br.com.pointel.jarch.data;

import java.util.LinkedHashMap;

public class DataMapLinked<K, V> extends LinkedHashMap<K, V> implements Data {

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

    public static DataMapLinked fromChars(String chars) {
        return Data.fromChars(chars, DataMapLinked.class);
    }

}
