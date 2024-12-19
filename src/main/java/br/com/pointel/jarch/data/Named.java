package br.com.pointel.jarch.data;

import java.util.Objects;
import com.google.gson.Gson;
import br.com.pointel.jarch.flow.FixVals;

public class Named implements FixVals {

    public String name;
    public Nature type;

    public Named() {}

    public Named(String name) {
        this.name = name;
    }

    public Named(Nature type) {
        this.type = type;
    }

    public Named(String name, Nature type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Named)) {
            return false;
        }
        Named named = (Named) o;
        return Objects.equals(name, named.name)
                        && Objects.equals(type, named.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Valued fromString(String json) {
        return new Gson().fromJson(json, Valued.class);
    }

}
