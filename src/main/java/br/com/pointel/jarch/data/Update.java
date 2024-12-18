package br.com.pointel.jarch.data;

import java.util.List;
import com.google.gson.Gson;

public class Update implements FixVals {
    
    public Head head;
    public List<Valued> valueds;
    public List<Filter> filters;
    public Integer limit;

    public Update() {}

    public Update(Head head) {
        this.head = head;
    }

    public Update(Head head, List<Valued> valueds) {
        this.head = head;
        this.valueds = valueds;
    }

    public Update(Head head, List<Valued> valueds, List<Filter> filters) {
        this.head = head;
        this.valueds = valueds;
        this.filters = filters;
    }

    public Update(Head head, List<Valued> valueds, List<Filter> filters,
                    Integer limit) {
        this.head = head;
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
