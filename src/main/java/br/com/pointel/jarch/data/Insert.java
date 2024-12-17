package br.com.pointel.jarch.data;

import java.util.List;
import com.google.gson.Gson;

public class Insert implements FixVals {
    
    public Registry registry;
    public List<Valued> valueds;
    public ToGetID toGetID;

    public Insert() {}

    public Insert(Registry registry) {
        this.registry = registry;
    }

    public Insert(Registry registry, List<Valued> valueds) {
        this.registry = registry;
        this.valueds = valueds;
    }

    public Insert(Registry registry, List<Valued> valueds, ToGetID toGetID) {
        this.registry = registry;
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
