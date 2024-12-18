package br.com.pointel.jarch.data;

import java.util.List;
import com.google.gson.Gson;

public class Delete implements FixVals {

    public Head head;
    public List<Filter> filters;

    public Delete() {}

    public Delete(Head head, List<Filter> filters) {
        this.head = head;
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
