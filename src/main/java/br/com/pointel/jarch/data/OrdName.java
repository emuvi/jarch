package br.com.pointel.jarch.data;

public class OrdName implements Data {

    public Integer ord;
    public String name;

    public OrdName() {
    }

    public OrdName(String name) {
        this.name = name;
    }

    public OrdName(Integer ord, String name) {
        this.ord = ord;
        this.name = name;
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

    public static OrdName fromChars(String chars) {
        return Base.fromChars(chars, OrdName.class);
    }

}
