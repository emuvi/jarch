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

    public boolean hasName() {
        return this.name != null;
    }

    public boolean hasDesc() {
        return this.desc != null;
    }

    public Order withName(String name) {
        this.name = name;
        return this;
    }

    public Order withNoName() {
        this.name = null;
        return this;
    }

    public Order withDesc(Boolean desc) {
        this.desc = desc;
        return this;
    }

    public Order withNoDesc() {
        this.desc = null;
        return this;
    }

    public Order uponName(String name) {
        var clone = this.clone();
        clone.name = name;
        return clone;
    }

    public Order uponNoName() {
        var clone = this.clone();
        clone.name = null;
        return clone;
    }

    public Order uponDesc(Boolean desc) {
        var clone = this.clone();
        clone.desc = desc;
        return clone;
    }

    public Order uponNoDesc() {
        var clone = this.clone();
        clone.desc = null;
        return clone;
    }

    @Override
    public Order clone() {
        return (Order) this.deepClone();
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
        return Base.fromChars(chars, Order.class);
    }

}
