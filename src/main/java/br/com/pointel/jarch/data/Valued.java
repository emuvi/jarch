package br.com.pointel.jarch.data;

public class Valued implements Data {

    public String name;
    public Nature type;
    public Object data;

    public Valued() {}

    public Valued(String name) {
        this.name = name;
    }

    public Valued(String name, Nature type) {
        this.name = name;
        this.type = type;
    }

    public Valued(String name, Object data) {
        this.name = name;
        this.data = data;
    }

    public Valued(String name, Nature type, Object data) {
        this.name = name;
        this.type = type;
        this.data = data;
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
        return Data.fromChars(chars, Valued.class);
    }

}
