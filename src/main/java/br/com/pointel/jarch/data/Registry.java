package br.com.pointel.jarch.data;

import java.util.Objects;
import com.google.gson.Gson;

public class Registry implements FixVals {

    public String base;
    public Head head;

    public Registry() {}

    public Registry(String base) {
        this.base = base;
    }

    public Registry(Head head) {
        this.head = head;
    }

    public Registry(String base, Head head) {
        this.base = base;
        this.head = head;
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
                        && Objects.equals(head, registry.head);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, head);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Registry fromString(String json) {
        return new Gson().fromJson(json, Registry.class);
    }

}
