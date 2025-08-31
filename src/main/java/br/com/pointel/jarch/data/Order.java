package br.com.pointel.jarch.data;

import java.io.Serializable;
import java.util.Objects;
import com.google.gson.Gson;
import br.com.pointel.jarch.flow.FixVals;

public class Order implements FixVals, Serializable {

    public String name;
    public Boolean desc;

    public Order() {}

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
        return Objects.equals(name, order.name)
                        && Objects.equals(desc, order.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, desc);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Order fromString(String json) {
        return new Gson().fromJson(json, Order.class);
    }

}
