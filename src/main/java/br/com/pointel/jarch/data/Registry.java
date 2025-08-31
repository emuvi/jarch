package br.com.pointel.jarch.data;

import java.io.Serializable;
import java.util.Objects;
import com.google.gson.Gson;
import br.com.pointel.jarch.flow.FixVals;

public class Registry implements FixVals, Serializable {

    public String base;
    public TableHead tableHead;

    public Registry() {}

    public Registry(String base) {
        this.base = base;
    }

    public Registry(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    public Registry(String base, TableHead tableHead) {
        this.base = base;
        this.tableHead = tableHead;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Registry)) {
            return false;
        }
        Registry registry = (Registry) o;
        return Objects.equals(base, registry.base)
                        && Objects.equals(tableHead, registry.tableHead);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, tableHead);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Registry fromString(String json) {
        return new Gson().fromJson(json, Registry.class);
    }

}
