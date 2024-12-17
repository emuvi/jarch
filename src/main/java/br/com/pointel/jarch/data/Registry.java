package br.com.pointel.jarch.data;

import java.util.Objects;
import com.google.gson.Gson;

public class Registry {
    
    public final String base;
    public final Head head;

    public Registry(String base) {
        this.base = base;
        this.head = null;
    }

    public Registry(Head head) {
        this.base = null;
        this.head = head;
    }

    public Registry(String base, Head registry) {
        this.base = base;
        this.head = registry;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Registry)) {
            return false;
        }
        Registry registier = (Registry) o;
        return Objects.equals(base, registier.base) && Objects.equals(head,
                        registier.head);
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
