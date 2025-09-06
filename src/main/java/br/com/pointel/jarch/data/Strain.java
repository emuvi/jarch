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

    public boolean hasRestrict() {
        return this.restrict != null;
    }

    public boolean hasModify() {
        return this.modify != null;
    }

    public boolean hasInclude() {
        return this.include != null;
    }

    public Strain withRestrict(String restrict) {
        this.restrict = restrict;
        return this;
    }

    public Strain withNoRestrict() {
        this.restrict = null;
        return this;
    }

    public Strain withModify(String modify) {
        this.modify = modify;
        return this;
    }

    public Strain withNoModify() {
        this.modify = null;
        return this;
    }

    public Strain withInclude(String include) {
        this.include = include;
        return this;
    }

    public Strain withNoInclude() {
        this.include = null;
        return this;
    }

    public Strain uponRestrict(String restrict) {
        var clone = this.clone();
        clone.restrict = restrict;
        return clone;
    }

    public Strain uponNoRestrict() {
        var clone = this.clone();
        clone.restrict = null;
        return clone;
    }

    public Strain uponModify(String modify) {
        var clone = this.clone();
        clone.modify = modify;
        return clone;
    }

    public Strain uponNoModify() {
        var clone = this.clone();
        clone.modify = null;
        return clone;
    }

    public Strain uponInclude(String include) {
        var clone = this.clone();
        clone.include = include;
        return clone;
    }

    public Strain uponNoInclude() {
        var clone = this.clone();
        clone.include = null;
        return clone;
    }

    @Override
    public Strain clone() {
        return (Strain) this.deepClone();
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
