package br.com.pointel.jarch.data;

import java.util.ArrayList;
import java.util.Objects;
import br.com.pointel.jarch.mage.WizChars;

public class Bases extends ArrayList<DataWays> implements Data {

    public Bases() {
    }

    public DataWays getFromName(String name) {
        for (var dataWay : this) {
            if (Objects.equals(dataWay.getName(), name)) {
                return dataWay;
            }
        }
        return null;
    }

    @Override
    public void fixDefaults() throws Exception {
        for (var dataWay : this) {
            dataWay.fixNullsAndEnvs();
        }
        this.removeIf(entry -> WizChars.isEmpty(entry.getName()));
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
        return Data.fromChars(chars, Bases.class);
    }

}
