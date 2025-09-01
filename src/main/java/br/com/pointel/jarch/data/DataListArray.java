package br.com.pointel.jarch.data;

import java.util.ArrayList;

public class DataListArray<T> extends ArrayList<T> implements Data {

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

    public static DataListArray fromChars(String chars) {
        return Data.fromChars(chars, DataListArray.class);
    }
    
}
