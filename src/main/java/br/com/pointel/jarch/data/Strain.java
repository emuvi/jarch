package br.com.pointel.jarch.data;

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

    public static Strain fromChars(String chars) {
        return Base.fromChars(chars, Strain.class);
    }

}
