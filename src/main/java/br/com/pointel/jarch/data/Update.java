package br.com.pointel.jarch.data;

import java.util.List;
import com.google.gson.Gson;

public class Update implements FixVals {
    public Registry registier;
    public List<Valued> valueds;
    public List<Filter> filters;
    public Integer limit;

    public Update() {}

    public Update(Registry registier) {
        this.registier = registier;
    }

    public Update(Registry registier, List<Valued> valueds) {
        this.registier = registier;
        this.valueds = valueds;
    }

    public Update(Registry registier, List<Valued> valueds, List<Filter> filters) {
        this.registier = registier;
        this.valueds = valueds;
        this.filters = filters;
    }

    public Update(Registry registier, List<Valued> valueds, List<Filter> filters,
                    Integer limit) {
        this.registier = registier;
        this.valueds = valueds;
        this.filters = filters;
        this.limit = limit;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Update fromString(String json) {
        return new Gson().fromJson(json, Update.class);
    }
}
