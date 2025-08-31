package br.com.pointel.jarch.data;

import java.util.Objects;

public class NamedChars implements Data {

    public String name;
    public String chars;

    public NamedChars() {}

    public NamedChars(String name) {
        this.name = name;
    }

    public NamedChars(String name, String chars) {
        this.name = name;
        this.chars = chars;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof NamedChars)) {
            return false;
        }
        NamedChars namedChars = (NamedChars) o;
        return Objects.equals(name, namedChars.name) && Objects.equals(chars, namedChars.chars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, chars);
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static NamedChars fromChars(String chars) {
        return Data.fromChars(chars, NamedChars.class);
    }

}
