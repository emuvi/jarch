package br.com.pointel.jarch.data;

import java.io.Serializable;
import java.util.Objects;
import com.google.gson.Gson;
import br.com.pointel.jarch.flow.FixVals;

public class ToInsert implements FixVals, Serializable {

    public String base;
    public Insert insert;

    public ToInsert() {}

    public ToInsert(String base) {
        this.base = base;
    }

    public ToInsert(Insert insert) {
        this.insert = insert;
    }

    public ToInsert(String base, Insert insert) {
        this.base = base;
        this.insert = insert;
    }

    public Registry getRegistry() {
        return new Registry(base, insert.tableHead);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ToInsert)) {
            return false;
        }
        ToInsert regNew = (ToInsert) o;
        return Objects.equals(base, regNew.base)
                        && Objects.equals(insert, regNew.insert);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, insert);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static ToInsert fromString(String json) {
        return new Gson().fromJson(json, ToInsert.class);
    }

}
