package br.com.pointel.jarch.data;

import java.io.Serializable;
import java.util.Objects;
import br.com.pointel.jarch.flow.FixVals;

public class ToDelete implements Data, FixVals, Serializable {

    public String base;
    public Delete delete;

    public ToDelete() {
    }

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
        ToDelete toDelete = (ToDelete) o;
        return Objects.equals(base, toDelete.base) && Objects.equals(delete, toDelete.delete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, delete);
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static ToDelete fromChars(String chars) {
        return Data.fromChars(chars, ToDelete.class);
    }

}
