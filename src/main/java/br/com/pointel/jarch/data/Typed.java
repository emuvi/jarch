package br.com.pointel.jarch.data;

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
    public boolean equals(Object that) {
        return this.deepEquals(that);
    }

    @Override
    public int hashCode() {
        return this.deepHash();
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static Valued fromChars(String chars) {
        return Base.fromChars(chars, Valued.class);
    }
    
}
