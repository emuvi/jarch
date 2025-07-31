package br.com.pointel.jarch.data;

import java.util.Objects;
import com.google.gson.Gson;
import br.com.pointel.jarch.flow.FixVals;

public class Linked implements FixVals {
    public String name;
    public String upon;

    public Linked() {}

    public Linked(String name) {
        this.name = name;
    }

    public Linked(String name, String upon) {
        this.name = name;
        this.upon = upon;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Linked)) {
            return false;
        }
        Linked linked = (Linked) o;
        return Objects.equals(name, linked.name)
                        && Objects.equals(upon, linked.upon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, upon);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Linked fromString(String json) {
        return new Gson().fromJson(json, Linked.class);
    }

}
