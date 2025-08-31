package br.com.pointel.jarch.data;

import java.util.Objects;

public class ToGetID implements Data {

    public String name;
    public Valued filter;

    public ToGetID() {}

    public ToGetID(String name) {
        this.name = name;
    }

    public ToGetID(Valued filter) {
        this.filter = filter;
    }

    public ToGetID(String name, Valued filter) {
        this.name = name;
        this.filter = filter;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ToGetID)) {
            return false;
        }
        ToGetID toGetID = (ToGetID) o;
        return Objects.equals(name, toGetID.name) && Objects.equals(filter, toGetID.filter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, filter);
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static ToGetID fromChars(String chars) {
        return Data.fromChars(chars, ToGetID.class);
    }

}
