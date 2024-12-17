package br.com.pointel.jarch.data;

import java.util.List;
import com.google.gson.Gson;

public class Insert implements FixVals {
    public Registier registier;
    public List<Valued> valueds;
    public ToGetID toGetID;

    public Insert() {}

    public Insert(Registier registier) {
        this.registier = registier;
    }

    public Insert(Registier registier, List<Valued> valueds) {
        this.registier = registier;
        this.valueds = valueds;
    }

    public Insert(Registier registier, List<Valued> valueds, ToGetID toGetID) {
        this.registier = registier;
        this.valueds = valueds;
        this.toGetID = toGetID;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Insert fromString(String json) {
        return new Gson().fromJson(json, Insert.class);
    }
}
