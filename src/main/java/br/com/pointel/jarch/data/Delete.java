package br.com.pointel.jarch.data;

import java.util.List;
import com.google.gson.Gson;

public class Delete implements FixVals {

    public Registry registry;
    public List<Filter> filters;

    public Delete() {}

    public Delete(Registry registry, List<Filter> filters) {
        this.registry = registry;
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
