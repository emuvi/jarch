package br.com.pointel.jarch.data;

import com.google.gson.Gson;

public class Named implements FixVals {
    public String name;
    public Nature type;

    public Named() {}

    public Named(String name) {
        this.name = name;
    }

    public Named(String name, Nature type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Valued fromString(String json) {
        return new Gson().fromJson(json, Valued.class);
    }
}
