package br.com.pointel.jarch.data;

import java.io.Serializable;
import java.util.Objects;
import com.google.gson.Gson;
import br.com.pointel.jarch.flow.FixVals;

public class NamedChars implements FixVals, Serializable {

    public String name;
    public String chars;

    public NamedChars() {}

    public NamedChars(String name) {
        this.name = name;
    }

    public NamedChars(String name, String chars) {
        this.name = name;
        this.chars = chars;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof NamedChars)) {
            return false;
        }
        NamedChars named = (NamedChars) o;
        return Objects.equals(name, named.name)
                        && Objects.equals(chars, named.chars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, chars);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static NamedChars fromString(String json) {
        return new Gson().fromJson(json, NamedChars.class);
    }

}
