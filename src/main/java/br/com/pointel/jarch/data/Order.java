package br.com.pointel.jarch.data;

public class Order implements Data {

    public String name;
    public Boolean desc;

    public Order() {
    }

    public Order(String name) {
        this.name = name;
    }

    public Order(String name, Boolean desc) {
        this.name = name;
        this.desc = desc;
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

    public static Order fromChars(String chars) {
        return Data.fromChars(chars, Order.class);
    }

}
