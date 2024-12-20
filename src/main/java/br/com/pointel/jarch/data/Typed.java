package br.com.pointel.jarch.data;

import java.util.Objects;
import com.google.gson.Gson;
import br.com.pointel.jarch.flow.FixVals;

public class Typed implements FixVals {

    public String name;
    public Nature type;
    public String alias;

    public Typed() {}

    public Typed(String name) {
        this.name = name;
    }

    public Typed(String name, Nature type) {
        this.name = name;
        this.type = type;
    }

    public Typed(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    public Typed(String name, Nature type, String alias) {
        this.name = name;
        this.type = type;
        this.alias = alias;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Typed)) {
            return false;
        }
        Typed typed = (Typed) o;
        return Objects.equals(name, typed.name)
                        && Objects.equals(type, typed.type)
                        && Objects.equals(alias, typed.alias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, alias);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Valued fromString(String json) {
        return new Gson().fromJson(json, Valued.class);
    }
    
}
