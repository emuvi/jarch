package br.com.pointel.jarch.data;

import java.util.Objects;

public class Linked implements Data {

    public String name;
    public String upon;

    public Linked() {
    }

    public Linked(String name) {
        this.name = name;
    }

    public Linked(String name, String upon) {
        this.name = name;
        this.upon = upon;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Linked)) {
            return false;
        }
        Linked linked = (Linked) o;
        return Objects.equals(name, linked.name) && Objects.equals(upon, linked.upon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, upon);
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static Linked fromChars(String chars) {
        return Data.fromChars(chars, Linked.class);
    }

}
