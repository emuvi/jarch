package br.com.pointel.jarch.data;

import java.util.List;
import com.google.gson.Gson;

public class Insert implements FixVals {

    public Head head;
    public List<Valued> valueds;
    public ToGetID toGetID;

    public Insert() {}

    public Insert(Head head) {
        this.head = head;
    }

    public Insert(Head head, List<Valued> valueds) {
        this.head = head;
        this.valueds = valueds;
    }

    public Insert(Head head, List<Valued> valueds, ToGetID toGetID) {
        this.head = head;
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
