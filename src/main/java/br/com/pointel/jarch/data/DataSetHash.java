package br.com.pointel.jarch.data;

import java.util.HashSet;

public class DataSetHash<T> extends HashSet<T> implements Data {

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

    public static DataSetHash fromChars(String chars) {
        return Data.fromChars(chars, DataSetHash.class);
    }
    
}
