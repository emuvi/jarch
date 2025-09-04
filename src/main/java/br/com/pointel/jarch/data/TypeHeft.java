package br.com.pointel.jarch.data;

public class TypeHeft implements Firm {

    public final Class<?> type;
    public final int heft;

    public TypeHeft(Class<?> type, int heft) {
        this.type = type;
        this.heft = heft;
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

    public static TypeHeft fromChars(String chars) {
        return Firm.fromChars(chars, TypeHeft.class);
    }

    public static TypeHeft of(Class<?> type, int heft) {
        return new TypeHeft(type, heft);
    }

}
