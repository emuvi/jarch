package br.com.pointel.jarch.data;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Order)) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(name, order.name) && Objects.equals(desc, order.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, desc);
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static Order fromChars(String chars) {
        return Data.fromChars(chars, Order.class);
    }

}
