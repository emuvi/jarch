package br.com.pointel.jarch.data;
import java.io.Serializable;
import java.util.Objects;
import com.google.gson.Gson;
import br.com.pointel.jarch.flow.FixVals;

public class OrdName implements FixVals, Serializable {

    public Integer ord;
    public String name;

    public OrdName() {
    }

    public OrdName(String name) {
        this.name = name;
    }

    public OrdName(Integer ord, String name) {
        this.ord = ord;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof OrdName)) {
            return false;
        }
        OrdName ordName = (OrdName) o;
        return Objects.equals(ord, ordName.ord) && Objects.equals(name, ordName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ord, name);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Match fromString(String json) {
        return new Gson().fromJson(json, Match.class);
    }

}
