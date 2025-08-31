package br.com.pointel.jarch.data;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Valued)) {
            return false;
        }
        Valued valued = (Valued) o;
        return Objects.equals(name, valued.name) && Objects.equals(type, valued.type) && Objects.equals(data, valued.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, data);
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static Valued fromChars(String chars) {
        return Data.fromChars(chars, Valued.class);
    }

}
