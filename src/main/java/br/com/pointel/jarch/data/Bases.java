package br.com.pointel.jarch.data;

import java.util.Objects;

public class Bases extends DataListArray<BasedWays> implements Data {

    public Bases() {
    }

    public BasedWays getFromName(String name) {
        for (var dataWay : this) {
            if (Objects.equals(dataWay.getName(), name)) {
                return dataWay;
            }
        }
        return null;
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

    public static Bases fromChars(String chars) {
        return Base.fromChars(chars, Bases.class);
    }

}
