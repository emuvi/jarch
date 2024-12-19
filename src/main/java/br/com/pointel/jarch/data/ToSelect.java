package br.com.pointel.jarch.data;

import java.util.Objects;
import com.google.gson.Gson;
import br.com.pointel.jarch.flow.FixVals;

public class ToSelect implements FixVals {

    public String base;
    public Select select;

    public ToSelect() {}

    public ToSelect(String base) {
        this.base = base;
    }

    public ToSelect(Select select) {
        this.select = select;
    }

    public ToSelect(String base, Select select) {
        this.base = base;
        this.select = select;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ToSelect)) {
            return false;
        }
        ToSelect regNew = (ToSelect) o;
        return Objects.equals(base, regNew.base)
                        && Objects.equals(select, regNew.select);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, select);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static ToSelect fromString(String json) {
        return new Gson().fromJson(json, ToSelect.class);
    }

}
