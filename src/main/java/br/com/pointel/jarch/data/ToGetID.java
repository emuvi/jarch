package br.com.pointel.jarch.data;

import java.util.Objects;
import com.google.gson.Gson;

public class ToGetID {

    public String name;
    public Valued filter;

    public ToGetID() {}

    public ToGetID(String name) {
        this.name = name;
    }

    public ToGetID(String name, Valued filter) {
        this.name = name;
        this.filter = filter;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ToGetID)) {
            return false;
        }
        ToGetID toGetID = (ToGetID) o;
        return Objects.equals(name, toGetID.name)
                        && Objects.equals(filter, toGetID.filter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, filter);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static ToGetID fromString(String json) {
        return new Gson().fromJson(json, ToGetID.class);
    }

}
