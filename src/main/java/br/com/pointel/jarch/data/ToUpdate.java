package br.com.pointel.jarch.data;

import java.util.Objects;
import com.google.gson.Gson;
import br.com.pointel.jarch.flow.FixVals;

public class ToUpdate implements FixVals {

    public String base;
    public Update update;

    public ToUpdate() {}

    public ToUpdate(String base) {
        this.base = base;
    }

    public ToUpdate(Update update) {
        this.update = update;
    }

    public ToUpdate(String base, Update update) {
        this.base = base;
        this.update = update;
    }

    public Registry getRegistry() {
        return new Registry(base, update.tableHead);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ToUpdate)) {
            return false;
        }
        ToUpdate regNew = (ToUpdate) o;
        return Objects.equals(base, regNew.base)
                        && Objects.equals(update, regNew.update);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, update);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static ToUpdate fromString(String json) {
        return new Gson().fromJson(json, ToUpdate.class);
    }

}
