package br.com.pointel.jarch.data;

import java.util.ArrayList;
import java.util.Objects;
import com.google.gson.Gson;
import br.com.pointel.jarch.mage.WizChars;

public class Bases extends ArrayList<DataWays> {

    public DataWays getFromName(String name) {
        for (var dataWay : this) {
            if (Objects.equals(dataWay.getName(), name)) {
                return dataWay;
            }
        }
        throw new RuntimeException("Did not found a DataWay with this name: '" + name + "'.");
    }

    public void fixDefaults() throws Exception {
        for (var dataWay : this) {
            dataWay.fixNullsAndEnvs();
        }
        this.removeIf(entry -> WizChars.isEmpty(entry.getName()));
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Bases fromString(String json) {
        return new Gson().fromJson(json, Bases.class);
    }

}
