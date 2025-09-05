package br.com.pointel.jarch.data;

public class Valued implements Data {

    public String name;
    public Nature type;
    public Object value;

    public Valued() {}

    public Valued(String name) {
        this.name = name;
    }

    public Valued(String name, Nature type) {
        this.name = name;
        this.type = type;
    }

    public Valued(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public Valued(String name, Nature type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
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
