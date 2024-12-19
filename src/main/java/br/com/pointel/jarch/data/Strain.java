package br.com.pointel.jarch.data;

import java.util.Objects;
import com.google.gson.Gson;

public class Strain implements FixVals {

    public String restrict;
    public String modify;
    public String include;

    public Strain() {}

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
        return Objects.equals(restrict, strain.restrict)
                        && Objects.equals(modify, strain.modify)
                        && Objects.equals(include, strain.include);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restrict, modify, include);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Strain fromString(String json) {
        return new Gson().fromJson(json, Strain.class);
    }

}
