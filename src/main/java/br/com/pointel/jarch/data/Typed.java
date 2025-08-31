package br.com.pointel.jarch.data;

import java.util.Objects;

public class Typed implements Data {

    public String name;
    public Nature type;
    public String alias;

    public Typed() {
    }

    public Typed(String name) {
        this.name = name;
    }

    public Typed(String name, Nature type) {
        this.name = name;
        this.type = type;
    }

    public Typed(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    public Typed(String name, Nature type, String alias) {
        this.name = name;
        this.type = type;
        this.alias = alias;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Typed)) {
            return false;
        }
        Typed typed = (Typed) o;
        return Objects.equals(name, typed.name) && Objects.equals(type, typed.type) && Objects.equals(alias, typed.alias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, alias);
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static Valued fromChars(String chars) {
        return Data.fromChars(chars, Valued.class);
    }
    
}
