package br.com.pointel.jarch.data;

import java.io.Serializable;
import java.util.Objects;
import com.google.gson.Gson;
import br.com.pointel.jarch.flow.FixVals;

public class ToDelete implements FixVals, Serializable {

    public String base;
    public Delete delete;

    public ToDelete() {}

    public ToDelete(String base) {
        this.base = base;
    }

    public ToDelete(Delete delete) {
        this.delete = delete;
    }

    public ToDelete(String base, Delete delete) {
        this.base = base;
        this.delete = delete;
    }

    public Registry getRegistry() {
        return new Registry(base, delete.tableHead);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ToDelete)) {
            return false;
        }
        ToDelete regNew = (ToDelete) o;
        return Objects.equals(base, regNew.base)
                        && Objects.equals(delete, regNew.delete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, delete);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static ToDelete fromString(String json) {
        return new Gson().fromJson(json, ToDelete.class);
    }

}
