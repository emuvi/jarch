package br.com.pointel.jarch.data;

import java.util.List;
import com.google.gson.Gson;

public class Delete implements FixVals {
    public Registry registier;
    public List<Filter> filters;

    public Delete() {}

    public Delete(Registry registier, List<Filter> filters) {
        this.registier = registier;
        this.filters = filters;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Delete fromString(String json) {
        return new Gson().fromJson(json, Delete.class);
    }
}
