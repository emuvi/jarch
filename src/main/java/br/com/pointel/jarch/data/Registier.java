package br.com.pointel.jarch.data;

import java.util.Objects;
import com.google.gson.Gson;

public class Registier {
    public String base;
    public Head registry;

    public Registier() {}

    public Registier(String base) {
        this.base = base;
    }

    public Registier(Head registry) {
        this.registry = registry;
    }

    public Registier(String base, Head registry) {
        this.base = base;
        this.registry = registry;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Registier)) {
            return false;
        }
        Registier registier = (Registier) o;
        return Objects.equals(base, registier.base) && Objects.equals(registry,
                        registier.registry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, registry);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Registier fromString(String json) {
        return new Gson().fromJson(json, Registier.class);
    }
}
