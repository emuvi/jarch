package br.com.pointel.jarch.data;

import java.util.ArrayList;
import com.google.gson.Gson;

public class Heads extends ArrayList<TableHead> {

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Heads fromString(String json) {
        return new Gson().fromJson(json, Heads.class);
    }

}
