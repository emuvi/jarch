package br.com.pointel.jarch.data;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof OrdName)) {
            return false;
        }
        OrdName ordName = (OrdName) o;
        return Objects.equals(ord, ordName.ord) && Objects.equals(name, ordName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ord, name);
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static OrdName fromChars(String chars) {
        return Data.fromChars(chars, OrdName.class);
    }

}
