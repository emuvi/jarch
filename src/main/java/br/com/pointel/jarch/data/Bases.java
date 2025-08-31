package br.com.pointel.jarch.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import br.com.pointel.jarch.flow.FixVals;
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
    public String toString() {
        return this.toChars();
    }

    public static Bases fromChars(String chars) {
        return Data.fromChars(chars, Bases.class);
    }

}
