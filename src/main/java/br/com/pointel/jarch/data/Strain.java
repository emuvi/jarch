package br.com.pointel.jarch.data;

import java.util.Objects;

public class Strain implements Data {

    public String restrict;
    public String modify;
    public String include;

    public Strain() {
    }

    public Strain(String restrict) {
        this.restrict = restrict;
    }

    public Strain(String restrict, String modify) {
        this.restrict = restrict;
        this.modify = modify;
    }

    public Strain(String restrict, String modify, String include) {
        this.restrict = restrict;
        this.modify = modify;
        this.include = include;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Strain)) {
            return false;
        }
        Strain strain = (Strain) o;
        return Objects.equals(restrict, strain.restrict) && Objects.equals(modify, strain.modify) && Objects.equals(include, strain.include);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restrict, modify, include);
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static Strain fromChars(String chars) {
        return Data.fromChars(chars, Strain.class);
    }

}
