package br.com.pointel.jarch.data;

import java.io.Serializable;
import java.util.Objects;
import br.com.pointel.jarch.flow.FixVals;

public class ToUpdate implements Data {

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
        ToUpdate toUpdate = (ToUpdate) o;
        return Objects.equals(base, toUpdate.base) && Objects.equals(update, toUpdate.update);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, update);
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static ToUpdate fromChars(String chars) {
        return Data.fromChars(chars, ToUpdate.class);
    }

}
